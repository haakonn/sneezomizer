package achoo

import achoo.assessments.{Prime, Fibonacci}

import scala.scalajs.js.JSApp
import org.scalajs.dom
import dom.document

import scala.scalajs.js.annotation.JSExport

/**
 * Encapsulate state.
 * @param timestamps timestamps of the sneezes in millisecond-epochs (newest first)
 */
case class State(timestamps: List[Long]) {
  def add(ts: Long) = State(ts :: timestamps)
  val count = timestamps.size
  def isStart = timestamps.isEmpty
}

/**
 * Helps you determine whether you have the rare gift of sneezing a prime
 * number of times per sequence.
 */
object Sneezomizer extends JSApp {

  /**
   * These are the assessments the sneeze number will be subjected to.
   */
  val assessments = Set(Prime, Fibonacci)

  val sneezomatopoeia = Vector("Weshee", "Hachu", "Whereshe", "A-haasha", "Achoo", "Pichee", "Ochoooh")

  val startState = State(List.empty)
  var state = startState

  val cutoffSeconds = 10

  var timerHandle = 0
  lazy val sneequence = document.getElementById("sneequence")
  lazy val button = document.getElementById("sneeze-button")

  def main(): Unit = {
  }

  def randomSneeze = util.Random.shuffle(sneezomatopoeia).head

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
    val notabilities = assessments.map(_(state)).flatten
    val reaction = react(notabilities.size)
    val conclusion =
      if (notabilities.size > 0) {
        val notabilityStr = notabilities.mkString(" ")
        s"You sneezed a $notabilityStr number$reaction"
      } else
        s"You did not sneeze an interesting number$reaction"
    sneeq(conclusion)
    state = startState
  }

  def resetTimer() = {
    dom.window.clearTimeout(timerHandle)
    timerHandle = dom.window.setTimeout(() => { conclude() }, cutoffSeconds * 1000)
  }

  def times(count: Int) = count match {
    case 1 => "once"
    case 2 => "twice"
    case _ => s"$count times"
  }

  /**
   * Tell the sneezer he dun good.
   */
  def informCount() = sneeq(s"You have sneezed ${times(state.count)}!")

  /**
   * The sneeze is good.
   * @param when millisecond-timestamp of sneeze
   */
  def commitSneeze(when: Long) = {
    state = state.add(when)
    informCount()
    resetTimer()
  }

  /**
   * Handle a sneeze event.
   */
  @JSExport
  def sneeze(): Unit = {
    val now = System.currentTimeMillis()
    if (state.isStart) sneequence.innerHTML = ""
    commitSneeze(now)
    button.innerHTML = randomSneeze
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
