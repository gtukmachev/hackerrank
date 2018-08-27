package kt.LeetCode.problems.hard.largest_rectangle_on_picture

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 8/25/2018.
 */
class Tests {

    val s = LargestRectangleOnPicture()

    @Test fun t1_1__1() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1')
                ))
                , `is`(1)
        )
    }
    @Test fun t1_1__0() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('0')
                ))
                , `is`(0)
        )
    }

    @Test fun t1_2__1 () {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('0','1')
                ))
                , `is`(1)
        )
    }
    @Test fun t1_2__1s() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1','0')
                ))
                , `is`(1)
        )
    }
    @Test fun t1_2__0 () {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('0','0')
                ))
                , `is`(0)
        )
    }

    @Test fun t2_1__0 () {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('0'),
                        charArrayOf('0')
                ))
                , `is`(0)
        )
    }
    @Test fun t2_1__1 () {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1'),
                        charArrayOf('0')
                ))
                , `is`(1)
        )
    }
    @Test fun t2_1__1s() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('0'),
                        charArrayOf('1')
                ))
                , `is`(1)
        )
    }

    @Test fun t4_4__4a() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('0', '0', '0', '0'),
                        charArrayOf('0', '1', '1', '0'),
                        charArrayOf('0', '1', '1', '0'),
                        charArrayOf('0', '0', '0', '0')
                ))
                , `is`(4)
        )
    }
    @Test fun t4_4__4b() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1', '0', '0', '1'),
                        charArrayOf('0', '1', '1', '0'),
                        charArrayOf('0', '1', '1', '0'),
                        charArrayOf('1', '0', '0', '1')
                ))
                , `is`(4)
        )
    }
    @Test fun t4_4__4c() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1', '0', '0', '1'),
                        charArrayOf('1', '1', '1', '0'),
                        charArrayOf('0', '1', '1', '1'),
                        charArrayOf('1', '0', '0', '1')
                ))
                , `is`(4)
        )
    }

    @Test fun t2_6__5a() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1', '1', '0', '0', '0', '0'),
                        charArrayOf('1', '1', '1', '1', '1', '0')
                ))
                , `is`(5)
        )
    }
    @Test fun t4_4__6a() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1', '0', '0', '1'),
                        charArrayOf('1', '1', '1', '1'),
                        charArrayOf('0', '1', '1', '1'),
                        charArrayOf('1', '0', '0', '1')
                ))
                , `is`(6)
        )
    }

    @Test fun t5_5__10a() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1','1','1','1','0'),
                        charArrayOf('1','1','1','1','1'),
                        charArrayOf('1','0','0','0','0'),
                        charArrayOf('1','1','1','1','1'),
                        charArrayOf('1','1','1','1','1')
                ))
                , `is`(10)
        )
    }

    @Test fun t6_14__14a() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1','1','0','0','0','0','0','0','0','0','0','0','0','0'),
                        charArrayOf('1','1','1','1','0','0','0','0','0','0','0','0','0','0'),
                        charArrayOf('1','1','1','1','1','1','1','0','0','0','0','0','0','0'),
                        charArrayOf('1','1','1','1','1','1','1','0','0','0','0','0','0','0'),
                        charArrayOf('0','0','0','0','0','0','0','0','0','0','0','0','0','0'),
                        charArrayOf('0','0','0','0','0','0','0','0','0','0','0','0','0','0')
                ))
                , `is`(14)
        )
    }
    @Test fun t4_7__14a() {
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('1','1','0','0','0','0','0'),
                        charArrayOf('1','1','1','1','0','0','0'),
                        charArrayOf('1','1','1','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1')
                ))
                , `is`(14)
        )
    }
    
    
    @Test fun t_leet(){
        assertThat(
                s.maximalRectangle( arrayOf(
                        charArrayOf('0','1','1','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','0'),
                        charArrayOf('1','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
                        charArrayOf('1','1','0','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1'),
                        charArrayOf('1','1','1','0','1','1','1','0','1','1','1','1','1','1','1','1','1','0','1'),
                        charArrayOf('1','0','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','0'),
                        charArrayOf('0','0','1','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1'),
                        charArrayOf('1','1','0','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','1'),
                        charArrayOf('0','1','1','0','1','1','1','0','1','1','1','1','1','1','1','1','1','1','1'),
                        charArrayOf('1','1','1','1','0','1','1','1','1','1','1','1','1','1','0','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','0','1'),
                        charArrayOf('1','1','1','1','1','1','1','1','0','1','1','0','1','1','0','1','1','1','1'),
                        charArrayOf('1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','0','1','1','1')
                ))
                , `is`(51)
        )

    }
    
}