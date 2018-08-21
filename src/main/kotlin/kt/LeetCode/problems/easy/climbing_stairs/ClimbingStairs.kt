package kt.LeetCode.problems.easy.climbing_stairs

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */

class Solution {


    fun climbStairs(n: Int): Int {
        val cache = IntArray(n + 1)

        fun calc(t: Int): Int = when {
            t == 0 -> 1
            t < 0 -> 0
            else -> {
                if (cache[t] == 0) cache[t] = calc(t - 1) + calc(t - 2)
                cache[t]
            }
        }

        return calc(n)
    }


}

class Tests {


    val s = Solution()

    @Test fun t3() { assertThat( s.climbStairs(3), `is`(3) ) }
    @Test fun t44() { assertThat( s.climbStairs(44), `is`(1134903170) ) }


}