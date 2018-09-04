package LeetCode.problems.hard.shortest_path_in_a_graph2.shortest_path_in_a_graph;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 9/3/2018.
 */
public class Tests {
    static int M = Integer.MAX_VALUE;

    ShortestPathInGraph.Resolver s;
    @Before public void init() {
        s = new ShortestPathInGraph.Resolver();
    }



    @Test public void t_buildGraph_1() {
        assertThat( s.buildGraph(
                new int[]{1,3,2},
                new int[]{2,2,2} ),
                is(new int[][]{
                        {0,1,0},
                        {2,1,0},
                        {1,1,0},
                        {1,0,0},
                        {1,2,0},
                        {1,1,0},
                        {M,M,M}}) );
    }
    @Test public void t_buildGraph_and_sort_1() {
        int[][] graph = s.buildGraph(
                new int[]{1,3,2},
                new int[]{2,2,2} );
        s.sortGraph(graph);
        assertThat( graph, is(new int[][]{
                        {0,1,0},
                        {1,0,0},
                        {1,1,0},
                        {1,1,0},
                        {1,2,0},
                        {2,1,0},
                        {M,M,M}}));
    }
    @Test public void t_buildGraph_sort_index1() {
        int[][] graph = s.buildGraph(
                new int[]{1,1,4},
                new int[]{2,5,5} );
        s.sortGraph(graph);
//      0  0 -> 3
//      1  0 -> 4
//      2  1 -> 4
//      3  3 -> 0
//      4  4 -> 0
//      5  4 -> 1

        int[] index = s.buildGraphIndex(graph, 5);
        assertThat(index, is(
                new int[]{0, 2, -1, 3, 4}
        ));
    }
    @Test public void t_buildGraph_sort_index2() {
        int[][] graph = s.buildGraph(
                new int[]{1, 3},
                new int[]{5,10} );
        s.sortGraph(graph);
//      0  0 -> 4
//      1  2 -> 9
//      2  4 -> 0
//      3  9 -> 2

        int[] index = s.buildGraphIndex(graph, 10);
        assertThat(index, is(
                new int[]{0, -1, 1, -1, 2, -1, -1, -1, -1, 3}
        ));
    }

    @Test public void t_spiders1() {
        s.colors = new long[]{1,2,3,3,0,1,2};
        List<ShortestPathInGraph.Resolver.Spider> sps = s.createSpiders(s.colors, 1);
        assertThat(sps.size(), is(2));
        assertThat(sps.get(0).queue.peek(), is(0));
        assertThat(sps.get(1).queue.peek(), is(5));

    }

    @Test public void t0()   { assertThat(s.findShortest(0, new int[0], new int[0], new long[0], 0), is(-1)); }
    @Test public void t1_1() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 1), is( 2)); }
    @Test public void t1_2() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 2), is(-1)); }
    @Test public void t1_3() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 3), is( 1)); }
    @Test public void t1_4() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 4), is(-1)); }
    @Test public void t1_5() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 5), is(-1)); }
    @Test public void t_hr_08(){ assertThat(fileTest("08"), is(-1)); }

    private int fileTest(String testId) {
        try {

            Iterator<String> input = Files.readAllLines(getFileForTest("test-" + testId + "-input.txt")).iterator();

            String[] graphNodesEdges = input.next().split(" ");
            int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
            int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

            int[] graphFrom = new int[graphEdges];
            int[] graphTo = new int[graphEdges];

            for (int i = 0; i < graphEdges; i++) {
                String[] graphFromTo = input.next().split(" ");
                graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
                graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
            }

            long[] ids = new long[graphNodes];

            String[] idsItems = input.next().split(" ");

            for (int i = 0; i < graphNodes; i++) {
                long idsItem = Long.parseLong(idsItems[i]);
                ids[i] = idsItem;
            }

            int val = Integer.parseInt(input.next().trim());

            return s.findShortest(graphNodes, graphFrom, graphTo, ids, val);

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private Path getFileForTest(String sufix) {
        return Paths.get("src/main/java/" + ShortestPathInGraph.class.getName().replace(".", "/") + "-" + sufix);
    }


}
