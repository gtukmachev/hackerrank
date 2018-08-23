package LeetCode.problems.hard.largest_rectangle_in_histogram;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/23/2018.
 */
public class Tests {

    LargestRectangleInHistogram s = new LargestRectangleInHistogram();


    @Test public void tMax1() { assertThat(s.maxRect(10, 5, 0, 2), is(10)); }
    @Test public void tMax2() { assertThat(s.maxRect(11, 5, 0, 2), is(11)); }
    @Test public void tMax3() { assertThat(s.maxRect( 9, 5, 0, 2), is(10)); }
    @Test public void tMax4() { assertThat(s.maxRect( 1, 7, 3, 4), is(7)); }
    @Test public void tMax5() { assertThat(s.maxRect( 1, 7, 3, 5), is(14)); }

    @Test public void t1(){
        assertThat( s.largestRectangleArea(new int[]{2,1,5,6,2,3}), is(10));
    }

}
