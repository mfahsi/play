package com.mf.exercise.lists

object Arrays {

  def removeDuplicatesFromSortedArrayInPlace(nums: Array[Int]): Int = {
    if nums.size == 0 then 0
    else if nums.size == 1 then 1
    else {
      var k = 1

      def remove(arr: Array[Int], pos: Int, offset: Int = 0): Int = {
        println(s"$pos $offset")
        if pos >= nums.size then {
          k
        } else {
          if arr(pos) == arr(pos - offset) then {
            val offsetNew = offset + 1
            remove(arr, pos + 1, offsetNew)

          } else {
            k = k + 1
            arr(pos - offset + 1) = arr(pos)
            remove(arr, pos + 1, offset)
            k
          }
        }
      }

      remove(nums, 1, 1)
      k
    }
  }

}
