package Week10Assignment

import org.apache.spark.SparkContext
import org.apache.log4j.{Logger, Level}

object Exercise1 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "Week10Ex1")

    val chaptersRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
      "chapters.csv")

    chaptersRDD.map(x => (x.substring(x.indexOf(",")+1).toInt, 1))
      .reduceByKey(_ + _)
      .sortByKey()
      .collect()
      .foreach(println)
}
