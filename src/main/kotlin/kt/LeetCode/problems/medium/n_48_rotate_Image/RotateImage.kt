package kt.LeetCode.problems.medium.n_48_rotate_Image

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class RotateImage {

    fun rotate(m: Array<IntArray>) {
        val size = m.size // assume that  lines and rows number are the same (the matrix is square)

        var p1 = 0
        var p2 = size-1
        var buffer: Int
        var iterations = size - 2

        while (iterations >= 0) {
            for (i in 0..iterations){
                buffer = m[p1][p1+i]
                m[p1  ][p1+i] = m[p2-i][p1  ]
                m[p2-i][p1  ] = m[p2  ][p2-i]
                m[p2  ][p2-i] = m[p1+i][p2  ]
                m[p1+i][p2  ] = buffer
            }
            iterations -= 2
            p1++
            p2--
        }

    }



    @Test fun t1(){ check( 3,
                1,2,3,   7,4,1,
                4,5,6,   8,5,2,
                7,8,9,   9,6,3
            )
    }

    @Test fun t2(){ check( 6,
            2,  29, 20, 26, 16, 28,     4,  33, 13, 32, 12,  2,
            12, 27, 9,  25, 13, 21,     24,  1, 14, 33, 27, 29,
            32, 33, 32, 2,  28, 14,     1,  20, 32, 32, 9,  20,
            13, 14, 32, 27, 22, 26,     6,   7, 27,  2, 25, 26,
            33, 1,  20, 7,  21, 7,      32, 21, 22, 28, 13, 16,
            4,  24, 1,  6,  32, 34,     34,  7, 26, 14, 21, 28
            )
    }

    private fun check(size: Int, vararg m: Int) {
        val firstMatrix  = Array(size){ l -> IntArray(size){ c -> m[       l*size*2 + c]} }
        val secondMatrix = Array(size){ l -> IntArray(size){ c -> m[size + l*size*2 + c]} }

        rotate(firstMatrix)

        assertThat(firstMatrix, `is`(secondMatrix))
    }

    private fun matrixToString(m: Array<IntArray>)= m.joinToString(prefix = "\n", separator = "\n"){  row -> row.joinToString() }

}