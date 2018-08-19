package kt.LeetCode.problems.medium

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test


/**
 * Created by grigory@clearscale.net on 8/18/2018.
 */


object LongestSubstringWithoutRepeatingCharacters_allSymbols {

class Solution {

    fun lengthOfLongestSubstring(s: String): Int {
        val L = s.length
        if (L == 0) return 0;
        if (L == 1) return 1;

        val map = HashMap<Char, Int>()
        var currMax = 0;
        var currLen = 0;

        for (i in 0 until L) {
            val ch = s[i]

            map.computeIfPresent(ch){ _, lastPos ->
                currLen = i - lastPos
                if (currLen > currMax) currMax = currLen
                currLen = - 1
                i
            }

            currLen += 1;
            map.put(ch, i)
        }

        return if (currMax > currLen) currMax else currLen
    }
}
}


class Tests_allSymbols() {

    val s = LongestSubstringWithoutRepeatingCharacters_allSymbols.Solution()

    val sForSt2 = "azcv".repeat(1_000_000)
    val sForSt3 = "".let{
                var st = it
                for (i in 0 until ('Z' - 'A' + 1)) st += ('a' + i)
                st
            }.repeat(1_000_000)


    @Test fun t0() { assertThat(s.lengthOfLongestSubstring(""), `is`(0)) }
    @Test fun t1() { assertThat(s.lengthOfLongestSubstring("a"), `is`(1)) }

    @Test fun t3() { assertThat(s.lengthOfLongestSubstring("ab"), `is`(2)) }
    @Test fun t4() { assertThat(s.lengthOfLongestSubstring("abab"), `is`(2)) }
    @Test fun t5() { assertThat(s.lengthOfLongestSubstring("acbab"), `is`(3)) }

    @Test fun sr1() {assertThat( s.lengthOfLongestSubstring("aaaaaaaaa"), `is`(1) )}

    @Test fun st1() {assertThat( s.lengthOfLongestSubstring("a".repeat(1_000_000)), `is`(1) )}
    @Test fun st2() {assertThat( s.lengthOfLongestSubstring(sForSt2), `is`(4) )}
    @Test fun st3() {assertThat( s.lengthOfLongestSubstring(sForSt3), `is`('Z' - 'A' + 1) )}

    @Test fun leet1() { assertThat(s.lengthOfLongestSubstring("abcabcbb"), `is`(3)) }
    @Test fun leet2() { assertThat(s.lengthOfLongestSubstring("   "), `is`(1)) }


}