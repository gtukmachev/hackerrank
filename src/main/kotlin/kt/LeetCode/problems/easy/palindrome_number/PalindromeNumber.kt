package kt.LeetCode.problems.easy.palindrome_number

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class PalindromeNumber {

    fun isPalindrome(x: Int) = when {
        x < 0  -> false
        x < 10 -> true
          else -> isNaturalPalindrome(x)
    }

    private fun isNaturalPalindrome(x: Int): Boolean { // x already > 9
        val arr = IntArray(10) //10 is max symbols number for decimal presentation of the Int type

        var i: Int = 0
        var m: Int = x

        while (m > 0) {
            arr[i++] = m % 10
            m /= 10
        }

        var j = 0
        while (j < i) {
            if (arr[--i] != arr[j++]) return false
        }

        return true
    }


    @Test fun t1(){ assertThat( isPalindrome(0), `is`(true) ) }
    @Test fun t2(){ assertThat( isPalindrome(1), `is`(true) ) }
    @Test fun t3(){ assertThat( isPalindrome(3), `is`(true) ) }

    @Test fun tn1(){ assertThat( isPalindrome(-1), `is`(false) ) }
    @Test fun tn2(){ assertThat( isPalindrome(-13), `is`(false) ) }
    @Test fun tn3(){ assertThat( isPalindrome(Int.MIN_VALUE), `is`(false) ) }

    @Test fun tt1(){ assertThat( isPalindrome(13), `is`(false) ) }
    @Test fun tt2(){ assertThat( isPalindrome(11), `is`(true) ) }
    @Test fun tt3(){ assertThat( isPalindrome(31), `is`(false) ) }
    @Test fun tt4(){ assertThat( isPalindrome(313), `is`(true) ) }

    @Test fun tm1(){ assertThat( isPalindrome(2147483647), `is`(false) ) }
    @Test fun tm2(){ assertThat( isPalindrome(1234554321), `is`(true) ) }

}