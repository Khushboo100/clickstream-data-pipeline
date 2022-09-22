package utils

import com.typesafe.config.{Config, ConfigFactory}
import constants.ApplicationConstants.{APP_MASTER, APP_NAME}
import exceptions.Exceptions.{ColumnNotFoundException, DataframeIsEmptyException}
import org.apache.spark.sql.types.{DataType, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.io.File
import scala.io.Source

object ApplicationUtils {

  //configuration
  def configuration(inputPath:String):Config = {
    val parsedConfig:Config = ConfigFactory.parseFile(new File(inputPath))
    val appConf: Config = ConfigFactory.load(parsedConfig)

    appConf
  }
  //Creating the spark session
  def createSparkSession(inputAppConf:Config): SparkSession = {
        implicit val spark: SparkSession = SparkSession.getActiveSession.getOrElse(
          SparkSession.builder
            .appName(inputAppConf.getString(APP_NAME))
            .master(inputAppConf.getString(APP_MASTER))
            .getOrCreate()
        )
    spark
    }

  //checking for exceptions
  def check(inputDF: DataFrame, colName: Seq[String]): Unit = {
    if (inputDF.count() == 0) {
      throw DataframeIsEmptyException("The dataframe is empty")
    } else {
      colName.foreach { (currentColName: String) =>
        if (!inputDF.columns.contains(currentColName))
          throw ColumnNotFoundException("The specified column does not exist")
      }
    }
  }

  def schemaRead(schemaPath : String) : StructType = {
    val source = Source.fromFile(schemaPath)
    val schemaJson = try source.getLines().mkString finally source.close()
    val correctSchema = DataType.fromJson(schemaJson).asInstanceOf[StructType]
    correctSchema
  }

}