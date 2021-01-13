package com.rrr.shakemon.utils

import com.google.gson.Gson
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Util class for mock response from the input file data
 * @property json - test response json string read from the input file
 * @property mockObject - function that returns the containing string in Class<T> object form
 */
class MockResponse(path: String) {
    private val RESOURCE_PATH = "../shakemon/src/test/resources/"
    var json: String? = null

    init {
        val reader = InputStreamReader(FileInputStream( RESOURCE_PATH + path))
        json = reader.readText()
        reader.close()
    }

    fun <T> mockObject(classOfT: Class<T>?): T {
        return Gson().fromJson(json, classOfT)
    }
}