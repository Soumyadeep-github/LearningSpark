package SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DataFrames1 extends App{
    val spark = SparkSession.builder().
      appName("myApp1").
      master("local[*]").
      getOrCreate()
// OR
//    val sparkConf = new SparkConf()
//    sparkConf.set("spark.app.name", "myApp1")
//    sparkConf.set("spark.master", "local[*]")
//
//    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    spark.stop()
}
