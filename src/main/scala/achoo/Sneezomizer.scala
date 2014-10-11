package achoo

import achoo.interests.{Prime, Fibonacci}

import scala.scalajs.js.JSApp
import org.scalajs.dom
import dom.document

import scala.scalajs.js.annotation.JSExport

/**
 * Helps you determine whether you have the rare gift of sneezing a prime
 * number of times per sequence.
 */
object Sneezomizer extends JSApp {

  /**
   * These are the assessments the sneeze number will be subjected to.
   */
  val assessments = Set(Prime, Fibonacci)

  var count = 0
  var last = 0l
  val cutoffSeconds = 10

  var timerHandle = 0
  lazy val sneequence = document.getElementById("sneequence")

  def main(): Unit = {
  }

  /**
   * The emotion-based elements of a reaction string.
   * @param emoticon a face
   * @param punctuation Multiple exclamation marks!!! An ellipsis … An interrobang‽
   */
  case class Reaction(emoticon: String, punctuation: String = ".") {
    override def toString = s"$punctuation $emoticon"
  }

  /**
   * Produce a Reaction to a given amount of notable sneeze numbers.
   */
  def react(notables: Int) = notables match {
    case 0 => Reaction("ಠ_ಠ")
    case 1 => Reaction("ʘ_ʘ")
    case _ => Reaction("ʘ‿ʘ", "!")
  }

  /**
   * The sneequence is over, evidence has been gathered, so we can conclude.
   */
  def conclude() = {
    sneeq(s"The sneezing sequence (Sneequence™) expired.")
    val notabilities = assessments.map(_(count)).flatten
    val reaction = react(notabilities.size)
    val notabilityStr = notabilities.mkString(" ")
    val conclusion =
      if (notabilities.size > 0)
        s"You sneezed a $notabilityStr number$reaction"
      else
        s"You did not sneeze an interesting number$reaction"
    sneeq(conclusion)

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
   * @param when millisecond-timestamp of sneeze
   */
  def commitSneeze(when: Long) = {
    last = when
    count += 1
    informCount()
    resetTimer()
  }

  /**
   * Handle a sneeze event.
   */
  @JSExport
  def sneeze(): Unit = {
    val now = System.currentTimeMillis()
    if (last == 0) sneequence.innerHTML = ""
    commitSneeze(now)
  }

  /**
   * Add an entry to the sneequence.
   * @param text what happen?
   */
  def sneeq(text: String): Unit = {
    val li = document.createElement("li")
    li.innerHTML = text
    sneequence.appendChild(li)
  }

}
