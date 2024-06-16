use std::borrow::Borrow;
use std::cell::RefCell;
use std::collections::HashMap;
use std::collections::LinkedList;
use std::rc::Rc;

/// Remove duplicates from a list
fn remove_dups(l: LinkedList<i32>) -> LinkedList<i32> {
  let mut m: HashMap<i32, i32> = HashMap::new();
  let mut out = LinkedList::new();

  for ele in l.iter() {
    if !m.contains_key(ele) {
      m.insert(*ele, 1);
      out.push_back(*ele);
    }
  }

  out
}

#[derive(Debug, PartialEq, Eq)]
struct Node {
  data: Rc<RefCell<i32>>,
  next: Option<BNode>,
}

type BNode = Rc<RefCell<Node>>;

impl Node {
  fn new(data: i32) -> Node {
    Node {
      data: Rc::new(RefCell::new(data)),
      next: None,
    }
  }
}

struct MyList {
  head: Option<BNode>,
}

trait IntList {
  fn new() -> Self;
  fn add(&mut self, data: &i32);
  fn tail(&self) -> Option<BNode>;
}

fn intersect(_l1: MyList, _l2: MyList) -> Option<Node> {
  None
}

fn last(n: &Rc<RefCell<Node>>) -> Rc<RefCell<Node>> {
  match n.as_ref().borrow().next.clone() {
    None => Rc::clone(n),
    Some(next_node) => last(&next_node),
  }
}

impl IntList for MyList {
  fn new() -> Self {
    MyList { head: None }
  }

  fn add(&mut self, aData: &i32) {
    let new_node = Rc::new(RefCell::new(Node::new(*aData)));

    match self.head {
      None => {
        self.head = Some(new_node);
      }
      Some(ref head) => {
        let tail_node = last(head);
        tail_node.borrow_mut().next = Some(new_node);
      }
    }
  }

  fn tail(&self) -> Option<BNode> {
    self.head.as_ref().map(|head| last(head))
  }
}

#[cfg(test)]
mod tests {
  use super::*;

  #[test]
  fn test_creation() {
    let l = LinkedList::from([1, 2, 3, 3, 4, 3, 5]);
    let l2 = LinkedList::from([1, 2, 3, 4, 5]);
    assert_eq!(remove_dups(l), l2);
  }

  #[test]
  fn test_remove_dups() {
    let l = LinkedList::from([1, 2, 3, 3, 4, 3, 5]);
    let l2 = LinkedList::from([1, 2, 3, 4, 5]);
    assert_eq!(remove_dups(l), l2);
  }

  #[test]
  fn test_remove_dups_empty() {
    assert_eq!(remove_dups(LinkedList::new()), LinkedList::new());
  }

  #[test]
  fn test_remove_dups_single_element() {
    assert_eq!(remove_dups(LinkedList::from([1])), LinkedList::from([1]));
  }

  #[test]
  fn test_remove_dups_no_duplicates() {
    let l = LinkedList::from([1, 2, 3, 4, 5]);
    assert_eq!(remove_dups(l.clone()), l);
  }

  #[test]
  fn test_int_list_tail() {
    let mut l: MyList = IntList::new();
    l.add(&1);
    l.add(&2);
    println!("{:?}", l.tail());
  }
  fn test_intersect() {
    let mut l: MyList = IntList::new();
    l.add(&1);
    l.add(&2);
    let mut l2: MyList = IntList::new();
    l.add(&1);
    l.add(&2);
    println!("{:?}", l.tail());
  }
}
