object StringUtils extends App {

  /**
   * Gets the number of chars in a string
   *
   * @param s A string
   * @return The number of chars in this string
   */
  def length(s: String): Int = s.length

  /**
   * Extracts a letter from a String
   *
   * @param s   A String
   * @param pos The index of the letter
   * @return The char at position pos
   */
  def charAt(s: String, pos: Int): Char = s.charAt(pos)

  println(length("Bob"))
  println(charAt("bou", 2))

}
