#[cfg(test)]
mod smartpointers {
    use std::rc::Rc;

    #[test]
    fn test_rc() {
        let rc = Rc::new(3);
        {
            let a = rc.as_ref();
            println!("{},{}", a, Rc::strong_count(&rc));
            let clone = rc.clone();
            assert_eq!(Rc::strong_count(&rc), 2);
        }
        assert_eq!(Rc::strong_count(&rc), 1);
    }

    #[test]
    fn test_rc_get_mut() {
        let mut rc = Rc::new(3);
        if let Some(rw) = Rc::get_mut(&mut rc) {
            *rw = 4;
            assert_eq!(*rc, 4);
        }
    }

    #[test]
    fn test_rc_make_mut() {
        let rc = Rc::new(3);
        let mut rc2 = rc.clone();
        let rcm = Rc::make_mut(&mut rc2);
        *rcm = 4;
        assert_eq!(*rcm, 4);
        assert_eq!(*rc.as_ref(), 3);
        assert_eq!(*rc2.as_ref(), 4);
        assert_eq!(Rc::strong_count(&rc), 1);
        let mut rc3 = rc2.clone();
        assert_eq!(Rc::strong_count(&rc2), 2);
    }

    #[test]
    fn test_rc_make_mut_no_clone() {
        let mut rc = Rc::new(3);
        let rcm = Rc::make_mut(&mut rc);
        *rcm = 4;
        assert_eq!(*rcm, 4);
        assert_eq!(*rc.as_ref(), 4);
    }

    #[test]
    fn test_rc_make_mut_gpt() {
        let mut rc = Rc::new(42);

        // Get a mutable reference to the value, cloning if necessary
        let value = Rc::make_mut(&mut rc);
        *value = 43;
        println!("Value: {}", value); // Output: Value: 43

        // Create another strong reference
        let rc_clone = Rc::clone(&rc);

        // Rc::make_mut will clone the value and return a mutable reference to the clone
        let value = Rc::make_mut(&mut rc);
        *value = 44;
        println!("Value: {}", value); // Output: Value: 44

        // The original reference remains unchanged
        println!("Original Rc value: {}", *rc_clone);
    }
}


