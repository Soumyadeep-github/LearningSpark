package Week10SparkSessions

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object AccumulatorVariable extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]", "accumulatordemo")

  val textRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
    "samplefile.txt")

  val myAccum = sc.longAccumulator("blankLinesAccumulator")

  textRDD.foreach( x => if (x =="") myAccum.add(1))

  println(myAccum.value)

  scala.io.StdIn.readLine()
}
