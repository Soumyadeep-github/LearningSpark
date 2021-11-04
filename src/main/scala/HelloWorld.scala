import org.apache.spark.SparkContext

object HelloWorld {
    def main(args: Array[String]): Unit = {
        println("Hello World! This is " + args(0)
          + args(1))
    }
}
