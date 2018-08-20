package kt.LeetCode.problems.easy.symmetric_tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by grigory@clearscale.net on 8/20/2018.
 */
public class Tests {

    SymmetricTree_via_recursion.Solution s = new SymmetricTree_via_recursion().new Solution();

    @Test
    public void tt0() {
        TreeNode task = tree(0,
                1, 2,
                3, 4, 5, 6);

        TreeNode solution = new TreeNode(0);
        solution.left = new TreeNode(1);
        solution.left.left = new TreeNode(3);
        solution.left.right = new TreeNode(4);

        solution.right = new TreeNode(2);
        solution.right.left = new TreeNode(5);
        solution.right.right = new TreeNode(6);


        assertThat(task, is(solution));
    }

    @Test
    public void leet1() {
        assertTrue(s.isSymmetric(tree(1, 2, 2, 3, 4, 4, 3)));
    }

    @Test
    public void leet2() {
        assertFalse(s.isSymmetric(tree(1, 2, 2, null, 3, null, 3)));
    }

    @Test
    public void leet3() {
        assertFalse(s.isSymmetric(tree(1, null, 2)));
    }


    @Test
    public void t1() {
        assertTrue(s.isSymmetric(tree(1, 2, 2, 3, null, null, 3)));
    }

    @Test
    public void t2() {
        assertTrue(s.isSymmetric(tree(1, 2, 2, 3, null, null, 3, 5, null, null, null, null, null, null, 5)));
    }


    TreeNode tree(Integer... a) {
        return tree_(0, a);
    }

    private TreeNode tree_(int pos, Integer... a) {
        if (a == null || pos >= a.length) return null;
        if (a[pos] == null) return null;

        TreeNode n = new TreeNode(a[pos]);
        int left = (pos << 1) + 1;
        int right = left + 1;

        n.left = tree_(left, a);
        n.right = tree_(right, a);

        return n;
    }
}
