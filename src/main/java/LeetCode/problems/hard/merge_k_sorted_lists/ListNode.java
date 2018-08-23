package LeetCode.problems.hard.merge_k_sorted_lists;

/**
 * Created by grigory@clearscale.net on 8/23/2018.
 */
public class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListNode listNode = (ListNode) o;

        if (val != listNode.val) return false;
        return next != null ? next.equals(listNode.next) : listNode.next == null;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[").append(this.val);

        ListNode n = this.next;
        while (n != null) {
            s.append(", ").append(n.val);
            n = n.next;
        }

        s.append("]");

        return s.toString();
    }
}
