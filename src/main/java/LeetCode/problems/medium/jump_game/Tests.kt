package LeetCode.problems.medium.jump_game

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 8/29/2018.
 */

class Tests {

    val s = JumpGame.Solution()

    @Test fun t0() { assertThat(s.canJump(intArrayOf(1)), `is`(true)); }
    @Test fun t1() { assertThat(s.canJump(intArrayOf(1,1)), `is`(true)); }
    @Test fun t2() { assertThat(s.canJump(intArrayOf(1,1,0,1)), `is`(false)); }
    @Test fun t3() { assertThat(s.canJump(intArrayOf(0,1,1,1)), `is`(false)); }
    @Test fun t4() { assertThat(s.canJump(intArrayOf(0,1)), `is`(false)); }
    @Test fun t5() { assertThat(s.canJump(intArrayOf(0,0)), `is`(false)); }
    @Test fun t6() { assertThat(s.canJump(intArrayOf(0)), `is`(true)); }

    @Test fun tr1() { assertThat(s.canJump(intArrayOf(1,1,1,4,1,0,2,0,1)), `is`(true)); }
    @Test fun tr2() { assertThat(s.canJump(intArrayOf(1,1,1,2,1,0,2,0,1)), `is`(false)); }
    @Test fun tr3() { assertThat(s.canJump(intArrayOf(1,1,6,4,2,1,1,0,2,2,0,1)), `is`(true)); }

    @Test fun tlz1() { assertThat(s.canJump(intArrayOf(1,1,6,4,2,1,1,0,2,2,0,0)), `is`(true)); }


    @Test fun t_leet_1() { assertThat(s.canJump(intArrayOf(2,3,1,1,4)), `is`(true)); }
    @Test fun t_leet_2() { assertThat(s.canJump(intArrayOf(3,2,1,0,4)), `is`(false)); }

}