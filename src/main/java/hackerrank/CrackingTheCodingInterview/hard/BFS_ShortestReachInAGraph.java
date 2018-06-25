package hackerrank.CrackingTheCodingInterview.hard;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by grigory@clearscale.net on 6/25/2018.
 */
public class BFS_ShortestReachInAGraph {

    private static final int END_OF_CYCLE = -10;
    private static final int START_MARKER = -16;
    private static final int WAIT_MARKER  = -20;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final List<Integer> EMPTY_LIST = new ArrayList<>(0);

    private static HashMap<Integer, List<Integer>> buildAgesTable(int[] ages) {
        HashMap<Integer, List<Integer>> agesTable = new HashMap<>();

        for (int i = 0; i < ages.length; i += 2) {
            int out1 = ages[i+1]; agesTable.compute( ages[i],  (k, list) -> {
                List<Integer> l = list == null ? new ArrayList<>(1) : list;
                l.add(out1);
                return l;
            });
            int out2 = ages[i]; agesTable.compute( ages[i+1],  (k, list) -> {
                List<Integer> l = list == null ? new ArrayList<>(1) : list;
                l.add(out2);
                return l;
            });
        }

        return agesTable;
    }

    private static int[] calcPaths(int nondesCount, int startNode, int[] ages) {
        int[] p = new int[nondesCount+1]; p[startNode] = START_MARKER;

        HashMap<Integer, List<Integer>> agesTable = buildAgesTable(ages);

        MyQueue<Integer> q = new MyQueue<Integer>()
                .push(startNode)
                .push(END_OF_CYCLE);

        int pathLen = 0;
        while (q.hasNext()) {
            int node = q.pop();
            if (node == END_OF_CYCLE) {
                pathLen += 6;
                if (q.hasNext()) q.push(END_OF_CYCLE);
            } else {
                if (p[node] == WAIT_MARKER) p[node] = pathLen;
                for (int n_ : agesTable.getOrDefault(node, EMPTY_LIST))
                    if (p[n_] == 0){
                        q.push(n_);
                        p[n_] = WAIT_MARKER;
                    }
            }
        }

        for (int i = 1; i < p.length; i++) if (p[i] == 0) p[i] = -1;

        return p;
    }

    private static int[] printPaths(int n, int s, int[] ages) {
        int[] paths = calcPaths(n, s, ages);
        for (int i = 1; i < paths.length; i++) {
            if (i != s) {
                System.out.print(paths[i]);
                System.out.print(i < (paths.length - 1) ? " " : "\n");
            }
        }
        return paths;
    }

    static class MyQueue<T> {
        Stack<T> stackNewestOnTop = new Stack<>();
        Stack<T> stackOldestOnTop = new Stack<>();

         MyQueue<T> push(T value) { stackNewestOnTop.push(value); return this; }
                  //T peek()        { checkOutput(); return stackOldestOnTop.peek(); }
                  T pop()         { checkOutput(); return stackOldestOnTop.pop(); }
            boolean hasNext()  { return stackOldestOnTop.size() > 0 || stackNewestOnTop.size() > 0; }


        private void checkOutput() {
            if (stackOldestOnTop.empty()) reorder();
        }

        private void reorder() {
            while (!stackNewestOnTop.empty())
                stackOldestOnTop.push( stackNewestOnTop.pop() );
        }
    }


    @Test
    public void test1() {
        int[] paths = printPaths(4, 1, new int[]{1, 2, 1, 3});
        //for (int i = 0; i < paths.length; i++) System.out.print(paths[i] + " ");
        Assert.assertArrayEquals(new int[]{0, START_MARKER, 6, 6, -1}, paths);
    }

    @Test
    public void test2() {
        int[] paths = printPaths(3, 2, new int[]{2, 3});
        //for (int i = 0; i < paths.length; i++) System.out.print(paths[i] + " ");
        Assert.assertArrayEquals(new int[]{0, -1, START_MARKER, 6}, paths);
    }


    @Test
    public void test3() {
        int[] paths = printPaths(6, 1, new int[]{1,2, 2,3, 3,4, 1,5});
        //for (int i = 0; i < paths.length; i++) System.out.print(paths[i] + " ");
        Assert.assertArrayEquals(new int[]{0, START_MARKER, 6, 12, 18, 6, -1}, paths);
    }


}
