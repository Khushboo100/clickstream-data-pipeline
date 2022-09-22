package service

import exceptions.Exceptions.{DataframeIsEmptyException, EmptyFilePathException, FilePathNotFoundException, FileReaderException, InvalidInputFormatException}
import org.apache.spark.internal.Logging
import org.apache.spark.sql.{AnalysisException, DataFrame, SparkSession}

object FileReader extends Logging {

  def fileReader(filePath: String, fileFormat: String)(implicit sparkSession: SparkSession): DataFrame = {
    if(filePath == "" ){
      throw EmptyFilePathException("The file path is empty. Please provide a valid file path.")
    }
    val outputDF = try {
      sparkSession.read.option("header", "true").format(fileFormat).load(filePath)

    } catch {
      case _: AnalysisException => throw FilePathNotFoundException("The file path is not found.")
      case _: java.lang.ClassNotFoundException => throw InvalidInputFormatException("The file format is invalid")
      case _: Exception => throw FileReaderException("Unable to read file from given path.")

    }
    if (outputDF.count() == 0) {
      throw DataframeIsEmptyException("The dataFrame is empty")
    }
    outputDF
  }
}
