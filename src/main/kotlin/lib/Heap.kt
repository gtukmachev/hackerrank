package lib

import java.util.*

class Heap<T:  Comparable<T>>(
        val isMaxHeap: Boolean = true,
        initialElements: Collection<T>? = null,
        initialSize: Int = if (initialElements?.isNotEmpty() == true) initialElements.size else INCREMENT_NUMBER
) {

    companion object {
        private val INCREMENT_NUMBER = 100
    }

    var arr: Array<Any?> = arrayOfNulls(initialSize)
    var size = 0
    lateinit var root: T

    init {
        if (initialElements != null) { for (v in initialElements) add(v) }
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
