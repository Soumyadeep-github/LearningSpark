package Week10SparkSessions

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object Session3 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]", "customerpurchase")

  val input = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\customer-orders\\" +
    "customer-orders.csv")
  println(input.getNumPartitions)
  val headers = input.first()
  val finalOutput = input.filter(_ != headers)
    .repartition(10)
    .map( x => {val splitRow = x.split(",")
    (splitRow(0), splitRow(2).toFloat)})
    .reduceByKey(_ + _)
    .filter(_._2 > 50000)
    .sortBy( x => x._2, ascending = false)
    .coalesce(5)

//  finalOutput.take(10).foreach(println)
  
  finalOutput.saveAsTextFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "customer-orders-output11")

  val opParts = finalOutput.getNumPartitions
  println(f"Print total number of partitions : $opParts")
  println(finalOutput.count())
  scala.io.StdIn.readLine()
}
