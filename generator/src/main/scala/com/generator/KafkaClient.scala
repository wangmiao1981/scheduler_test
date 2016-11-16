package com.generator

import cakesolutions.kafka.{KafkaProducer, KafkaProducerRecord}
import com.typesafe.config.Config
import org.apache.kafka.common.serialization.StringSerializer
import com.generator.JsonSerializer

class KafkaClient(config: Config) {

  private val topic = config.getString("topic")

  // define producer instance

  // define send function

}
