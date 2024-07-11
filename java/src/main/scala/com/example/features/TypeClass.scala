package com.example.features

class TypeClass {}
import scala.compiletime.{erasedValue, summonInline}
import scala.deriving.*

trait RandomGen[A]:
  def generate(): A

inline def summonAll[A <: Tuple]: List[RandomGen[_]] =
  inline erasedValue[A] match
    case _: EmptyTuple => Nil
    case _: (t *: ts)  => summonInline[RandomGen[t]] :: summonAll[ts]

def toTuple(xs: List[_], acc: Tuple): Tuple =
  xs match
    case Nil      => acc
    case (h :: t) => h *: toTuple(t, acc)

object RandomGen:

  inline given derived[A](using m: Mirror.Of[A]): RandomGen[A] =
    lazy val instances = summonAll[m.MirroredElemTypes]
    inline m match
      case s: Mirror.SumOf[A]     => deriveSum(s, instances)
      case p: Mirror.ProductOf[A] => deriveProduct(p, instances)

  private def deriveSum[A](
      s: Mirror.SumOf[A],
      instances: => List[RandomGen[_]]
  ): RandomGen[A] =
    new RandomGen[A]:
      def generate(): A =
        instances(scala.util.Random.nextInt(instances.size))
          .asInstanceOf[RandomGen[A]]
          .generate()

  private def deriveProduct[A](
      p: Mirror.ProductOf[A],
      instances: => List[RandomGen[_]]
  ): RandomGen[A] =
    new RandomGen[A]:
      def generate(): A =
        p.fromProduct(toTuple(instances.map(_.generate()), EmptyTuple))

end RandomGen
