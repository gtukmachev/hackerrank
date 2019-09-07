package kt.LeetCode.problems.easy.indexOf

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

/**
 * https://leetcode.com/submissions/detail/258592981/
 */
class IndexOf {

    fun strStr(source: String, pattern: String): Int {
        val srcLen = source.length
        val ptLen = pattern.length
        when {
            (ptLen == 0) -> return 0
            (srcLen < ptLen) -> return -1
        }

        var matchPositions = ArrayList<Int>()

        val firstPatternCh = pattern[0]
        val lastPatternCh = pattern[ptLen-1]

        var rest = srcLen

        for (i in 0 until srcLen) {
            val ch = source[i]

            if (ch == firstPatternCh) {
                if (rest >= ptLen && source[i+ptLen-1] == lastPatternCh) {
                    matchPositions.add(0)
                }
            }

            var n: Int = 0
            while (n < matchPositions.size) {
                val matchPosition = matchPositions[n]

                if (pattern[matchPosition] == ch) {
                    val nextPosition = matchPosition + 1
                    if (nextPosition == ptLen) {
                        return i - ptLen + 1
                    } else {
                        matchPositions[n] = nextPosition
                    }
                    n++
                } else {
                    matchPositions.removeAt(n)
                }
            }

            rest--
        }
        return -1
    }

    //                                  0123456789
    //                                      ------
    @Test fun q1() { assertThat(strStr("..12121234..", "121234"), `is`(4)) }


    //                                  0123456789
    //                                    ---
    @Test fun q2() { assertThat(strStr(".1112......", "112"), `is`(2)) }


    @Test fun t0() { assertThat(strStr("hello", "ll"), `is`(2)) }
    @Test fun t1() { assertThat(strStr("", "ll"), `is`(-1)) }
    @Test fun t2() { assertThat(strStr("", ""), `is`(0)) }
    @Test fun t3() { assertThat(strStr("hello", "lll"), `is`(-1)) }
    @Test fun t4() { assertThat(strStr("hello", "el_o"), `is`(-1)) }
    @Test fun t5() { assertThat(strStr("1", "1"), `is`(0)) }
    @Test fun t6() { assertThat(strStr("-1", "1"), `is`(1)) }
    @Test fun t7() { assertThat(strStr("--1", "1"), `is`(2)) }
    @Test fun t8() { assertThat(strStr("--1-", "1"), `is`(2)) }
    @Test fun t9() { assertThat(strStr("--1123-1123---", "1123"), `is`(2)) }

    @Test fun a1() { assertThat(strStr("", "a"), `is`(-1)) }
    @Test fun a2() { assertThat(strStr("a", ""), `is`(0)) }
    @Test fun a3() { assertThat(strStr("aaa", "aaa"), `is`(0)) }

    @Test
    fun testLong1() {
        val longStr = "1234567890".repeat(10000)
        assertThat(strStr(longStr, "a"), `is`(-1))
    }

    @Test
    fun testLong2() {
        val longStr1 = "1234567890".repeat(10000)
        val longStr2 = "9876543210".repeat(1000)
        assertThat(strStr(longStr1, longStr2), `is`(-1))
    }

    @Test
    fun testLong3() {
        val pattern = "qwert"
        val repeatN = 100_000
        val longStr1 = pattern.repeat(repeatN)
        val src = longStr1 + "qwerty" + longStr1 + "qwerty"
        assertThat(strStr(src, "qwerty"), `is`(repeatN * pattern.length))
    }

    @Test
    fun testLong4() {
        val pattern = "qwertyqwertyqwert"
        val repeatN = 100_000
        val longStr1 = pattern.repeat(repeatN)
        val src = longStr1 + "qwertyqwertyqwerty" + longStr1 + "qwertyqwertyqwerty"
        assertThat(strStr(src, "qwertyqwertyqwerty"), `is`(repeatN * pattern.length))
    }


    @Test
    fun testLong5() {
        val example="qwe".repeat(100)
        val repeatN = 100_000
        val longStr1 = example.repeat(repeatN)
        val searchFor = example + "1"
        val src = longStr1 + searchFor + longStr1 + searchFor
        assertThat(strStr(src, searchFor), `is`(repeatN * example.length))
    }

}

