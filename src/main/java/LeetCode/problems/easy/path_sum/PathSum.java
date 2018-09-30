package LeetCode.problems.easy.path_sum;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 9/30/2018.
 */
public class PathSum {

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

        public boolean hasPathSum(TreeNode n, int sum) {
            if (n == null) return false;

            if (n.right == null && n.left == null) return sum == n.val;

            int newSum = sum - n.val;

            return hasPathSum(n.left,  newSum) || hasPathSum(n.right, newSum);
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
            assertThat(s.hasPathSum(t(5, 4,8, 11,null,13,4, 7,2,null,null,null,null,null,1), 22), is(true));
        }

        @Test public void t_leet_1_1() {
            assertThat(s.hasPathSum(t(5, 3,8, 11,null,13,4, 7,2,null,null,null,null,null,1), 22), is(false));
        }

        @Test public void t_leet_1_2() {
            assertThat(s.hasPathSum(t(5, 3,8, 11,null,13,4, 7,2,null,null,null,null,null,1), 17), is(false));
        }

        @Test public void t_leet_1_3() {
            assertThat(s.hasPathSum(t(5, 3,8, 11,null,13,4, 7,2,null,null,null,null,null,1), 18), is(true));
        }

/*
        @Test public void t_leet_2() {
            assertThat(s.isBalanced(t(1,2,2,3,3,null,null,4,4)), is(false));
        }
*/

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
