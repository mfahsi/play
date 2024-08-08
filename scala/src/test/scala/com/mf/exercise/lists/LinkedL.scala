package com.mf.exercise.lists

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

enum LinkedL {
  case Const(head: Int, tail: LinkedL) extends LinkedL
  case End extends LinkedL

  def partition[T](e: LinkedL, f: Int => Boolean): LinkedL = {
    e match {
      case Const(h, t) =>
        f(h) match {
          case true  => Const(h, partition(t, f))
          case false => concat(partition(t, f), h)
        }
      case End => End
    }
  }

  def concat(e1: LinkedL, e2: Int): LinkedL = {
    e1 match {
      case Const(h, t) => Const(h, concat(t, e2))
      case End         => Const(e2, End)
    }
  }
}

object LinkedL:

  import LinkedL.*

class LinkedLTest extends AnyFlatSpec with should.Matchers:
  behavior of "LinkedL"

  import LinkedL.*

  it should "partition" in {
    val l = Const(1, Const(2, Const(3, Const(4, End))))
    val p = l.partition(l, _ < 3)
    p should be(Const(1, Const(2, Const(4, Const(3, End)))))
  }
