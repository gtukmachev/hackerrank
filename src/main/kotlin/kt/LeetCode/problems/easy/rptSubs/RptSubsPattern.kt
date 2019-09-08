package kt.LeetCode.problems.easy.rptSubs

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class RptSubsPattern {

//AABBAA AAB BAA AABBAA
//A-2 * 2 * 3 = 12
//B-2 * 1 * 3 = 6
//n*(count)

    companion object {
        val one: Byte = 1
        val zero: Byte = 0
        val MAX_LEN = 10_000
    }

    fun repeatedSubstringPattern(s: String): Boolean {
        return peaces(s) != 0
    }

    private fun peaces(s: String): Int {
        val LEN = s.length
        if (LEN < 2) return 0

        val lettersArr = IntArray('z' - 'a' + 1)
        for (ch in s) lettersArr[ch - 'a']++

        val letters = lettersArr.asSequence().filter { it > 0 }.toList()
        if (letters.size == 1) return 1

        val min = letters.min()!! // 2..min

        val deg = ByteArray(MAX_LEN + 1)
        // deg[1] = 1

        fun checkPattern(chunksTotalNumber: Int): Boolean {

            val chunkSize: Int = LEN / chunksTotalNumber
            for (j in 0 until chunkSize) {
                val ch = s[j]
                var jInChunk = j

                for (chunk in 1 until chunksTotalNumber) {
                    jInChunk += chunkSize
                    if (s[jInChunk] != ch) return false
                }
            }
            return true
        }

        fun remove(n: Int) {
            var j = n
            while (j <= MAX_LEN) { deg[j] = one; j += n }
        }

        for (chunksTotalNumber in 2..min) {
            if (
                    deg[chunksTotalNumber] == zero
                    &&
                    letters.all { it.rem(chunksTotalNumber) == 0 }
            ) {
                if (checkPattern(chunksTotalNumber)) return chunksTotalNumber
            }


            remove(chunksTotalNumber)
        }

        return 0
    }



    @Test fun t1() {
        assertThat(repeatedSubstringPattern("aa"), `is`(true))
    }


    @Test fun p0() { assertThat(peaces("a"), `is`(0)) }
    @Test fun p1() { assertThat(peaces("aa"), `is`(1)) }
    @Test fun p2() { assertThat(peaces("aaa"), `is`(1)) }
    @Test fun p3() { assertThat(peaces("aaaa"), `is`(1)) }
    @Test fun p4() { assertThat(peaces("ababab"), `is`(3)) }
    @Test fun p5() { assertThat(peaces("qwertyqwertyqwertyqwerty"), `is`(2)) }
    @Test fun p6() { assertThat(peaces("abababababab"), `is`(2)) }

}