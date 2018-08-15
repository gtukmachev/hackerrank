package LeetCode.problems.easy.Min_Stack;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/15/2018.
 *
 *
 * Design a head that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * push(x) -- Push element x onto head.
 * pop() -- Removes the element on top of the head.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the head.
 */
public class MinStack {

    private static Node Node(int val, Node prev, long id){ return new Node(val, prev, id); }
    private static final class Node {
        final int val;
        final Node prev;
        final long id;

        Node(int val, Node prev, long id) {
            this.val = val;
            this.prev = prev;
            this.id = id;
        }
    }

    private Node head;
    private Node min;
    private long size;

    /** initialize your data structure here. */
    public MinStack() {
        this.head = null;
        this.min = null;
        this.size = 0;
    }

    public void push(int x) {
        head = Node(x, head, size++);
        if (min == null || head.val < min.val) {
            min = Node(x, min, head.id);
        }
    }

    public void pop() {  // NPE must be here!!!
        if (min.id == head.id) {
            min = min.prev;
        }
        head = head.prev;
    }

    public int top() {
        return head.val; // NPE must be here!!!
    }

    public int getMin() {
        return min.val; // NPE must be here!!!
    }

    public static class Tests {

        @Test public void t1() {
            MinStack minStack = new MinStack();
            minStack.push(-2);
            minStack.push(0);
            minStack.push(-3);
            assertThat(minStack.getMin(), is(-3));
            minStack.pop();
            assertThat(minStack.top(), is(0));
            assertThat(minStack.getMin(), is(-2));
        }

        @Test public void t2() {
            MinStack minStack = new MinStack();
            minStack.push(5);
            minStack.push(7);
            minStack.push(12);
            minStack.push(1);
            minStack.push(8);
            minStack.push(1);
            assertThat(minStack.getMin(), is(1));
            minStack.pop();
            assertThat(minStack.top(), is(8));
            assertThat(minStack.getMin(), is(1));
            minStack.pop();
            assertThat(minStack.top(), is(1));
            assertThat(minStack.getMin(), is(1));
            minStack.pop();
            assertThat(minStack.top(), is(12));
            assertThat(minStack.getMin(), is(5));
            minStack.pop();
            assertThat(minStack.top(), is(7));
            assertThat(minStack.getMin(), is(5));
            minStack.pop();
            assertThat(minStack.top(), is(5));
            assertThat(minStack.getMin(), is(5));
        }

    }

}
