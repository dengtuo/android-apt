package com.dengtuo.apt.processor

import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements


class ClassCreatorFactory(
    elementUtils: Elements,
    classElement: TypeElement,
) {

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
}