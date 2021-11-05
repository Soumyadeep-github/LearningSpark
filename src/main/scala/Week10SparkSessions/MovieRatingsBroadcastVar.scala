package Week10SparkSessions

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

import java.nio.charset.CodingErrorAction
import scala.collection.mutable.ArrayBuffer
import scala.io.{Codec, Source}

// DOES NOT WORK YET!
object MovieRatingsBroadcastVar extends App{
  Logger.getLogger("org").setLevel(Level.ERROR)

//  def getMovies: (ArrayBuffer[String], ArrayBuffer[String]) = {
//    var allMovies, movieIds =  ArrayBuffer[String]()
//    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
//    val moviesFiles = Source.fromFile("D:\\BigDataCourse\\Week-9-10-Spark\\movies.dat")(decoder)
//    for (i <- moviesFiles.getLines()) {allMovies += i; movieIds += i.slice(0, i.indexOf("::"))}
//    moviesFiles.close
//    (allMovies, movieIds)
//  }

  def getMovies: ArrayBuffer[(String, String)] ={
    var allMovies = ArrayBuffer[(String, String)]()
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val moviesFiles = Source.fromFile("D:\\BigDataCourse\\Week-9-10-Spark\\movies.dat")(decoder)
    for (i <- moviesFiles.getLines()) {
      val row = i.split("::")
      allMovies += Tuple2(row(0), row(1))
    }
    allMovies
  }

  val sc = new SparkContext("local[*]", "movieRatings")

//  Need to investigate this - Failing during the function call
//  var movieNamesIds  = getMovies
//  var movieNames = movieNamesIds._1
//  var movieIds = movieNamesIds._2
//  var movieNamesBroadcast = sc.broadcast(movieNames)
//  var movieIdsBroadcast = sc.broadcast(movieIds)
  val moviesRDD = sc.parallelize(getMovies)
  val moviesLookup = sc.broadcast(moviesRDD.collect.toMap)

  val ratingsRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "ratings.dat")

  val ratingsNecessaryColumns = ratingsRDD.map(x => {
    val row = x.split("::")
    (row(1), row(2))
  })
    val mappedRatings = ratingsNecessaryColumns.mapValues(x => (x.toFloat,1.0))
    val reducedRatings = mappedRatings.reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2))
    val filteredRDD = reducedRatings.filter(x => x._2._2 >= 1000)
    val ratingsProcessed = filteredRDD.mapValues(x => x._1/x._2).
      filter(x => x._2 > 4.0)

//    val moviesRatings = ratingsProcessed.map(x => {
//      movieIdsBroadcast.value.eq(x._1)
//    })
    ratingsProcessed.flatMap {
    case (key, value) =>
      moviesLookup.value.get(key).
        map(otherValue => (otherValue, value))
  }.collect.foreach(println)

    scala.io.StdIn.readLine()
}
