package plenix.tools.xmlite

object IndentHandler {

  def annotate(input: String) = {

    val lines = "" :: input.linesIterator.toList ::: List("")
    val tails = lines.tails.takeWhile(_ != Nil)

    def getIndent(rest: List[String]): Int = rest match {
      case Nil => 0
      case x if x.head.trim() != "" => x.head.takeWhile(_ == ' ').length
      case _ => getIndent(rest.tail)
    }

    val linesOut = tails.foldLeft((0 :: Nil, Seq[String]())) { (x, tail) =>
      val (stack, text) = x
      val current :: next = tail

      val nextIndent = getIndent(next)

      val delta = if (nextIndent > stack.head) 1
      else -stack.takeWhile(_ > nextIndent).length

      val newText = if (delta > 0) current.takeWhile(_ == ' ') + indent + current.dropWhile(_ == ' ')
      else current + dedent * -delta

      val newStack = if (delta > 0) nextIndent :: stack
      else stack.drop(-delta)

      (newStack, text :+ newText)
    }

    linesOut._2.mkString("\n")
  }

  def indent = ">" //64976

  def dedent = "<" //64977
}
