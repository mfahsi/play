use std::collections::LinkedList;
use std::collections::HashMap;
use std::collections::hash_map::DefaultHasher;
use std::borrow::Borrow;
use std::collections::HashSet;

///remove duplicates from a list
fn remove_dups(l: LinkedList<i32>) -> LinkedList<i32> {
    let mut m:HashMap<i32,i32> = HashMap::new();
    let mut out = LinkedList::new();

     for ele in l.iter() {
        if !m.contains_key(ele) {
            m.insert(*ele, 1);
            out.push_back(*ele);
        }
    }   
   
    out
}




#[cfg(test)]
mod tests {
  use super::*;

  #[test]
  fn test_remove_dups() {
    let l = LinkedList::from([1, 2, 3,3, 4, 3, 5]);
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
}

