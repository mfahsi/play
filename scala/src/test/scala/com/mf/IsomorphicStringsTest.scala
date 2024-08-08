package com.mf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.should

import scala.collection.mutable
import scala.util.boundary

object IsomorphicStrings:
  def isIsomorphic(s: String, t: String): Boolean = {
    val ismo = mutable.Map[Char, Char]()
    val values = mutable.HashSet[Char]()
    boundary:
      if s.size != t.size then boundary.break(false)

      def addOrAccept(l: Char, r: Char): Boolean = {
        if !ismo.contains(l) then {
          if values.contains(r) then {
            boundary.break(false)
          } else {
            ismo.put(l, r)
            values.add(r)
            true
          }
        } else {
          ismo.getOrElse(l, r) == r
        }
      }

      var res: Boolean = true
      for i <- 0 until s.size do {
        val lc = s.charAt(i)
        val rc = t.charAt(i)
        res = addOrAccept(lc, rc) && res
        if !res then boundary.break(res)
      }
      res
  }

class IsomorphicStringsTest extends AnyFlatSpec with Matchers:

  behavior of "Isomorphic"

  it should "fail for non iso" in {
    IsomorphicStrings.isIsomorphic("Hello", "Celmi") should be(false)
  }

  it should "accept iso" in {
    IsomorphicStrings.isIsomorphic("Hello", "Cekki") should be(true)
  }

  it should "check size" in {
    IsomorphicStrings.isIsomorphic("Hello", "Helloo") should be(false)
  }
  it should "fail for non subj" in {
    IsomorphicStrings.isIsomorphic("Hello", "Ckkki") should be(false)
  }
