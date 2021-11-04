package Week10SparkSessions

import org.apache.spark.SparkContext
import org.apache.log4j.{Level, Logger}

object Session1 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]", "countwords")

    val myList = List("WARN: Tuesday 4 September 0405",
         "ERROR: Tuesday 4 September 0405",
         "ERROR: Tuesday 4 September 0405",
         "ERROR: Tuesday 4 September 0405",
         "ERROR: Tuesday 4 September 0405")

//  Since this is a local collection that is why we are using
//  sc.parallelize to create an RDD from the above list.

//    val originalLogsRDD = sc.parallelize(myList)
//    val newPairRDD =  originalLogsRDD.map( x => (x.slice(0, x.indexOf(":")), 1))
//    val resultRDD = newPairRDD.reduceByKey(_ + _)
  sc.parallelize(myList).map(x => (x.slice(0, x.indexOf(":")), 1))
    .reduceByKey(_ + _).collect().foreach(println)
  
  scala.io.StdIn.readLine()
}
