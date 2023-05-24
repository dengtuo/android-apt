package com.dengtuo.apt.processor

import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements


class ClassCreatorFactory(
    private val elementUtils: Elements,
    private val classElement: TypeElement,
) {
    //可以使用字符串方式进创建，这里使用面向对象的方式
    private var mBindClassName: String? = null
    private var mPackageName: String? = null
    private val mVariableElementMap = mutableMapOf<Int, VariableElement>()

    init {
        val packageElement = elementUtils.getPackageOf(classElement)
        mPackageName = packageElement.qualifiedName.toString()
        val className = classElement.simpleName.toString()
        mBindClassName = className + "_ViewBinding"
    }

    fun putElement(id: Int, element: VariableElement) {
        mVariableElementMap[id] = element
    }

    fun getClassFullName(): String {
        return "$mPackageName.$mBindClassName"
    }

    fun getTypeElement(): TypeElement {
        return classElement
    }

    fun generateJavaCode(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """/**
 * Auto Created by apt
*/
"""
        )
        stringBuilder.append("package ").append(mPackageName).append(";\n")
        stringBuilder.append('\n')
        stringBuilder.append("public class ").append(mBindClassName)
        stringBuilder.append(" {\n")
        generateBindViewMethods(stringBuilder)

        stringBuilder.append('\n')
        stringBuilder.append("}\n")
        return stringBuilder.toString()
    }

    private fun generateBindViewMethods(stringBuilder: java.lang.StringBuilder) {
        stringBuilder.append("\tpublic void bindView(")
        stringBuilder.append(getTypeElement().qualifiedName)
        stringBuilder.append(" owner) {\n")
        for (id in mVariableElementMap.keys) {
            val variableElement = mVariableElementMap[id]
            val viewName = variableElement!!.simpleName.toString()
            val viewType = variableElement.asType().toString()
            stringBuilder.append("\t\towner.")
            stringBuilder.append(viewName)
            stringBuilder.append(" = ")
            stringBuilder.append("(")
            stringBuilder.append(viewType)
            stringBuilder.append(")(((android.app.Activity)owner).findViewById( ")
            stringBuilder.append(id)
            stringBuilder.append("));\n")
        }
        stringBuilder.append("  }\n")
    }
}