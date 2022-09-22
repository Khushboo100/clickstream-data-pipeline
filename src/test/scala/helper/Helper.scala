package helper

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{BooleanType, DateType, DoubleType, IntegerType, StringType, StructField, StructType, TimestampType}

object Helper {
  val DATABASE_TEST_URL = "jdbc:mysql://localhost:3306/target_project_test"
  val CLICK_STREAM_TEST_INPUT_PATH = "data/clickstream_test.csv"
  val INPUT_TEST_FILE_FORMAT = "csv"

  val FINAL_TABLE_SCHEMA: StructType = StructType(List(
    StructField("item_id", StringType, nullable = false),
    StructField("id", IntegerType, nullable = false),
    StructField("event_timestamp", TimestampType, nullable = false),
    StructField("device_type", StringType, nullable = true),
    StructField("session_id", StringType, nullable = false),
    StructField("visitor_id", StringType, nullable = false),
    StructField("redirection_source", StringType, nullable = false),
    StructField("is_add_to_cart", BooleanType, nullable = true),
    StructField("is_order_placed", BooleanType, nullable = true),
    StructField("item_price", DoubleType, nullable = true),
    StructField("product_type", StringType, nullable = true),
    StructField("department_name", StringType, nullable = true),
    StructField("vendor_id", IntegerType, nullable = true),
    StructField("vendor_name", StringType, nullable = true),
    StructField("event_d", DateType, nullable = true),
    StructField("record_load_ts", TimestampType, nullable = true)))

  val JOINED_TABLE_SCHEMA: StructType = StructType(List(
    StructField("item_id", StringType, nullable = false),
    StructField("id", IntegerType, nullable = false),
    StructField("event_timestamp", TimestampType, nullable = false),
    StructField("device_type", StringType, nullable = true),
    StructField("session_id", StringType, nullable = false),
    StructField("visitor_id", StringType, nullable = false),
    StructField("redirection_source", StringType, nullable = false),
    StructField("is_add_to_cart", BooleanType, nullable = true),
    StructField("is_order_placed", BooleanType, nullable = true),
    StructField("item_price", DoubleType, nullable = true),
    StructField("product_type", StringType, nullable = true),
    StructField("department_name", StringType, nullable = true),
    StructField("vendor_id", IntegerType, nullable = true),
    StructField("vendor_name", StringType, nullable = true)))

  val CLICK_STREAM_SCHEMA: StructType = StructType(Array(
    StructField("id", IntegerType),
    StructField("event_timestamp", TimestampType),
    StructField("device_type", StringType),
    StructField("session_id", StringType),
    StructField("visitor_id", StringType),
    StructField("item_id", StringType),
    StructField("redirection_source", StringType),
    StructField("is_add_to_cart", BooleanType),
    StructField("is_order_placed", BooleanType)
  ))
  def createSparkSession(): SparkSession = {
    implicit val spark: SparkSession = SparkSession.getActiveSession.getOrElse(
      SparkSession.builder
        .appName("UpCurve Data Pipeline Test")
        .master("local[*]")
        .getOrCreate())
    spark
  }

}
