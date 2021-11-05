package SparkStreaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object DataSets1 extends App{
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "appDatasets1")
  sparkConf.set("spark.master", "local[*]")

  val spark = SparkSession.builder().config(sparkConf).
    getOrCreate()

  val ordersDF: Dataset[Row] = spark.read.
    option("header",  value = true).
    option("inferSchema", value = true).
    csv("D:\\BigDataCourse\\Week-9-10-Spark\\" +
      "orders.csv")

  ordersDF.filter("order_id < 10").show

  spark.close()
}
