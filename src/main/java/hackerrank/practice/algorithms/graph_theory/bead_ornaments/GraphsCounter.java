package hackerrank.practice.algorithms.graph_theory.bead_ornaments;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/11/2018.
 */
public class GraphsCounter {



    static class Graph {
        final ImmutableSet<Integer> nodes;
        final ImmutableSet<Edge> edges;

        Graph(int node) {
            nodes = ImmutableSet.of(node);
            edges = ImmutableSet.of();
        }

        Graph(ImmutableSet<Integer> nodes, ImmutableSet<Edge> edges) {
            this.nodes = nodes;
            this.edges = edges;
        }

        Graph add(int nodeInGraph, int newNode) {
            boolean isNodeInGraphExists = false;
            for (int node : nodes) {
                if (newNode == node) {
                    throw new RuntimeException("Node " + newNode + " already present in the graph.");
                } else {
                    isNodeInGraphExists |= (node == nodeInGraph);
                }
            }

            if (!isNodeInGraphExists) {
                throw new RuntimeException("There is no node " + nodeInGraph + " in the graph.");
            }

            Edge e = new Edge(nodeInGraph, newNode);
            if (edges.contains(e)) {
                throw new RuntimeException("There is already present one edge " + edges + " in the graph");
            }

            return new Graph(
                    ImmutableSet.<Integer>builder().addAll(nodes).add(newNode).build(),
                    ImmutableSet.<Edge>builder().addAll(edges).add(e).build()
            );

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Graph graph = (Graph) o;

            if (nodes != null ? !nodes.equals(graph.nodes) : graph.nodes != null) return false;
            return edges != null ? edges.equals(graph.edges) : graph.edges == null;
        }

        @Override
        public int hashCode() {
            int result = nodes != null ? nodes.hashCode() : 0;
            result = 31 * result + (edges != null ? edges.hashCode() : 0);
            return result;
        }

    }

    public static Edge Edge(int f, int t) { return new Edge(f, t); }
    public static class Edge {
        final int f, t;

        Edge(int f, int t) {
            this.f = f;
            this.t = t;
        }

        @Override
        public String toString() { return "(" + f + "-" + t + ")"; }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge e = (Edge) o;
            return (t == e.t && f == e.f) || (f == e.t && t == e.f);
        }

        @Override
        public int hashCode() {
            int min = f < t ? f : t;
            int max = f < t ? t : f;
            return 31 * min + max;
        }
    }

    public static class Tests {

        @Test public void tEdgesEquals1() {
            assertThat(new Edge(1,2), equalTo(new Edge(2,1)) );
        }

        @Test public void tEdgesEquals2() {
            assertThat( new Edge(1,2), is(new Edge(1,2)) );
        }

        @Test public void tEdgesNotEquals() {
            assertThat( new Edge(2,2), is(not(new Edge(2,1))) );
        }

        @Test public void tGraphsEqual1() {
            assertThat(
                         new Graph(1)
                        , is(new Graph(1))
            );
        }

        @Test public void tGraphsEqual2() {
            assertThat(
                             new Graph(1)
                    , is(not(new Graph(2)))
            );
        }

        @Test public void tGraphsEqual3() {
            assertThat(
                         new Graph(1).add(1,2).add(1,3).add(3,4)
                    , is(new Graph(1).add(1,2).add(1,3).add(3,4))
            );
        }

        @Test public void tGraphsEqual4() {
            assertThat(
                         new Graph(1).add(1,2).add(1,3).add(3,4)
                    , is(new Graph(1).add(1,3).add(1,2).add(3,4))
            );
        }

        @Test public void tGraphsEqual5() {
            assertThat(
                             new Graph(1).add(1,2).add(1,3).add(3,4)
                    , is(not(new Graph(1).add(1,3).add(1,2).add(1,4)))
            );
        }

    }

}
