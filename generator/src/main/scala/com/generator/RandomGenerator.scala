package com.generator

import java.util.Timer
import java.util.TimerTask

import play.api.libs.json._

import scala.io.Source
import scala.util.Random

class RandomGenerator extends Generator {

  val t: Timer = new Timer()

  var task: TimerTask = _

  /*
   * Simple random generator: load 1000 tweets as seed;
   * Run a periodical task of generating random index within the range;
   * Send the tweet object to a Kafka cluster
   */
  val objBuffer: Array[JsValue] = new Array[JsValue](1000)

  private def initObjBuffer(): Unit = {
    val sourceFile = Source.fromFile(sourceURI)
    val line = sourceFile.getLines()
    try {
      for (i <- 0 to 999) {
        objBuffer(i) = Json.parse(line.next())
      }
    } catch {
      case e: Exception =>
        logError(s"initiObjBuffer Error")
    } finally {
      sourceFile.close()
    }
  }

  override def start(): Unit = {
    initObjBuffer()
    task = new TimerTask {
      override def run(): Unit = generateTweets()
    }
    t.schedule(task, 1000L, 1000L)
  }

  override def stop(): Unit = task.cancel()

  override val sourceName: String = "Random-Generator"

  override val sourceURI: String = "/Users/mwang/Downloads/tweetsMixSample"

  def generateTweets() = {
    val index = new Random().nextInt(999)
    require(index >=0 && index <= 999, "The random index is out of range.")
    objBuffer(index)
  }
}
