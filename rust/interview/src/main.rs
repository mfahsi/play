fn sum(a:i32,b:i32)->i32 {
    (a+b)
}


fn main() {
    println!("Hello, world!");
}

#[cfg(test)]
mod tests {
  use super::*;
  #[test]
    fn test_sum() {
        assert_eq!(sum(1,2), 3);
    }

}