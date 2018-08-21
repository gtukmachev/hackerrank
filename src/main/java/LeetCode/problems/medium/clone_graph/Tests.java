package LeetCode.problems.medium.clone_graph;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */
public class Tests {

    CloneGraphI s = new CloneGraph();

    @Test public void tts1() {
        //the graph is: 1 -> 2

        UndirectedGraphNode n1 = new UndirectedGraphNode(1);
        UndirectedGraphNode n2 = new UndirectedGraphNode(2);
        n1.neighbors.add(n2);

        assertThat( n1.toString(), is("{#1,2#2}") );
    }
    @Test public void tts2() {
        //the graph is: 1 -> 2 -> 1

        UndirectedGraphNode n1 = new UndirectedGraphNode(1);
        UndirectedGraphNode n2 = new UndirectedGraphNode(2);
        n1.neighbors.add(n2);
        n2.neighbors.add(n1);

        assertThat( n1.toString(), is("{#1,2#2,1}") );
    }

    @Test public void ttd1() {
        UndirectedGraphNode n = createGraph("{#1,2#2}");

        assertThat(n.label, is(1));
        assertThat(n.neighbors.size(), is(1));
        assertThat(n.neighbors.get(0).label, is(2));
        assertThat(n.neighbors.get(0).neighbors.size(), is(0));
    }
    @Test public void ttd2() {
        UndirectedGraphNode n = createGraph("{#1,2#2,1}");

        assertThat(n.label, is(1));
        assertThat(n.neighbors.size(), is(1));
        assertThat(n.neighbors.get(0).label, is(2));
        assertThat(n.neighbors.get(0).neighbors.size(), is(1));
        assertTrue( n.neighbors.get(0).neighbors.get(0) == n );
    }


    @Test public void t1(){
        //the graph is: 1 -> 2

        UndirectedGraphNode n1 = new UndirectedGraphNode(1);
        UndirectedGraphNode n2 = new UndirectedGraphNode(2);
        n1.neighbors.add(n2);

        UndirectedGraphNode a1 = s.cloneGraph(n1);

        assertFalse(n1 == a1);
        assertThat(a1.label, is(n1.label));
        assertThat(a1.neighbors.size(), is(1));

        UndirectedGraphNode a2 = a1.neighbors.get(0);
        assertFalse(n2 == a2);
        assertThat(a2.label, is(n2.label));
        assertThat(a2.neighbors.size(), is(0));
    }
    @Test public void t2(){ cloneTest("{#0,1,2#1,2#2,2}"); }


    public void cloneTest(String graph){

        UndirectedGraphNode task = createGraph(graph);
        UndirectedGraphNode answer = s.cloneGraph(task);

        assertThat(answer.toString(), is( task.toString() ));

        Map<Integer, UndirectedGraphNode> nodes = new HashMap<>();

        task.depthTravers( n -> nodes.put(n.label, n));
        answer.depthTravers( n -> {
            UndirectedGraphNode old = nodes.remove(n.label);
            assertNotNull(old);
            assertNotSame(n, old);
        });

        assertThat(nodes.size(), is(0));
    }


    UndirectedGraphNode createGraph(String s) {
        String sub = s.substring(1, s.length()-1);

        Map<Integer, UndirectedGraphNode> nodes = new HashMap<>();

        UndirectedGraphNode root = null;

        String[] groups = sub.split("#");
        for (int r = 0; r < groups.length; r++) {
            String gr = groups[r];
            if (gr != null && gr.length() > 0) {

                String[] nd = gr.split(",");
                UndirectedGraphNode node  = nodes.computeIfAbsent(Integer.parseInt(nd[0]), UndirectedGraphNode::new);
                if (root == null) root = node;

                for (int i = 1; i < nd.length; i++) {
                    int childLabel = Integer.parseInt(nd[i]);
                    UndirectedGraphNode childNode  = nodes.computeIfAbsent(childLabel, UndirectedGraphNode::new);
                    node.neighbors.add(childNode);
                }

            }
        }

        return root;

    }
}
