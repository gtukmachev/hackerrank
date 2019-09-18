package kt.LeetCode.problems.medium.n_15_3Sum

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

class ThreeSum {

    fun threeSum(nums: IntArray): List<List<Int>> {
        val numsCount = TreeMap<Int, Int>() // a number -> count of this number occurrences in the array
        nums.forEach{ n -> numsCount.merge(n, 1){oldValue, _ -> oldValue + 1} }

        val triples: MutableList<List<Int>> = mutableListOf()

        numsCount.forEachPair{ a, b ->
            val c = -a-b
            if (c >= b) {
                var count = numsCount[c] ?: 0
                if (c == a) count--
                if (c == b) count--
                if (count > 0)  triples.add(listOf(a, b, c))
            }
        }

        return triples
    }

    private fun Map<Int, Int>.forEachPair(checkPair: (Int, Int) -> Unit) {
        val entries = this.entries.toTypedArray()
        val s = this.size

        if (s == 1 && entries[0].value > 1) {
            val a = entries[0].key
            checkPair(a,a)
        }

        var i = 0
        while ((i < (s-1)) && entries[i].key <= 0 ) {
            val (a, countA) = entries[i]
            if (countA > 1) checkPair(a, a)

            var j = i + 1
            while ((j < s) && (a + entries[j].key) <= 0) {
                checkPair(a, entries[j].key)
                j++
            }
            i++
        }
    }


    @Test fun tz1() { check( intArrayOf(0, 0, 0), listOf(0, 0, 0)) }

    @Test fun t1() { check( intArrayOf(-1, 0, 1, 2, -1, -4), listOf(-1,0,1), listOf(-1,-1,2)) }
    @Test fun t2() { check( intArrayOf(-1, 0, 1, 2, -1, -4), listOf(-1,0,1), listOf(-1,2,-1)) }
    @Test fun t3() { check( intArrayOf(-1, 0, 1, 2, -1, -4), listOf(0,1,-1), listOf(2,-1,-1)) }

    fun check(nums: IntArray, vararg etalons: List<Int>) {
        val result = threeSum( nums )
        assertThat( result, isSameListOfListWithAnyOrder(etalons.toList()))
    }

    class isSameListOfListWithAnyOrder<T: Comparable<T>>(val expected: List<List<T>>): BaseMatcher<List<List<T>>>() {
        override fun describeTo(description: Description) {
            description.appendText("is same as: $expected")
        }

        override fun matches(list: Any?): Boolean {
            if (list == null) return false
            list as List<List<T>>
            
            if (list.size != expected.size) return false
            
            val l = LinkedList<List<T>>(expected)
            
            for (item in list) {
                var found = false
                val iter = l.iterator()
                while (iter.hasNext() && !found) {
                    val expectedItem = iter.next()
                    if (isTheSameWithoutOrder(item, expectedItem)) {
                        found = true
                        iter.remove()
                    }
                }
                if (!found) return false
            }
            
            return  l.isEmpty()
        }

        private fun isTheSameWithoutOrder(item: List<T>, expectedItem: List<T>): Boolean {
            if (item.size != expectedItem.size) return false
            val sortedItem = item.sortedBy { it }
            val sortedExpectedItem = expectedItem.sortedBy { it }
            return sortedExpectedItem == sortedItem 
        }
    }

    @Test fun tt1() { assertThat(             listOf(listOf(1), listOf(2,3,4), listOf(2,3,4), listOf(2)) ,
                isSameListOfListWithAnyOrder( listOf(listOf(1), listOf(2,3,4), listOf(2,3,4), listOf(2)) ))}

    @Test fun tt2() { assertThat(             listOf(listOf(1), listOf(2,3,4), listOf(2,3,4), listOf(2)) ,
                isSameListOfListWithAnyOrder( listOf(listOf(2), listOf(3,2,4), listOf(2,3,4), listOf(1)) ))}

    @Test fun tt3() { assertThat(             listOf(listOf(1), listOf(2,3,4), listOf(2,3,4), listOf(2)) ,
            not(isSameListOfListWithAnyOrder( listOf(listOf(2), listOf(3,2,4), listOf(2,3,4), listOf(1), listOf(1)) )))}


}