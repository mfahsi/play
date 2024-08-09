package com.mf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.annotation.tailrec

/** Definition for singly-linked list. * */
class ListNode(_x: Int = 0, _next: ListNode = null):
  var next: ListNode = _next
  var x: Int = _x

object MiddleLinkedList:

  @tailrec
  @scala.annotation.tailrec
  def lengths(ln: ListNode, acc: Int = 0): Int = ln.next match {
    case null        => acc + 1
    case a: ListNode => lengths(a, acc + 1)
  }
  @tailrec
  def asList(ln: ListNode, acc: List[Int] = List()): List[Int] = ln.next match
    case null => acc.appended(ln.x)
    case y    => asList(y, acc.appended(ln.x))

  def deleteMiddle(head: ListNode): ListNode = {
    if head == null || head.next == null then {
      return null
    } else {
      val element = (lengths(head) / 2).toInt
      val ahead = head
      var curr = head
      for i <- 1 until element do {
        curr = curr.next
      }
      if curr.next != null then {
        curr.next = curr.next.next
      } else {
        curr.next = null
      }
      ahead
    }
  }

class MiddleLinkedListTest extends AnyFlatSpec, should.Matchers:
  import MiddleLinkedList.*
  behavior of "middlelist"
  it should "delete middle" in {
    val a = MiddleLinkedList
      .deleteMiddle(
        ListNode(1, ListNode(2, ListNode(3)))
      )
    asList(a) should be(asList(ListNode(1, ListNode(3, null))))
  }

  it should "lenght" in {
    lengths(ListNode(1, ListNode(2, ListNode(3)))) should be(3)
  }
