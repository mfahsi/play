pub const VOWELS: &[char] = &['a', 'e', 'i', 'o', 'u'];

fn is_vowel(c: &char) -> bool {
    VOWELS.contains(&c)
}

fn countVowels(s: &str) -> i32 {
    return s.chars().into_iter()
    .filter(|c| is_vowel(c))
    .count().try_into().unwrap_or(0)
  }


#[cfg(test)]
mod tests {
use super::*;



    #[test]
    fn test_count_vowels() {
        assert_eq!(count_vowels("hello"), 2);
        assert_eq!(count_vowels("world"), 1);
        assert_eq!(count_vowels("rust"), 1);
        assert_eq!(count_vowels("programming"), 3);
    }

}
