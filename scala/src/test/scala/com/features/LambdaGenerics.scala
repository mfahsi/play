package com.features

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

object LambdaGenerics {

  type Res = [T] =>> Option[T]
  type MyTry = [X] =>> Either[Throwable, X]

  val sortAlgo: [T] => Array[T] => Array[T] = [T] => (arr: Array[T]) => arr
  val sortInts = sortAlgo[Int]

  def half(t: Int): Res[Int] = Some(1)

}
class LambdaGenericsTest extends AnyFlatSpec with should.Matchers {
  behavior of "LambdaGenerics"

  it should "LambdaGenerics" in {
    LambdaGenerics.sortInts(Array(1, 2, 3)) should be(Array(1, 2, 3))
  }

  it should "Type Lambda" in {
    LambdaGenerics.sortInts(Array(1, 2, 3)) should be(Array(1, 2, 3))
  }
}
