package Week10SparkSessions

object IterationStyles extends App{
//  Array provides indexing support.
//  List provides immutability.
//  Vector provides both index based search and immutability.
//  Using yield we are returned with a proper Vector

  val b = for (i <- 1 to 10) yield i*i
  println(b)

//  Although this works there is a better way to achieve
//  this. It is called as if-guard
  val c = 1 to 10 map(x => if (x%2==0) x*x) filter(_ != ())
  println(c)

  val cIfGuarded = for(i <- 1 to 10; if i%2==0) yield i*i
  println(cIfGuarded)

//  Same syntax written differently
  val d = for {
    i <- 1 to 10
    if i%2==0
  } yield i*i
  println(d)

//  Pattern guard
//  Case statements can be combined with if expressions to
//  provide extra logic during pattern match.
//  Pattern guards should be exhaustive, i.e., all cases
//  should be handled.

  def checkSign(x: Int): String = {
    x match{
      case a if a < 0 => s"$a is a negative number"
      case b if b < 0 => s"$b is a positive number"
      case c => s"$c is neither negative nor positive number"
    }
  }

  println(checkSign(10))
  println(checkSign(-10))
  println(checkSign(0))

  def foo(x: Option[Int]): Unit = x match {
    case Some(i) if i%2==0 => println(i)
    case None => println("none")
    case _ => println("Something else")
  }

//  For comprehension
  for(i <- 1 to 10) print(i*i+" ")
  println()
  (1 to 10).foreach(i => print(i*i+" "))
  println()
  (1 to 10) map(i => i*i+" ")
  println()
  (1 to 10).map(i => i*i+" ")
  println()
//  Nested for loop
  for (i <- 1 to 10; j <- 'a' to 'c') yield i*2+" "+j
  for (i <- 1 to 10; j <- 'a' to 'c') print(i*2+" "+j+" | ")

//  Difference between == in Java and Scala
//  In Java == is meant for reference comparison
//  to check the natural equality we use equals method in Java
//  String a = new String("Soumyadeep");
//  String b = new String("Soumyadeep");
//  a == b | False
//  a and b are 2 different instances hence they reside in
//  different locations in memory
//  a.equals(b) | True
//  because content is being compared
//  whereas in Scala
//  a = "Soumyadeep"
//  b = "Soumyadeep"
//  a == b | True
//  a.equals(b) | True
//  a.==(b) | True
//  a equals b | True
//  because by default in Scala the content is compared
//  but reference comparison is done like this
/*
  scala> val a = 't'
a: Char = t

scala> val b = 't'
b: Char = t

scala> a == b
res16: Boolean = true

scala> a.eq(b)
<console>:28: error: the result type of an implicit conversion must be more specific than AnyRef
       a.eq(b)
            ^

scala> val a = new String("t")
a: String = t

scala> val b = new String("t")
b: String = t

scala> a.eq(b)
res18: Boolean = false

scala> a eq b
res19: Boolean = false
*/




}
