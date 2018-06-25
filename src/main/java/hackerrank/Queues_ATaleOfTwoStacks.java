package hackerrank;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;


/**
 * Created by grigory@clearscale.net on 6/24/2018.
 */
public class Queues_ATaleOfTwoStacks {


    public static class MyQueue<T> {
        Stack<T> stackNewestOnTop = new Stack<T>();
        Stack<T> stackOldestOnTop = new Stack<T>();

        public void enqueue(T value) { stackNewestOnTop.push(value); }
        public    T peek()           { checkOutput(); return stackOldestOnTop.peek(); }
        public    T dequeue()        { checkOutput(); return stackOldestOnTop.pop(); }


        private void checkOutput() {
            if (stackOldestOnTop.empty()) reorder();
        }

        private void reorder() {
            while (!stackNewestOnTop.empty())
                stackOldestOnTop.push( stackNewestOnTop.pop() );
        }
    }


    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        //2_147_483_647
    }

    @Test
    public void test1(){
        int v;
        MyQueue<Integer> q = new MyQueue<>();

        q.enqueue(42);
        v = q.dequeue(); Assert.assertEquals(42, v);

        q.enqueue(14);
        v = q.peek(); Assert.assertEquals(14, v);

        q.enqueue(28);
        v = q.dequeue(); Assert.assertEquals(14, v);

        q.enqueue(60); q.enqueue(78);

        v = q.peek(); Assert.assertEquals(28, v);
        v = q.peek(); Assert.assertEquals(28, v);

    }

}
