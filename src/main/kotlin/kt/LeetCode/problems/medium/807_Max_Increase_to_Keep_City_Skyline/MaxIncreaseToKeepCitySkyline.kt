package kt.LeetCode.problems.medium.`807_Max_Increase_to_Keep_City_Skyline`

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import kotlin.math.min

/**
 * Created by grigory@clearscale.net on 1/30/2019.
 *
 * original: https://leetcode.com/problems/max-increase-to-keep-city-skyline/
 *
 */

class Solution {

    fun maxIncreaseKeepingSkyline(grid: Array<IntArray>): Int {
        val LIN = grid.size
        val COL = grid[0].size

        val top = IntArray(COL)
        val side = IntArray(LIN)

        buildSkyline(grid, top, side, LIN, COL)

        var amount = 0

        for (l in 0 until LIN ) {
            for (c in 0 until COL) {

                val height = grid[l][c]
                val minHeight = min(side[l], top[c])

                if (height < minHeight) {
                    amount += (minHeight - height)
                }

            }
        }

        return amount
    }

    fun buildSkyline(grid: Array<IntArray>, top: IntArray, side: IntArray, LINES: Int, COLUMNS: Int) {
        for (l in 0 until LINES ) {
            for (c in 0 until COLUMNS) {
                val height = grid[l][c]
                if (height > top[c] ) top[c]  = height
                if (height > side[l]) side[l] = height
            }
        }
    }

    operator fun get(grid: Array<IntArray>) = maxIncreaseKeepingSkyline(grid)
}

fun Array<IntArray>.to2dString() = this.joinToString(separator = "\n"){
    it.joinToString(prefix = "[", postfix = "]")
}

class Tests {


    val s = Solution()

    @Test
    fun tt_0() {
        val city = arrayOf(
                intArrayOf(3, 0, 8, 4),
                intArrayOf(2, 4, 5, 7),
                intArrayOf(9, 2, 6, 3),
                intArrayOf(0, 3, 1, 0)
        )

        val top = IntArray(4)
        val side = IntArray(4)

        s.buildSkyline(city, top, side, 4, 4)

        assertThat(top, `is`(intArrayOf(9,4,8,7)))
        assertThat(side, `is`(intArrayOf(8,7,9,3)))
    }



    @Test
    fun tl_0() {
        val city = arrayOf(
                intArrayOf(3, 0, 8, 4),
                intArrayOf(2, 4, 5, 7),
                intArrayOf(9, 2, 6, 3),
                intArrayOf(0, 3, 1, 0)
        )

        println(city.to2dString())

        assertThat(s[city], `is`(35))

        println(city.to2dString())
    }

}