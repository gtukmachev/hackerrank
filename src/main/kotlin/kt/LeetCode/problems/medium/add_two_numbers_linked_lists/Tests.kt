package kt.LeetCode.problems.medium.add_two_numbers_linked_lists

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 8/28/2018.
 */
class Tests {

    val s = Solution()

    @Test fun tt_l_0(){
        var head = l("123456")!!
                            assertThat(head.`val`, `is`(6) )
        head = head.next!!; assertThat(head.`val`, `is`(5) )
        head = head.next!!; assertThat(head.`val`, `is`(4) )
        head = head.next!!; assertThat(head.`val`, `is`(3) )
        head = head.next!!; assertThat(head.`val`, `is`(2) )
        head = head.next!!; assertThat(head.`val`, `is`(1) )
        assertNull(head.next)
    }
    @Test fun tt_l_1(){ assertNull(l("")) }

    @Test fun t0(){ check("0","0","0") }
    @Test fun t1(){ check("1","1","2") }

    @Test fun tl1(){ check("2739024581094709918","104732273910244581009918", "104735012934825675719836") }
    @Test fun tl2(){ check("104732273910244581009918","2739024581094709918", "104735012934825675719836") }

    @Test fun tb1(){ check("999","999","1998") }
    @Test fun tb2(){ check("9","999","1008") }
    @Test fun tb3(){ check("999","9","1008") }

    @Test fun tb4(){ check("111","1","112") }
    @Test fun tb5(){ check("1","111","112") }

    @Test fun tn1(){ check("","111","111") }
    @Test fun tn2(){ check("111","","111") }

    fun l(s:String): ListNode? {
        val head = ListNode(); var n = head
        for (ch in s.reversed()) {
            n.next = ListNode(ch - '0')
            n = n.next!!
        }

        return head.next
    }

    fun check(v1: String, v2: String, result: String){
        val l1 = l(v1)
        val l2 = l(v2)
        val l3 = l(result)
        print("$l1 + $l2 == $l3")
        val response = s.addTwoNumbers(l1, l2)
        assertThat( response, `is`(l3) )
    }
}