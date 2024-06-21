package com.mf.exercise.lists

import com.mf.exercise.lists.ListNum.{asNum, sumNumbers}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should


object ListNum:
  type Digit = Int
  type NUM = List[Digit]

  //@tailrec
  def sumList(nums: List[NUM], out: NUM): List[Digit] = {
    val heads = nums.map(_.headOption)
    val tails: List[NUM] = nums.filterNot(_.isEmpty).map(_.tail).filter(_ != Nil)
    val headsum = heads.filter(_.isDefined).map(_.getOrElse(0)).sum
    println(headsum)
    val sum: List[Digit] = ListNum.asNum(headsum)
    val res: NUM = out.appended(sum.head)
    (sum.tail, tails) match {
      case (Nil, Nil) => res
      case (Nil, tails) => sumList(tails, res)
      case _ => sumList(tails ++ List(sum.tail), res)
    }
  }

  def sumNumbers(n1: NUM, n2: NUM): NUM = {
    sumList(List(n1, n2), List())
  }

  def asNum(n: Int): NUM = {
    n.toString.map(_.asDigit).toList.reverse
  }

  extension [A](list: List[A]) {
    def quickSort(implicit cmp: Ordering[A]): List[A] = {
      list match {
        case Nil => Nil
        case head :: Nil => head :: Nil
        case _ => {
          val pivot = list.head
          val split = list.partition(cmp.compare(_, pivot) < 0)
          val less: List[A] = split._1
          val greaterOrEqual = (split._2).partition(cmp.compare(_, pivot) > 0)
          val greater: List[A] = greaterOrEqual._1
          val pivotList = greaterOrEqual._2
          less.quickSort ++ pivotList ++ greater.quickSort
        }
      }
    }
  }


class ListNumTest extends AnyFlatSpec with should.Matchers {
  behavior of "ListNum"

  it should "should say hello" in {
    sumNumbers(List(8, 5, 1), List(3, 7, 1)) should be(List(1, 3, 3))
  }

  it should "asNum" in {
    asNum(34) should be(List(4, 3))
  }

  behavior of "QuickSort"
  it should "sort numbers" in {
    import ListNum.*
    List[Int](3, 4, 8, 2).quickSort should be(List(2, 3, 4, 8))
  }
}





