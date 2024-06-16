mod traits;
trait Animal {
  fn make_sound(&self) -> String;
}
struct Dog;
struct Cat;
impl Animal for Dog {
  fn make_sound(&self) -> String {
    "woof".to_string()
  }
}
impl Animal for Cat {
  fn make_sound(&self) -> String {
    "miao".to_string()
  }
}
