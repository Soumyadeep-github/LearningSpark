package Week10SparkSessions

import org.apache.spark.SparkContext
import org.apache.log4j.{Logger, Level}

object MovieRatings extends App{
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sc = new SparkContext("local[*]", "movieRatings")

  val ratingsRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "ratings.dat")
  val moviesRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "movies.dat")

  val ratingsNecessaryColumns = ratingsRDD.map(x => {
    val row = x.split("::")
    (row(1), row(2))
  })
    val mappedRatings = ratingsNecessaryColumns.mapValues(x => (x.toFloat,1.0))
    val reducedRatings = mappedRatings.reduceByKey((x,y) => (x._1 + y._1.toFloat, x._2 + y._2))
    val filteredRDD = reducedRatings.filter(x => x._2._2 >= 1000)
    val ratingsProcessed = filteredRDD.mapValues(x => x._1/x._2).
      filter(x => x._2 > 4.0)

    val moviesProcessed = moviesRDD.map(x => {
      val movie = x.split("::")
      (movie(0), movie(1))
    })
//    ratingsProcessed.join(moviesProcessed)
    moviesProcessed.join(ratingsProcessed).map(_._2).collect.foreach(println)
//    ratingsProcessed.collect.foreach(println)
//    println(ratingsProcessed.count)
    scala.io.StdIn.readLine()
}
