package LeetCode.problems.hard.shortest_path_in_a_graph2.shortest_path_in_a_graph;

import java.util.*;

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
        int[] index;
        List<Spider> spiders;


        void initSpiders(int graphNodesNumber, int[] graphFrom, int[] graphTo, long[] ids, int val) {
            colors = ids;
            targetColor = val;
            spiders = createSpiders(colors, targetColor);
        }

        void initGraphIndexes(int graphNodesNumber, int[] graphFrom, int[] graphTo, long[] ids, int val) {
            graph = buildGraph(graphFrom, graphTo);
            sortGraph(graph);
            index = buildGraphIndex(graph, graphNodesNumber);
        }

        int findShortest(int graphNodesNumber, int[] graphFrom, int[] graphTo, long[] ids, int val) {
            if (graphNodesNumber < 2) return -1;
            if (graphFrom == null || graphFrom.length == 0) return -1;
            if (graphTo.length != graphFrom.length) throw new RuntimeException("Bad parameters: the graph defined bad - number of 'out' in 'in' nodes in the edges deffinition are different.");
            if (graphNodesNumber != ids.length)           throw new RuntimeException("Bad parameters: number of nodes than 'ids' array");

            initSpiders(graphNodesNumber, graphFrom, graphTo, ids, val);
            if (spiders.isEmpty()) return -1;

            initGraphIndexes(graphNodesNumber, graphFrom, graphTo, ids, val);

            while (!spiders.isEmpty()) {
                int minPath = -1;
                int currentLen = spiders.get(0).len;

                for (Spider s : spiders) {
                    int detectedMinPath = s.grow();
                    if (detectedMinPath != -1) {
                        int fullMinPath = detectedMinPath + currentLen;
                        if (detectedMinPath < currentLen) {
                            return fullMinPath;
                        } else {
                            if (minPath == -1 || fullMinPath < minPath) minPath = fullMinPath;
                        }
                    }
                }

                if (minPath != -1) return minPath;
                clearSpiders();
            }

            return -1;
        }


        private void clearSpiders() {
            Iterator<Spider> it = spiders.iterator();
            while(it.hasNext()) {
                Spider s = it.next();
                if (s.queue.isEmpty()) it.remove();
            }
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
            int spiderId = -2;
            for (int i = 0; i < nodeColors.length; i++) {
                if (nodeColors[i] == neededColor) sps.add( new Spider(i, spiderId--) );
            }
            return sps;
        }

        class Spider {
            int spiderId;
            int startNodeId;
            int len = 0;
            Queue<Integer> queue = new LinkedList<>();

            Spider(int nodeId, int spiderId) {
                this.startNodeId = nodeId;
                this.spiderId = spiderId;
                this.queue.add(nodeId);
                Resolver.this.colors[nodeId] = -1;
            }

            int grow() {
                int minPath = -1;
                len++;
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {

                    int nodeFromId = queue.remove();
                    int edgeId = index[nodeFromId];

                    while (graph[edgeId][0] == nodeFromId) {
                        int nodeToId = graph[edgeId][1];

                        if (nodeToId != -1 && nodeToId != startNodeId) { // skipping of erased edges and back edges
                            graph[edgeId][1] = -1; // erasing of the forward edge

                            long nodeToColor = colors[nodeToId];
                            if (nodeToColor == -1) { // got it - the target
                                if (minPath == -1 || graph[edgeId][2] < minPath) minPath = graph[edgeId][2];
                                if (minPath < len) return minPath;
                            } else { // skip it - this pider already was in this node
                                prolongBackEdge(nodeFromId, nodeToId);

                                if (nodeToColor != this.spiderId) {
                                    queue.add(nodeToId);
                                    colors[nodeToId] = this.spiderId;
                                }
                            }

                        }

                        edgeId++;
                    }

                }
                return minPath;
            }

            void prolongBackEdge(int nodeFromId, int nodeToId) {
                int edgeId = Resolver.this.index[nodeToId];
                while (graph[edgeId][0] == nodeToId) {
                    if (graph[edgeId][1] == nodeFromId) {
                        graph[edgeId][1] = this.startNodeId;
                        graph[edgeId][2] = this.len;
                    }
                    edgeId++;
                }
            }

        }

    }



}
