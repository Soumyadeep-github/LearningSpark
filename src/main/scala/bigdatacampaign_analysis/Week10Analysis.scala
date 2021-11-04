package bigdatacampaign_analysis

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import scala.io.Source


object Week10Analysis extends App{

  def loadBoringWords(): Set[String] = {
    var boringWords:Set[String] = Set()
    val lines = Source.fromFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
      "boringwords.txt")
    for (line <- lines.getLines()) {boringWords += line}
    lines.close
    return boringWords
  }

  Logger.getLogger("org").setLevel(Level.ERROR)
//    Initialize the SparkContext
  val sc = new SparkContext("local[*]", "campaignAnalysisWeek10")

//  Just like in MapSide join a smaller dataset is shuffled to mappers
//  in the same way the set of boring words is also being broad casted
//  to every worker node within the cluster.
  var nameSet = sc.broadcast(loadBoringWords())

  val initialRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "bigdatacampaigndata.csv")
  
  val mappedInput = initialRDD.map(x => x.split(",")(10).toFloat ->
    x.split(",")(0))

  val finalMappedInput = mappedInput.flatMapValues(_.split(" "))
                          .map(x => (x._2.toLowerCase(), x._1))

//   This is where the file is being checked whether the words are present in
  //   the broadcast variable.
  val finalMappedInputFiltered = finalMappedInput.filter( x => !nameSet.value(x._1))

  val finalMappedOutput = finalMappedInputFiltered.reduceByKey(_ + _).sortBy( x => x._2, ascending = false)

  finalMappedOutput.take(20).foreach(println)




}
