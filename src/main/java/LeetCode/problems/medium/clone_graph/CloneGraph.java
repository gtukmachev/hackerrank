package LeetCode.problems.medium.clone_graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */
public class CloneGraph implements CloneGraphI {

    @Override
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {

        Map<Integer, UndirectedGraphNode> visited = new HashMap<>();
        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
        visited.put(node.label, newNode);

        cloneNeigbors(newNode, node, visited);

        return newNode;
    }

    private void cloneNeigbors(UndirectedGraphNode newNode, UndirectedGraphNode node, Map<Integer, UndirectedGraphNode> clonned) {

        for (UndirectedGraphNode link : node.neighbors) {
            boolean isNew = !clonned.containsKey(link.label);
            UndirectedGraphNode newChild = clonned.computeIfAbsent( link.label, UndirectedGraphNode::new );
            newNode.neighbors.add( newChild );
            if (isNew) cloneNeigbors(newChild, link, clonned);
        }

    }


}
