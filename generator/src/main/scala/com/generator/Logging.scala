package com.generator

import org.apache.log4j.{Level, LogManager, PropertyConfigurator}
import org.slf4j.{Logger, LoggerFactory}
import org.slf4j.impl.StaticLoggerBinder
import java.nio.file.{Paths, Files}
import java.util.Locale

trait Logging {

  @transient private var log_ : Logger = null

  protected def logName = {
    this.getClass.getName.stripSuffix("$")
  }

  protected def log: Logger = {
    if (log_ == null) {
      LogInit
      log_ = LoggerFactory.getLogger(logName)
    }
    log_
  }

  protected def logInfo(msg: => String) {
    if (log.isInfoEnabled) log.info(msg)
  }

  protected def logDebug(msg: => String) {
    if (log.isDebugEnabled) log.debug(msg)
  }

  protected def logTrace(msg: => String) {
    if (log.isTraceEnabled) log.trace(msg)
  }

  protected def logWarning(msg: => String) {
    if (log.isWarnEnabled) log.warn(msg)
  }

  protected def logError(msg: => String) {
    if (log.isErrorEnabled) log.error(msg)
  }

  protected def logInfo(msg: => String, throwable: Throwable) {
    if (log.isInfoEnabled) log.info(msg, throwable)
  }

  protected def logDebug(msg: => String, throwable: Throwable) {
    if (log.isDebugEnabled) log.debug(msg, throwable)
  }

  protected def logTrace(msg: => String, throwable: Throwable) {
    if (log.isTraceEnabled) log.trace(msg, throwable)
  }

  protected def logWarning(msg: => String, throwable: Throwable) {
    if (log.isWarnEnabled) log.warn(msg, throwable)
  }

  protected def logError(msg: => String, throwable: Throwable) {
    if (log.isErrorEnabled) log.error(msg, throwable)
  }

  protected def isTraceEnabled(): Boolean = {
    log.isTraceEnabled
  }

  private def LogInit: Unit = {
    if (!Logging.initialized) {
      Logging.initLock.synchronized {
        if (!Logging.initialized) {
          LogInitAction
        }
      }
    }
  }

  private def LogInitAction: Unit = {
    val binderClass = StaticLoggerBinder.getSingleton.getLoggerFactoryClassStr
    val usingLog4j12 = "org.slf4j.impl.Log4jLoggerFactory".equals(binderClass)
    if (usingLog4j12) {
      val log4j12Initialized = LogManager.getRootLogger.getAllAppenders.hasMoreElements
      if (!log4j12Initialized) {
        val defaultLogProperty = "generator/src/main/resources/log4j-defaults.properties"
        if (Files.exists(Paths.get(defaultLogProperty))) {
          PropertyConfigurator.configure(defaultLogProperty)
          System.err.println(s"Using default log4j profile: $defaultLogProperty")
        } else {
          System.err.println(s"Cannot load $defaultLogProperty")
        }
      }
      val rootLogger = LogManager.getRootLogger()
      rootLogger.setLevel(Level.WARN)
    }
    Logging.initialized = true
    log
  }

  //TODO: Add interface accepting user input and move this function to wrapper class
  def setLogLevel(l: String): Unit = {
    val upperCase = l.toUpperCase(Locale.ENGLISH)
    require(Logging.VALID_LOG_LEVELS.contains(upperCase),
      s"Input level $l did not match one of the valid:") +
        s" ${Logging.VALID_LOG_LEVELS.mkString(",")}"
    LogManager.getRootLogger().setLevel(Level.toLevel(upperCase))
  }
}

private  object  Logging {
  @volatile private var initialized = false
  val initLock = new Object()
  private val VALID_LOG_LEVELS =
    Set("ALL", "DEBUG", "ERROR", "FATAL", "INFO", "OFF", "TRACE", "WARN")
}
