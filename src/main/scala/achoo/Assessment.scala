package achoo

trait Assessment {
  val notability: String
  def matches(state: State): Boolean
  def apply(state: State) = if (matches(state)) Some(notability) else None
}
