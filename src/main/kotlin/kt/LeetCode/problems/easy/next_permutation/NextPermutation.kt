package kt.LeetCode.problems.easy.next_permutation

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 12/9/2018.
 */

class NextPermutation(val length: Int) {

    var pm: IntArray? = null

    fun get(): IntArray? {

        if (pm == null) return IntArray(length){ i -> i+1 }.let { pm = it; it }

        val ar = pm!!

        val firstReverse = ar.findByComparingReversed{ l, r -> l < r }
        if (firstReverse == -1) return null

        val firstReverseValue = ar[firstReverse]
        val firstGrather = ar.findReversedTo( firstReverse ){ value -> value > firstReverseValue }

        ar.swap(firstReverse, firstGrather)

        ar.reverse(firstReverse+1)

        return ar
    }

    fun IntArray.findByComparingReversed(predicate: (valLeft: Int, valRight: Int) -> Boolean ): Int {
        for (i in (this.size - 2) downTo 0) {
            if (predicate(this[i], this[i+1])) {
                return i
            }
        }
        return -1
    }

    fun IntArray.findReversedTo(indexTo: Int, predicate: (arValue: Int) -> Boolean ): Int {
        for (i in (this.size - 1) downTo indexTo) {
            if (predicate(this[i])) {
                return i
            }
        }
        return indexTo
    }

    fun IntArray.swap(i: Int, j :Int) {
        val v = this[i]
        this[i] = this[j]
        this[j] = v
    }

    fun IntArray.reverse(fromIndex: Int = 0) {
        var i = fromIndex
        var j = this.lastIndex

        while (i < j) this.swap(i++, j--)
    }
}

class Tests {

    @Test
    fun t0() {
        val m = NextPermutation(0)

        assertThat( m.get(), `is`(intArrayOf()) )
        assertNull( m.get() )
    }

    @Test
    fun t1() {
        val m = NextPermutation(1)

        assertThat( m.get(), `is`(intArrayOf(1)) )
        assertNull( m.get() )
    }

    @Test
    fun t2() {
        val m = NextPermutation(2)

        assertThat( m.get(), `is`(intArrayOf(1,2)) )
        assertThat( m.get(), `is`(intArrayOf(2,1)) )
        assertNull( m.get() )
    }

    @Test
    fun t3() {
        val m = NextPermutation(3)

        assertThat( m.get(), `is`(intArrayOf(1,2,3)) )
        assertThat( m.get(), `is`(intArrayOf(1,3,2)) )
        assertThat( m.get(), `is`(intArrayOf(2,1,3)) )
        assertThat( m.get(), `is`(intArrayOf(2,3,1)) )
        assertThat( m.get(), `is`(intArrayOf(3,1,2)) )
        assertThat( m.get(), `is`(intArrayOf(3,2,1)) )
        assertNull( m.get() )
    }

}