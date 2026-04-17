package tries.capa

import console.{Console, Print, Read}

import scala.util.Random


trait Rand:
  def range(min: Int, max: Int): Int


def rand[R](m : Rand ?=> R) =
  val handler = new Rand:
    val r = new Random
    override def range(min: Int, max: Int) = r.between(min, max)
//      def range(min: Int, max: Int) = Random.between(min, max) 
  m(using handler)

def constInt[R](c: Int)(m : Rand ?=> R): R =
  val handler = new Rand:
    override def range(min: Int, max: Int) = c
  m(using handler)


object Guess extends App:

  def readLine : Read ?=> String =
    r ?=> r.readLine()

  def readLine2(using r : Read) : String = r.readLine()

  def println(a: Any): Print ?=> Unit =
    p ?=> p.println(a.toString)

  def range(min: Int, max: Int): Rand ?=> Int =
    r ?=> r.range(min, max)

  def nextInt(max: Int): Rand ?=> Int =
    range(0, max)


//
//  system:
//    console:
//      run

  console.printNot:
    console.readConst("5"):
      constInt(6):
        assert(run)

  val runSwapped: (Console, Rand) ?=> Boolean = run

//  def run: (Rand, Console) ?=> Boolean =
  def run: (Rand, Read, Print) ?=> Boolean =
    val target = nextInt(10)

    println("Input a value between 0 and 10:")
    loop(target, 5)


  def readInput: Read ?=>   Option[Int] =
    try Some(readLine.toInt)
    catch
      case _ => None

  def guess(target: Int): (Print, Read) ?=> Boolean =
    readInput match
      // Correct guess
      case Some(`target`) => true

      // Incorrect guess
      case Some(input) =>
        if input < target then println("Too low")
        else println("Too high")
        false

      // Invalid guess
      case None =>
        println("Not a valid number")
        false

  def loop(target: Int, remaining: Int): (Read, Print) ?=> Boolean =
    // Correct guess
    if guess(target) then
      println("Congratulations!")
      true

    // Incorrect guess, no more attempts
    else if remaining < 0 then
      println("No more attempts")
      false

    // Incorrect guess, some remaining attempts
    else
      if remaining == 0 then println("Last attempt")
      else  println(s"$remaining attempts remaining")

      loop(target, remaining - 1)