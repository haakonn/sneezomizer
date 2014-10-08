package achoo

import scala.scalajs.js.JSApp
import org.scalajs.dom
import dom.document

import scala.scalajs.js.annotation.JSExport

/**
 * Helps you determine whether you have the rare gift of sneezing a prime
 * number of times per sequence.
 */
object Sneezomizer extends JSApp {

  var count = 0
  var last = 0l
  val cutoffSeconds = 10

  var timerHandle = 0

  lazy val sneequence = document.getElementById("sneequence")

  def main(): Unit = {
  }

  /**
   * Adapted from http://www.scala-lang.org/old/node/47.html
   */
  def isPrime(n: Int) = {
    def divisors(n: Int): List[Int] = for (i <- List.range(1, n + 1) if n % i == 0) yield i
    divisors(n).length == 2
  }

  /**
   * The sneequence is over, evidence has been gathered, so we can conclude.
   */
  def conclude() = {
    sneeq(s"The sneezing sequence (Sneequence™) expired.")
    if (isPrime(count))
      sneeq(s"You sneezed a prime number! ʘ‿ʘ")
    else
      sneeq(s"You did not sneeze a prime number. ಠ_ಠ")
    count = 0
    last = 0
  }

  def resetTimer() = {
    dom.window.clearTimeout(timerHandle)
    timerHandle = dom.window.setTimeout(() => { conclude() }, cutoffSeconds * 1000)
  }

  /**
   * Tell the sneezer he dun good.
   */
  def informCount() = {
    val times = "time" + (if (count > 1) "s" else "")
    sneeq(s"You have sneezed $count $times!")
  }

  /**
   * The sneeze is good.
   * @param when
   */
  def commitSneeze(when: Long) = {
    last = when
    count += 1
    informCount()
    resetTimer()
  }

  @JSExport
  def sneeze(): Unit = {
    val now = System.currentTimeMillis()
    if (last == 0) {
      sneequence.innerHTML = ""
    }
    commitSneeze(now)
  }

  def sneeq(text: String): Unit = {
    val li = document.createElement("li")
    li.innerHTML = text
    sneequence.appendChild(li)
  }

}
