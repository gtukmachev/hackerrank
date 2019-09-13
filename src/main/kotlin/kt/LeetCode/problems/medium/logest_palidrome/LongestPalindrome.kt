package kt.LeetCode.problems.medium.logest_palidrome

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class LongestPalindrome {

    fun longestPalindrome(s: String): String {
        when (s.length) {
            0,1 -> return s
            2 -> return if (s[0] == s[1]) s else s[0].toString()
        }

        var maxLen: Int = 1
        var maxStart: Int = 0
        var maxEnd: Int = 1

        val root: Matcher = Matcher(-1)
        val LEN = s.length
        val LAST_INDEX = LEN - 1

        fun checkMatchers(ch: Char, endIndex: Int): Matcher { // returns a last matcher in the list
            var prevMatcher: Matcher = root
            var matcher: Matcher? = prevMatcher.next

            while (matcher != null) {
                matcher.startIndex--
                if (matcher.startIndex < 0 || s[matcher.startIndex] != ch) {
                    val len = endIndex - matcher.startIndex - 1
                    if (len > maxLen) { // this part is the biggest one
                        maxLen = len
                        maxStart = matcher.startIndex+1
                        maxEnd = endIndex
                    }
                    prevMatcher.next = matcher.next // remove the current matcher from the list:
                } else {
                    prevMatcher = matcher
                }
                matcher = matcher.next
            }
            return prevMatcher
        }

        var mode = if (s[1] == s[0]) Mode.REGION else Mode.SCAN
        var regionStartIndex = 0

        var endIndex = 2

        while (endIndex < LEN) {
            val ch = s[endIndex]
            val lastMatcher = checkMatchers(ch, endIndex)

            when (mode) {
                Mode.SCAN -> {
                    if (s[endIndex-2] == ch) {
                        lastMatcher.next = Matcher(endIndex-2)
                    }
                    if (s[endIndex-1] == ch) {
                        if (endIndex < LAST_INDEX) {
                            mode = Mode.REGION
                            regionStartIndex = endIndex - 1
                        } else {
                            lastMatcher.next = Matcher(endIndex-1)
                        }
                    }

                }
                Mode.REGION -> {
                    when {
                        (s[endIndex-1] != ch) -> { // the region finished
                                    if (regionStartIndex > 0 && s[regionStartIndex-1] == ch) {
                                        lastMatcher.next = Matcher(regionStartIndex-1)
                                    } else {
                                        val len = endIndex - regionStartIndex
                                        if (len > maxLen) { // this REGION is the biggest one
                                            maxLen = len
                                            maxStart = regionStartIndex
                                            maxEnd = endIndex
                                        }

                                    }
                                    mode = Mode.SCAN
                                }

                        (endIndex == LAST_INDEX) -> {
                                    lastMatcher.next = Matcher(regionStartIndex)
                                }
                    }
                }
            }

            endIndex++
        }

        checkMatchers(0.toChar(), LEN)

        return s.substring(maxStart, maxEnd)

    }

    enum class Mode{ SCAN, REGION }

    data class Matcher(var startIndex: Int){
        var next: Matcher? = null
    }

    @Test fun t1() { assertThat(longestPalindrome("1aba2"), `is`("aba"))}
    @Test fun t2() { assertThat(longestPalindrome("1abababa2"), `is`("abababa"))}
    @Test fun t3() { assertThat(longestPalindrome("abababa"), `is`("abababa"))}
    @Test fun t4() { assertThat(longestPalindrome("123123123"), `is`("1"))}
    @Test fun t5() { assertThat(longestPalindrome(""), `is`(""))}
    @Test fun t6() { assertThat(longestPalindrome("aaaaaaaaaaaa"), `is`("aaaaaaaaaaaa"))}
    @Test fun t7() { assertThat(longestPalindrome("aaaaaaaaaaaab"), `is`("aaaaaaaaaaaa"))}
    @Test fun t8() { assertThat(longestPalindrome("1aaaaaaaaaaaa"), `is`("aaaaaaaaaaaa"))}
    @Test fun t9() { assertThat(longestPalindrome("1"), `is`("1"))}
    @Test fun t10() { assertThat(longestPalindrome("22"), `is`("22"))}
    @Test fun t11() { assertThat(longestPalindrome("34"), `is`("3"))}
    @Test fun t12() { assertThat(longestPalindrome("345"), `is`("3"))}
    @Test fun t13() { assertThat(longestPalindrome("776"), `is`("77"))}
    @Test fun t14() { assertThat(longestPalindrome("-dd"), `is`("dd"))}
    @Test fun t15() { assertThat(longestPalindrome("-dd-"), `is`("-dd-"))}
    @Test fun t16() { assertThat(longestPalindrome("--dd"), `is`("--"))}
    @Test fun t17() { assertThat(longestPalindrome("fffdd"), `is`("fff"))}
    @Test fun t18() { assertThat(longestPalindrome("ffddd"), `is`("ddd"))}
    @Test fun t19() { assertThat(longestPalindrome("fffddd"), `is`("fff"))}

    val bigs1 = "qqqqqqqqqq".repeat(100) // len = 1000
    @Test fun t_big1() {
        assertThat(bigs1, `is`(bigs1))
    }

}