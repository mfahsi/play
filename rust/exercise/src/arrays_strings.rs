use std::collections::HashSet;

///are_all_unique check all char in a string are unique

fn are_all_unique(s: &str) -> bool {
    let mut ss = HashSet::new();
    ss.extend(s.chars());
    ss.len() == s.len()
}

fn are_all_unique_no_struct(s: &str) -> bool {
    let mut bitfield: i64 = 0;
    let a_int_char: i16 = 'a' as i16;

    for c in s.chars() {
        let mut int_char: i16 = c as i16;
        int_char -= a_int_char;

        if (1 << int_char) & bitfield != 0 {
            return false;
        }

        // set bit with Or assignment
        bitfield |= 1 << int_char;
    }

    true
}


fn one_char_changed(s1: &str, s2: &str) -> bool {
    let mut had_edit = false;
    for (c1, c2) in s1.chars().zip(s2.chars()) {
        if c1 != c2 && !had_edit {
            had_edit = true;
        } else if c1 != c2 {
            return false;
        }
    }
    true
}

fn one_char_inserted(s1: &str, s2: &str) -> bool {
    assert!(s1.len() == s2.len() + 1);
    let mut idx1 = 0;
    let mut idx2 = 0;
    while idx1 < s1.len() && idx2 < s2.len() {
        let char1 = s1.get(idx1..=idx1).unwrap();
        let char2 = s2.get(idx2..=idx2).unwrap();
        if char1 == char2 {
            idx2 += 1;
        }
        idx1 += 1;
    }
    idx1 >= s1.len() - 1 && idx2 == s2.len()
}

///one_away check a string is one edit from an other
fn one_edit_away(s1: &str, s2: &str) -> bool {
    if s1 == s2 {
        return true;
    }
    if ((s1.len() as i32) - (s2.len() as i32)).abs() > 1 {
        return false;
    }
    if s1.len() == s2.len() {
        return one_char_changed(s1, s2);
    }
    if s2.len() > s1.len() {
        return one_char_inserted(s2, s1);
    }
    one_char_inserted(s1, s2)
}


type Matrix = Vec<Vec<i32>>;

/// zeroMatrix set a matrix to zero all row and column if i,j is 0
fn zero_matrix(matrix: &mut Matrix) -> &mut Matrix {
    let m = matrix.len();
    let n = matrix[0].len();
    let copy = matrix.clone();

    for i in 0..=(m - 1) {
        for j in 0..=(n - 1) {
            println!("check {},{} ?? {}", i, j, copy[i][j]);
            if copy[i][j] == 0 {
                set_zeros(matrix, i, j);
            }
        }
    }
    matrix
}

fn set_zeros(matrix: &mut Matrix, i: usize, j: usize) -> &mut Matrix {
    matrix[i].iter_mut().for_each(|x| *x = 0);
    for cur in 0..=(matrix.len() - 1) {
        matrix[cur][j] = 0;
    }
    matrix
}


#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_are_all_unique_edge_cases() {
        assert_eq!(are_all_unique(""), true);
        assert_eq!(are_all_unique("a"), true);
        assert_eq!(are_all_unique("aa"), false);
    }

    #[test]
    fn test_are_all_unique_normal_cases() {
        assert_eq!(are_all_unique("12$74"), true);
        assert_eq!(are_all_unique("12$724"), false);
    }

    #[test]
    fn test_are_all_unique_no_struct() {
        assert_eq!(are_all_unique_no_struct("abc"), true);
        assert_eq!(are_all_unique_no_struct("abcb"), false);
    }


    #[test]
    fn test_one_away() {
        assert_eq!(one_edit_away("abcd", "axcd"), true);
        assert_eq!(one_edit_away("abcd", "abxd"), true);
        assert_eq!(one_edit_away("abcd", "abxdy"), false);
        assert_eq!(one_edit_away("abcd", "axyd"), false);
    }

    #[test]
    fn test_zero_matrix() {
        let mut mx: Matrix = vec![vec![1, 2, 3], vec![4, 5, 0], vec![7, 8, 9]];
        let expected: Matrix = vec![vec![1, 2, 0], vec![0, 0, 0], vec![7, 8, 0]];
        let mut v = zero_matrix(&mut mx);
        // assert_eq!(v.to_owned(), expected);
        assert_eq!(v.to_owned(), expected);
    }
}