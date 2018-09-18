package LeetCode.problems.hard.shortest_path_in_a_graph2.shortest_path_in_a_graph;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
        assertThat(sps.get(0).spiderId, is(-2));

        assertThat(sps.get(1).queue.peek(), is(5));
        assertThat(sps.get(1).spiderId, is(-3));

    }

    @Test public void t_spider_grow1(){
        //                         0 1 2 3 4 5 6 7 8
        long[] colors = new long[]{1,2,3,4,5,6,7,8,9};
        int graphNodesNumber = colors.length;
        int[] graphFrom = new int[]{1,2,3,4,5,6,7,8};
        int[] graphTo   = new int[]{2,3,4,5,6,7,8,9};
        int targetColor = 1;

        s.initSpiders(graphNodesNumber, graphFrom, graphTo, colors, targetColor);
        assertThat(s.spiders.size(), is(1));

        ShortestPathInGraph.Resolver.Spider spider = s.spiders.get(0);

        assertThat(spider.startNodeId, is(0));
        assertThat(spider.spiderId, is(-2));
        assertThat(colors, is(new long[]{-1,2,3,4,5,6,7,8,9}));

        s.initGraphIndexes(graphNodesNumber, graphFrom, graphTo, colors, targetColor);

        assertThat( s.graph, is( new int[][]{
                    {0,1,0}, //0
                    {1,0,0}, //1
                    {1,2,0}, //2
                    {2,1,0}, //3
                    {2,3,0}, //4
                    {3,2,0}, //5
                    {3,4,0}, //6
                    {4,3,0}, //7
                    {4,5,0}, //8
                    {5,4,0}, //9
                    {5,6,0}, //10
                    {6,5,0}, //11
                    {6,7,0}, //12
                    {7,6,0}, //13
                    {7,8,0}, //14
                    {8,7,0}, //15
                    {M, M, M}
                }
        ));

        assertThat(s.index, is(new int[]{0,1,3,5,7,9,11,13,15}));

        assertThat(spider.grow(), is(-1));
        assertThat(spider.queue, is(Arrays.asList(1))); assertThat(spider.len, is(1));
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1},
                        {1,2,0}, {2,1,0}, {2,3,0}, {3,2,0}, {3,4,0}, {4,3,0}, {4,5,0}, {5,4,0}, {5,6,0}, {6,5,0}, {6,7,0},{7,6,0}, {7,8,0}, {8,7,0},
                {M, M, M}
            }
        ));

        assertThat(spider.grow(), is(-1));
        assertThat(spider.queue, is(Arrays.asList(2))); assertThat(spider.len, is(2));
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1},
                        {1,-1,0}, {2,0,2},
                        {2,3,0}, {3,2,0}, {3,4,0}, {4,3,0}, {4,5,0}, {5,4,0}, {5,6,0}, {6,5,0}, {6,7,0},{7,6,0}, {7,8,0}, {8,7,0},
                        {M, M, M}
                }
        ));

        assertThat(spider.grow(), is(-1));
        assertThat(spider.queue, is(Arrays.asList(3))); assertThat(spider.len, is(3));
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1}, {1,-1,0}, {2,0,2},
                        {2,-1,0}, {3,0,3},
                        {3,4,0}, {4,3,0}, {4,5,0}, {5,4,0}, {5,6,0}, {6,5,0}, {6,7,0},{7,6,0}, {7,8,0}, {8,7,0},
                        {M, M, M}
                }
        ));

        assertThat(spider.grow(), is(-1));
        assertThat(spider.queue, is(Arrays.asList(4))); assertThat(spider.len, is(4));
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1}, {1,-1,0}, {2,0,2}, {2,-1,0}, {3,0,3},
                        {3,-1,0}, {4,0,4},
                        {4,5,0}, {5,4,0}, {5,6,0}, {6,5,0}, {6,7,0},{7,6,0}, {7,8,0}, {8,7,0},
                        {M, M, M}
                }
        ));

        assertThat(spider.grow(), is(-1));
        assertThat(spider.queue, is(Arrays.asList(5))); assertThat(spider.len, is(5));
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1}, {1,-1,0}, {2,0,2}, {2,-1,0}, {3,0,3}, {3,-1,0}, {4,0,4},
                        {4,-1,0}, {5,0,5},
                        {5,6,0}, {6,5,0}, {6,7,0},{7,6,0}, {7,8,0}, {8,7,0},
                        {M, M, M}
                }
        ));

        assertThat(spider.grow(), is(-1));
        assertThat(spider.grow(), is(-1));
        assertThat(spider.grow(), is(-1));
        assertThat(spider.queue, is(Arrays.asList(8))); assertThat(spider.len, is(8));
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1}, {1,-1,0}, {2,0,2}, {2,-1,0}, {3,0,3}, {3,-1,0}, {4,0,4}, {4,-1,0}, {5,0,5},
                        {5,-1,0}, {6,0,6}, {6,-1,0},{7,0,7}, {7,-1,0}, {8,0,8},
                        {M, M, M}
                }
        ));

        assertThat(spider.grow(), is(-1));
        assertThat(spider.queue, is(Arrays.asList())); assertThat(spider.len, is(9));
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1}, {1,-1,0}, {2,0,2}, {2,-1,0}, {3,0,3}, {3,-1,0}, {4,0,4}, {4,-1,0}, {5,0,5},
                        {5,-1,0}, {6,0,6}, {6,-1,0},{7,0,7}, {7,-1,0}, {8,0,8},
                        {M, M, M}
                }
        ));


    }

    @Test public void t0()   { assertThat(s.findShortest(0, new int[0], new int[0], new long[0], 0), is(-1)); }
    @Test public void t1_1() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 1), is( 2)); }
    @Test public void t1_2() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 2), is(-1)); }

    @Test public void t1_3() {
        //                         0 1 2 3 4
        long[] colors = new long[]{1,2,3,1,3};
        int graphNodesNumber = colors.length;
        int[] graphFrom = new int[]{1,2,2,3};
        int[] graphTo   = new int[]{2,3,4,5};
        int targetColor = 3;

        s.initSpiders(graphNodesNumber, graphFrom, graphTo, colors, targetColor);
        assertThat(s.spiders.size(), is(2));

        ShortestPathInGraph.Resolver.Spider spider1 = s.spiders.get(0);
        ShortestPathInGraph.Resolver.Spider spider2 = s.spiders.get(1);

        assertThat(spider1.startNodeId, is(2));
        assertThat(spider2.startNodeId, is(4));

        assertThat(spider1.spiderId, is(-2));
        assertThat(spider2.spiderId, is(-3));
        assertThat(colors, is(new long[]{1,2,-1,1,-1}));

        s.initGraphIndexes(graphNodesNumber, graphFrom, graphTo, colors, targetColor);

        assertThat( s.graph, is( new int[][]{
                        {0,1,0}, //0
                        {1,0,0}, //1
                        {1,2,0}, //2
                        {1,3,0}, //3
                        {2,1,0}, //4
                        {2,4,0}, //5
                        {3,1,0}, //6
                        {4,2,0}, //7
                        {M, M, M}
                }
        ));

        assertThat(s.index, is(new int[]{0,1,4,6,7}));

        assertThat(spider1.grow(), is(1));
        assertThat(spider1.queue, is(Arrays.asList(2))); assertThat(spider1.len, is(1));
/*
        assertThat( s.graph, is( new int[][]{
                        {0,-1,0}, {1,0,1},
                        {1,2,0}, {2,1,0}, {2,3,0}, {3,2,0}, {3,4,0}, {4,3,0}, {4,5,0}, {5,4,0}, {5,6,0}, {6,5,0}, {6,7,0},{7,6,0}, {7,8,0}, {8,7,0},
                        {M, M, M}
                }
        ));
*/

        //assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 3), is( 1));
    }

    @Test public void t1_4() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 4), is(-1)); }
    @Test public void t1_5() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 5), is(-1)); }
/*    @Test public void t_hr_08(){ assertThat(fileTest("08"), is(-1)); }
*/

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
