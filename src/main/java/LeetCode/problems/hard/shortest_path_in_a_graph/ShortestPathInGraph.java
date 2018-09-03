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
    public class Solution {

        int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
            if (graphNodes < 2 ) return -1;
            if (graphFrom == null || graphFrom.length == 0) return -1;
            if (graphTo.length != graphFrom.length) throw new RuntimeException("Bad parameters: the graph defined bad - number of 'out' in 'in' nodes in the edges deffinition are different.");


            // index edges
            Map<Integer, Set<Integer>> edges = new HashMap<>(graphFrom.length * 2);
            for (int i = 0; i < graphFrom.length; i++ ) {
                edges.computeIfAbsent(graphFrom[i], HashSet::new).add(graphTo[i]);
                edges.computeIfAbsent(graphTo[i], HashSet::new).add(graphFrom[i]);
            }

            // initiate balloons
            List<Balloon> balloons = new LinkedList<>();
            for (int nodeId = 0; nodeId < graphNodes; nodeId++) {
                if (ids[nodeId] == val) {
                    // create a baloon
                    balloons.add(new Balloon(nodeId, edges));
                }
            }

            if (balloons.size() < 2) return -1;



            int minLen = Integer.MAX_VALUE;

            while (balloons.size() > 1) {
                // grow all balloons to 1 point
                for (Balloon b : balloons) { b.growTo1(); }

                // check crossing
                int solution = checkCrossing(balloons);
                if (solution != 0) return solution;


                // remove balloons
                clearBaloons(balloons);
            }


            return -1;
        }

        int checkCrossing(List<Balloon> balloons) {
            Map<Integer, Set<Balloon>> crossingMap = new HashMap<>();
            IntWrapper min = new IntWrapper(0);

            for (Balloon thisBaloon : balloons) {
                for (int nodeId_ : thisBaloon.getQueue()) {
                    crossingMap.compute( nodeId_, (integer, setOfBallons) -> {
                        if (setOfBallons == null) setOfBallons = new HashSet<>();

                        for (Balloon crossedBalloon : setOfBallons ) {
                            int path = thisBaloon.deep + crossedBalloon.deep - 1;
                            if (min.val == 0 || path <= min.val) min.val = path;
                            if (path == 1) {
                                // got it! stop algorith here
                            }
                        }

                        setOfBallons.add(thisBaloon);
                        return setOfBallons;

                    });
                }
            }

            return min.val;
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
