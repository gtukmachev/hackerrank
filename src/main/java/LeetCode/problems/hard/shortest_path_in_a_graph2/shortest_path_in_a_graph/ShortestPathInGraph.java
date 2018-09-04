package LeetCode.problems.hard.shortest_path_in_a_graph2.shortest_path_in_a_graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by grigory@clearscale.net on 9/3/2018.
 */
public class ShortestPathInGraph {

    /*
     * Complete the findShortest function below.
     * For the unweighted graph, <name>:
     *
     * 1. The number of nodes is <name>Nodes.
     * 2. The number of edges is <name>Edges.
     * 3. An edge exists between <name>From[i] to <name>To[i].
     *
     */

    static int findShortest(int graphNodes,
                            int[] graphFrom,
                            int[] graphTo,
                            long[] ids,
                            int val)
    {
        return new ShortestPathInGraph.Resolver().findShortest(graphNodes, graphFrom, graphTo, ids, val);
    }


    static class Resolver {
        int[][] graph;
        long[] colors;
        int targetColor;

        int findShortest(int graphNodesNumber, int[] graphFrom, int[] graphTo, long[] ids, int val) {
            if (graphNodesNumber < 2) return -1;
            if (graphFrom == null || graphFrom.length == 0) return -1;
            if (graphTo.length != graphFrom.length) throw new RuntimeException("Bad parameters: the graph defined bad - number of 'out' in 'in' nodes in the edges deffinition are different.");
            if (graphNodesNumber != ids.length)           throw new RuntimeException("Bad parameters: number of nodes than 'ids' array");

            colors = ids;
            targetColor = val;

            graph = buildGraph(graphFrom, graphTo);
            sortGraph(graph);
            int[] index = buildGraphIndex(graph, graphNodesNumber);

            List<Spider> spiders = createSpiders(colors, targetColor);

            return 0;
        }

        int[][] buildGraph(int[] graphFrom, int[] graphTo) {
            int[][] graph = new int[graphFrom.length * 2 + 1][3];

            // build edges with weights
            for (int i = 0, j = graphFrom.length; i < graphFrom.length; i++) {
                graph[i][0] = graphFrom[i]-1; graph[i][1] = graphTo[i]-1;
                graph[j][0] = graphTo[i]-1;   graph[j][1] = graphFrom[i]-1;
                j++;
            }

            // barrier valur
            graph[graph.length-1][0] = Integer.MAX_VALUE;
            graph[graph.length-1][1] = Integer.MAX_VALUE;
            graph[graph.length-1][2] = Integer.MAX_VALUE;

            return graph;
        }

        void sortGraph(int[][] graph) {
            Arrays.sort(graph, (a1, a2) -> {
                int cond = a1[0] - a2[0];
                if (cond != 0) return cond;
                return a1[1] - a2[1];
            });
        }

        int[] buildGraphIndex(int[][] graph, int graphNodesNumber) {
            int[] index = new int[graphNodesNumber];
            int L = graph.length - 1;
            int j = 0;
            index[j]=0;
            for (int i = 1; i < L; i++) {
                if (graph[i][0] != index[j]) {
                    j++;
                    while(j < graph[i][0]) index[j++] = -1;
                    index[j] = i;
                }
            }
            return index;
        }

        List<Spider> createSpiders(long[] nodeColors, int neededColor){
            List<Spider> sps = new LinkedList<>();
            for (int i = 0; i < nodeColors.length; i++) {
                if (nodeColors[i] == neededColor) sps.add( new Spider(i) );
            }
            return sps;
        }

        class Spider {
            int len = 0;
            Queue<Integer> queue = new LinkedList<>();

            public Spider(int nodeId) {
                queue.add(nodeId);
                colors[nodeId] = -1;
            }
        }

    }



}
