package LeetCode.problems.medium.clone_graph;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */
public class UndirectedGraphNode {
    int label;
    List<UndirectedGraphNode> neighbors;

    UndirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<>();
    }

    void depthTravers(Consumer<UndirectedGraphNode> f){
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.add(this);

        Set<Integer> visited = new HashSet<>();
        visited.add(this.label);

        while (q.size() > 0) {
            UndirectedGraphNode n = q.remove();
            f.accept(n);
            for (UndirectedGraphNode child : n.neighbors)
                if (!visited.contains(child.label)) {
                    visited.add(child.label);
                    q.add( child );
                }
        }
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");
        this.depthTravers( n -> {
            s.append("#").append(n.label);
            for (UndirectedGraphNode ch : n.neighbors) {
                s.append(",").append(ch.label);
            }
        });

        return s.append("}").toString();
    }
}
