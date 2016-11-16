package com.generator

import org.slf4j.LoggerFactory
import org.apache.log4j.{Level, LogManager, PropertyConfigurator}

object Application extends App {
  val logger = LoggerFactory.getLogger(this.getClass)
  val fileName = "/Users/mwang/Downloads/tweetsMixSample"
  /*
  for (line <- Source.fromFile(fileName).getLines()) {
    println("========================start=====================")
    println(line)
    println("========================end=======================")
  }
  */
  val generator = new RandomGenerator
  generator.start()
//  generator.stop()
}