package com.mf.exercise.lists

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable.Stack

type Plate = Int

class StackSet[Plate](val stackCapacity: Int = 4) {
  var stacks: Stack[Stack[Plate]] = Stack(Stack())

  var current: Int = 0

  def stackCount(): Int = stacks.size

  def peekSize(): Int = stacks.top.size

  def top(): Plate = stacks.top.top

  def pop(): Plate = {
    val stack = stacks.top
    if (stack.isEmpty) {
      stacks.pop()
      pop()
    } else {
      stack.pop()
    }
  }

  def push(p: Plate): Unit = {
    val stack = stacks.top
    if (stack.size >= stackCapacity) {
      stacks.push(Stack())
      push(p)
    } else {
      stack.push(p)
    }
  }
}

class StackSetTest extends AnyFlatSpec with should.Matchers {
  behavior of "Stacks"

  it should "resize when full" in {
    val staSet = new StackSet[Int](2)
    staSet.push(Int.box(1))
    staSet.push(2)
    staSet.stackCount() should be(1)
    staSet.push(3)
    staSet.stackCount() should be(2)
    staSet.pop() should be(3)
    staSet.pop()
    staSet.stackCount() should be(1)
  }
}
