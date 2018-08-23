package LeetCode.problems.hard.merge_k_sorted_lists;

import org.jetbrains.annotations.NotNull;

/**
 * Created by grigory@clearscale.net on 8/23/2018.
 */
public class MergeSortedLists {

    private static final class ListWrapper implements Comparable<ListWrapper> {
        ListNode head;

        public ListWrapper(ListNode head) {
            this.head = head;
        }

        @Override
        public int compareTo(@NotNull ListWrapper o) {
            return head.val - o.head.val;
        }
    }

    static final class MinHeap<T extends Comparable<T>>{
        Object[] heap;
        int currentSize;

        public MinHeap(int maxHeapSize) {
            this.heap = new Object[maxHeapSize];
            this.currentSize = 0;
        }

        @SuppressWarnings("unchecked")
        void push(T obj) {
            heap[currentSize++] = obj;

            // drill up
            int i = currentSize-1;
            int parent = (i - 1) >> 1;
            while (i > 0 && ((T)heap[i]).compareTo((T)heap[parent]) < 0) {
                T tmp = (T)heap[i];
                heap[i] = heap[parent];
                heap[parent] = tmp;
                i = parent;
                parent = (i - 1) >> 1;
            }

        }

        @SuppressWarnings("unchecked")
        T pull() {
            T r = (T)heap[0];
            heap[0] = heap[--currentSize];

            //drill down;
            int i = 0;
            int min;
            boolean gotMin = true;

            do {
                int child1 = (i << 1) + 1;
                int child2 = child1 + 1;

                if ( child1 < currentSize ) {
                    min = ((T) heap[i]).compareTo((T) heap[child1]) < 0 ? i : child1;
                    if (child2 < currentSize) {
                        min = ((T) heap[min]).compareTo((T) heap[child2]) < 0 ? min : child2;
                    }
                } else {
                    min = i;
                }


                if (min != i) {
                    T tmp = (T) heap[i];
                    heap[i] = heap[min];
                    heap[min] = tmp;
                    i = min;
                } else {
                    gotMin = false;
                }
            } while(gotMin);

            return r;
        }

        @SuppressWarnings("unchecked")
        T get(int i){ return (T)heap[i]; }

        int size() { return currentSize; }

    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        MinHeap<ListWrapper> minHeap = new MinHeap<>(lists.length);
        for (int i = 0; i < lists.length; i++)
            if (lists[i] != null)
                minHeap.push(new ListWrapper(lists[i]));

        ListNode head = new ListNode(-1);
        ListNode last = head;


        while (minHeap.size() > 1) {
            ListWrapper lw = minHeap.pull();

            last.next = new ListNode(lw.head.val);
            last = last.next;

            lw.head = lw.head.next;
            if (lw.head != null) {
                minHeap.push(lw);
            }
        }

        if (minHeap.size() > 0)
            last.next = minHeap.get(0).head;

        return head.next;
    }


}
