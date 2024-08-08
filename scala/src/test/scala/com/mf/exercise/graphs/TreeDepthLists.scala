package com.mf.exercise.graphs

import com.mf.exercise.graphs.TreeDepthLists.Tree
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

trait TreeT[T]
object TreeDepthLists {
  case class Tree[T](
      val root: T,
      val left: Option[Tree[T]],
      val right: Option[Tree[T]]
  ) extends TreeT[T]

  extension [T](tree: Tree[T]) {
    def depthLists(): List[List[T]] = {
      val queue: mutable.Queue[List[Tree[T]]] =
        scala.collection.mutable.Queue(List(tree))
      val visited = scala.collection.mutable.HashSet[Tree[T]]()
      visit(queue, visited)
    }
    def visit(
        queue: scala.collection.mutable.Queue[List[Tree[T]]],
        visisted: scala.collection.mutable.HashSet[Tree[T]]
    ): List[List[T]] = {
      if queue.isEmpty then {
        List.empty
      } else {
        val current: List[Tree[T]] = queue.dequeue().toList
        val l: List[Tree[T]] = current.filterNot(visisted.contains(_)).toList
        val neighbours = current
          .map(n => List(n.left, n.right).filterNot(_.isEmpty).map(_.get))
          .flatten
        if !neighbours.isEmpty then queue.enqueue(neighbours)
        if !l.isEmpty then visisted.addAll(l)
        val rec = visit(queue, visisted)
        val result: List[T] = l.toList.map(_.root)
        rec match {
          case Nil => List(result)
          case _   => l.toList.map(_.root) :: rec
        }

      }
    }
  }
}
class TreeDepthListsTest extends AnyFlatSpec with should.Matchers {
  behavior of "Tree"

  "Listdepth" should "signle element" in {
    val tree = Tree[Int](1, None, None)
    tree.depthLists() should be(List(List(1)))
  }
  "Listdepth" should "real tree" in {
    val tree = Tree[Int](
      1,
      Some(Tree(2, Some(Tree(10, None, None)), Some(Tree(11, None, None)))),
      Some(Tree(4, Some(Tree(20, None, None)), Some(Tree(21, None, None))))
    )
    tree.depthLists() should be(List(List(1), List(2, 4), List(10, 11, 20, 21)))
  }
}
