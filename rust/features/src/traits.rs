use super::*;
pub fn notify(item: &impl Animal) -> String {
  println!("souound= {}", item.make_sound());
  item.make_sound()
}

pub fn notify_dynamic(item: &dyn Animal) -> String {
  println!("souound= {}", item.make_sound());
  item.make_sound()
}

///write tests here
#[cfg(test)]
mod tests {
  use super::*;
  #[test]
  fn test_notify() {
    let dog = Dog {};
    let cat = Cat {};
    assert_eq!(notify(&dog), "woof".to_string());
    assert_eq!(notify(&cat), "miao".to_string());
  }
  #[test]
  fn test_notify_dynamic() {
    let dog = Dog {};
    let cat = Cat {};
    assert_eq!(notify_dynamic(&dog), "woof".to_string());
    assert_eq!(notify_dynamic(&cat), "miao".to_string());
  }
}
