package kt.LeetCode.problems.hard.sudoku_solver

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test

typealias SudokuTask = Array<IntArray>

object SudokuSolver {

    fun resolve(task: SudokuTask): Array<IntArray> {
        val board = Board(9)

        for (l in 0..8)
            for (c in 0..8)
                if (task[l][c] != 0) board.put(l, c, task[l][c])

        while (board.rest > 0) {
            val wasChanges = board.turn()

            if (wasChanges == false && board.rest > 0) {
                throw UnresolvedGame(board)
                //todo: implement recursive "suggestion" algorithm
            }

        }

        return board.board
    }


}

class UnresolvedGame(board_: Board) : RuntimeException("The game cannot be resolved!") {
    val board = board_.copy()
}


data class IntSet(private val internalSet: BooleanArray) {
    companion object {
        fun full() = IntSet(BooleanArray(10) { true })
        fun empty() = IntSet(BooleanArray(10) { false })
    }

    var size = internalSet.count { it } - 1
        private set

    fun remove(element: Int) {
        if (internalSet[element]) {
            size--
            internalSet[element] = false
        }
    }

    fun erase() {
        for (i in 1 until internalSet.size) internalSet[i] = false
        size = 0
    }

    fun add(element: Int) {
        if (!internalSet[element]) {
            size++
            internalSet[element] = true
        }
    }

    fun has(element: Int) = internalSet[element]

    fun getFirst(): Int {
        for (i in 1 until internalSet.size) if (internalSet[i]) return i
        return 0
    }


    override fun toString(): String {
        val sb = StringBuilder("IntSet{size=")
        sb.append(this.size)
        sb.append(", [")

        for (i in 1 until (internalSet.size - 1)){
            sb.append( when(internalSet[i]){
                true -> i
                else -> '.'
            } )
        }

        sb.append("]}")

        return sb.toString()
    }
}

// Copy of the original board (array and sub-arrays)
data class Board(val SIZE: Int) {
    val board = Array(SIZE) { IntArray(SIZE) }
    var rest = SIZE * SIZE

    val drafts = Array(SIZE) { Array(SIZE) { IntSet.full() } }

    val lineIndexs = Array(SIZE) { IntSet.full() }
    val columnIndexs = Array(SIZE) { IntSet.full() }
    val sectionIndexes = Array(SIZE / 3) { Array(SIZE / 3) { IntSet.full() } }

    fun put(l: Int, c: Int, value: Int) {
        if (board[l][c] != 0) {
            throw ElementAlreadySetPresent(l, c, value, board[l][c])
        }

        board[l][c] = value
        rest--
        val sL = l / 3
        val sC = c / 3

        lineIndexs[l].remove(value)
        columnIndexs[c].remove(value)
        sectionIndexes[sL][sC].remove(value)

        drafts[l][c].erase()

        for (col in 0 until SIZE) if (board[l][col] == 0) drafts[l][col].remove(value)
        for (lin in 0 until SIZE) if (board[lin][c] == 0) drafts[lin][c].remove(value)

        for (lin in (sL * 3) until ((sL + 1) * 3))
            for (col in (sC * 3) until ((sC + 1) * 3))
                if (board[lin][col] == 0) drafts[lin][col].remove(value)

    }

    fun turn(): Boolean {
        var success = checkIndexes()

/*
        if (!success && rest > 0) {
            success = success || reduceByLines()
            success = success || reduceByColumns()
        }
*/


        return success;
    }

    fun checkIndexes(): Boolean {
        var success = false

        success = success || checkLineIndexes(); if (rest == 0) return success
        success = success || checkColumnIndexes(); if (rest == 0) return success
        success = success || checkSectionIndexes(); if (rest == 0) return success
        success = success || checkDrafts(); if (rest == 0) return success

        return success
    }

    fun checkColumnIndexes(): Boolean {
        var success = false
        for (col in 0 until SIZE) {
            if (lineIndexs[col].size == 1) {
                val l = findEmpty(0, col, +1, +0)
                this.put(l, col, lineIndexs[col].getFirst())
                success = true
            }
        }
        return success
    }

    fun checkLineIndexes(): Boolean {
        var success = false
        for (lin in 0 until SIZE) {
            if (lineIndexs[lin].size == 1) {
                val c = findEmpty(lin, 0, +0, +1)
                this.put(lin, c, lineIndexs[lin].getFirst())
                success = true
            }
        }
        return success
    }

    fun checkSectionIndexes(): Boolean {
        var success = false
        for (ls in 0..2) {
            for (cs in 0..2) {
                if (sectionIndexes[ls][cs].size == 1) {
                    val (l, c) = findEmptyInSection(ls, cs)
                    this.put(l,c, sectionIndexes[ls][cs].getFirst() )
                    success = true
                }
            }
        }
        return success
    }

    private fun checkDrafts(): Boolean {
        var success = false
        for (l in 0..8) {
            for (c in 0..8) {
                if (drafts[l][c].size == 1) {
                    this.put(l,c, drafts[l][c].getFirst())
                    success = true
                }
            }
        }
        return success
    }


    fun findEmptyInSection(ls: Int, cs: Int): Pair<Int, Int> {
        for (l in (ls * 3) until ((ls + 1) * 3))
            for (c in (cs * 3) until ((cs + 1) * 3))
                if (board[l][c] != 0)
                    return l to c
        return -1 to -1
    }

    fun findEmpty(l0: Int, c0: Int, dl: Int, dc: Int): Int {
        var l = l0
        var c = c0
        while (l < SIZE && c < SIZE && board[l][c] != 0) {
            l += dl; c += dc;
        }
        if (l < SIZE && c < SIZE) return if (dl == 0) c else l
        return -1
    }

}

class ElementAlreadySetPresent(l: Int, c: Int, newValue: Int, currentValue: Int) : RuntimeException(
        "Can't add the '$newValue' to [${l+1},${c+1}] - there is the another value already present: '$currentValue'"
)


class Tests {

    @Test
    fun test0() {
        test("""
                3 6 2   5 8 4   9 1 7   |   3 6 2   5 8 4   9 1 7
                5 4 7   2 1 9   3 6 8   |   5 4 7   2 1 9   3 6 8
                8 9 1   7 6 3   2 4 5   |   8 9 1   7 6 3   2 4 5
                                        |                          
                2 7 8   6 4 5   1 3 9   |   2 7 8   6 4 5   1 3 9
                1 5 9   3 2 7   4 8 6   |   1 5 9   3 2 7   4 8 6
                6 3 4   8 9 1   5 7 2   |   6 3 4   8 9 1   5 7 2
                                        |                         
                7 8 5   1 3 2   6 9 4   |   7 8 5   1 3 2   6 9 4
                4 1 6   9 5 8   7 2 3   |   4 1 6   9 5 8   7 2 3
                9 2 3   4 7 6   8 5 1   |   9 2 3   4 7 6   8 5 1
            """.trimIndent()
        )
    }

    @Test
    fun test1() {
        test("""
                3 6 2   5 8 4   9 1 7   |   3 6 2   5 8 4   9 1 7
                5 4 7   2 1 9   3 6 8   |   5 4 7   2 1 9   3 6 8
                8 9 1   7 6 3   2 4 5   |   8 9 1   7 6 3   2 4 5
                                        |                          
                2 7 8   6 4 5   1 3 9   |   2 7 8   6 4 5   1 3 9
                1 5 9   3 . 7   4 8 6   |   1 5 9   3 2 7   4 8 6
                6 3 4   8 9 1   5 7 2   |   6 3 4   8 9 1   5 7 2
                                        |                         
                7 8 5   1 3 2   6 9 4   |   7 8 5   1 3 2   6 9 4
                4 1 6   9 5 8   7 2 3   |   4 1 6   9 5 8   7 2 3
                9 2 3   4 7 6   8 5 1   |   9 2 3   4 7 6   8 5 1
            """.trimIndent()
        )
    }

    @Test
    fun test2() {
        test("""
                3 6 2   5 8 4   9 . 7   |   3 6 2   5 8 4   9 1 7
                5 4 7   . 1 9   3 6 8   |   5 4 7   2 1 9   3 6 8
                8 9 1   7 6 .   2 4 5   |   8 9 1   7 6 3   2 4 5
                                        |                          
                2 7 8   6 . 5   1 3 9   |   2 7 8   6 4 5   1 3 9
                1 . 9   3 2 7   4 8 6   |   1 5 9   3 2 7   4 8 6
                . 3 4   8 9 1   5 7 2   |   6 3 4   8 9 1   5 7 2
                                        |                         
                . 8 5   1 3 2   6 9 4   |   7 8 5   1 3 2   6 9 4
                4 1 6   9 5 .   7 2 3   |   4 1 6   9 5 8   7 2 3
                . 2 3   4 7 6   8 5 1   |   9 2 3   4 7 6   8 5 1
            """.trimIndent()
        )
    }

    @Test
    fun test3() {
        test("""
                . . .   5 8 4   9 1 7   |   3 6 2   5 8 4   9 1 7
                . . .   2 1 9   3 6 8   |   5 4 7   2 1 9   3 6 8
                . . .   7 6 3   2 4 5   |   8 9 1   7 6 3   2 4 5
                                        |                          
                2 7 8   . . .   1 3 9   |   2 7 8   6 4 5   1 3 9
                1 5 9   . . .   4 8 6   |   1 5 9   3 2 7   4 8 6
                6 3 4   . . .   5 7 2   |   6 3 4   8 9 1   5 7 2
                                        |                         
                7 8 5   1 3 2   . . .   |   7 8 5   1 3 2   6 9 4
                4 1 6   9 5 8   . . .   |   4 1 6   9 5 8   7 2 3
                9 2 3   4 7 6   . . .   |   9 2 3   4 7 6   8 5 1
            """.trimIndent()
        )
    }


    fun test(statement: String) {

        val task = Array(9) { IntArray(9) }
        val solution = Array(9) { IntArray(9) }

        var lin = -1
        statement
                .lines()
                .forEach { st ->
                    val lr = st.split("|")
                    val taskStr = lr[0].trim().split(" ").filter { it.isNotBlank() }
                    val solutionStr = lr[1].trim().split(" ").filter { it.isNotBlank() }
                    if (taskStr.isNotEmpty() && solutionStr.isNotEmpty()) {
                        lin++

                        fun toNum(ch: Char) = if (ch.isDigit()) (ch - '0') else 0

                        for (c in 0..8) task[lin][c] = toNum(taskStr[c][0])
                        for (c in 0..8) solution[lin][c] = toNum(solutionStr[c][0])

                    }

                }

        val resolved = SudokuSolver.resolve(task)

        assertThat(resolved, `is`(solution))

    }
}