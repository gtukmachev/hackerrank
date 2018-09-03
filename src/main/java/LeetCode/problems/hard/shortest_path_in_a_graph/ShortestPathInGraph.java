package LeetCode.problems.hard.shortest_path_in_a_graph;

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

        int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
            if (graphNodes < 2 ) return -1;
            if (graphFrom == null || graphFrom.length == 0) return -1;
            if (graphTo.length != graphFrom.length) throw new RuntimeException("Bad parameters: the graph defined bad - number of 'out' in 'in' nodes in the edges deffinition are different.");
            if (graphNodes != ids.length) throw new RuntimeException("Bad parameters: number of nodes than 'ids' array");

            // index edges
            Map<Integer, Set<Integer>> edges = buildEdges(graphFrom, graphTo);

            // initiate balloons
            List<Balloon> balloons = createBalloons(ids, val, edges);

            if (balloons.size() < 2) return -1;


            while (balloons.size() > 1) {
                // grow all balloons to 1 point
                int minPath = 0;
                for (Balloon b : balloons) {
                    b.growTo1();

                    // check crossing
                    Balloon touchedBalloon = checkCrossing(b, balloons);
                    if (touchedBalloon != null) {
                        int currentPath = touchedBalloon.deep + b.deep;
                        if (touchedBalloon.deep < b.deep) {
                            return currentPath;
                        } else {
                            if (minPath == 0 || currentPath < minPath) minPath = currentPath;
                        }
                    }
                }

                if (minPath != 0) return minPath;

                // remove balloons
                clearBaloons(balloons);
            }


            return -1;
        }

        List<Balloon> createBalloons(long[] ids, int val, Map<Integer, Set<Integer>> edges) {
            List<Balloon> balloons = new LinkedList<>();
            for (int nodeId = 0; nodeId < ids.length; nodeId++) {
                if (ids[nodeId] == val) {
                    // create a baloon
                    balloons.add(new Balloon(nodeId, edges));
                }
            }
            return balloons;
        }

        Map<Integer, Set<Integer>> buildEdges(int[] graphFrom, int[] graphTo) {
            Map<Integer, Set<Integer>> edges = new HashMap<>(graphFrom.length * 2);
            for (int i = 0; i < graphFrom.length; i++ ) {
                edges.computeIfAbsent(graphFrom[i]-1, HashSet::new).add(graphTo[i]-1);
                edges.computeIfAbsent(graphTo[i]-1, HashSet::new).add(graphFrom[i]-1);
            }
            return edges;
        }

        Balloon checkCrossing(Balloon target, List<Balloon> balloons) {
            int minPath = 0;
            Balloon minBallon = null;
            for (Balloon b : balloons) {
                if (target != b) {
                    Set<Integer> nodes = b.getQueue();
                    for (int nodeId : target.getQueue()) {
                        if (nodes.contains(nodeId)) {
                            int path = target.deep + b.deep;
                            if (
                                    (minBallon == null) ||
                                    (path < minPath)    ||
                                    (path == minPath && b.deep < minBallon.deep)
                            ) {
                                minPath = path;
                                minBallon = b;
                            }
                        }
                    }
                }
            }

            return minBallon;
        }

        void clearBaloons(List<Balloon> balloons) {
            Iterator<Balloon> baloonIterator = balloons.iterator();
            while (baloonIterator.hasNext()) {
                Balloon b = baloonIterator.next();
                if (b.canNotGrow()) baloonIterator.remove();
            }
        }

    }

    static class IntWrapper{
        int val;
        IntWrapper(int val) { this.val = val; }
    }

    static class Balloon {
        private int deep = 0;
        private Set<Integer> queue1 = new HashSet<>();
        private Set<Integer> queue2 = new HashSet<>();
        private Set<Integer> visited = new HashSet<>();
        Map<Integer, Set<Integer>> edges;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Balloon balloon = (Balloon) o;

            if (deep != balloon.deep) return false;
            if (queue1 != null ? !queue1.equals(balloon.queue1) : balloon.queue1 != null) return false;
            if (queue2 != null ? !queue2.equals(balloon.queue2) : balloon.queue2 != null) return false;
            if (visited != null ? !visited.equals(balloon.visited) : balloon.visited != null) return false;
            return edges != null ? edges.equals(balloon.edges) : balloon.edges == null;
        }

        @Override
        public int hashCode() {
            int result = deep;
            result = 31 * result + (queue1 != null ? queue1.hashCode() : 0);
            result = 31 * result + (queue2 != null ? queue2.hashCode() : 0);
            result = 31 * result + (visited != null ? visited.hashCode() : 0);
            result = 31 * result + (edges != null ? edges.hashCode() : 0);
            return result;
        }

        Balloon(int nodeId, Map<Integer, Set<Integer>> edges) {
            this.edges = edges;
            getQueue().add(nodeId);


        }

        Set<Integer> getQueue(){
            return (deep & 1) == 0 ? queue1 : queue2;
        }

        boolean canNotGrow() { return getQueue().isEmpty(); }

        void growTo1(){
            Set<Integer> srcQueue = getQueue();
            deep++;
            Set<Integer> dstQueue = getQueue();

            for (int srcNodeId : srcQueue) {
                Set<Integer> dstNodes = edges.get(srcNodeId);
                if (dstNodes != null) {
                    for (int dstNodeId : dstNodes) {
                        if (!visited.contains(dstNodeId)) {
                            visited.add(dstNodeId);
                            dstQueue.add(dstNodeId);
                        }
                    }
                }
            }
            srcQueue.clear();
        }
    }

}
