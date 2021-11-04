package count_words

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WordCount extends App{

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sc = new SparkContext("local[*]", "wordcount")

  // Transformations:
  // Transformation 1:
  val input = sc.textFile("D:\\BigDataCourse\\Week2-assignment\\Q6.txt")

  // Transformation 2:
  val words = input.flatMap(x => x.split(" "))
  // Transformation 2.1:
  val wordsLower = words.map(x => x.toLowerCase())

  // Transformation 3:
  val wordMap = wordsLower.map(x => (x,1))

  // Transformation 4:
  // wordMap.reduceByKey((a,b) => a+b)
  val finalCount = wordMap.reduceByKey(_ + _)

  // Transformation 5: (sorting based on count)
//  val reversedTuple = finalCount.map(x => (x._2, x._1))
//  val sortedResults = reversedTuple.sortByKey(ascending = false)
//  val restoredTuple = sortedResults.map(x => (x._2, x._1))
  val results = finalCount.sortBy(x => x._2, ascending = false)
  // Action:
//  restoredTuple.collect().foreach(println)
//  val results = restoredTuple.collect
  for (r <- results) {
    val key = r._1
    val value = r._2
    println(s"$key : $value")
  }

  println("Please enter no/yes: ")
  val a = scala.io.StdIn.readLine()
  // Holds the Spark WebUI even after execution ends
  if (a == "yes") scala.io.StdIn.readLine()
}
