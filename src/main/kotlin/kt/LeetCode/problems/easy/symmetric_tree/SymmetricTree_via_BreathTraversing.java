package kt.LeetCode.problems.easy.symmetric_tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by grigory@clearscale.net on 8/20/2018.
 */
public class SymmetricTree_via_BreathTraversing {

    public class Solution {

        public boolean isSymmetric(TreeNode root) {

            if (root == null) return true;
            if (root.left == null && root.right == null ) return true;

            Queue<TreeNode> queue = new LinkedList<>();
            Deque<Integer> stack = new LinkedList<>();

            queue.add(root.left);
            queue.add(root.right);

            while (queue.size() > 0) {
                int half = queue.size() >> 1;
                //if (queue.size() != (half << 1)) return false;

                for (int i = 1; i <= half; i++){
                    TreeNode n = queue.remove();
                    if (n == null) {
                        stack.addFirst(null);
                    } else {
                        stack.addFirst(n.val);
                        queue.add(n.left);
                        queue.add(n.right);
                    }
                }

                for (int i = half; i >= 1; i--){
                    TreeNode n = queue.remove();
                    if (n == null) {
                        if (stack.pop() != null) return false;
                    } else {
                        Integer st = stack.pop();
                        if ( st == null || st != n.val ) return false;
                        queue.add(n.left);
                        queue.add(n.right);
                    }
                }

            }

            return true;
        }

    }

}
