package Week10SparkSessions

object NullNilNoneNothingOptionUnit extends App{

//  Null trait in action
  def tryIt(thing: Null): Unit = println("This worked!")

//  tryIt(null)

//  Null is a trait in Scala. There exists only one instance of Null
//  which is null. Note : We should restrict the use of Null as it can
//  lead to null pointer exceptions.

//  Nil : An empty List. Here Lists are LinkedLists
  val c = Nil;
//  println(c)

//  Nothing is a trait in Scala and there are no instances of
//  Nothing. It means there was an error and nothing was returned.

//  def fun(): Nothing = {
//    throw Exception
//  }

//  fun

  //  Option : Consider you're writing a function and you run into a
  //  situation where you don't have anything useful to return. Returning
  //  null is not preferred due to null pointer exception.

  def getAStringMaybe(num: Int): Option[String] = {
    if (num >= 0) Some("A positive number!")
    else None
  }

  def printResult(num: Int): Unit = {
    getAStringMaybe(num) match {
      case Some(str) => println(str)
      case None => println("No String!")
    }}

  println(printResult(num = 10))
  println(printResult(num = -10))


  //  Unit is like void in Scala. This means that the function has some
  //  side effects.

  def funcNew(): Unit = {
    println("This func has a side effect.")
  }

  //    Dealing with nulls in Scala.

  case class Address(city: String, state: String, zip: String)

  class User(email: String, password: String) {
    var firstName: Option[String] = None
    var lastName: Option[String] = _ // None or _ is the same
    var address: Option[Address] = None // Either a valid string or None
  }

  val user1 = new User("soumyamyb95@gmail.com", "abcd")
  println(user1.firstName.getOrElse("<not assigned>"))
  user1.firstName = Some("Soumyadeep")
  user1.lastName = Some("Mukhopadhyay")
  user1.address = Some(Address("Kolkata", "West Bengal", "700070"))
  println(user1.firstName.getOrElse("<not assigned>"))
}
