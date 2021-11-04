package fake_freinds
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext


object AverageFriendsByAge extends App{

  def getAgeNumFriends(line : String): (Int, Int) = {
    val line_split = line.split(",")
    val age = line_split(2).toInt
    val numFriends = line_split(3).toInt
    return (age, numFriends)
  }

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sc = new SparkContext("local[*]", "avg_friends")

  val input = sc.textFile("D:\\BigDataCourse\\Data-practicals\\fakefriends.csv")

  val mappedInput = input.map(getAgeNumFriends)

//  val paddedInputs = mappedInput.map(x => (x._1, (x._2, 1)))
   val paddedInputs =  mappedInput.mapValues(x => (x,1))

  val reducedData = paddedInputs.reduceByKey( (x,y) => (x._1 + y._1, x._2 + y._2))

//  val averageByAge = reducedData.map( x => (x._1, x._2._1/x._2._2.toFloat)).sortBy(x => x._2, ascending=false)
   val averageByAge = reducedData.mapValues( x =>  x._1/x._2.toFloat).sortBy(x => x._2, ascending=false)

  averageByAge.collect.foreach(println)
}
