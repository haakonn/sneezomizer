package achoo.interests

import achoo.{State, Assessment}

/**
 * Determine if the sneeze count is a prime number.
 */
object Prime extends Assessment {

  override val notability = "prime"

  /**
   * Adapted from http://www.scala-lang.org/old/node/47.html
   */
  override def matches(state: State) = {
    def divisors(n: Int): List[Int] = for (i <- List.range(1, n + 1) if n % i == 0) yield i
    divisors(state.count).length == 2
  }

}
