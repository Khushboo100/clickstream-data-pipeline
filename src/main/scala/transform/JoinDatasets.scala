package transform

import constants.ApplicationConstants._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{lit, to_date}
import utils.ApplicationUtils.check
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object JoinDatasets {
  def joinDataFrame(df1: DataFrame, df2: DataFrame, joinKey: Seq[String], joinType: String): DataFrame = {
    check(df1, joinKey)
    check(df2, joinKey)

    val joinedDataFrame: DataFrame = df1.join(df2, joinKey, joinType)
    joinedDataFrame
  }


  def transformDataFrame(df: DataFrame): DataFrame = {
    val currentTime = Timestamp.valueOf(DateTimeFormatter.ofPattern(OUTPUT_TIME_STAMP_FORMAT).format(LocalDateTime.now()))
    val newDfJoin = df.withColumn(EVENT_DATE, to_date(df.col(TIME_STAMP_COL), DATE_FORMAT)).withColumn(RECORD_LOAD_TIME, lit(currentTime))
   newDfJoin
  }
}

