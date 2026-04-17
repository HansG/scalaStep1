package console

trait Print:
  def println(s: String): Unit

trait Read:
  def readLine(): String


trait Console extends Print with Read

def apply[A](ca: Console ?=> A): A =
  val handler = new Console:
    override def readLine() =
      Console.in.readLine

    override def println(s: String) =
      Console.println(s)

  ca(using handler)


def printNot[A](pa: Print ?=> A): A =
  val handler = new Print:
    override def println(s: String) = ()

  pa(using handler)

def readConst[A](s: String)(ra: Read ?=> A): A =
  val handler = new Read:
    override def readLine() =
      s

  ra(using handler)