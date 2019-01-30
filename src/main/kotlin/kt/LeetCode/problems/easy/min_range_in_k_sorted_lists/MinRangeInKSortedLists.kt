package kt.LeetCode.problems.easy.min_range_in_k_sorted_lists

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 12/9/2018.
 */

data class Node(val v: Int, val next: Node?) : Comparable<Node> {

    override fun compareTo(other: Node) = this.v - other.v

}

class MinRangeInKSortedLists {

    fun get(lists: Array<Node>): Pair<Int, Int> {

        val sorted = lists.sortedArray()
        var min: Node = sorted[0]
        var max: Node = sorted.last()
        var range: Int = max.v - min.v


        while (sorted[0].next != null) {

            sorted.insertWithShiftUp(sorted[0].next!!)

            val currentRange = sorted.last().v - sorted[0].v

            if (currentRange < range) {
                range = currentRange
                min = sorted[0]
                max = sorted.last()
            }
        }

        return min.v to max.v
    }

    fun <T> Array<T>.insertWithShiftUp(element: T) where T : Comparable<*> {

        var insertPosition = this.binarySearch(element)
        if (insertPosition < 0) {
            insertPosition = -(insertPosition + 1) - 1
        }

        if (insertPosition > 0) {
            System.arraycopy(this, 1, this, 0, insertPosition)
        }

        this[insertPosition] = element

    }

}

class Tests {

    val s = MinRangeInKSortedLists()

    @Test
    fun t0() {
        assertThat(s.get(arrayOf(
                nodesList(1, 2, 3),
                nodesList(4, 5, 6),
                nodesList(7, 8, 9)
        ))
                , `is`(3 to 7)
        )
    }

    @Test
    fun t1() {
        assertThat(s.get(arrayOf(
                nodesList(4, 10, 15, 24),
                nodesList(0, 9, 12, 20),
                nodesList(5, 18, 22, 30)
        ))
                , `is`(20 to 24)
        )
    }


    fun nodesList(vararg elements: Int): Node {
        val reversed = elements.reversed()
        var n = Node(reversed[0], null)
        for (i in 1..reversed.lastIndex) {
            n = Node(reversed[i], n)
        }
        return n
    }


    @Test
    fun test_nodesList_0() {
        assertThat(nodesList(1, 2, 3), `is`(Node(1, Node(2, Node(3, null)))))
    }
}