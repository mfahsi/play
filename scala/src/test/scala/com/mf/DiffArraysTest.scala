package com.mf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.should

object ArrayDifference:
  def findDifferenceRec(
      nums1: Array[Int],
      nums2: Array[Int],
      out: Array[Array[Int]]
  ): Array[Array[Int]] = {
    nums1 match {
      case Array(x, tail*) => {
        if nums2.contains(x) then {
          if tail.isEmpty then Array(out(0), out(1).filterNot(v => v == x))
          else
            findDifferenceRec(
              tail.toArray,
              nums2.filterNot(v => v == x),
              Array(out(0), out(1).filterNot(v => v == x))
            )
        } else {
          if !tail.isEmpty then
            findDifferenceRec(
              tail.toArray,
              nums2,
              Array(out(0).appended(x), out(1))
            )
          else Array(out(0).appended(x), out(1))
        }
      }
    }

  }

  def findDifference(nums1: Array[Int], nums2: Array[Int]): List[List[Int]] =
    val result: Array[Array[Int]] =
      findDifferenceRec(nums1.distinct, nums2, Array(Array[Int](), nums2))
    result.map((arr: Array[Int]) => List.from(arr).distinct).toList

  def findDifferenceWithSetApi(
      nums1: Array[Int],
      nums2: Array[Int]
  ): List[List[Int]] =
    val set1 = nums1.toSet
    val set2 = nums2.toSet

    val diff1 = set1.diff(set2).toList
    val diff2 = set2.diff(set1).toList

    List(diff1, diff2)

class DiffArraysTest extends AnyFlatSpec with Matchers:

  behavior of "Difference"

  it should "diff of array" in {
    ArrayDifference.findDifference(
      Array(-3, 6, -5, 4, 5, 5),
      Array(6, 6, -3, -3, 3, 5)
    ) should be(
      List(List(-5, 4), List(3))
    )
  }

  it should "diff of array set api" in {
    ArrayDifference.findDifferenceWithSetApi(
      Array(-3, 6, -5, 4, 5, 5),
      Array(6, 6, -3, -3, 3, 5)
    ) should be(
      List(List(-5, 4), List(3))
    )
  }

  it should "diff of array with dups" in {
    ArrayDifference.findDifference(
      Array(1, 2, 3, 3, 7),
      Array(4, 2, 6, 6, 8)
    ) should be(
      List(List(1, 3, 7), List(4, 6, 8))
    )
  }
