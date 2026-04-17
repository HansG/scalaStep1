package consoleX

import console.{Console, Print, Read}



def apply[A](ca: Console ?=> A): A =
  val handler = new Console:
    override def readLine() =
      Console.in.readLine

    override def println(s: String) =
      Console.println("ConsoleX: "+s)

  ca(using handler)