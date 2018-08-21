package LeetCode.problems.hard.trapping_rain_water;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 *
 *
 */
public class Tests {

    TrappingRainWater_faster.Solution s = new TrappingRainWater_faster().new Solution();

    /**
     *  {@see https://leetcode.com/problems/trapping-rain-water/description/}
     */
    @Test public void leet1(){ assertThat( s.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}), is(6) ); }
    @Test public void leet2(){ assertThat( s.trap(new int[]{2,0,2}), is(2) ); }


    @Test public void t0_1(){ assertThat( s.trap(new int[]{0,0,0,  0,100,  0,0,0,0}), is( 0) ); }
    @Test public void t0_2(){ assertThat( s.trap(new int[]{0,0,0,100,100,100,0,0,0}), is( 0) ); }
    @Test public void t0_3(){ assertThat( s.trap(new int[]{0,0,0,100, 50,100,0,0,0}), is(50) ); }

    @Test public void te_0(){ assertThat( s.trap(null), is(0) ); }
    @Test public void te_1(){ assertThat( s.trap(new int[]{}), is(0) ); }
    @Test public void te_2(){ assertThat( s.trap(new int[1_000_000]), is(0) ); }


    private static int[] st1Arr = new int[1_000_000];
    static { for (int i = 0; i < st1Arr.length; i++) { st1Arr[i] = i % 2; } }
    @Test public void st_1(){ assertThat( s.trap(st1Arr), is(1_000_000 / 2 - 1) ); }

    private static int[] st2Arr = new int[1_000_000];
    static { for (int i = 0; i < st2Arr.length; i++) { st2Arr[i] = i % 3; } }
    @Test public void st_2(){ assertThat( s.trap(st2Arr), is((1_000_000 / 3 - 1)*3) ); }

}
