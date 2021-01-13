package com.rrr.shakemon

import java.lang.Exception

/**
 * SDK callback for results to pass to application
 */
interface IServiceCallback {
    fun success(text: String)
    fun error(exception: Exception)
}