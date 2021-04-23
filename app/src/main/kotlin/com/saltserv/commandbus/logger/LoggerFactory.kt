package com.saltserv.commandbus.logger

interface LoggerFactory {

    fun makeLogger(clazz: Class<*>): Logger
}