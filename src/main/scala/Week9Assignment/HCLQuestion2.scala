package Week9Assignment

//import org.apache.spark._
//import org.apache.spark.SparkContext._
//import org.apache.log4j._
import org.apache.spark.SparkContext
import org.apache.log4j.{Level, Logger}

object HCLQuestion2 extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "HCLQ1")

    val tempInputRDD = sc.textFile("D:\\BigDataCourse\\Week-9-10-Spark\\" +
      "tempdata.csv")

  tempInputRDD.map(_.split(","))
    .filter(_(2) == "TMIN")
    .map(x =>(x(0), x(3).toFloat))
    .reduceByKey( (x,y) => math.min(x, y))
    .collect()
    .foreach(println)


}
