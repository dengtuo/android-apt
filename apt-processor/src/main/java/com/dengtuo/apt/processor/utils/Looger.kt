package com.dengtuo.apt.processor.utils

import javax.annotation.processing.Messager
import javax.tools.Diagnostic

class Looger(private val messager: Messager) {

    companion object {
        private const val PREFIX_OF_LOGGER = "APT::Processor"
    }

    fun info(info: String?) {
        messager.printMessage(Diagnostic.Kind.NOTE, PREFIX_OF_LOGGER + info)
    }

    fun error(error: String?) {
        if (!error.isNullOrEmpty()) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                PREFIX_OF_LOGGER + "An exception is encountered, [" + error + "]"
            )
        }
    }

    fun error(error: Throwable?) {
        if (null != error) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                PREFIX_OF_LOGGER + "An exception is encountered, [" + error.message + "]" + "\n" + formatStackTrace(
                    error.stackTrace
                )
            )
        }
    }

    fun warning(warning: String?) {
        if (!warning.isNullOrEmpty()) {
            messager.printMessage(Diagnostic.Kind.WARNING, PREFIX_OF_LOGGER + warning)
        }
    }

    private fun formatStackTrace(stackTrace: Array<StackTraceElement>): String {
        val sb = StringBuilder()
        for (element in stackTrace) {
            sb.append("    at ").append(element.toString())
            sb.append("\n")
        }
        return sb.toString()
    }
}