package com.generator

import org.slf4j.LoggerFactory
import scala.io.Source

object Application extends App {
  val logger = LoggerFactory.getLogger(this.getClass)
  val fileName = "/Users/mwang/Downloads/tweetsMixSample"
  for (line <- Source.fromFile(fileName).getLines()) {
    println("========================start=====================")
    println(line)
    println("========================end=======================")
  }
}