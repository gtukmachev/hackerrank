package LeetCode.problems.hard.merge_k_sorted_lists;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by grigory@clearscale.net on 8/23/2018.
 */
public class Tests {

    MergeSortedLists s = new MergeSortedLists();

    @Test public void tt1() {
        ListNode list = listOf(1,2,3);
        assertThat(list.val, is(1));
        assertThat(list.next.val, is(2));
        assertThat(list.next.next.val, is(3));
        assertNull(list.next.next.next);
    }

    @Test public void tt2() { assertNull(listOf()); }
    @Test public void tt3() { assertThat(listOf(3,2,1), is(listOf(3,2,1))); }
    @Test public void tt4() { assertThat(listOf(3,2,1), is(not(listOf(3,1,2)))); }


    @Test public void th_push_3_1() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(3);
        heap.push(5);
        heap.push(6);
        heap.push(7);
        assertThat(heap.heap, is(new Integer[]{5, 6, 7}));
    }

    @Test public void th_push_2() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(3);
        heap.push(7);
        heap.push(9);
        heap.push(3);
        assertThat(heap.heap, is(new Integer[]{3, 9, 7}));
    }

    @Test public void th_push_4() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(4);
        heap.push(7);
        heap.push(9);
        heap.push(3);
        heap.push(8);
        assertThat(heap.heap, is(new Integer[]{3, 8, 7, 9}));
    }

    @Test public void th_push_5() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(5);
        heap.push(7);
        heap.push(9);
        heap.push(3);
        heap.push(8);
        heap.push(1);
        assertThat(heap.heap, is(new Integer[]{1, 3, 7, 9, 8}));
    }

    @Test public void th_push_6() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(6);
        heap.push(7);
        heap.push(9);
        heap.push(3);
        heap.push(8);
        heap.push(1);
        heap.push(2);
        assertThat(heap.heap, is(new Integer[]{1, 3, 2, 9, 8, 7}));
    }

    @Test public void th_push_7() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(7);
        heap.push(7);
        heap.push(9);
        heap.push(3);
        heap.push(8);
        heap.push(1);
        heap.push(2);
        heap.push(2);
        assertThat(heap.heap, is(new Integer[]{1, 3, 2, 9, 8, 7, 2}));
    }

    @Test public void th_push_15() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(15);
        heap.push(7); heap.push(9); heap.push(3);
        heap.push(8); heap.push(1); heap.push(2);
        heap.push(2);
        heap.push(11); heap.push(12);
        heap.push(13); heap.push(14);
        heap.push(15); heap.push(16);
        heap.push(17); heap.push(18);
        assertThat(heap.heap, is(new Integer[]{1, 3, 2, 9, 8, 7, 2,11,12,13,14,15,16,17,18}));
    }

    @Test public void th_15_pull() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(15);
        heap.push(7); heap.push(9); heap.push(3);
        heap.push(8); heap.push(1); heap.push(2);
        heap.push(2);
        heap.push(11); heap.push(12);
        heap.push(13); heap.push(14);
        heap.push(15); heap.push(16);
        heap.push(17); heap.push(18);

        int min = heap.pull();
        assertThat( min, is(1));

        assertThat(heap.heap, is(new Integer[]{2, 3,2, 9,8,7,17, 11,12,13,14,15,16,18, 18}));
    }

    @Test public void th2() {
        MergeSortedLists.MinHeap<Integer> heap = new MergeSortedLists.MinHeap<>(100);
        Random rnd = new Random();
        for(int i = 0; i < 100; i++) {
            heap.push( rnd.nextInt(50) );
        }

        int curr = heap.pull();
        int last = curr;
        for(int i = 0; i < 99; i++) {
            curr = heap.pull();
            if (curr < last) { System.out.print(curr + " > " + last); }
            assertTrue(curr >= last);
            last = curr;
        }

    }

    @Test public void tn1() {assertThat( s.mergeKLists( new ListNode[]{} ), is(listOf())); }
    @Test public void tn2() {assertThat( s.mergeKLists( null             ), is(listOf())); }

    @Test public void tn3() {assertThat( s.mergeKLists( new ListNode[]{listOf( )}), is(listOf( )) );}
    @Test public void tn4() {assertThat( s.mergeKLists( new ListNode[]{listOf(1)}), is(listOf(1)) );}

    @Test public void t1() {
        assertThat(
                s.mergeKLists( new ListNode[]{
                        listOf(1,4,5),
                        listOf(1,3,4),
                        listOf(2,6)})
                , is(listOf(1,1,2,3,4,4,5,6))
        );
    }

    @Test public void t_stress_1() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(100);
        ListNode c2 = l2;
        for (int i = 1; i <= 1_000_000; i++) {
            c2.next = new ListNode(100);
            c2 = c2.next;
        }

        ListNode result =  s.mergeKLists(new ListNode[]{l1, l2});

        assertThat( result.val, is(1) );
        assertThat( result.next.val, is(100) );

        for (int i = 1; i <= 1_000_001; i++) {
            result = result.next;
        }
        assertThat( result.val, is(100) );
        assertNull( result.next );

    }


    private ListNode listOf(int... elements) {
        ListNode head = new ListNode(-1);
        ListNode last = head;
        for (int v : elements) {
            ListNode n = new ListNode(v);
            last.next = n;
            last = n;
        }
        return head.next;
    }

}
