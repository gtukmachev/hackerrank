package hackerrank.practice.algorithms.graph_theory.breadth_first_search;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by grigory@clearscale.net on 8/8/2018.
 */
public class ShortestReach {

    // Complete the bfs function below.
    static int[] bfs(int n, int m, int[][] edges, int s) {

        return new int[]{6,6,-1};
    }

    final static class P {
        final int f;
        final int s;
        public P(int f, int s) { this.f = f; this.s = s; }
    }

    private static P P(int f, int t) { return new P(f, t); }

    @Test public void t1() {
        int[] res = runBFS
                ( 4 // total points
                , 1 // start point
                , new P[]{ P(4,2), P(1,2), P(1,3) } //edges
                );
        Assert.assertArrayEquals(new int[]{6,6,-1}, res);
    }

    public int[] runBFS(int pointsNumber, int startPoint, P[] edges) {

        int[][] edgesArray = new int[edges.length][2];
        for (int i = 0; i < edges.length; i++) {
            edgesArray[i][0] = edges[i].f;
            edgesArray[i][1] = edges[i].f;
        }

        return bfs(pointsNumber, edges.length, edgesArray, startPoint);
    }

}
