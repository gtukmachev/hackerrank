package kt.LeetCode.problems.easy.reverse_integer

import org.hamcrest.core.Is
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test


/**
 * Created by grigory@clearscale.net on 8/13/2018.
 */
class ReverseInteger {

    fun reverse(x: Int): Int {

        var src = x
        var dst = 0L

        while (src != 0) {
            val d = src % 10
            src /= 10

            dst = dst*10 + d
        }

        return when {
                (dst > Int.MAX_VALUE || dst < Int.MIN_VALUE) -> 0
                                                        else -> dst.toInt()
                }
    }

}

class Tests{

    val ri = ReverseInteger()
    @Test fun t1() { assertThat( ri.reverse(12345), `is`(54321) ) }

}