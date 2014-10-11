package achoo

trait Assessment {
  val notability: String
  def matches(sneezeCount: Int): Boolean
  def apply(sneezeCount: Int) = if (matches(sneezeCount)) Some(notability) else None
}
