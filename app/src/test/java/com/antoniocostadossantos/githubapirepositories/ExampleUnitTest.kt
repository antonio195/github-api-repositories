package com.antoniocostadossantos.githubapirepositories

import com.antoniocostadossantos.githubapirepositories.validation.ModelValidation
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {

    private val modelValidation = ModelValidation()

    @Test
    fun languageIsNotEmpty() {
        val test = modelValidation.checkLanguage("Kotlin")
//        assertEquals(false, test)
        assertEquals(false, test)
    }

    @Test
    fun languageIsEmpty() {
        val test = modelValidation.checkLanguage("")
        assertEquals(true, test)
    }
}