package checks

import constants.ApplicationConstants.{ERR_TABLE_DUP_CHECK, ERR_TABLE_NULL_CHECK}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, desc, row_number}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import service.FileWriter
import utils.ApplicationUtils.schemaRead

import scala.collection.JavaConversions._

object DataQualityChecks {

  //null data quality check
  def nullCheck(databaseUrl: String, inputDF: DataFrame)(implicit spark: SparkSession): DataFrame = {
    var count = 0
    var errorList: List[Row] = List[Row]()
    inputDF.collect().foreach(row => {
      row.toSeq.foreach(c => {
        if (c == "unknown" || c == -1 || c == false || c == "null" || c == "NULL" || c == "" || c == null) {
          count = count + 1
        }
      })
      if (count > 9) {
        errorList = errorList :+ row
      }
      count = 0
    })

    val errorDF = spark.createDataFrame(errorList, inputDF.schema)
    FileWriter.fileWriter(databaseUrl, ERR_TABLE_NULL_CHECK, errorDF)
    val nullCheckFinalDF = inputDF.except(errorDF)
    nullCheckFinalDF
  }

  //duplicates data quality check
  def duplicatesCheck(databaseUrl: String, inputDF: DataFrame, primaryKeyCols: Seq[String], orderByCol: String): DataFrame = {
    val exceptionsDF = inputDF.withColumn("rn", row_number().over(Window.partitionBy(primaryKeyCols.map(col): _*).orderBy(desc(orderByCol))))
      .filter(col("rn") > 1).drop("rn")

    val duplicateCheckFinalDF = inputDF.except(exceptionsDF)
    FileWriter.fileWriter(databaseUrl, ERR_TABLE_DUP_CHECK, exceptionsDF)
    duplicateCheckFinalDF
  }

  //schema validation check
  def schemaValidationCheck(inputDF: DataFrame, schemaPath: String)(implicit sparkSession: SparkSession): DataFrame = {
    val dfSchema = inputDF.schema
    val correctSchema = schemaRead(schemaPath)
    if(dfSchema != correctSchema){
      val outputDF = sparkSession.sqlContext.createDataFrame(inputDF.rdd, correctSchema)
      outputDF
    }else{
      inputDF
    }
  }
}