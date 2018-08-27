package kt.LeetCode.problems.hard.largest_rectangle_on_picture

/**
 * Created by grigory@clearscale.net on 8/25/2018.
 */

class LargestRectangleOnPicture {

    fun maximalRectangle(f: Array<CharArray>  ): Int {
        val L = f.size;    if (L == 0) return 0
        val C = f[0].size; if (C == 0) return 0

        val lastL = L - 1;
        val sqrIndex = RectsIndex(L)


        for (c in 0 until C) {
            val bigSqr = Rect(0,c, lastL, c-1) // fake rectangle

            for (l in 0 until L) {
                bigSqr.pushPoint(l, c, f[l][c] )
                sqrIndex.pushPointToAllNeeded(l, c, f[l][c])
            }

            val newSqrs = sqrIndex.evaluate()

            for(sqr in newSqrs          ) sqrIndex.addSqrToIndex(sqr)
            for(sqr in bigSqr.evaluate()) sqrIndex.addSqrToIndex(sqr)

            bigSqr.clear()
        }

        sqrIndex.evaluateMaxS()

        return sqrIndex.maxS
    }

    //data class Point(var l: Int, var c: Int)

    /**
     * Rectatngle
     *    +--------+
     *    |ps      |
     *    |      pe|
     *    +--------+
     *
     * @field ps - start of the rectangle inclusive
     * @field pe - end of the rectangle inclusive
     */
    data class Rect(val ls: Int, val cs: Int, var le: Int, var ce: Int) {

        var id: Int = -1
        
        val lines: MutableList<Rect> = mutableListOf() //t: review real class of it
        var lastLine: Rect? = null
        fun S() = (le - ls + 1) * (ce - cs + 1)

        fun pushPoint(line: Int, column: Int, color: Char) {
            if (line < ls || line > le) return; //todo: remove it after indexing
            if (color == '0') {
                if (lastLine != null) lastLine = null
                return
            }

            if (lastLine == null) {
                lastLine = Rect(line, cs, line, column)
                lines.add(lastLine!!)
            } else {
                lastLine!!.le = line
            }
        }

        fun evaluate(): List<Rect> {
            if (lines.size == 1 && lines[0].ls == this.ls && lines[0].le == this.le) {
                this.ce = lines[0].ce
                lines[0] = this
            }
            return lines
        }

        fun clear() {
            lines.clear()
            lastLine = null
        }

    }

    class RectsIndex(val lines: Int){
        
        var rectsId = 0;
        var maxS: Int = 0
        val index: Array<MutableMap<Int, Rect>> = Array(lines){ _ -> HashMap<Int, Rect>() }

        fun pushPointToAllNeeded(l: Int, c: Int, color: Char) {
            index[l].values.forEach{ sqr -> sqr.pushPoint(l,c, color) }
        }

        fun addSqrToIndex(rect: Rect) {
            if (index[rect.ls].values.any{
                        it.ls <= rect.ls &&
                                it.le >= rect.le
                    }
            ) return // skip it, if another one present, which covers this one

            rect.id = rectsId++;
            for (l in rect.ls .. rect.le)
                index[l].put(rect.id, rect)
        }

        fun remove(rect: Rect) {
            for (l in rect.ls .. rect.le) {
                val removed = index[l].remove(rect.id)
                if (removed == null) throw RuntimeException("!!!")
            }

            val s = rect.S()
            if (s > maxS) maxS = s
            //println("- $rect : $s")
        }

        fun forEach(f: (Rect) -> Unit){
            for (l in 0 until index.size)
                for (entry in index[l])
                    if (entry.value.ls == l)
                        f(entry.value)
        }

        fun evaluate(): Collection<Rect> {
            val newSqrts = mutableListOf<Rect>()
            val toRemove = mutableListOf<Rect>()

            this.forEach { sqr ->
                val sqrts = sqr.evaluate()

                if ( sqrts.size != 1 || sqrts[0] != sqr) {
                    // in case if 'new' collection represents the same original rectangle - we have to skip it
                    toRemove.add(sqr)
                    newSqrts.addAll(sqrts)
                }

                sqr.clear()

            }

            for (sqr in toRemove) remove(sqr)

            return newSqrts
        }

        fun evaluateMaxS() {
            forEach {
                val s = it.S()
                //println("+ $it : $s")
                if (s > maxS) maxS = s
            }
        }

    }
}

