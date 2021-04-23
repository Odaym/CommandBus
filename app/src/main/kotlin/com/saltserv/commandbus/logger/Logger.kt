package com.saltserv.commandbus.logger

interface Logger {

    fun debug(message: String, throwable: Throwable? = null)

    fun warning(message: String, throwable: Throwable? = null)

    fun error(message: String, throwable: Throwable? = null)

    fun reportException(throwable: Throwable)
}