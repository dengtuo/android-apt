package com.dengtuo.apt.processor

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic


class BindViewProcessor : AbstractProcessor() {
    private var mElementUtils: Elements? = null
    private var messager:Messager? = null

    private val mClassCreatorFactoryMap = mutableMapOf<String, ClassCreatorFactory>()

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        println("BizPrintProcessor init")
        messager = p0?.messager
        messager?.printMessage(Diagnostic.Kind.NOTE, "BizPrintProcessor Kotlin init")
        mElementUtils = p0?.elementUtils
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        return false
    }

}
