object MaxVowels2 {

  def maxVowels(s: String, k: Int): Int = {
    val vowels = Set('a', 'e', 'i', 'o', 'u')
    var maxVowelsCount = 0
    var currentVowelsCount = 0

    for i <- 0 until k do {
      if vowels.contains(s(i)) then currentVowelsCount += 1
    }

    maxVowelsCount = currentVowelsCount

    for i <- k until s.length do {
      if vowels.contains(s(i)) then currentVowelsCount += 1
      if vowels.contains(s(i - k)) then currentVowelsCount -= 1
      maxVowelsCount = math.max(maxVowelsCount, currentVowelsCount)
    }

    maxVowelsCount
  }
}
