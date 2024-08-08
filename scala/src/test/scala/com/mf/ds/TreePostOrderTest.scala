package com.mf.ds

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable
import scala.collection.mutable.*
import scala.io.StdIn

class TreePostOrderTest extends AnyFlatSpec with should.Matchers {

  type T = Int
  case class Node[T](v: Int, left: Node[T], right: Node[T]):
    def isLeaf: Boolean = left == null && right == null

  object TreeF:
    def postOrderPrint(tree: Node[T]): Unit = {
      val stack = mutable.Stack[Node[T]]()
      val done = mutable.Set[Node[T]]()
      stack.push(tree)
      rec(stack, done)
      def process(
          stack: mutable.Stack[Node[T]],
          done: mutable.Set[Node[T]]
      ): Unit = {
        val pop = stack.pop()
        println(pop.v)
        done.addOne(pop)
        rec(stack, done)
      }

      def isDone(t: Node[T], c: mutable.Set[Node[T]]): Boolean = {
        t == null || c.contains(t)
      }

      def rec(
          stack: mutable.Stack[Node[T]],
          done: mutable.Set[Node[T]]
      ): Unit = {
        // println(s"stack ${stack.toList.map(_.v)}")
        if stack.isEmpty then {} else {
          val top: Node[T] = stack.top
          if top.isLeaf || (isDone(top.left, done) && isDone(top.right, done))
          then {
            process(stack, done)
          } else {
            // stack.push(top)
            if !isDone(top.right, done) then {
              stack.push(top.right)
            }
            if !isDone(top.left, done) then {
              stack.push(top.left)
            }
            rec(stack, done)
          }
        }
      }
    }

  "post order tree" should "print" in {
    val tree = Node[T](
      1,
      null,
      Node(
        2,
        null,
        Node(5, Node(3, null, Node(4, null, null)), Node(6, null, null))
      )
    )
    TreeF.postOrderPrint(tree)

  }
}
