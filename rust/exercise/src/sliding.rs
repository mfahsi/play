fn max_vowels(s: &str, k: usize) -> usize {
    let vowels: Vec<char> = vec!['a', 'e', 'i', 'o', 'u'];
    let mut max_vowels_count = 0;
    let mut current_vowels_count = 0;
    let s_chars: Vec<char> = s.chars().collect();

    for i in 0..k {
        if vowels.contains(&s_chars[i]) {
            current_vowels_count += 1;
        }
    }

    max_vowels_count = current_vowels_count;

    for i in k..s.len() {
        if vowels.contains(&s_chars[i]) {
            current_vowels_count += 1;
        }
        if vowels.contains(&s_chars[i - k]) {
            current_vowels_count -= 1;
        }
        max_vowels_count = max_vowels_count.max(current_vowels_count);
    }

    max_vowels_count
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_max_vowels() {
        assert_eq!(max_vowels("abciiidef", 3), 3);
        assert_eq!(max_vowels("aeiou", 2), 2);
        assert_eq!(max_vowels("leetcode", 3), 2);
        assert_eq!(max_vowels("rhythm", 2), 0);
        assert_eq!(max_vowels("cdefghi", 1), 1);
        assert_eq!(max_vowels("aeioubbbaaaiii", 5), 5);
    }
}
