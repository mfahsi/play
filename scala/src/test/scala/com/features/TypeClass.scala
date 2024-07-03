package com.features

trait Random[T] {
  def generate(): T
  def generate(n: Int): Seq[T] = (1 to n).map(_ => generate())
}

enum User {
  case Premuim(user: String, subscription: String, id: Long)
  case Anonymous(user: String)
}

class TypeClass {}
