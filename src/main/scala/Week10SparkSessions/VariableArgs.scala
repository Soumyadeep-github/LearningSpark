package Week10SparkSessions

object VariableArgs extends App{
   def prnFn(inputs : String*) = {
     for (i <- inputs) {
       println(if (i != ()) i else "")
     }
   }

  println(prnFn("Hi", "Heey", "Hola", "Hello"))

}
