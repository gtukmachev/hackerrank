package hackerrank.practice.algorithms.graph_theory.bead_ornaments;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.collect.FluentIterable.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * n == 1 -> 1
 * n == 0 -> 1
 *      _ -> n^(n-2)
 */
public class GraphsCounter {



    public static void main(String[] args) {
        GraphsCounter gc = new GraphsCounter(); // "gc" :-)

        gc.resolve(Graph(), ImmutableSet.of(1,2,3));

        System.out.println("Graphs Number = " + gc.graphs.size());

        int i = 0;
        for (Map.Entry<Graph, Integer> e : gc.graphs.entrySet()) {
            i++;
            System.out.println( e.getValue() + " " + e.getKey().edges );
            if ( i == 125 ) break;
        }

    }

    private Map<Graph, Integer> graphs = new HashMap<>();

    private void resolve(Graph graph, ImmutableSet<Integer> nodes) {
        if (nodes.isEmpty()) { consume(graph); return; }

        for (Integer n : nodes) {
            ImmutableSet<Integer> tail = from(nodes).filter(i -> i != n).toSet();
            if (graph.nodes.size() == 0) {
                resolve(Graph(n), tail);
            } else {
                for (Integer srcNode : graph.nodes) {
                    resolve(graph.addEdge( srcNode, n ), tail);
                }
            }

        }
    }

    private void consume(Graph graph) {
        graphs.merge(graph, 1, (i, j)->i+j);
    }


    static Graph Graph(){ return new Graph(ImmutableSet.of(), ImmutableSet.of()); }
    static Graph Graph(int node){ return new Graph(node); }
    static class Graph {
        final ImmutableSet<Integer> nodes;
        final ImmutableSet<Edge> edges;

        Graph(int node) {
            nodes = ImmutableSet.of(node);
            edges = ImmutableSet.of();
        }

        private Graph(ImmutableSet<Integer> nodes, ImmutableSet<Edge> edges) {
            this.nodes = nodes;
            this.edges = edges;
        }

        Graph addEdge(int nodeInGraph, int newNode) {
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

            Edge e = Edge(nodeInGraph, newNode);
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

    static Edge Edge(int f, int t) { return new Edge(f, t); }
    public static class Edge {
        final int f, t;

        Edge(int f, int t) {
            this.f = f < t ? f : t; // min
            this.t = f < t ? t : f; // max
        }

        @Override
        public String toString() { return "(" + f + "-" + t + ")"; }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (f != edge.f) return false;
            return t == edge.t;
        }

        @Override
        public int hashCode() {
            return 31 * f + t;
        }
    }

    public static class Tests {

        @Test public void tEdgesEquals1() {
            assertThat(Edge(1,2), equalTo(Edge(2,1)) );
        }

        @Test public void tEdgesEquals2() {
            assertThat( Edge(1,2), is(Edge(1,2)) );
        }

        @Test public void tEdgesNotEquals() {
            assertThat( Edge(2,2), is(not(Edge(2,1))) );
        }

        @Test public void tGraphsEqual1() {
            assertThat(
                         Graph(1)
                        , is(Graph(1))
            );
        }

        @Test public void tGraphsEqual2() {
            assertThat(
                             Graph(1)
                    , is(not(Graph(2)))
            );
        }

        @Test public void tGraphsEqual3() {
            assertThat(
                         Graph(1).addEdge(1,2).addEdge(1,3).addEdge(3,4)
                    , is(Graph(1).addEdge(1,2).addEdge(1,3).addEdge(3,4))
            );
        }

        @Test public void tGraphsEqual4() {
            assertThat(
                         Graph(1).addEdge(1,2).addEdge(1,3).addEdge(3,4)
                    , is(Graph(1).addEdge(1,3).addEdge(1,2).addEdge(3,4))
            );
        }

        @Test public void tGraphsEqual5() {
            assertThat(
                             Graph(1).addEdge(1,2).addEdge(1,3).addEdge(3,4)
                    , is(not(Graph(1).addEdge(1,3).addEdge(1,2).addEdge(1,4)))
            );
        }

    }

}
