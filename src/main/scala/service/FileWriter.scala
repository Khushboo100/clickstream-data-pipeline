package service

import exceptions.Exceptions.DatabaseException
import org.apache.spark.sql.DataFrame

import java.nio.charset.StandardCharsets
import java.util.Base64

object FileWriter {


  def decryptPassword(encryptedPasswordPath:String):String={
    //read from file and decrypt password
    val source=scala.io.Source.fromFile(encryptedPasswordPath)
    val encryptedPassword = try source.mkString finally source.close()
    val decoded = Base64.getDecoder.decode(encryptedPassword)

    //return the decrypted password as a string
    val password = new String(decoded, StandardCharsets.UTF_8)
    password
  }

  def fileWriter(databaseURL:String,tableName: String, df: DataFrame): Unit = {
    //change in conf

    val password = decryptPassword(constants.ApplicationConstants.ENCRYPTED_DATABASE_PASSWORD)
    try {
        df.write.format("jdbc")
          .option("url", databaseURL)
          .option("driver", "com.mysql.cj.jdbc.Driver")
          .option("dbtable", tableName)
          .option("user", "root")
          .option("password",password)
          .mode("overwrite")
          .save()
    }
      catch{
        case e: Exception => throw DatabaseException("Database connection is not established")
    }
  }
}
