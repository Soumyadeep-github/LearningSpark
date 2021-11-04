package Week10Assignment

import org.apache.spark.SparkContext
import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel

object Exercise2And3 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)

    def getScores(row: Double): Long =  {
        if (row >= 0.9) 10l
        else if (row <= 0.9 && row > 0.5)  4l
        else if (row <= 0.5 && row > 0.25)  2l
        else 0l
    }

    val sc = new SparkContext("local[*]", "Week10Ex2")

    val titlesRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
      "titles.csv").map(_.split(",")).map(x => (x(0).toInt, x(1)))

    val viewsData = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
      "views-*.csv")

    val chaptersRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "chapters.csv").map(_.split(",")).map(x => (x(0).toInt, x(1).toInt))

    val uniqueViewerChapters = viewsData.map(_.split(",")).
      map(x => (x(0).toInt, x(1).toInt)).
      distinct().map(x  => (x._2, x._1))

    val courseViewCounts = uniqueViewerChapters.join(chaptersRDD).
      map(_._2).
      map(x => (x,1)).
      reduceByKey(_ + _).
      map(x => (x._1._2, x._2))

    val chapterCount = chaptersRDD.map(x => (x._2,1)).reduceByKey(_ + _)

    val courseCompletionScoresRDD = courseViewCounts.join(chapterCount).
      map(x => (x._1, x._2._1.toDouble/x._2._2)).
      mapValues(getScores).
      reduceByKey(_ + _).persist(StorageLevel.MEMORY_AND_DISK_SER)

    val scoreTitleRDD = courseCompletionScoresRDD.join(titlesRDD).
      map(_._2).
      sortByKey(ascending = false)

    println("CourseId, Scores")
    scoreTitleRDD.collect.foreach(println)
    println(scoreTitleRDD.toDebugString)
    println(scoreTitleRDD.count)
}
