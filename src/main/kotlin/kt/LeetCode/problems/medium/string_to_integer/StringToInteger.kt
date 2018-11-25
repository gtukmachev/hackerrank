package kt.LeetCode.problems.medium.string_to_integer

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 11/25/2018.
 */


object StringToInteger {

    val MAX = Integer.MAX_VALUE.toLong()
    val MIN = MAX+1 //positive representation

    fun myAtoi(str: String): Int {
        val L = str.length
        if (L == 0) return 0

        var i = 0
        while (i < L && str[i] == ' ') { i ++; }
        if (i == L) return 0

        val isMinus = str[i] == '-'
        if (isMinus || str[i] == '+') {
            i++
            if (i == L) return 0
        }

        var num: Long = 0

        do {
            val ch = str[i]
            if (ch !in '0'..'9') return (if (isMinus) -num.toInt() else num.toInt())

            num = num*10 + (ch - '0')

            if (isMinus) { if (num >= MIN) return Int.MIN_VALUE }
                    else { if (num >= MAX) return Int.MAX_VALUE }

            i++
        } while (i < L)


        return if (isMinus) (-num.toInt()) else num.toInt()
    }

}


class Tests {

    @Test fun t() { assertThat( StringToInteger.myAtoi(""), `is`(0)) }
    @Test fun t__() { assertThat( StringToInteger.myAtoi("   "), `is`(0)) }

    @Test fun t0() { assertThat( StringToInteger.myAtoi("0"), `is`(0)) }
    @Test fun t1() { assertThat( StringToInteger.myAtoi("1"), `is`(1)) }
    @Test fun t2() { assertThat( StringToInteger.myAtoi("99"), `is`(99)) }
    @Test fun t3() { assertThat( StringToInteger.myAtoi("00991"), `is`(991)) }

    @Test fun t_0() { assertThat( StringToInteger.myAtoi("  0"), `is`(0)) }
    @Test fun t_1() { assertThat( StringToInteger.myAtoi("  1"), `is`(1)) }
    @Test fun t_2() { assertThat( StringToInteger.myAtoi("  99"), `is`(99)) }
    @Test fun t_3() { assertThat( StringToInteger.myAtoi("  00991"), `is`(991)) }

    @Test fun t_0_() { assertThat( StringToInteger.myAtoi("  0   abc"), `is`(0)) }
    @Test fun t_1_() { assertThat( StringToInteger.myAtoi("  1   abc"), `is`(1)) }
    @Test fun t_2_() { assertThat( StringToInteger.myAtoi("  99   abc"), `is`(99)) }
    @Test fun t_3_() { assertThat( StringToInteger.myAtoi("  00991   abc"), `is`(991)) }

    @Test fun t_x0_() { assertThat( StringToInteger.myAtoi(" x 0   abc"), `is`(0)) }
    @Test fun t_x1_() { assertThat( StringToInteger.myAtoi(" x 1   abc"), `is`(0)) }
    @Test fun t_x2_() { assertThat( StringToInteger.myAtoi(" x 99   abc"), `is`(0)) }
    @Test fun t_x3_() { assertThat( StringToInteger.myAtoi(" x 00991   abc"), `is`(0)) }

    @Test fun t_px0_() { assertThat( StringToInteger.myAtoi(" +x 0   abc"), `is`(0)) }
    @Test fun t_px1_() { assertThat( StringToInteger.myAtoi("+ x 1   abc"), `is`(0)) }
    @Test fun t_mx2_() { assertThat( StringToInteger.myAtoi(" -x 99   abc"), `is`(0)) }
    @Test fun t_mx3_() { assertThat( StringToInteger.myAtoi("- x 00991   abc"), `is`(0)) }

    @Test fun t_m0() { assertThat( StringToInteger.myAtoi(" -0"), `is`(-0)) }
    @Test fun t_m1() { assertThat( StringToInteger.myAtoi(" -1"), `is`(-1)) }
    @Test fun t_m2() { assertThat( StringToInteger.myAtoi(" -99"), `is`(-99)) }
    @Test fun t_m3() { assertThat( StringToInteger.myAtoi(" -00991"), `is`(-991)) }

    @Test fun t_p0() { assertThat( StringToInteger.myAtoi(" +0"), `is`(0)) }
    @Test fun t_p1() { assertThat( StringToInteger.myAtoi(" +1"), `is`(1)) }
    @Test fun t_p2() { assertThat( StringToInteger.myAtoi(" +99"), `is`(99)) }
    @Test fun t_pm3() { assertThat( StringToInteger.myAtoi(" +00991"), `is`(991)) }

    @Test fun tmaxLong() { assertThat( StringToInteger.myAtoi( Long.MAX_VALUE.toString() ), `is`( Int.MAX_VALUE )) }
    @Test fun tminLong() { assertThat( StringToInteger.myAtoi( Long.MIN_VALUE.toString() ), `is`( Int.MIN_VALUE )) }

}
