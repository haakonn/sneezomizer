package achoo.interests

import achoo.Assessment

/**
 * Determine if the sneeze count is in the fibonacci sequence.
 */
object Fibonacci extends Assessment {

  override val notability = "fibonacci"

  /* From http://stackoverflow.com/a/9867004/40066 */
  val fibs: Stream[Int] = 0 #:: fibs.scanLeft(1)(_ + _)

  override def matches(n: Int) = fibs.takeWhile(_ <= n).last == n

}
