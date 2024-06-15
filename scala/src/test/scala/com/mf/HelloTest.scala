package com.mf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.should

class HelloTest extends AnyFlatSpec with Matchers {

  behavior of "HelloApp"

  it should "should say hello" in {
   1+1 should be (2)
  }
}
