package com.dengtuo.apt.processor

import com.dengtuo.apt.annotation.BindView
import com.dengtuo.apt.processor.utils.CloseAbility.close
import com.dengtuo.apt.processor.utils.Looger
import com.google.auto.service.AutoService
import java.io.Writer
import java.util.function.Consumer
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements


@AutoService(Processor::class)
class BindViewProcessor : AbstractProcessor() {
    private var mLooger: Looger? = null
    private var messager: Messager? = null
    private val mClassCreatorFactoryHashMap = HashMap<String, ClassCreatorFactory>()
    private var mElementUtils: Elements? = null

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        messager = processingEnvironment.messager
        messager?.let {
            mLooger = Looger(it)
        }
        mLooger?.info("BindViewProcessor Kotlin init")
        mElementUtils = processingEnv.elementUtils
    }

    override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        mLooger?.info("BindViewProcessor processing....")
        mClassCreatorFactoryHashMap.clear()
        //获取不同的Activity
        val elements = roundEnvironment.getElementsAnnotatedWith(
            BindView::class.java
        )
        val elementUtils = mElementUtils ?: return false
        //分类
        elements.forEach(Consumer { element ->
            val variableElement = element as VariableElement
            val typeElement = variableElement.enclosingElement as TypeElement
            val className = typeElement.qualifiedName.toString()
            mLooger?.info("processing:className:$className")
            var factory = mClassCreatorFactoryHashMap[className]
            if (factory == null) {
                factory = ClassCreatorFactory(elementUtils, typeElement)
                mClassCreatorFactoryHashMap[className] = factory
            }
            val bindView = variableElement.getAnnotation(BindView::class.java)
            val id: Int = bindView.value
            factory.putElement(id, variableElement)
        })
        //创建类
        mClassCreatorFactoryHashMap.forEach { (s: String?, classCreatorFactory: ClassCreatorFactory) ->
            var writer: Writer? = null
            try {
                val javaFileObject = processingEnv.filer.createSourceFile(
                    classCreatorFactory.getClassFullName(),
                    classCreatorFactory.getTypeElement()
                )
                writer = javaFileObject.openWriter()
                val code = classCreatorFactory.generateJavaCode()
                mLooger?.info(code)
                writer.write(code)
                writer.flush()
            } catch (e: Exception) {
            } finally {
                close(writer)
            }
        }
        return true
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val supportTypes: MutableSet<String> = HashSet()
        supportTypes.add(BindView::class.java.canonicalName)
        return supportTypes
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }
}