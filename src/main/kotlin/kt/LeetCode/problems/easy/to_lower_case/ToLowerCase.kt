package kt.LeetCode.problems.easy.to_lower_case

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 1/30/2019.
 */

class Solution {

    fun toLowerCase(str: String): String {

        val chars = str.toCharArray()

        for (i in 0 until chars.size) {
            if (chars[i] in 'A'..'Z') {
                chars[i] = chars[i] + 32
            }
        }

        return String(chars)
    }

    operator fun get(str: String) = toLowerCase(str)
}


class Tests {
    private val s = Solution()

    @Test fun t0() { assertThat(s["Hello world"], `is`("hello world")) }
}


/*
fun main(args: Array<String>) {

    val big_letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val small_letters = "abcdefghijklmnopqrstuvwxyz"

    printLettes(big_letters)
    printLettes(small_letters)


}

fun printLettes(letters: String) {
    println(letters)
    letters.toCharArray().forEach {
        println("$it - ${it.toLong()}")
    }
}*/
