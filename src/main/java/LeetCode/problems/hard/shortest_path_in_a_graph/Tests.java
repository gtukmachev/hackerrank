package LeetCode.problems.hard.shortest_path_in_a_graph;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 9/3/2018.
 */
public class Tests {

    ShortestPathInGraph.Solution s = new ShortestPathInGraph().new Solution();


    @Test public void tt_1() {
        assertThat(s.findShortest(0, new int[0], new int[0], new long[0], 0), is(-1));
    }


    @Test public void t0() {
        assertThat(s.findShortest(0, new int[0], new int[0], new long[0], 0), is(-1));
    }

    @Test public void t1_1() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 3), is(1)); }
    @Test public void t1_2() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 1), is(2)); }
}
