package com.mf

import com.mf.CountPing.RecentCounter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

object CountPing:

  class RecentCounter():
    val DURATION: Int = 3000
    var requests = scala.collection.mutable.Queue[Int]()

    def ping(t: Int): Int =
      requests.enqueue(t)
      while requests.front < t - DURATION do requests.dequeue()
      requests.size

class CountPingTest extends AnyFlatSpec with should.Matchers:
  behavior of "RecentCounter"

  it should "count by interval" in {
    val count = new RecentCounter()
    count.ping(1) should be(1)
    count.ping(200)
    count.ping(3000) should be(3)
    count.ping(3201) should be(2)
  }

  it should "count first" in {
    val count = new RecentCounter()
    count.ping(1) should be(1)
  }
