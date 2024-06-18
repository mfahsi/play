use std::{cell::RefCell, rc::Rc};

enum ConsList<T> {
  Cons(T, Option<Rc<RefCell<ConsList<T>>>>),
  Nil,
}
//use crate::ConsList::{Cons, Nil};

impl ConsList<i32> {
  fn new() -> Self {
    ConsList::Nil
  }
}
