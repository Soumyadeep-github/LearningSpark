package Week10SparkSessions

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object Session2 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]", "countwords")

    val inputLog = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\bigLogtxt\\" +
      "bigLog.txt")

//  Since this is a local collection that is why we are using
//  sc.parallelize to create an RDD from the above list.

//    val originalLogsRDD = sc.parallelize(myList)
//    val newPairRDD =  originalLogsRDD.map( x => (x.slice(0, x.indexOf(":")), 1))
//    val resultRDD = newPairRDD.reduceByKey(_ + _)

  inputLog.map(x => (x.slice(0, x.indexOf(":")), 1))
    .reduceByKey(_ + _).collect().foreach(println)

//  inputLog.map(x => (x.slice(0, x.indexOf(":")), 1))
//    .groupByKey.collect().foreach(x => println(x._1, x._2.size))

  scala.io.StdIn.readLine()
}
