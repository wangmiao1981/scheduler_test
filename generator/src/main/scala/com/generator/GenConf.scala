package com.generator

import com.typesafe.config._

class GenConf() {

}

object GenConf {
  // Loading application.conf
  val globalConf: Config = ConfigFactory.load("generator-app").getConfig("generator")

  // Load Kafka client
  val topic = globalConf.getConfig("kafka-topic")

  // Load Source name
  val sourceConfig = globalConf.getConfig("source-config")

  val intervalConfig = globalConf.getConfig("timer")

}
