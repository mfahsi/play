package com.mf.exercise.lists

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class StackMin {
  val data = scala.collection.mutable.ListBuffer[Int]()
  val minData = scala.collection.mutable.ListBuffer[Int]()

  def pop(): Option[Int] = {
    val index = data.size - 1
    if index < 0 then None
    else {
      val e = data.remove(index)
      minData.remove(index)
      Some(e)
    }
  }

  def peek(): Option[Int] = {
    val index = data.size - 1
    Option.when(index >= 0)(data(index)).orElse(None)
  }

  def push(e: Int): StackMin = {
    val min = currentMin()
    data.append(e)
    if e < min.getOrElse(Int.MaxValue) then {
      minData.append(e)
    } else {
      minData.append(min.getOrElse(Int.MaxValue))
    }
    this
  }

  private def currentMin(): Option[Int] = {
    val index = minData.size - 1
    Option.when(index >= 0)(minData(index)).orElse(None)
  }

  def min(): Option[Int] = currentMin()

}

class StackMinTest extends AnyFlatSpec with should.Matchers {
  behavior of "StackMin"

  def stackGen(): StackMin = {
    val s = new StackMin()
    s.push(3)
    s.push(5)
    s.push(2)
    s.push(7)
    s
  }

  it should "current checks" in {
    val stack = stackGen()
    stack.min() should be(Some(2))
    stack.peek() should be(Some(7))
  }

  it should "pop should remove last" in {
    val stack = stackGen()
    stack.pop() should be(Some(7))
    stack.pop() should be(Some(2))
  }

  it should "pop then min" in {
    val stack = stackGen()
    stack.pop() should be(Some(7))
    stack.pop() should be(Some(2))
    stack.min() should be(Some(3))
  }

  it should "manage empty stack" in {
    val stackEmpty = new StackMin()
    stackEmpty.peek() should be(None)
    stackEmpty.pop() should be(None)
  }

}
