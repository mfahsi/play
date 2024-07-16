package com.mf.exercise.lists

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable.PriorityQueue

object HeapCase {
  // given ord: Ordering[Int] = Ordering.Int.reverse

  def findKthLargest(nums: Array[Int], k: Int): Int = {
    // Define the ordering for a min-heap

    // Initialize a min-heap with the custom ordering
    val queue = new PriorityQueue[Int](using Ordering[Int].reverse)

    // Iterate over the numbers
    for num <- nums do {
      queue.enqueue(num)
      // If the size of the heap exceeds k, remove the smallest element
      if queue.size > k then queue.dequeue()
    }

    // The root of the heap is the k-th largest element
    queue.head
  }

}

class KthLargestTest extends AnyFlatSpec with should.Matchers:
  behavior of "Heap"

  import HeapCase.*

  it should "order" in {
    Array(2, 1, 3).sorted(using Ordering[Int].reverse) should be(
      Array(3, 2, 1)
    )
  }
  it should "work with distinct" in {
    findKthLargest(Array(1, 2, 3, 4), 3) should be(2)
  }

  it should "work with duplicates" in {
    findKthLargest(
      Array(1, 1, 1, 1, 2, 3, 4, 4, 5, 5, 6, 6, 6, 6),
      7
    ) should be(4)
    findKthLargest(Array(1, 1, 1, 1, 2, 3, 4, 4, 5, 5, 6), 4) should be(4)
  }
