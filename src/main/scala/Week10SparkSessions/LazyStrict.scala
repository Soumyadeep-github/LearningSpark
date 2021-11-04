package Week10SparkSessions

object LazyStrict extends App{
    object Person {
      def apply(name: String, age: Int): String = {
        s"$name is $age years old."
      }
    }
    val r = 10
    val area = {
      println("Calculating area of circle")
      3.14 * r * r
    }
    println(area)

  lazy val areaLazy = {
    println("Calculating area of circle")
    3.14 * r * r
  }
  /*
  scala> val r = 10
  r: Int = 10

  scala> val area = {
       |       println("Calculating area of circle")
       |       3.14 * r * r
       |     }
  Calculating area of circle
  area: Double = 314.0

  scala>   lazy val areaLazy = {
       |     println("Calculating area of circle")
       |     3.14 * r * r
       |   }
  areaLazy: Double = <lazy>
  scala> java.lang.Math.sqrt(9)
  res1: Double = 3.0

  scala> Math.sqrt(9)
  res2: Double = 3.0

  scala> math.sqrt(9)
  res3: Double = 3.0

  scala> math.random
  res4: Double = 0.027802756177110477

  scala> Math.random
  res5: Double = 0.26397351724692364

//  Modules imported by default in Scala
//  java.lang._
//  scala._
//  scala.Predef._

  Scala apply method
  This serves the purpose of closing the gap between object
  oriented and function paradigm in scala. We would be able to
  call an object like a function
  */
  println(Person.apply("Soumya", 26))
  println(Person("Soumya", 26))
  
  case class PersonCase(name: String, age: Int)

  println(PersonCase("Soumya", 26))
  println(PersonCase.apply("Soumya", 26))

  /*
  * Diamond Problem in Scala
  * A problem which arises with multiple inheritance
  * class A{ def func1()= "foo"}
  * class B{ def func1()= "foo"}
  * class C extends A,B {func1()} - Not feasible in Scala
  * But multiple inheritance is supported in Scala by traits
  * Interfaces ahas only unimplemented things.
  * Scala doesn't support multiple inheritance but a user
  * can extend extend multiple traits in a single class.
  * Linearization rule comes into play to decide call hierarchy
  * object grandChild extends traitB with traitC
  * grandChild -> traitC -> traitB -> traitA  */
  trait traitA {
    def name(): Unit = println("This is the grandparent trait")
  }

  trait traitB extends traitA {
    override def name(): Unit = {
      println("B is a child of A")
      super.name()
    }
  }

  trait traitC extends traitA {
    override def name(): Unit = {
      println("C is a child of A")
      super.name()
    }
  }
  object grandChild extends traitB with traitC
/*
  * scala>   object grandChild extends traitB with traitC
  defined object grandChild

  scala> grandChild.name()
  C is a child of A
  B is a child of A
  This is the grandparent trait

  scala> object grandChild extends traitC with traitB
  defined object grandChild

  scala> grandChild.name()
  B is a child of A
  C is a child of A
  This is the grandparent trait
*/
    grandChild.name()

  try{
    val a = 5/0
  }
  catch {
    case e: Exception => println("Please provide some denominator other than 0.")
  }
  finally {
    println("Finally block")
  }

}
