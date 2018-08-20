package kt.LeetCode.problems.easy.symmetric_tree;

/**
 * Created by grigory@clearscale.net on 8/20/2018.
 */
public class SymmetricTree_via_recursion {

    public class Solution {

        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            if (root.left == null && root.right == null ) return true;

            return isMirrored(root.left, root.right);
        }

        public boolean isMirrored(TreeNode a, TreeNode b){
            if (a == null && b == null) return true;
            if (a == null || b == null) return false;

            if (a.val != b.val) return false;

            return isMirrored(a.left, b.right) && isMirrored(a.right, b.left);
        }

    }

}
