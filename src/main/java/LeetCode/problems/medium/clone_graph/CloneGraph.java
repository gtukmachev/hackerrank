package LeetCode.problems.medium.clone_graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */
public class CloneGraph implements CloneGraphI {

    @Override
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return null;

        Map<UndirectedGraphNode, UndirectedGraphNode> nodes = new HashMap<>();
        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
        nodes.put(node, newNode);

        cloneNeigbors(newNode, node, nodes);

        return newNode;
    }

    private void cloneNeigbors(UndirectedGraphNode newNode, UndirectedGraphNode node, Map<UndirectedGraphNode, UndirectedGraphNode> nodes) {
        for (UndirectedGraphNode child : node.neighbors) {

            boolean isNew = !nodes.containsKey(child);

            UndirectedGraphNode newChild = nodes.computeIfAbsent( child, l -> new UndirectedGraphNode(l.label) );
            newNode.neighbors.add( newChild );

            if (isNew) cloneNeigbors(newChild, child, nodes);
        }
    }


}
