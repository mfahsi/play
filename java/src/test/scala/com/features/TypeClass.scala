package com.features

import com.example.features.RandomGen
import com.features.UserPackage.User
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

object RandomGenGivens {
  given strRand: RandomGen[String] with
    def generate(): String = scala.util.Random.nextString(3)

  given longRand: RandomGen[Long] with
    def generate(): Long = scala.util.Random.nextLong(2)
}

object UserPackage {
  import RandomGen.derived
  import RandomGenGivens.given
  trait UserLogin {
    def login(): String
  }
  enum User extends UserLogin derives RandomGen:
    case Premuim(user: String, subscription: String, id: Long)
    case Anonymous(user: String)

    def login(): String = this match {
      case Anonymous(user)                 => user
      case Premuim(user, subscription, id) => user
    }
}

class TypeClassTest extends AnyFlatSpec with should.Matchers {

  case class UserSetting(user: User.Anonymous)

  "derive enum rand " should "derive premuim" in {
    val user = User.Premuim("user", "subscription", 1L)

    val userG = User.derived$RandomGen.generate()
    println(userG)
    userG.login() should not be empty

  }

}
