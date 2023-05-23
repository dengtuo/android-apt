package com.dengtuo.apt.processor

import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic


@AutoService(Processor::class)
class BindViewProcessor : AbstractProcessor() {

    private var mElementUtils: Elements? = null

    private val mClassCreatorFactoryMap = mutableMapOf<String, ClassCreatorFactory>()

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        println("BizPrintProcessor init")
        p0?.messager?.printMessage(Diagnostic.Kind.NOTE, "BizPrintProcessor init")
        mElementUtils = p0?.elementUtils
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        return false
    }
}
