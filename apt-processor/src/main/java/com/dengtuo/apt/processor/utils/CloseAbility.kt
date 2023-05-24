package com.dengtuo.apt.processor.utils

import java.io.Closeable
import java.io.IOException

object CloseAbility {

    @JvmStatic
    fun close(vararg closeables: Closeable?) {
        for (closeable in closeables) {
            if (null != closeable) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}