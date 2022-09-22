package service

import constants.ApplicationConstants.FILE_FORMAT
import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec

class FileReaderTest extends AnyFlatSpec {

  implicit val spark:SparkSession = helper.Helper.createSparkSession()
 import spark.implicits._
  "file reader " should "return a dataframe with records " in {

    val outputDF = service.FileReader.fileReader(helper.Helper.CLICK_STREAM_TEST_INPUT_PATH,FILE_FORMAT )
    val expectedDF = Seq(
      ("29839", null, "android", "B000078", "I7099", "B17543", "GOOGLE", null, "TRUE"),
      ("30504", "11/15/2020 15:27", "android", "B000078", "I7099", "B19304", "LinkedIn", null, "TRUE"),
      ("30334", "11/15/2020 15:23", "android", "B000078", "I7099", "B29093", "Youtube", null,null),
      (  "30385", "11/15/2020 15:24", "android", "B000078", "I7099", "D8142", "google", "TRUE",null ))
      .toDF("id",
      "event_timestamp",
      "device_type",
      "session_id",
      "visitor_id",
      "item_id",
      "redirection_source",
      "is_add_to_cart",
      "is_order_placed")

    val result = expectedDF.except(outputDF)
    val ans = result.count()
    val count = 0
    assertResult(count)(ans)
  }

}

