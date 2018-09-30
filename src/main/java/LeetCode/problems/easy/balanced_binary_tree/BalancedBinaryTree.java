package LeetCode.problems.easy.balanced_binary_tree;

import org.junit.Test;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 9/30/2018.
 */
public class BalancedBinaryTree {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TreeNode)) return false;

            TreeNode treeNode = (TreeNode) o;

            if (val != treeNode.val) return false;
            if (left != null ? !left.equals(treeNode.left) : treeNode.left != null) return false;
            return right != null ? right.equals(treeNode.right) : treeNode.right == null;
        }

        @Override
        public int hashCode() {
            int result = val;
            result = 31 * result + (left != null ? left.hashCode() : 0);
            result = 31 * result + (right != null ? right.hashCode() : 0);
            return result;
        }

        public TreeNode withLeft(TreeNode left) {
            this.left = left;
            return this;
        }

        public TreeNode withRight(TreeNode right) {
            this.right = right;
            return this;
        }
    }

    static class Solution {

        /**
         * <p>Determine if a tree is balanced or not</p>
         * <p>An empty tree (root == null) is a balanced tree with height = 0.</p>
         *
         * @param root - root node of the tree
         * @return 'true' for a balanced tree and 'false' for unbalanced
         */
        public boolean isBalanced(TreeNode root) {
            return height(root) >= 0;
        }

        /**
         * Determine height of a balanced tree (or -1 if the tree is unbalanced)
         * @param node - root of the tree
         * @return height of a balanced tree (or -1 if the tree is unbalanced)
         */
        int height(TreeNode node) {
            if (node == null) return 0;

            int leftHeight = height(node.right);
            if (leftHeight == -1) return -1;

            int rightHeight = height(node.left) ;
            if (rightHeight == -1) return -1;

            if (abs(rightHeight - leftHeight) <= 1) {
                return max(leftHeight, rightHeight) + 1;
            }

            return -1;
        }

    }

    public static class Tests {

        Solution s = new Solution();

        @Test public void tt0() {

            assertThat( t(0,1,2,null,4,5,null), is(
                    new TreeNode(0)
                        .withLeft( new TreeNode(1)
                                        .withRight(new TreeNode(4)) )
                        .withRight( new TreeNode(2)
                                        .withLeft(new TreeNode(5)))
            ) );
        }

        @Test public void t_leet_1() {
            assertThat(s.isBalanced(t(3,9,20,null,null,15,7)), is(true));
        }

        @Test public void t_leet_2() {
            assertThat(s.isBalanced(t(1,2,2,3,3,null,null,4,4)), is(false));
        }

        TreeNode t(Integer... nodes){ return tree(0, nodes); }
        TreeNode tree(int i, Integer... nodes){
            if (i >= nodes.length || nodes[i] == null) return null;

            TreeNode node = new TreeNode(nodes[i]);
            node.left = tree((i << 1) + 1, nodes);
            node.right = tree((i << 1) + 2, nodes);

            return node;
        }

    }

}
