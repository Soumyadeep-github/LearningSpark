package SparkStreaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DataFrames1 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)
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

    val ordersDF = spark.
      read.
      option("header", value = true).
      option("inferSchema", value = true).
      csv("D:\\BigDataCourse\\Week-9-10-Spark\\" +
      "orders.csv")
    ordersDF.show(truncate = false)
    ordersDF.printSchema()

    val groupedOrdersDF = ordersDF.
      repartition(4).
      where("order_customer_id > 10000").
      select("order_id","order_customer_id").
      groupBy("order_customer_id").
      count()

    groupedOrdersDF.show(truncate = false)
//    For custom logging
    Logger.getLogger(getClass.getName).
      info("My application has been completed successfully.")

    scala.io.StdIn.readLine()
    spark.stop()
}
