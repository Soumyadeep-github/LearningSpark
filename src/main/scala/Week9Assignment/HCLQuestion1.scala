package Week9Assignment

import org.apache.spark.SparkContext
import org.apache.log4j.{Level, Logger}

object HCLQuestion1 extends App{
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sc = new SparkContext("local[*]", "HCLQ1")

  val inputRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "dataset1.csv")

  inputRDD.map(_.split(",")).map(x => {
    val adultCol = if (x(1).toInt > 18) "Y" else "N"
    (x(0), x(1), x(2), adultCol)
  }).collect().foreach(println)



}
