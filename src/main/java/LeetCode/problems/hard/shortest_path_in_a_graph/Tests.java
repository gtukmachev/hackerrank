package LeetCode.problems.hard.shortest_path_in_a_graph;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 9/3/2018.
 */
public class Tests {

    ShortestPathInGraph.Solution s = new ShortestPathInGraph().new Solution();


    @Test public void tt_buildEdges() {
        Map<Integer, Set<Integer>> response = s.buildEdges(new int[]{1,2,2,3}, new int[]{2,3,4,5});

        Map<Integer, Set<Integer>> answer = new HashMap<>();
        Set<Integer> from1 = new HashSet<>();
        from1.add(2);
        answer.put(1, from1);

        Set<Integer> from2 = new HashSet<>();
        from2.add(1);
        from2.add(3);
        from2.add(4);
        answer.put(2, from2);

        Set<Integer> from3 = new HashSet<>();
        from3.add(2);
        from3.add(5);
        answer.put(3, from3);

        Set<Integer> from4 = new HashSet<>();
        from4.add(2);
        answer.put(4, from4);

        Set<Integer> from5 = new HashSet<>();
        from5.add(3);
        answer.put(5, from5);

        assertThat(response, is(answer));
    }

    @Test public void tt_createBalloons() {
        Map<Integer, Set<Integer>> edges = s.buildEdges(new int[]{1,2,2,3}, new int[]{2,3,4,5});
        List<ShortestPathInGraph.Balloon> response = s.createBalloons(new long[]{1,2,3,1,3}, 3, edges);

        List<ShortestPathInGraph.Balloon> answer = new LinkedList<>();

        answer.add(new ShortestPathInGraph.Balloon(2, edges));
        answer.add(new ShortestPathInGraph.Balloon(4, edges));

        assertThat(response, is(answer));
    }

    @Test public void t0() {
        assertThat(s.findShortest(0, new int[0], new int[0], new long[0], 0), is(-1));
    }

    @Test public void t1_1() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 3), is(1)); }
    @Test public void t1_2() { assertThat(s.findShortest(5, new int[]{1,2,2,3}, new int[]{2,3,4,5}, new long[]{1,2,3,1,3}, 1), is(2)); }
}
