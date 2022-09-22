package constants

import org.apache.spark.sql.types.{BooleanType, DateType, DoubleType, IntegerType, StringType, StructField, StructType, TimestampType}

import java.sql.Timestamp

object ApplicationConstants {

  val APP_NAME = "spark.app.name"
  val APP_MASTER = "spark.app.master"

  //input path
  val CLICK_STREAM_INPUT_PATH: String = "spark.app.clickStreamInputPath"
  val ITEM_DATA_INPUT_PATH: String = "spark.app.itemDataInputPath"


  val CLICK_STREAM_PRIMARY_KEYS: Seq[String] = Seq("session_id", "item_id")
  val ITEM_PRIMARY_KEYS: Seq[String] = Seq("item_id")

  //constants for null values handling
  val COLUMN_NAME_DEFAULT_VALUE_CLICK_STREAM_MAP=Map("id"->(-1),"device_type"->"unknown","event_timestamp"->"1999-01-01 00:00:00","session_id"->"unknown","redirection_source"->"unknown","is_add_to_cart"->false,"is_order_placed"->false)

  val COLUMN_NAME_DEFAULT_VALUE_ITEM_DATA_MAP=Map("item_price"->(-1),"product_type"->"unknown","department_name"->"unknown","vendor_id"->(-1),"vendor_name"->"unknown")

  val DEFAULT_TIMESTAMP_VALUE = "1999-01-01 00:00:00"
  val FINAL_PRIMARY_KEY = Seq("session_id", "item_id")
  val INPUT_TIME_STAMP_FORMAT = "MM/dd/yyyy HH:mm"
  val OUTPUT_TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss"
  val DATE_FORMAT = "yyyy-MM-dd"
  val TIME_STAMP_COL = "event_timestamp"
  val REDIRECTION_COL = "redirection_source"
  val EVENT_DATE = "event_d"
  val RECORD_LOAD_TIME = "record_load_ts"

  val FILE_FORMAT = "csv"

  val CLICK_STREAM_DATATYPE = List(
    ("id", "int"),
    ("event_timestamp", "timestamp"),
    ("device_type", "string"),
    ("session_id", "string"),
    ("visitor_id", "string"),
    ("item_id", "string"),
    ("redirection_source", "string"),
    ("is_add_to_cart", "boolean"),
    ("is_order_placed", "boolean"))

  val ITEM_DATATYPE = List(
    ("item_id", "string"),
    ("item_price", "double"),
    ("product_type", "string"),
    ("department_name", "string"),
    ("vendor_id", "int"),
    ("vendor_name", "string"))

  //  Join
  val JOIN_KEY: Seq[String] = Seq("item_id")
  val JOIN_TYPE: String = "left"

  val ENCRYPTED_DATABASE_PASSWORD: String = "data/encrypted_password.txt"
  val DATABASE_URL: String = "spark.app.databaseURL"


  val ERR_TABLE_NULL_CHECK = "error_table_nullCheck"
  val ERR_TABLE_DUP_CHECK = "error_table_duplicateCheck"

  val TABLE_NAME = "final_table"

  val DB_SOURCE = "jdbc"
  val JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"
  val DB_USER = "root"


  val SCHEMA_PATH = "spark.app.schemaPath"


}
