package kt.LeetCode.problems.medium

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.lang.Math.max

/**
 * Created by grigory@clearscale.net on 8/18/2018.
 */


object LongestSubstringWithoutRepeatingCharacters {

    class Solution {
        val CHARACTERS_NUMBER = 'z' - 'a' + 1;
        val ALL_BITS = 0.let {
            var M: Int = it
            for (i in 1..CHARACTERS_NUMBER) M = ( M shl 1 ) + 1
            M
        }

        fun lengthOfLongestSubstring(s: String): Int {
            val L = s.length
            if (L == 0) return 0;
            if (L == 1) return 1;

            val sets = IntArray(L)
            var maxLength = 0;
            var start = 0

            for (i in 0 until L) {
                val ch = s[i]
                val chMask = 1 shl (ch - 'a')
                var ns: Int = 0

                start = max(0, i - CHARACTERS_NUMBER + 1)
                for (p in start..i) {
                    ns = sets[p]
                    if (ns >= 0) { // skip excluded
                        ns = ns xor chMask
                        when{
                            (ns and chMask) == 0 -> { maxLength = max(maxLength, bitsCount(ns) + 1); ns = -1 }  //excluding
                                (ns == ALL_BITS) -> return CHARACTERS_NUMBER
                                            else -> sets[p] = ns
                        }
                    }
                }
                if (start > 0 && sets[start-1] >= 0 ) maxLength = max(maxLength, bitsCount(sets[start-1]))
            }

            for (p in start until L) {
                val n = sets[p];
                if (n >= 0)
                    maxLength = max(maxLength, bitsCount(sets[p]))
            }

            return maxLength;
        }

        fun bitsCount(v : Int): Int { // wotks for positive ints only
            var n = 0;
            var rest = v;
            while (rest > 0) {
                n += rest and 1
                rest = rest shr 1
            }
            return n
        }
    }
}


class Tests() {

    val s = LongestSubstringWithoutRepeatingCharacters.Solution()

    val sForSt2 = "azcv".repeat(1_000_000)
    val sForSt3 = "".let{
                var st = it
                for (i in 0 until s.CHARACTERS_NUMBER) st += ('a' + i)
                st
            }.repeat(1_000_000)

    @Test fun t_bitsCount0() { assertThat(s.bitsCount(0), `is`(0)) }
    @Test fun t_bitsCount4() { assertThat(s.bitsCount(0b1111), `is`(4)) }
    @Test fun t_bitsCount8() { assertThat(s.bitsCount(0b0011001100101010100), `is`(8)) }

    @Test fun t0() { assertThat(s.lengthOfLongestSubstring(""), `is`(0)) }
    @Test fun t1() { assertThat(s.lengthOfLongestSubstring("a"), `is`(1)) }

    @Test fun t3() { assertThat(s.lengthOfLongestSubstring("ab"), `is`(2)) }
    @Test fun t4() { assertThat(s.lengthOfLongestSubstring("abab"), `is`(2)) }
    @Test fun t5() { assertThat(s.lengthOfLongestSubstring("acbab"), `is`(3)) }


    @Test fun st1() { assertThat(s.lengthOfLongestSubstring("a".repeat(1_000_000)), `is`(1)) }
    @Test fun st2() { assertThat(s.lengthOfLongestSubstring(sForSt2), `is`(4)) }
    @Test fun st3() { assertThat(s.lengthOfLongestSubstring(sForSt3), `is`( s.CHARACTERS_NUMBER )) }

}