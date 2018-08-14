package kt.LeetCode.problems.hard.sudoku_solver

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class Solution {
    companion object {
        val NO_INDEX = 0
        val NO_VALUE = 100
    }

    inner class NoSolutionFound : java.lang.RuntimeException("Not enough information to resolve the sudoku")
    inner class WrongSudokuStatement(d: Int) : java.lang.RuntimeException("The duplicate value $d detected!")

    val index = Array(9){ IntArray(9) }

    fun solveSudoku(board: Array<CharArray>) {

        val lin = buildSetsByLines(board)
        val col = buildSetsByColumns(board)
        val sq = buildSetsBySquares(board)
        var unresolved = getUnresolved(board)


        while (unresolved > 0) {
            val (l, c, v) = findMin(lin, col, sq, board)

            val cross = lin[l] and col[c] and sq[l/3][c/3]

            when(v) {
                1 -> { setDigit(lin, col, sq, board, l, c, positionOf1(cross)  ); unresolved--; }
                in 2..9 -> unresolved = makeHypotesis(lin, col, sq, board, l, c, unresolved, cross)
                0 -> unresolved = switchHypotesis(lin, col, sq, board)
            }

        }
    }

    val hyotesisStack = java.util.LinkedList<State>()
    data class State(val lin: IntArray, val col: IntArray, val sq: Array<IntArray>, val board: Array<CharArray>, val l: Int, val c: Int, val unresolved: Int, val nextMask: Int)

    private fun makeHypotesis(lin: IntArray, col: IntArray, sq: Array<IntArray>, board: Array<CharArray>, l: Int, c: Int, unresolved: Int, mask: Int): Int {
        val d = firstOf1(mask)
        val nextMask = mask shr d

        if (nextMask > 0) { // if the set of available solutions > 1
            // save the hypotesis to stack in order to restore it and switch ti the next one in case if this hypotesis will be wrong
            val st = State(lin.copyOf(), col.copyOf(),
                    Array(3) { sq[it].copyOf() },
                    Array(9) { board[it].copyOf() },
                    l, c, unresolved, nextMask)

            hyotesisStack.push(st)
        }

        setDigit(lin, col, sq, board, l, c, d)

        return unresolved - 1
    }

    private fun switchHypotesis(lin: IntArray, col: IntArray, sq: Array<IntArray>, board: Array<CharArray>): Int {
        if (hyotesisStack.size == 0) throw NoSolutionFound()

        val st = hyotesisStack.pop()

        // restore the state
        st.lin.forEachIndexed{idx, i -> lin[idx] = i}
        st.col.forEachIndexed{idx, i -> col[idx] = i}
        st.sq.forEachIndexed{idxL, arr -> arr.forEachIndexed{idxC, v -> sq[idxL][idxC] = v }}
        st.board.forEachIndexed{idxL, arr -> arr.forEachIndexed{idxC, v -> board[idxL][idxC] = v }}

        return makeHypotesis(lin, col, sq, board, st.l, st.c, st.unresolved, st.nextMask)
    }


    private fun setDigit(lin: IntArray, col: IntArray, sq: Array<IntArray>, board: Array<CharArray>, l: Int, c: Int, d: Int) {
        if (d < 1 || d > 9) throw RuntimeException("Index broken: the value in ($l, $c) calculated as $d by the index!")

        if (board[l][c] != '.') throw RuntimeException("The position ($l, $c) to set up a digit defined wrong (the board already contains a digit at the place)!")
        board[l][c] = '0' + d

        val lsq = l/3
        val csq = c/3
        val mask = 1 shl (d-1)
        lin[l]       = lin[l]       xor mask
        col[c]       = col[c]       xor mask
        sq[lsq][csq] = sq[lsq][csq] xor mask
    }

    fun findMin(lin: IntArray, col: IntArray, sq: Array<IntArray>, board: Array<CharArray>): Triple<Int, Int, Int> {
        var min = NO_VALUE
        var minL = -1
        var minC = -1

        // todo: comment
        for (l in 0..8)
            for (c in 0..8)
                when (board[l][c]) {
                     '.' -> countOf1(lin[l] and col[c] and sq[l/3][c/3]).apply {
                         if (this < min) { min = this; minL = l; minC = c }
                     }
                    else -> -1
                }

/*
        for (l in 0..8)
            for (c in 0..8)
                index[l][c] = when (board[l][c]) {
                     '.' -> countOf1(lin[l] and col[c] and sq[l/3][c/3]).apply {
                         if (this < min) { min = this; minL = l; minC = c }
                     }
                    else -> -1
                }

*/

        return Triple(minL, minC, min)
    }

    fun buildSetsByLines(board: Array<CharArray>): IntArray {
        val lin = IntArray(9)
        for( l in 0..8 )
            lin[l] = toBSet( board[l][0], board[l][1], board[l][2], board[l][3], board[l][4], board[l][5], board[l][6], board[l][7], board[l][8] )
        return lin
    }

    private fun buildSetsByColumns(board: Array<CharArray>): IntArray {
        val col = IntArray(9)
        for( c in 0..8 )
            col[c] = toBSet( board[0][c], board[1][c], board[2][c], board[3][c], board[4][c], board[5][c], board[6][c], board[7][c], board[8][c] )
        return col
    }

    private fun buildSetsBySquares(board: Array<CharArray>): Array<IntArray> {
        val sq = Array<IntArray>(3){ IntArray(3) }
        for( ls in 0..2 )
            for( cs in 0..2 ){
                val l = ls * 3
                val c = cs * 3
                sq[ls][cs] = toBSet( board[l][c],   board[l+1][c],   board[l+2][c],
                        board[l][c+1], board[l+1][c+1], board[l+2][c+1],
                        board[l][c+2], board[l+1][c+2], board[l+2][c+2])
            }
        return sq
    }

    private fun getUnresolved(board: Array<CharArray>): Int {
        var unresolved = 0
        for (l in 0..8) {
            for (c in 0..8) {
                if (board[l][c] == '.') unresolved++
            }
        }
        return unresolved
    }

    fun toBSet(vararg ch: Char): Int {
        var b = 0
        for(i in 0..8) {
            val d = ch[i].toDigit()
            val bit = when(d) {
                -1 -> 0
                else -> 1 shl d
            }
            if( (b and bit) > 0 ) throw WrongSudokuStatement( positionOf1(bit) )
            b = b or bit
        }
        val r = b.inv() and 0b111111111
        return r
    }

    fun countOf1(i: Int): Int {
        return   (i and 0b000000001) +
                ((i and 0b000000010) shr 1) +
                ((i and 0b000000100) shr 2) +
                ((i and 0b000001000) shr 3) +
                ((i and 0b000010000) shr 4) +
                ((i and 0b000100000) shr 5) +
                ((i and 0b001000000) shr 6) +
                ((i and 0b010000000) shr 7) +
                ((i and 0b100000000) shr 8)
    }

    fun firstOf1(i: Int): Int {
        return when {
                   (i and 0b000000001) > 0 -> 1
                   (i and 0b000000010) > 0 -> 2
                   (i and 0b000000100) > 0 -> 3
                   (i and 0b000001000) > 0 -> 4
                   (i and 0b000010000) > 0 -> 5
                   (i and 0b000100000) > 0 -> 6
                   (i and 0b001000000) > 0 -> 7
                   (i and 0b010000000) > 0 -> 8
                   (i and 0b100000000) > 0 -> 9
                                 else -> -1
        }
    }

    fun positionOf1(i: Int): Int {
        var p = 0
        var mask = i
        while(mask > 0) {
            p += 1
            mask = mask shr 1
        }
        return p
    }

    fun Char.toDigit(): Int = when(this) {
        '.' -> -1
        else -> this - '1'
    }

}

class Tests {

    val s = Solution()

    @Test fun positionOf1_test1(){
        0b00100.let{ assertThat( it , `is`( 1 shl (s.positionOf1(it)-1)) ) }
    }
    @Test fun positionOf1_test2(){
        0b100000000.let{ assertThat( it , `is`( 1 shl (s.positionOf1(it)-1)) ) }
    }
    @Test fun positionOf1_test3(){
        0b000100000.let{ assertThat( it , `is`( 1 shl (s.positionOf1(it)-1)) ) }
    }
    @Test fun positionOf1_test4(){
        0b000000001.let{ assertThat( it , `is`( 1 shl (s.positionOf1(it)-1)) ) }
    }
    @Test fun positionOf1_test0(){
        assertThat( s.positionOf1(0), `is`(0) )
    }

    @Test fun buildSetsByLines_test1(){
        val answer: Array<CharArray> = arrayOf(
                charArrayOf('4','3','5', '2','6','9', '7','8','1'),
                charArrayOf('6','8','2', '5','7','1', '4','9','3'),
                charArrayOf('1','9','7', '8','3','4', '5','6','2'),

                charArrayOf('8','2','6', '1','9','5', '3','4','7'),
                charArrayOf('3','7','4', '6','8','2', '9','1','5'),
                charArrayOf('9','5','1', '7','4','3', '6','2','8'),

                charArrayOf('5','1','9', '3','2','6', '8','7','4'),
                charArrayOf('2','4','8', '9','5','7', '1','3','6'),
                charArrayOf('7','6','3', '4','1','8', '2','5','9')
        )

        val task = Array(9){ i -> answer[i].copyOf()}.apply {
            for (l in 0..8)
                for (c in 0..8)
                    if (this[l][c] == '7') this[l][c] = '.'
        }

        val lin = s.buildSetsByLines(task)

        assertThat(lin[0], `is`(0b001000000))
        assertThat(lin[1], `is`(0b001000000))
        assertThat(lin[2], `is`(0b001000000))
        assertThat(lin[3], `is`(0b001000000))
        assertThat(lin[4], `is`(0b001000000))
        assertThat(lin[5], `is`(0b001000000))
        assertThat(lin[6], `is`(0b001000000))
        assertThat(lin[7], `is`(0b001000000))
        assertThat(lin[8], `is`(0b001000000))

    }

    @Test(expected = Solution.WrongSudokuStatement::class)
    fun buildSetsByLines_test2(){
        val answer: Array<CharArray> = arrayOf(
                charArrayOf('4','3','5', '2','6','9', '7','8','1'),
                charArrayOf('6','8','2', '5','7','1', '4','9','3'),
                charArrayOf('1','9','7', '8','3','4', '5','6','2'),

                charArrayOf('8','2','6', '1','9','5', '3','4','7'),
                charArrayOf('3','7','4', '6','8','2', '9','1','5'),
                charArrayOf('9','5','1', '7','4','3', '6','2','8'),

                charArrayOf('5','1','9', '3','2','6', '8','7','4'),
                charArrayOf('2','4','8', '9','5','7', '1','3','6'),
                charArrayOf('7','6','3', '4','1','8', '2','9','9')
        )

        val task = Array(9){ i -> answer[i].copyOf()}.apply {
            for (l in 0..8)
                for (c in 0..8)
                    if (this[l][c] == '7') this[l][c] = '.'
        }

        s.buildSetsByLines(task)
    }

    @Test fun countOf1_test1(){ assertThat( s.countOf1(0b0001), `is`(1) ) }
    @Test fun countOf1_test2(){ assertThat( s.countOf1(0b1010), `is`(2) ) }
    @Test fun countOf1_test8(){ assertThat( s.countOf1(0b011111111), `is`(8) ) }


    @Test fun toBSet_test0(){ assertThat(  s.toBSet('.','.','.','.','.','.','.','.','.'), `is`(0b111111111)  ) }
    @Test fun toBSet_test1(){ assertThat(  s.toBSet('.','.','.','9','.','.','.','.','.'), `is`(0b011111111)  ) }
    @Test fun toBSet_test2(){ assertThat(  s.toBSet('.','5','.','.','.','.','2','.','.'), `is`(0b111101101)  ) }
    @Test fun toBSet_test3(){ assertThat(  s.toBSet('.','8','.','9','.','.','4','.','.'), `is`(0b001110111)  ) }
    @Test fun toBSet_test4(){ assertThat(  s.toBSet('.','3','5','2','6','9','7','8','1'), `is`(0b000001000)  ) }

    @Test fun t1() {
        val answer: Array<CharArray> = arrayOf(
                charArrayOf('4','3','5','2','6','9','7','8','1'),
                charArrayOf('6','8','2','5','7','1','4','9','3'),
                charArrayOf('1','9','7','8','3','4','5','6','2'),
                charArrayOf('8','2','6','1','9','5','3','4','7'),
                charArrayOf('3','7','4','6','8','2','9','1','5'),
                charArrayOf('9','5','1','7','4','3','6','2','8'),
                charArrayOf('5','1','9','3','2','6','8','7','4'),
                charArrayOf('2','4','8','9','5','7','1','3','6'),
                charArrayOf('7','6','3','4','1','8','2','5','9')
        )

        val task = Array(9){ i -> answer[i].copyOf()}.apply {
            this[0][0] = '.'
        }

        s.solveSudoku(task)

        assertThat( task, `is`(answer) )
    }

    @Test fun t2() {
        val answer: Array<CharArray> = arrayOf(
                charArrayOf('4','3','5','2','6','9','7','8','1'),
                charArrayOf('6','8','2','5','7','1','4','9','3'),
                charArrayOf('1','9','7','8','3','4','5','6','2'),
                charArrayOf('8','2','6','1','9','5','3','4','7'),
                charArrayOf('3','7','4','6','8','2','9','1','5'),
                charArrayOf('9','5','1','7','4','3','6','2','8'),
                charArrayOf('5','1','9','3','2','6','8','7','4'),
                charArrayOf('2','4','8','9','5','7','1','3','6'),
                charArrayOf('7','6','3','4','1','8','2','5','9')
        )

        val task = Array(9){ i -> answer[i].copyOf()}.apply {
            this[0][0] = '.'
            this[0][3] = '.'
            this[0][6] = '.'
        }

        s.solveSudoku(task)

        assertThat( task, `is`(answer) )
    }

/*
    todo: refactor
    @Test(expected = Solution.NoSolutionFound::class)
    fun t3() {
        val answer: Array<CharArray> = arrayOf(
                charArrayOf('4','3','5', '2','6','9', '7','8','1'),
                charArrayOf('6','8','2', '5','7','1', '4','9','3'),
                charArrayOf('1','9','7', '8','3','4', '5','6','2'),

                charArrayOf('8','2','6', '1','9','5', '3','4','7'),
                charArrayOf('3','7','4', '6','8','2', '9','1','5'),
                charArrayOf('9','5','1', '7','4','3', '6','2','8'),

                charArrayOf('5','1','9', '3','2','6', '8','7','4'),
                charArrayOf('2','4','8', '9','5','7', '1','3','6'),
                charArrayOf('7','6','3', '4','1','8', '2','9','9')
        )

        val task = Array(9){ i -> answer[i].copyOf()}.apply {
            for (l in 0..8)
                for (c in 0..8)
                    if (this[l][c] in '3'..'5') this[l][c] = '.'
        }

        s.solveSudoku(task)

        assertThat( task, `is`(answer) )
    }
*/

    @Test fun t4() {
        val answer: Array<CharArray> = arrayOf(
                charArrayOf('4','3','5', '2','6','9', '7','8','1'),
                charArrayOf('6','8','2', '5','7','1', '4','9','3'),
                charArrayOf('1','9','7', '8','3','4', '5','6','2'),

                charArrayOf('8','2','6', '1','9','5', '3','4','7'),
                charArrayOf('3','7','4', '6','8','2', '9','1','5'),
                charArrayOf('9','5','1', '7','4','3', '6','2','8'),

                charArrayOf('5','1','9', '3','2','6', '8','7','4'),
                charArrayOf('2','4','8', '9','5','7', '1','3','6'),
                charArrayOf('7','6','3', '4','1','8', '2','5','9')
        )

        val task = Array(9){ i -> answer[i].copyOf()}.apply {
            for (l in 0..8)
                for (c in 0..8)
                    if (this[l][c] == '7') this[l][c] = '.'
        }

        s.solveSudoku(task)

        assertThat( task, `is`(answer) )
    }

    @Test fun tLeetCode1() {

        val task: Array<CharArray> = arrayOf(
                charArrayOf('5','3','.', '.','7','.', '.','.','.'),
                charArrayOf('6','.','.', '1','9','5', '.','.','.'),
                charArrayOf('.','9','8', '.','.','.', '.','6','.'),

                charArrayOf('8','.','.', '.','6','.', '.','.','3'),
                charArrayOf('4','.','.', '8','.','3', '.','.','1'),
                charArrayOf('7','.','.', '.','2','.', '.','.','6'),

                charArrayOf('.','6','.', '.','.','.', '2','8','.'),
                charArrayOf('.','.','.', '4','1','9', '.','.','5'),
                charArrayOf('.','.','.', '.','8','.', '.','7','9'))

        val answer: Array<CharArray> = arrayOf(
                charArrayOf('5','3','4', '6','7','8', '9','1','2'),
                charArrayOf('6','7','2', '1','9','5', '3','4','8'),
                charArrayOf('1','9','8', '3','4','2', '5','6','7'),

                charArrayOf('8','5','9', '7','6','1', '4','2','3'),
                charArrayOf('4','2','6', '8','5','3', '7','9','1'),
                charArrayOf('7','1','3', '9','2','4', '8','5','6'),

                charArrayOf('9','6','1', '5','3','7', '2','8','4'),
                charArrayOf('2','8','7', '4','1','9', '6','3','5'),
                charArrayOf('3','4','5', '2','8','6', '1','7','9'))

        s.solveSudoku(task)

        assertThat( task, `is`(answer) )
    }

    @Test fun tLeetCode2() {

        val task: Array<CharArray> = arrayOf(
                charArrayOf('.','.','9', '7','4','8', '.','.','.'),
                charArrayOf('7','.','.', '.','.','.', '.','.','.'),
                charArrayOf('.','2','.', '1','.','9', '.','.','.'),

                charArrayOf('.','.','7', '.','.','.', '2','4','.'),
                charArrayOf('.','6','4', '.','1','.', '5','9','.'),
                charArrayOf('.','9','8', '.','.','.', '3','.','.'),

                charArrayOf('.','.','.', '8','.','3', '.','2','.'),
                charArrayOf('.','.','.', '.','.','.', '.','.','6'),
                charArrayOf('.','.','.', '2','7','5', '9','.','.'))


        val answer: Array<CharArray> = arrayOf(
                charArrayOf('5','1','9', '7','4','8', '6','3','2'),
                charArrayOf('7','8','3', '6','5','2', '4','1','9'),
                charArrayOf('4','2','6', '1','3','9', '8','7','5'),

                charArrayOf('3','5','7', '9','8','6', '2','4','1'),
                charArrayOf('2','6','4', '3','1','7', '5','9','8'),
                charArrayOf('1','9','8', '5','2','4', '3','6','7'),

                charArrayOf('9','7','5', '8','6','3', '1','2','4'),
                charArrayOf('8','3','2', '4','9','1', '7','5','6'),
                charArrayOf('6','4','1', '2','7','5', '9','8','3'))
/*

                charArrayOf('1','2','9', '7','4','8', '1','2','2'),
                charArrayOf('7','1','4', '6','3','2', '8','5','9'),
                charArrayOf('8','2','3', '1','5','9', '6','7','4'),

                charArrayOf('3','5','7', '9','8','6', '2','4','1'),
                charArrayOf('2','6','4', '3','1','7', '5','9','8'),
                charArrayOf('1','9','8', '5','2','4', '3','6','7'),

                charArrayOf('9','7','1', '8','6','3', '4','2','5'),
                charArrayOf('5','3','2', '4','9','1', '7','8','6'),
                charArrayOf('4','8','6', '2','7','5', '9','1','3'))
*/

        s.solveSudoku(task)

        assertThat( task, `is`(answer) )
    }

}

