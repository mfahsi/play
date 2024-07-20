package com.mf.exercise.trie

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

object TrieNode {
  def apply(): TrieNode =
    TrieNode('*', scala.collection.mutable.Map[Char, TrieNode]())

  def find(prefix: List[Char], node: TrieNode): Option[TrieNode] = {
    prefix match {
      case Nil => Some(node)
      case c :: tail =>
        node.child(c) match {
          case None        => None
          case Some(child) => find(tail, child)
        }
    }
  }
}
case class TrieNode(
    c: Char,
    var children: scala.collection.mutable.Map[Char, TrieNode],
    var word: Boolean = false
) {
  def child(c: Char): Option[TrieNode] = children.get(c)

  def insert(c: Char, remain: List[Char]): Option[TrieNode] = child(c) match {
    case None => {
      children.put(
        c,
        TrieNode(
          c,
          scala.collection.mutable.Map[Char, TrieNode](),
          remain.isEmpty
        )
      )
      child(c)
    }
    case Some(ch) => {
      if ch.word then {
        Some(ch)
      } else {
        val cn = ch.copy(word = word)
        children.put(c, cn)
        child(c)
      }
    }
  }

  def insertWord(word: List[Char]): Option[TrieNode] = {
    val len = word.length
    val chars = word.toList
    chars match {
      case Nil      => None
      case c :: Nil => insert(c, Nil)
      case h :: tail => {
        insert(h, tail).flatMap(_.insertWord(tail))
      }
    }
  }

  def startsWith(prefix: List[Char]): Boolean = {
    TrieNode.find(prefix, this).isDefined
  }

  def search(prefix: List[Char]): Boolean = {
    TrieNode.find(prefix, this).map(_.word).getOrElse(false)
  }

}

class TrieTest extends AnyFlatSpec with Matchers {
  behavior of "Trie"

  it should "find word" in {
    val trie = TrieNode();
    trie.insertWord("Hello".toCharArray.toList)
    trie.insertWord("Hello Jane".toCharArray.toList)
    trie.insertWord("Apple Jane".toCharArray.toList)
    trie.insertWord("Hello".toCharArray.toList)
    trie.search("Hel".toCharArray.toList) should be(false)
    trie.search("Hello Jane".toCharArray.toList) should be(true)
  }

  it should "answer prefix startsWith" in {
    val trie = TrieNode();
    trie.insertWord("Hello".toCharArray.toList)
    trie.insertWord("Hello Jane".toCharArray.toList)
    trie.insertWord("Apple Jane".toCharArray.toList)
    trie.startsWith("Hello".toCharArray.toList) should be(true)
    trie.startsWith("Hel".toCharArray.toList) should be(true)
    trie.startsWith("Hela".toCharArray.toList) should be(false)
  }

}
