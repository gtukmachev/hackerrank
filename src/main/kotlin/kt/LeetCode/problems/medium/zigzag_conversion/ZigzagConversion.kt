package kt.LeetCode.problems.medium.zigzag_conversion

import LeetCode.problems.medium.zigzag_conversion.ZigzagConversion
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 8/14/2018.
 */
class ZigzagConversion {

    fun convert(s: String, numRows: Int): String {
        if (numRows < 1) throw RuntimeException("'numRows' parameter value should be larger than 0!")
        if (numRows == 1) return s

        val len = s.length

        if (len == 0) return s
        val out = CharArray(len)

        var step1 = numRows - 1 shl 1
        var step2 = 0

        var p = 0 // position in source string
        var j = 0 // position in target string

        // the first line
        while (p < len) { out[j++] = s[p]; p += step1 }

        val nr_1 = numRows - 1
        for (i in 1 until nr_1) {
            p = i
            step1 -= 2;  step2 += 2
            while (p < len) {
                out[j++] = s[p]; p += step1 // a char from the diagonal
                if (p < len) {
                    out[j++] = s[p]; p += step2 // a char from the vertical
                }
            }
        }

        // the last line
        p = nr_1
        step2 += 2
        while (p < len) { out[j++] = s[p];  p += step2 }

        return String(out)
    }

}

class Tests {

    private val zc = ZigzagConversion()

    @Test fun t1() { assertThat(zc.convert("123456789AB", 3), `is`<String>("1592468A37B")) }

    @Test fun t2() { assertThat(zc.convert("123456789ABCDEF", 5), `is`<String>("1928A37BF46CE5D")) }

}