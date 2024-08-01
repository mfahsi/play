package com.mf.exercise

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

object MaxVolwels {
  val vowels = Set('a', 'e', 'i', 'o', 'u')
  val isVowel = vowels.contains(_)
  def maxVowels(s: String, k: Int): Int = {
    if k == 0 then 0
    else if s.length < k then 0
    else
      val array = s.substring(0, k)
      val count = array.count(isVowel)
      Range(0, s.length - k - 1, 1)
        .foldLeft((count, count))((b, i) => {
          val old = if isVowel(s.charAt(i)) then 1 else 0
          val added = if isVowel(s.charAt(i + k)) then 1 else 0
          val countUpdated = b._1 - old + added
          (countUpdated, Math.max(countUpdated, b._2))
        })
        ._2
  }

  def maxVowels2(s: String, k: Int): Int = {
    val vowels = Set('a', 'e', 'i', 'o', 'u')
    var maxVowelsCount = 0
    var currentVowelsCount = 0

    for i <- 0 until k do {
      if isVowel(s(i)) then currentVowelsCount += 1
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

class TestMaxVowels extends AnyFlatSpec with should.Matchers {

  behavior of "MaxVowels"

  "max volwels" should "count max within size" in {
    MaxVolwels.maxVowels("hello abcd heeehaa xxxxzas", 6) should be(5)
  }
  "max volwels" should "empty string" in {
    MaxVolwels.maxVowels("", 3) should be(0)
  }
  "max volwels" should "handle empty size" in {
    MaxVolwels.maxVowels("aefd", 0) should be(0)
  }

}
