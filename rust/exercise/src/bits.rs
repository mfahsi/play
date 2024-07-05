fn flippingBits(n: i64) -> i64 {
    // Mask the input to get the least significant 32 bits and cast to u32
    let masked = n as u32;
    // Flip the bits using the bitwise NOT operator and cast back to u32
    let flipped = !masked;
    // Cast the result back to i64 and return
    flipped as i64
}


#[cfg(test)]
#[test]
fn test_flippingBits() {
    let a: i8 = 5;
    //5 = 00000101
    // flip = 11111010 =
    //println!(4294967290.bitxor())
    assert_eq!(flippingBits((a as i64)), 4294967290 as i64);
    assert_eq!(flippingBits(0 as i64), u32::MAX as i64);
}