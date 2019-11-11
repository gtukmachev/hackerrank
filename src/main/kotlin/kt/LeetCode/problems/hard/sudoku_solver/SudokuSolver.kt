package kt.LeetCode.problems.hard.sudoku_solver

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

typealias SudokuTask = Array<IntArray>

object SudokuSolver {

    fun resolve(task: SudokuTask): Array<IntArray> {
        val board = Board(9)

        for (l in 0..8) {
            for (c in 0..8) {
                val n = task[l][c]
                if (n != 0) board.put(l, c, n)
            }
        }

        while (board.rest > 0) {
            val wasChanges = board.turn()

            if (!wasChanges && board.rest > 0) {
                throw UnresolvedGame(board)
                //todo: implement recursive "suggestion" algorithm
                //      find a draft with two possible values and try to resolve the game recursively for the first, then for the second one.
            }
        }

        return board.board
    }

}

class UnresolvedGame(board_: Board) : RuntimeException("The Sudoku game cannot be resolved (turn = ${board_.turnsCounter})!") {
    val board = board_.copy()
}


class PossibleForeverLoop(board_: Board) : RuntimeException("The Sudoku game cannot be resolved by this algorithm - too many turns performed already (turn = ${board_.turnsCounter})!") {
    val board = board_.copy()
}


data class IntSet(private val internalSet: BooleanArray) {
    companion object {
        fun full() = IntSet(BooleanArray(10) { true })
        fun empty() = IntSet(BooleanArray(10) { false })
    }

    var size = internalSet.count { it } - 1
        private set

    fun remove(element: Int): Boolean {
        if (internalSet[element]) {
            size--
            internalSet[element] = false
            return true
        }
        return false
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

    val putQueue: Queue<Pair<Int, Int>> = LinkedList()
    var turnsCounter = 0
        private set

    fun put(l: Int, c: Int, value: Int) {
        if (board[l][c] != 0) {
            if (board[l][c] == value) return
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


        fun cleanDraft(ld: Int, cd: Int) {
            if (board[ld][cd] == 0) { // there is no value yet in this cell
                if (drafts[ld][cd].remove(value)) { // remove the value from the draft
                    if (drafts[ld][cd].size == 1) { // if the draft has only a single value now
                        putQueue.add(ld to cd)
                    }
                }
            }
        }

        for (col in 0 until SIZE) cleanDraft(l, col)
        for (lin in 0 until SIZE) cleanDraft(lin, c)

        for (lin in (sL * 3) until ((sL + 1) * 3))
            for (col in (sC * 3) until ((sC + 1) * 3))
                cleanDraft(lin, col)
    }

    fun putByQueue(): Boolean {
        if (putQueue.isEmpty()) return false
        var coordinates = putQueue.poll()
        while (coordinates != null) {
            val (l, c) = coordinates
            if (board[l][c] == 0) {
                put(l, c, drafts[l][c].getFirst())
            }
            coordinates = putQueue.poll()
        }
        return true
    }

    fun turn(): Boolean {
        turnsCounter++; if (turnsCounter >= 1000) throw PossibleForeverLoop(this)

        if (putByQueue()                ) return true // put all value if draft contains a last not excluded value
        if (checkIndexesForUniqueValue()) return true // If a line, column or section contains some number (in drafts) only once => put this one!
//        if (reduceBySectionLines()      ) return true // if a section contains a value (in drafts) in a single line/column => remove this value from this line/column in other sections in this raw
//        if (reduceByLinkedPairs()       ) return true // if 2 sections in the same line/column contains a value in the same two lines/column (and do not contain this value into the 3d line/column) => remove this value in the third section in the row from these 2 lines/columns
        return false
    }

    /**
     *
     */
    private fun checkIndexesForUniqueValue(): Boolean {
        var success = false

/* TODO:

        success = success ||    checkLineIndexesForUniqueValue(); if (rest == 0) return success
        success = success ||  checkColumnIndexesForUniqueValue(); if (rest == 0) return success
        success = success || checkSectionIndexesForUniqueValue(); if (rest == 0) return success
*/

        return success
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


    @Test
    fun test4() {
        test("""
                . . .   5 8 4   . . .   |   3 6 2   5 8 4   9 1 7
                . . .   2 1 9   . . .   |   5 4 7   2 1 9   3 6 8
                . . .   7 6 3   . . .   |   8 9 1   7 6 3   2 4 5
                                        |                          
                2 7 8   6 4 5   1 3 9   |   2 7 8   6 4 5   1 3 9
                1 5 9   3 2 7   4 8 6   |   1 5 9   3 2 7   4 8 6
                6 3 4   8 9 1   5 7 2   |   6 3 4   8 9 1   5 7 2
                                        |                         
                . . .   1 3 2   . . .   |   7 8 5   1 3 2   6 9 4
                . . .   9 5 8   . . .   |   4 1 6   9 5 8   7 2 3
                . . .   4 7 6   . . .   |   9 2 3   4 7 6   8 5 1
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