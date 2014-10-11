package achoo.interests

import achoo.Assessment

/**
 * Determine if the sneeze count is in the fibonacci sequence.
 */
object Fibonacci extends Assessment {

  override val notability = "fibonacci"

  /**
   * Adapted from thin air
   */
  override def matches(n: Int) = {
    var a = 0
    var b = 1
    while(b < n) {
      val c = a + b
      a = b
      b = c
    }
    n == b
  }

}
