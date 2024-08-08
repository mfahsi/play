package com.mf.exercise.lists

class MutableDoublyLinkedList[A] {
  private var head: Option[Node] = None
  private var tail: Option[Node] = None

  def append(value: A): Unit = {
    val newNode = new Node(value, tail, None)
    tail match {
      case Some(node) => node.next = Some(newNode)
      case None       => head = Some(newNode)
    }
    tail = Some(newNode)
  }

  def prepend(value: A): Unit = {
    val newNode = new Node(value, None, head)
    head match {
      case Some(node) => node.prev = Some(newNode)
      case None       => tail = Some(newNode)
    }
    head = Some(newNode)
  }

  def last: Option[A] = tail.map(_.value)

  def first: Option[A] = head.map(_.value)

  def length: Int = {
    var count = 0
    var current = head
    while current.isDefined do {
      count += 1
      current = current.get.next
    }
    count
  }

  override def toString: String = {
    val sb = new StringBuilder("MutableDoublyLinkedList(")
    var current = head
    while current.isDefined do {
      sb.append(current.get.value)
      current = current.get.next
      if current.isDefined then sb.append(", ")
    }
    sb.append(")").toString()
  }

  private class Node(
      var value: A,
      var prev: Option[Node],
      var next: Option[Node]
  )
}

@main def run = {

// Example usage:
  val mutableList = new MutableDoublyLinkedList[Int]
  mutableList.append(1)
  mutableList.append(2)
  mutableList.append(3)
  mutableList.append(4)
  mutableList.append(5)
  mutableList.prepend(0)

  println(mutableList)
  println(mutableList.last)
  println(mutableList.first)
  println(mutableList.length)
}
