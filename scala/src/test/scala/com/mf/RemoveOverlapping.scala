package com.mf

import com.mf.RemoveOverlapping.eraseOverlapIntervals
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

type Interval = Array[Int]

object RemoveOverlapping:
  val intOrd = Ordering[Int]

  def eraseOverlapIntervals(intervals: Array[Array[Int]]): Int =
    val sortedVals: Array[Interval] =
      intervals.sorted
    var count = 0
    var previous: Option[Interval] = None
    for p <- 0 until sortedVals.length - 1 do
      val pval = previous.getOrElse(sortedVals(p))
      val curr = sortedVals(p + 1)
      if interlock(pval, curr) then
        count += 1
        previous = Some(pval)
      else previous = Some(curr)
    count

  def interlock(a: Interval, b: Interval): Boolean =
    b(0) < a(1) // && a(1) > b(0)

  given intervOrdering: Ordering[Interval] with
    def compare(a: Interval, b: Interval): Int = intOrd.compare(a(1), b(1))

class RemoveOverlappingTest extends AnyFlatSpec with should.Matchers:

  val i14 = Array(1, 4)
  val i26 = Array(2, 6)
  val i24 = Array(2, 4)
  val i56 = Array(5, 6)
  val i68 = Array(6, 8)
  val i46 = Array(4, 6)

  behavior of "remove"

  "no overlap " should "return count" in {
    eraseOverlapIntervals(Array(i14, i46, i68)) should be(0)
  }

  "no overlap " should "return correct" in {
    eraseOverlapIntervals(Array(i14, i46, i68, i24)) should be(1)
  }

  behavior of "ordering"
  "interval order" should "work" in {
    import RemoveOverlapping.given
    RemoveOverlapping.intervOrdering.compare(i24, i26) < 0 should be(true)
  }
