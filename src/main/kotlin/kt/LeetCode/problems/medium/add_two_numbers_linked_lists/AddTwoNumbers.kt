package kt.LeetCode.problems.medium.add_two_numbers_linked_lists

import java.lang.StringBuilder


data class ListNode(var `val`: Int = 0) {
    var next: ListNode? = null

    override fun toString(): String {
        val s = StringBuilder("[");
        var n: ListNode = this
        while (n.next != null) {
            s.append(n.`val`).append(", ")
            n = n.next!!
        }

        return s.append(n.`val`).append("]").toString()
    }
}

/**
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */

class Solution {

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null) return l2
        if (l2 == null) return l1

        val list = ListNode()
        var v1 = l1;
        var v2 = l2;
        var n = list;
        var o = 0;

        while (v1 != null && v2 != null) {
            var p = v1.`val` + v2.`val` + o
            o = p / 10
            p = p % 10

            n.next = ListNode(p)
            n = n.next!!

            v1 = v1.next
            v2 = v2.next
        }

        var v = v1 ?: v2
        while (o != 0 && v != null) {
            var p = v.`val` + o
            o = p / 10
            p = p % 10

            n.next = ListNode(p)
            n = n.next!!

            v = v.next

        }

        if (v != null) {
            n.next = v
        } else if (o !=0 ) {
            n.next = ListNode(o)
        }

        return list.next
    }
}