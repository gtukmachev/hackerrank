package LeetCode.problems.easy.Merge_Two_Sorted_Lists;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/15/2018.
 */
public class Merge_Two_Sorted_Lists {

    /**
     * Merge two sorted linked lists and return it as a new list.
     * The new list should be made by splicing together the nodes of the first two lists
     * <p>
     * Example:
     * Input: 1->2->4, 1->3->4
     * Output: 1->1->2->3->4->4
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListNode listNode = (ListNode) o;

            if (val != listNode.val) return false;
            return next != null ? next.equals(listNode.next) : listNode.next == null;
        }

        @Override
        public int hashCode() {
            int result = val;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("[").append(val);
            ListNode l = this.next;
            while (l != null) {
                s.append(", ").append(l.val);
                l = l.next;
            }
            s.append("]");
            return s.toString();
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode result = new ListNode(0);
        ListNode current = result;

        while (l1 != null && l2 != null) {
            ListNode newNode;
            if (l1.val < l2.val) {
                newNode = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                newNode = new ListNode(l2.val);
                l2 = l2.next;
            }
            current.next = newNode;
            current = newNode;
        }


        current.next = l1 == null ? l2 : l1;

        return result.next;

    }


    public static class Tests {

        Merge_Two_Sorted_Lists s = new Merge_Two_Sorted_Lists();


        @Test public void t1() {
            assertThat(asList(1, 2, 3), is(asList(1, 2, 3)));
        }

        @Test public void tLeet2() {
            assertThat(s.mergeTwoLists(asList(1, 3, 4), asList(1, 2, 4)), is(asList(1, 1, 2, 3, 4, 4)));
        }

        @Test public void t3() {
            assertThat(s.mergeTwoLists(null, asList(1, 2, 4)), is(asList(1, 2, 4)));
        }

        @Test public void t4() {
            assertEquals(s.mergeTwoLists(null, null), null);
        }

        @Test public void t5() {
            assertThat(s.mergeTwoLists(asList(1, 2, 4), null), is(asList(1, 2, 4)));
        }

        @Test public void t6() {
            assertThat(s.mergeTwoLists(asList(2, 4, 4), asList(1, 6, 7)), is(asList(1, 2, 4, 4, 6, 7)));
        }


        private ListNode asList(int... nodes) {
            if (nodes == null || nodes.length == 0) return null;

            ListNode it;

            ListNode l1 = new ListNode(nodes[0]);
            it = l1;
            for (int i = 1; i < nodes.length; i++) {
                it.next = new ListNode(nodes[i]);
                it = it.next;
            }

            return l1;
        }

    }

}
