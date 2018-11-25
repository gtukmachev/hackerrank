package kt.LeetCode.problems.easy.remove_element

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

/**
 * Created by grigory@clearscale.net on 11/25/2018.
 */

class RemoveElement {

    fun removeElement(nums: IntArray, target: Int): Int {
        var iStart = -1
        var moveMode = true

        for (i in 0 until nums.size) {
            val isMAtched = nums[i] == target
            when(moveMode) {
                true -> {
                    if (isMAtched) {
                        moveMode = false
                        iStart = reorder(nums, target, iStart, i)
                    }
                }
                false -> {
                    if (!isMAtched) moveMode = true
                }
            }
        }

        return reorder(nums, target, iStart, nums.size)

    }

    fun reorder(nums: IntArray, target: Int, start: Int, end: Int): Int {
        if (start == -1) return end

        var iStart = start
        var iEnd = end - 1

        while (iEnd > iStart && nums[iEnd] != target && nums[iStart] == target) {
            nums[iStart] = nums[iEnd]; nums[iEnd] = target
            iStart++; iEnd--
        }

        return if (nums[iStart] == target) iStart else (iEnd + 1)
    }

}


class Tests {

    private val s = RemoveElement()

    @Test
    fun leet_1() { genericTest(intArrayOf(3, 2, 2, 3), 3,
                               intArrayOf(   2, 2   ))}

    @Test
    fun t1() { genericTest(intArrayOf(0, 1, 2, 3, 0, 5, 6), 0,
                           intArrayOf(   1, 2, 3,    5, 6))
    }

    @Test
    fun t2() {
        genericTest(intArrayOf(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 5, 6, 0, 0, 0, 0, 0), 0,
                    intArrayOf(            1,                      5, 6               ))
    }

    @Test
    fun t3() {
        genericTest(intArrayOf(), 0,
                    intArrayOf())
    }

    @Test
    fun t4() {
        genericTest(intArrayOf(), 1,
                    intArrayOf())
    }

    @Test
    fun t5() {
        genericTest(intArrayOf(1, 1, 1), 1,
                    intArrayOf())
    }

    @Test
    fun t6() {
        genericTest(intArrayOf(2), 2,
                    intArrayOf())
    }

    @Test
    fun t7() {
        genericTest(intArrayOf(1), 0,
                    intArrayOf(1))
    }

    @Test
    fun t8() {
        genericTest(intArrayOf(1, 2, 3), 0,
                    intArrayOf(1, 2, 3))
    }

    @Test
    fun t9() {
        genericTest(intArrayOf(1, 2, 3, 8, 8, 8), 8,
                    intArrayOf(1, 2, 3))
    }

    private fun genericTest(arr: IntArray, targetVal: Int, neededArbitraryArr: IntArray) {
        val len = s.removeElement(arr, targetVal)

        assertThat<Int>(len, `is`<Int>(neededArbitraryArr.size))

        val neededArbitraryArrAsList = LinkedList<Int>()
        for (i in neededArbitraryArr) neededArbitraryArrAsList.add(i)


        for (i in 0 until len) {
            assertThat<Boolean>(neededArbitraryArrAsList.remove(Integer.valueOf(arr[i])), `is`<Boolean>(true))
        }
        assertTrue(neededArbitraryArrAsList.isEmpty())

    }

}
