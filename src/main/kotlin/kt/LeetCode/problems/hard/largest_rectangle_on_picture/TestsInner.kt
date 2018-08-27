package kt.LeetCode.problems.hard.largest_rectangle_on_picture

import kt.LeetCode.problems.hard.largest_rectangle_on_picture.LargestRectangleOnPicture.Rect
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 8/25/2018.
 */
class TestsInner {

    val s = LargestRectangleOnPicture()

    @Test fun t_sqr_push1() {
        val sqr = Rect(1,1, 4,4)
        assertThat(sqr.lines.size, `is`(0))
        sqr.pushPoint(0,5, '1')
        sqr.pushPoint(1,5, '1')
        sqr.pushPoint(2,5, '1')
        sqr.pushPoint(3,5, '0')
        sqr.pushPoint(4,5, '1')
        sqr.pushPoint(5,5, '1')

        assertThat(sqr.lines, `is`(listOf(
                Rect(1,1, 2,5), Rect(4,1, 4,5)
        )))
   }
    @Test fun t_sqr_evaluate1() {
        val sqr = Rect(1,1, 4,4)
        assertThat(sqr.lines.size, `is`(0))
        sqr.pushPoint(0,5, '1')
        sqr.pushPoint(1,5, '1')
        sqr.pushPoint(2,5, '1')
        sqr.pushPoint(3,5, '0')
        sqr.pushPoint(4,5, '1')
        sqr.pushPoint(5,5, '1')

        val sqrs = sqr.evaluate()

        assertThat(sqrs, `is`(listOf(
                Rect(1,1, 2,5), Rect(4,1, 4,5)
        )))
    }
    @Test fun t_sqr_evaluate2() {
        val sqr = Rect(1,1, 4,4)
        assertThat(sqr.lines.size, `is`(0))
        sqr.pushPoint(0,5, '1')
        sqr.pushPoint(1,5, '1')
        sqr.pushPoint(2,5, '1')
        sqr.pushPoint(3,5, '1')
        sqr.pushPoint(4,5, '1')
        sqr.pushPoint(5,5, '1')

        val sqrs = sqr.evaluate()

        assertThat(sqrs, `is`( listOf(
                Rect(1,1, 4,5)
        ) ));
    }

    @Test fun t_sqr_s() {
        val sqr = Rect(1,1, 3,3)
        assertThat(sqr.S(), `is`(9))
    }

}