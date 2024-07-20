package com.mf.exercise.trie

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

object TrieNodeFP {
  def apply(): TrieNodeFP =
    TrieNodeFP('*', Map.empty[Char, TrieNodeFP])

  def find(prefix: List[Char], node: TrieNodeFP): Option[TrieNodeFP] = {
    prefix match {
      case Nil => Some(node)
      case c :: tail =>
        node.children.get(c) match {
          case None        => None
          case Some(child) => find(tail, child)
        }
    }
  }
}

case class TrieNodeFP(
    c: Char,
    children: Map[Char, TrieNodeFP],
    word: Boolean = false
) {
  def insertWord(word: List[Char]): TrieNodeFP = {
    word match {
      case Nil      => this
      case c :: Nil => insert(c, Nil)
      case c :: tail =>
        val updatedChild = insert(c, tail).children(c).insertWord(tail)
        copy(children = children + (c -> updatedChild))
    }
  }

  def insert(c: Char, remain: List[Char]): TrieNodeFP = {
    val childNode = children.get(c) match {
      case None     => TrieNodeFP(c, Map.empty, remain.isEmpty)
      case Some(ch) => if remain.isEmpty then ch.copy(word = true) else ch
    }
    copy(children = children + (c -> childNode))
  }

  def startsWith(prefix: List[Char]): Boolean = {
    TrieNodeFP.find(prefix, this).isDefined
  }

  def search(word: List[Char]): Boolean = {
    TrieNodeFP.find(word, this).exists(_.word)
  }
}

class TrieFPTest extends AnyFlatSpec with Matchers {
  behavior of "Trie"

  it should "find word" in {
    val trie = TrieNodeFP()
      .insertWord("Hello".toCharArray.toList)
      .insertWord("Hello Jane".toCharArray.toList)
      .insertWord("Apple Jane".toCharArray.toList)

    trie.search("Hel".toCharArray.toList) should be(false)
    trie.search("Hello Jane".toCharArray.toList) should be(true)
  }

  it should "answer prefix startsWith" in {
    val trie = TrieNodeFP()
      .insertWord("Hello".toCharArray.toList)
      .insertWord("Hello Jane".toCharArray.toList)
      .insertWord("Apple Jane".toCharArray.toList)

    trie.startsWith("Hello".toCharArray.toList) should be(true)
    trie.startsWith("Hel".toCharArray.toList) should be(true)
    trie.startsWith("Hela".toCharArray.toList) should be(false)
  }
}
