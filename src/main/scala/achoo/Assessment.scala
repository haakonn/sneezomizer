package achoo

/**
 * An assessment of a sneeze sequence, to determine whether it matches a certain
 * criterion.
 */
trait Assessment {
  val notability: String
  def matches(state: State): Boolean
  def apply(state: State) = if (matches(state)) Some(notability) else None
}
