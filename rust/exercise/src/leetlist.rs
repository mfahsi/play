use std::collections::LinkedList;

fn merge_sorted_lists_iter<T: std::cmp::Ord + Clone>(x: LinkedList<T>, y: LinkedList<T>) -> LinkedList<T> {
    let mut res = LinkedList::new();
    let mut xiter = x.iter();
    let mut yiter = y.iter();
    let mut count = 0;
    let mut xcurrent = xiter.next();
    let mut ycurrent = yiter.next();
    while (count < (x.len() + y.len())) {
        count += 1;
        match (xcurrent, ycurrent) {
            (None, None) => return res,
            (Some(x), None) => {
                res.push_back(x.clone());
                xcurrent = xiter.next()
            }
            (None, Some(y)) => {
                res.push_back(y.clone());
                ycurrent = yiter.next()
            }
            (Some(x), Some(y)) if x <= y => {
                res.push_back(x.clone());
                xcurrent = xiter.next();
            }
            (Some(x), Some(y)) if x > y => {
                res.push_back(y.clone());
                ycurrent = yiter.next();
            }
            _ => { return res; }
        }
    }
    return res;
}

fn merge_sorted_lists_rec<T: std::cmp::Ord + Clone>(x: LinkedList<T>, y: LinkedList<T>) -> LinkedList<T> {
    fn merge_recursive<T: std::cmp::Ord + Clone>(
        mut x: LinkedList<T>,
        mut y: LinkedList<T>,
        mut res: LinkedList<T>,
    ) -> LinkedList<T> {
        match (x.pop_front(), y.pop_front()) {
            (None, None) => res,
            (Some(xv), None) => {
                res.push_back(xv);
                res.append(&mut x);
                res
            }
            (None, Some(yv)) => {
                res.push_back(yv);
                res.append(&mut y);
                res
            }
            (Some(xv), Some(yv)) => {
                if xv <= yv {
                    res.push_back(xv);
                    y.push_front(yv);
                } else {
                    res.push_back(yv);
                    x.push_front(xv);
                }
                merge_recursive(x, y, res)
            }
        }
    }

    merge_recursive(x, y, LinkedList::new())
}

#[cfg(test)]
mod tests {
    use std::collections::LinkedList;

    use super::*;

    #[test]
    fn test_merge_empty() {
        let x = LinkedList::from([1, 3, 5]);
        let y = LinkedList::from([2, 8, 12]);
        assert_eq!(merge_sorted_lists_iter(x.clone(), y.clone()), LinkedList::from([1, 2, 3, 5, 8, 12]));
        assert_eq!(merge_sorted_lists_rec(x.clone(), y.clone()), LinkedList::from([1, 2, 3, 5, 8, 12]));
    }

    #[test]
    fn test_merge_normal() {
        let x = LinkedList::from([1, 3, 5]);
        let y = LinkedList::new();
        assert_eq!(merge_sorted_lists_iter(x.clone(), y.clone()), LinkedList::from([1, 3, 5]));
        assert_eq!(merge_sorted_lists_rec(x, y), LinkedList::from([1, 3, 5]));
    }

    #[test]
    fn test_merge_both_empty() {
        let x: LinkedList<i32> = LinkedList::new();
        let y = LinkedList::new();
        assert_eq!(merge_sorted_lists_rec(x, y), LinkedList::new());
    }

    #[test]
    fn test_merge_one_element() {
        let x = LinkedList::from([1]);
        let y = LinkedList::from([2]);
        assert_eq!(merge_sorted_lists_rec(x, y), LinkedList::from([1, 2]));
    }
}