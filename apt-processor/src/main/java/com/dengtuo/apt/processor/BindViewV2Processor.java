package com.dengtuo.apt.processor;

import com.dengtuo.apt.annotation.BindView;
import com.dengtuo.apt.processor.utils.CloseAbility;
import com.dengtuo.apt.processor.utils.Looger;
import com.google.auto.service.AutoService;

import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class BindViewV2Processor extends AbstractProcessor {
    private BindViewProcessor mBindViewProcessor = new BindViewProcessor();
    private Looger mLooger = null;
    private Messager messager = null;
    private final HashMap<String, ClassCreatorFactory> mClassCreatorFactoryHashMap = new HashMap<>();
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        mLooger = new Looger(messager);
        mLooger.info("BizPrintProcessor Java init");
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mLooger.info("processing....");
        mClassCreatorFactoryHashMap.clear();
        //获取不同的Activity
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        //分类
        elements.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                VariableElement variableElement = (VariableElement) element;
                TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
                String className = typeElement.getQualifiedName().toString();
                mLooger.info("processing:className:" + className);
                ClassCreatorFactory factory = mClassCreatorFactoryHashMap.get(className);
                if (factory == null) {
                    factory = new ClassCreatorFactory(mElementUtils, typeElement);
                    mClassCreatorFactoryHashMap.put(className, factory);
                }
                BindView bindView = variableElement.getAnnotation(BindView.class);
                int id = bindView.value();
                factory.putElement(id, variableElement);
            }
        });
        //创建类
        mClassCreatorFactoryHashMap.forEach((s, classCreatorFactory) -> {
            Writer writer = null;
            try {
                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(classCreatorFactory.getClassFullName(), classCreatorFactory.getTypeElement());
                writer = javaFileObject.openWriter();
                String code = classCreatorFactory.generateJavaCode();
                mLooger.info(code);
                writer.write(code);
                writer.flush();
            } catch (Exception e) {

            } finally {
                CloseAbility.close(writer);
            }
        });
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportTypes = new HashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}
