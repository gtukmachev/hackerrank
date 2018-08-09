package hackerrank.practice.algorithms.graph_theory.breadth_first_search;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/**
 * Created by grigory@clearscale.net on 8/8/2018.
 */
public class ShortestReach {

    private static Queue<Integer> queue = new LinkedList<>();

    // Complete the bfs function below.
    static int[] bfs(int n, int m, int[][] edges, int s) {
        queue.clear();

        Map<Integer, Set<Integer>> e = buildEdgesIndex(edges);

        int[] len = new int[n+1]; Arrays.fill(len, -1);

        queue.add(s); len[s] = 0;

        while (queue.size() > 0) {
            int f = queue.poll();
            Set<Integer> out = e.get(f);
            if (out != null) for (int t : out) {
                    if (len[t] == -1) {
                        queue.add(t);
                        len[t] = len[f]+6;
                    }
                }

        }

        return Arrays.stream(len).filter(o -> o != 0).skip(1).toArray();
    }

    private static Map<Integer, Set<Integer>> buildEdgesIndex(int[][] edges) {
        Map<Integer, Set<Integer>> index = new HashMap<>();
        for (int[] edge : edges) {
            int i = edge[0], j = edge[1];
            addToIndex(index, i, j);
            addToIndex(index, j, i);
        }
        return index;
    }

    private static void addToIndex(Map<Integer, Set<Integer>> index, int iFrom, int iTo) {
        index
            .computeIfAbsent(iFrom, i -> new HashSet<>())
            .add(iTo);
    }


    static class Tests {

        final static class P {
            final int f;
            final int s;

            P(int f, int s) {
                this.f = f;
                this.s = s;
            }
        }

        private P P(int f, int t) {
            return new P(f, t);
        }

        @Test
        public void tm1() {
            int[] res = runBFS
                    (4 // total points
                            , 1 // start point
                            , new P[]{P(4, 2), P(1, 2), P(1, 3)} //edges
                    );
            Assert.assertArrayEquals(new int[]{6, 6, -1}, res);
        }

        @Test
        public void tm2() {
            int[] res = runBFS
                    (8 // total points
                            , 1 // start point
                            , new P[]{P(1, 2), P(1, 3), P(1, 4), P(1, 5), P(1, 7), P(2, 6), P(2, 5), P(5, 7)} //edges
                    );
            Assert.assertArrayEquals(new int[]{6, 6, 6, 6, 12, 6, -1}, res);
        }

        @Test
        public void tm3() {

            int n = 1000;
            int n1 = n + 1;
            int m = n * n;
            int[][] edges = new int[m][2];

            int s = 0;
            for (int i = 1; i < n1; i++) {
                for (int j = 1; j < n1; j++) {
                    edges[s][0] = i;
                    edges[s++][1] = j;
                }
            }

            int[] res = bfs(n, m, edges, new Random().nextInt(n));

            Assert.assertEquals(n - 1, res.length);
            Assert.assertTrue(Arrays.stream(res).allMatch(k -> k == 6));
        }

        @Test
        public void t1() throws IOException {
            testByIncomeFile(1);
        }

        @Test
        public void t4() throws IOException {
            testByIncomeFile(4);
        }

        private void testByIncomeFile(int id) throws IOException {
            Iterator<String> input = Files.readAllLines(getFileForTest("t" + id + "-in.txt")).iterator();
            Iterator<String> output = Files.readAllLines(getFileForTest("t" + id + "-out.txt")).iterator();

            int qNumber = Integer.parseInt(input.next());

            for (int q = 0; q < qNumber; q++) {
                String[] s = input.next().split(" ");

                int n = Integer.parseInt(s[0]);
                int m = Integer.parseInt(s[1]);

                int[][] edges = new int[m][2];

                for (int i = 0; i < m; i++) {
                    String[] s1 = input.next().split(" ");
                    edges[i][0] = Integer.parseInt(s1[0]);
                    edges[i][1] = Integer.parseInt(s1[1]);
                }

                int start = Integer.parseInt(input.next());

                int[] res = bfs(n, m, edges, start);

                int[] exp = Arrays.stream(output.next().split(" ")).mapToInt(Integer::parseInt).toArray();

                Assert.assertArrayEquals(exp, res);

            }

        }


        private Path getFileForTest(String sufix) {
            return Paths.get("src/main/java/" + ShortestReach.class.getName().replace(".", "/") + "-" + sufix);
        }

        private int[] runBFS(int pointsNumber, int startPoint, P[] edges) {

            int[][] edgesArray = new int[edges.length][2];
            for (int i = 0; i < edges.length; i++) {
                edgesArray[i][0] = edges[i].f;
                edgesArray[i][1] = edges[i].s;
            }

            return bfs(pointsNumber, edges.length, edgesArray, startPoint);
        }
    }
}
