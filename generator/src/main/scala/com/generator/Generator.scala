package com.generator

/*
 * Generator class defines the interfaces of traffic generator
 * 1). Initialization: Set Source Name, Source URI and Clock;
 * 2). Start: Enters a loop to generate records periodically.
 *     The destination of a generated record is provided by the Sink.
 *     Now, the Sink is a Kafka client, which submits the record to a
 *     Kafka Cluster. The record is in the tuple (timestamp, JsObject).
 * 3). Stop: Stop the generator and release resources
 *
 * For a concreate generator, it inherits the abstract class and implement
 * Source, start(), stop() methods.
 *
 */

trait Source {
  val sourceName: String
  val sourceURI: String
}

abstract class Generator() extends Logging with Source {

  override val sourceName: String

  override val sourceURI: String

  val startTime = System.currentTimeMillis()

//  var interval: Int = GenConf.intervalConfig.getInt("interval")

  def start()

  def stop()

}
