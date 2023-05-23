package com.dengtuo.apt.processor

import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@AutoService(Processor::class)
class BizPrintProcessor : AbstractProcessor() {

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        p0?.messager?.printMessage(Diagnostic.Kind.NOTE, "BizPrintProcessor init")
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        return false
    }
}
