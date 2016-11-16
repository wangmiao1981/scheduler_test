package com.generator

import org.apache.spark.sql.execution.streaming.FileStreamSource.Timestamp
import play.api.libs.json._

case class SubmitSample(timestamp: Timestamp, message: JsObject) {
  require(timestamp > 0)
}

object SubmitSample {

}