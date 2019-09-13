package kt.LeetCode.problems.medium.n_11_Container_With_Most_Water

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*
import kotlin.math.min

class MaxArea {

    fun maxArea(height: IntArray) : Int{
        var l = 0
        var r = height.size - 1
        var result = 0
        while(l < r) {
            result = Math.max(result, Math.min(height[l], height[r]) * (r-l))
            when {
                height[l] < height[r] -> l++
                else -> r--
            }
        }
        return result

    }

    fun maxArea2(height: IntArray): Int {


        val maxHeap = Heap<Stick>(initialSize = height.size)
        height.forEachIndexed{ i, height -> maxHeap.add( Stick(i,height) ) }


        var left = maxHeap.removeRoot()
        var right = left
        var maxS = 0


        while (maxHeap.size > 0) {
            val stick = maxHeap.removeRoot()

            val isNewStickOutside: Boolean = when {
                (stick.x > right.x) -> { right = stick; true  }
                (stick.x < left.x)  -> { left = stick;  true  }
                               else -> {                false }
            }

            if (isNewStickOutside) {
                val newS = (right.x - left.x) * min(left.height, right.height)
                if (newS > maxS) maxS = newS
            }

        }

        return maxS

    }

    data class Stick(val x: Int, val height: Int): Comparable<Stick> {
        override fun compareTo(other: Stick): Int {
            return height.compareTo(other.height)
        }
    }

    class Heap<T:  Comparable<T>>(
            val isMaxHeap: Boolean = true,
            addToHeap: Array<T>? = null,
            initialSize: Int = if (addToHeap?.isNotEmpty() == true) addToHeap.size else INCREMENT_NUMBER
    ) {

        companion object {
            private val INCREMENT_NUMBER = 100
        }

        var arr: Array<Any?> = arrayOfNulls(initialSize)
        var size = 0
        lateinit var root: T

        init {
            if (addToHeap != null) { for (v in addToHeap) add(v) }
        }

        fun add(value: T) {
            if (size == arr.size) {
                val newArr = Arrays.copyOf(arr, arr.size + INCREMENT_NUMBER)
                this.arr = newArr
            }
            arr[size] = value
            size++

            siftUp(size - 1)
            root = arr[0]!! as T
        }

        private fun siftUp(i: Int): Int {
            val parentIndex = (i - 1) / 2
            val parentValue = arr[parentIndex]!! as T

            if (isMaxHeap && parentValue < (arr[i]!! as T) || !isMaxHeap && parentValue > (arr[i]!! as T)) {
                arr[parentIndex] = arr[i]
                arr[i] = parentValue
                return siftUp(parentIndex)
            } else {
                return i
            }
        }

        fun removeRoot(): T {
            if (size == 0) throw IndexOutOfBoundsException("Can't get root from the empty heap.")
            val v = root

            size--
            arr[0] = arr[size]
            siftDown(0)

            root = arr[0]!! as T
            return v
        }

        fun siftDown(i: Int) {
            val li = i * 2 + 1
            if (li >= size) return  // the node is leaf

            var target = if (isMaxHeap && (arr[li]!!     as T) > (arr[i]!! as T)      || !isMaxHeap && (arr[li]     as T)!! < (arr[i]!!      as T)) li     else i
                target = if (isMaxHeap && (arr[li + 1]!! as T) > (arr[target]!! as T) || !isMaxHeap && (arr[li + 1] as T)!! < (arr[target]!! as T)) li + 1 else target

            if (i == target) return  // no more deep down needed

            val v = arr[i]
            arr[i] = arr[target]
            arr[target] = v
            siftDown(target)
        }

        override fun toString() = arr.toString()

    }

    @Test fun t1(){ assertThat( maxArea(a(1,8,6,2,5,4,8,3,7)), `is`(49) ) }
    @Test fun t2(){ assertThat( maxArea(a(1,8,6,0,5,4,8,0,7)), `is`(49) ) }
    @Test fun t3(){ assertThat( maxArea(a(-100,8,6,0,5,4,8,0,7,-100)), `is`(49) ) }

    fun a(vararg n: Int):IntArray = n

}