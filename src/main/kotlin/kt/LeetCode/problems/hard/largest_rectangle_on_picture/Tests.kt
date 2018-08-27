package kt.LeetCode.problems.hard.largest_rectangle_on_picture

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 8/25/2018.
 */
class Tests {

    val s = LargestRectangleOnPicture()
    fun rectOf(s: String): Array<CharArray> =
            s.split("\n").map { line ->
                line.split(" ").map { col ->
                    col[0]
                }.toCharArray()
            }.toTypedArray()


    @Test fun t1_1__1() { assertThat( s.maximalRectangle(rectOf("1")), `is`(1)) }
    @Test fun t1_1__0() { assertThat( s.maximalRectangle(rectOf("0")), `is`(0)) }
    @Test fun t1_2__1() { assertThat( s.maximalRectangle(rectOf("0 1")), `is`(1)) }
    @Test fun t1_2__0() { assertThat( s.maximalRectangle(rectOf("0 0")), `is`(0)) }

    @Test fun t2_1__0 () {
        assertThat( s.maximalRectangle( rectOf(
                """0
                  |0""".trimMargin()
                ))
                , `is`(0)
        )
    }
    @Test fun t2_1__1a() {
        assertThat( s.maximalRectangle( rectOf(
                """1
                  |0""".trimMargin()
                ))
                , `is`(1)
        )
    }
    @Test fun t2_1__1b() {
        assertThat( s.maximalRectangle( rectOf(
                """0
                  |1""".trimMargin()
                ))
                , `is`(1)
        )
    }

    @Test fun t4_4__4a() {
        assertThat( s.maximalRectangle( rectOf(
                       """0 0 0 0
                         |0 1 1 0
                         |0 1 1 0
                         |0 0 0 0""".trimMargin() ))
                , `is`(4)
        )
    }
    @Test fun t4_4__4b() {
        assertThat( s.maximalRectangle( rectOf(
                       """1 0 0 1
                         |0 1 1 0
                         |0 1 1 0
                         |1 0 0 1""".trimMargin() ))
                , `is`(4)
        )
    }
    @Test fun t4_4__4c() {
        assertThat( s.maximalRectangle( rectOf(
                       """1 0 0 1
                         |1 1 1 0
                         |0 1 1 1
                         |1 0 0 1""".trimMargin() ))
                , `is`(4)
        )
    }

    @Test fun t2_6__5() {
        assertThat(
                s.maximalRectangle( rectOf(
                       """1 1 0 0 0 0
                         |1 1 1 1 1 0""".trimMargin() ))
                , `is`(5)
        )
    }

    @Test fun t4_4__6() {
        assertThat(
                s.maximalRectangle( rectOf(
                       """1 0 0 1
                         |1 1 1 1
                         |0 1 1 1
                         |1 0 0 1""".trimMargin() ))
                , `is`(6)
        )
    }

    @Test fun t5_5__10a() {
        assertThat(
                s.maximalRectangle( rectOf(
                       """1 1 1 1 0
                         |1 1 1 1 1
                         |1 0 0 0 0
                         |1 1 1 1 1
                         |1 1 1 1 1""".trimMargin() ))
                , `is`(10)
        )
    }

    @Test fun t6_14__14a() {
        assertThat(
                s.maximalRectangle(
                   rectOf("""1 1 0 0 0 0 0 0 0 0 0 0 0 0
                            |1 1 1 1 0 0 0 0 0 0 0 0 0 0
                            |1 1 1 1 1 1 1 0 0 0 0 0 0 0
                            |1 1 1 1 1 1 1 0 0 0 0 0 0 0
                            |0 0 0 0 0 0 0 0 0 0 0 0 0 0
                            |0 0 0 0 0 0 0 0 0 0 0 0 0 0""".trimMargin() ))
                , `is`(14)
        )
    }
    @Test fun t4_7__14a() {
        assertThat(
                s.maximalRectangle(
                    rectOf("""1 1 0 0 0 0 0
                             |1 1 1 1 0 0 0
                             |1 1 1 1 1 1 1
                             |1 1 1 1 1 1 1""".trimMargin() ))
                , `is`(14)
        )
    }
    
    
    @Test fun t_leet() {
        assertThat(
                s.maximalRectangle(
                        rectOf("""0 1 1 0 2 2 2 2 2 2 2 2 1 1 1 1 1 1 0
                                 |1 0 1 1 2 2 2 2 2 2 2 2 1 1 1 1 1 1 1
                                 |1 1 0 1 2 2 2 2 2 2 2 2 0 1 1 1 1 1 1
                                 |1 1 1 1 2 2 2 2 2 2 2 2 1 0 1 1 1 1 1
                                 |1 1 1 1 2 2 2 2 2 2 2 2 1 1 0 1 1 1 1
                                 |1 1 1 0 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1
                                 |1 0 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1
                                 |1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 0
                                 |0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1
                                 |1 1 0 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1
                                 |1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1
                                 |0 1 1 0 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1
                                 |1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1
                                 |3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 1 1
                                 |3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 1 1
                                 |3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 0 1
                                 |1 1 1 1 1 1 1 1 0 1 1 0 1 1 0 1 1 1 1
                                 |1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1""".trimMargin())

                //                0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8
                )
                , `is`(51)
        )
    }


}