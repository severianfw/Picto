package com.severianfw.picto

import org.junit.Assert
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

open class BaseTest {
    fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

    fun <T> any(): T = Mockito.any()

    fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    fun <T> eq(obj: T): T = Mockito.eq<T>(obj)

    inline fun <reified T : Any> anyArgs() = Mockito.any(T::class.java) ?: createInstance<T>()

    inline fun <reified T : Any> createInstance(): T = createInstance(T::class)

    fun <T : Any> createInstance(kClass: KClass<T>): T = castNull()

    @Suppress("UNCHECKED_CAST")
    private fun <T> castNull(): T = null as T

    fun setFinalStatic(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field[null] = newValue
    }

    fun <T> assertEmpty(value: T) {
        Assert.assertEquals(value, "")
    }
}