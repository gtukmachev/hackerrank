package LeetCode.problems.medium.jump_game

/**
 * Created by grigory@clearscale.net on 8/29/2018.
 */
object JumpGame {

    class Solution {

        fun canJump(nums: IntArray): Boolean {

            fun reachable(p: Int): Boolean{
                if(p == 0) return true;

                //zero position
                var zp = p-1; while (zp >= 0 && nums[zp] != 0) zp--
                if (zp == -1) return true

                // jumping over the zero
                var candidate = zp-1
                for (len in 2..(zp+1)) {
                    if ( nums[candidate] >= len && reachable(candidate)) return true
                    candidate--
                }
                return false
            }

            return nums.isEmpty() || reachable(nums.size-1)
        }

    }

}