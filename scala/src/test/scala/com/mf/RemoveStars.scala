package com.mf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

object RemoveStars:
  def removeStars(s: String): String = {
    val rev: String = s.reverse
    removeStars(rev, "", 0).reverse
  }

  def removeStarsStack(s: String): String = {
    val stack = new scala.collection.mutable.Stack[Char]()
    for ch <- s do {
      if ch == '*' then {
        if stack.nonEmpty then stack.pop()
      } else {
        stack.push(ch)
      }
    }
    stack.reverse.mkString
  }

  def removeStars(s: String, out: String, stacked: Int): String = {
    println("out=" + out)
    s match {
      case e if e.isEmpty => out
      case x if x.startsWith("*") =>
        removeStars(s.substring(1), out, stacked + 1)
      case y if !y.startsWith("*") && stacked > 0 =>
        removeStars(y.substring(1), out, stacked - 1)
      case z if stacked > 0 => removeStars(z.substring(1), out, stacked - 1)
      case n if stacked == 0 =>
        removeStars(n.substring(1), out + n.substring(0, 1), 0)
    }
  }

class RemoveStartsTest extends AnyFlatSpec with should.Matchers:
  import RemoveStars.*

  behavior of "RemoveStar"

  it should "remove star and character in left" in {
    removeStars("Leet*Code") should be("LeeCode")
  }

  it should "remove star and character neutral" in {
    removeStarsStack("LeetCode") should be("LeetCode")
  }

  it should "handle edge cases with stars at the end" in {
    removeStarsStack("a*") should be("")
  }

  it should "handle edge cases with multiple characters and stars" in {
    removeStarsStack("ab*c*d*e*") should be("a")
  }
