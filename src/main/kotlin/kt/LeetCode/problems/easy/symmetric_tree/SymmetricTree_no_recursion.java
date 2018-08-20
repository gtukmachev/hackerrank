package kt.LeetCode.problems.easy.symmetric_tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by grigory@clearscale.net on 8/20/2018.
 */
public class SymmetricTree_no_recursion {

    public class Solution {

        private class Solver{

            private Queue<TreeNode> queue = new LinkedList<>();

            public boolean solve(TreeNode root) {
                if (root == null) return true;
                if (root.left == null && root.right == null ) return true;

                queue.add(root.left);
                queue.add(root.right);

                while (queue.size() > 0) {
                    TreeNode a = queue.remove();
                    TreeNode b = queue.remove();

                    if ((a == null && b !=null) || (b == null && a != null)) {
                        return false;
                    } else if (a == null && b == null) {

                    } else if (a.val != b.val) {
                        return false;
                    } else {
                        queue.add(a.left); queue.add(b.right);
                        queue.add(a.right); queue.add(b.left);
                    }

                }

                return true;
            }

        }

        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            if (root.left == null && root.right == null ) return true;

            return new Solver().solve(root);
        }

    }

}
