package com.generator

object GeneratorMethod extends Enumeration {
  type GeneratorMethod = Value
  val RandomFile, RandomLive, SeqLive, SeqFile = Value
}

trait Source {
  val sourceName: String
  val sourceURI: String
}

abstract class Generator(genConf: GenConf) extends Logging with Source {

  def this() = this(new GenConf())

  override val sourceName: String

  override val sourceURI: String

  val startTime = System.currentTimeMillis()

  var interval: Int

  def setInterval(value: Int) = {
    interval = value
  }

  def getInterval: Int = interval

  var generateMethod: Enumeration

  def setGenerateMethod(method: Enumeration) = {
    generateMethod = method
  }

  def getGenerateMethod(): Enumeration =  generateMethod

  def initGenerator(): Unit

  def start()

  def stop()

}
