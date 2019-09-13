package kt.LeetCode.problems.hard.regexp

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*
import kotlin.math.max
import kotlin.math.min

class Regexp {

    fun isMatch(s: String, pattern: String): Boolean {
        if (pattern == ".*") return true
        val patternParts = splitToParts(pattern)
        return isMatch(s, 0, s.length, patternParts)
    }

    private fun isMatch(str: String, startIndex: Int, endIndex: Int, p: List<Part>) = when(p.size) {
           0 -> endIndex - startIndex == 1 // an empty string
           1 -> p[0].matchExact(str, startIndex, endIndex)
        else -> complexMatch(str, startIndex, endIndex, p)
    }

    private fun complexMatch(str: String, startIndex_: Int, endIndex_: Int, p: List<Part>): Boolean {
        var startIndex = startIndex_
        var endIndex = endIndex_

        fun Part.takeIfNotDynamic(): Part? = if (this is FixedPart || (this is DynamicPart && this.dynamicChar != '.')) this else null


        // there the  p list has length > 1 (at least 2)


        // reduce head
        var startDynamicPartIndex = 0
        fun getFirst(): Part? = if (startDynamicPartIndex < p.size) p[startDynamicPartIndex] else null
        var part = getFirst()?.takeIfNotDynamic()
        while (part != null) {
            if (part is FixedPart) {
                val m = part.matchBegin(str, startIndex, endIndex)
                if (!m) return false
                startDynamicPartIndex++
                startIndex += part.len

            } else if (part is DynamicPart) {
                startIndex = part.findEndOfPattern(str, startIndex, endIndex)
                if (startIndex == -1) return false
                startDynamicPartIndex++
            }
            part = getFirst()?.takeIfNotDynamic()
        }

        // reduce tail
        var endDynamicPartIndex = p.size
        fun getLast(): Part? = if (endDynamicPartIndex - startDynamicPartIndex > 1) p[endDynamicPartIndex-1] else null

        part = getLast()?.takeIfNotDynamic()
        while (part != null) {
            if (part is FixedPart) {
                val m = part.matchEnd(str, startIndex, endIndex)
                if (!m) return false
                endDynamicPartIndex--
                endIndex -= part.len

            } else if (part is DynamicPart) {
                endIndex = part.findBeginOfPattern(str, startIndex, endIndex)
                if (endIndex == -1) return false
                endDynamicPartIndex--
            }
            part = getLast()?.takeIfNotDynamic()
        }

        return when(startDynamicPartIndex - endDynamicPartIndex) {
            0 -> endIndex - startIndex == 1 // an empty string
            1 -> p[startDynamicPartIndex].matchExact(str, startIndex, endIndex)
            else -> bothSideDynamicMatch(str, startIndex, endIndex, p.subList(startDynamicPartIndex, endDynamicPartIndex+1))
        }
    }

    private fun bothSideDynamicMatch(str: String, startIndex: Int, endIndex: Int, p: List<Part>): Boolean {

        var part1EndIndex = 0
        var partsFor1 = p

        var part2StartIndex = 0
        var partsFor2 = p


        fun nextSplit(): Boolean {
            // find a longest fixed part... first for the beginning
            //     -> iterate trough all occurrence of the part to initial string
            // if no fixed part present - find a dynamic part with not '.' pattern
            //    -> iterate trough all combinations of the part occurrence .*aa*.*
            TODO()
        }

        while (nextSplit()) {
            val match1 = isMatch(str, startIndex, part1EndIndex, partsFor1)
            var match2 = if (match1) isMatch(str, part2StartIndex, endIndex, partsFor2) else false

            if (match1 && match2) return true
        }

        return false
    }

    private fun splitToParts(pattern: String): List<Part> {
        val parts: MutableList<Part> = LinkedList()

        var partStartedAt = 0
        var prevCh = ' '
        var dynamicChar = ' '
        var minLen = 0

        var mode = Mode.NORMAL

        pattern.forEachIndexed{ position, ch ->
            when (mode){
                Mode.NORMAL -> {
                    if (ch == '*') {
                        val segmentLen =(position - partStartedAt)
                        if (segmentLen <= 0) {
                            throw WrongRegexFormat("wrong regx format probably you added * (star symbol) at the begin of the regex pattern")
                        }
                        if (segmentLen > 1) {
                            parts.add(FixedPart(pattern, partStartedAt, position - 1))
                        }
                        dynamicChar = pattern[position-1]
                        partStartedAt = position-1
                        minLen = 0
                        mode = Mode.DYNAMIC
                    }
                }
                Mode.DYNAMIC -> {
                    when  {
                        (ch == '*') -> {
                            if (prevCh == dynamicChar) {
                                if (minLen > 0) minLen--
                            }
                        }
                        (ch == dynamicChar) -> minLen++
                    else -> {
                            parts.add(DynamicPart(dynamicChar, minLen))
                            mode = Mode.NORMAL
                            partStartedAt = position
                        }
                    }
                }
            }
            prevCh = ch
        }

        if (partStartedAt < pattern.length) when (mode){
            Mode.NORMAL  -> parts.add(FixedPart(pattern, partStartedAt, pattern.length))
            Mode.DYNAMIC -> parts.add(DynamicPart(dynamicChar, minLen))
        }

        println("$pattern => $parts")
        return parts
    }

    enum class Mode { NORMAL, DYNAMIC }

    /**
     * *************************************************************************************************
      */
    @Test fun tSplit1(){ check("aabbcc", "[(aabbcc)]")}
    @Test fun tSplit2(){ check("aab*bc*c*c*", "[(aa), (b*1), (c*)]")}
    @Test(expected = WrongRegexFormat::class) fun tSplit3(){ check("*aaaa*", "[(a*3)]")}
    @Test fun tSplit4(){ check("aaaa*", "[(aaa), (a*)]")}
    @Test fun tSplit5(){ check("a.*b", "[(a), (.*), (b)]")}
    @Test fun tSplit6(){ check("a.*b.**", "[(a), (.*), (b), (.*)]")}
    @Test fun tSplit7(){ check("a.*b.*.*", "[(a), (.*), (b), (.*)]")}
    @Test fun tSplit8(){ check("a.*b..*", "[(a), (.*), (b.), (.*)]")}
    @Test fun tSplit9(){ check("", "[]")}
    @Test fun tSplit10(){ check(".*aa*.*", "[(.*0), (a*1), (.*0)]")}

    private fun check(regex: String, compiled: String) {
        assertThat( splitToParts(regex).toString(),  `is`(compiled) )
    }

    @Test fun tFixedPartMatch0(){ assertThat( FixedPart("012345679", 2, 5).matchExact("2345", 0,4), `is`(false) )}
    @Test fun tFixedPartMatch1(){ assertThat( FixedPart("012345679", 2, 5).matchExact("2/4", 0,3), `is`(false) )}
    @Test fun tFixedPartMatch2(){ assertThat( FixedPart("012345679", 2, 5).matchExact("234", 0,3), `is`(true) )}
    @Test fun tFixedPartMatch3(){ assertThat( FixedPart("012.45679", 2, 5).matchExact("2a4", 0,3), `is`(true) )}


    @Test fun tDynamicPartMatch1(){ assertThat( DynamicPart('a', 0).matchExact("aaa", 0,3), `is`(true) )}
    @Test fun tDynamicPartMatch2(){ assertThat( DynamicPart('a', 0).matchExact("aab", 0,3), `is`(false) )}
    @Test fun tDynamicPartMatch3(){ assertThat( DynamicPart('a', 1).matchExact("aab", 0,2), `is`(true) )}
    @Test fun tDynamicPartMatch4(){ assertThat( DynamicPart('a', 2).matchExact("aab", 0,2), `is`(true) )}
    @Test fun tDynamicPartMatch5(){ assertThat( DynamicPart('a', 2).matchExact("aab", 0,3), `is`(false) )}
    @Test fun tDynamicPartMatch6(){ assertThat( DynamicPart('a', 3).matchExact("aa", 0,2), `is`(false) )}
    @Test fun tDynamicPartMatch7(){ assertThat( DynamicPart('a', 3).matchExact("aaa", 0,2), `is`(false) )}
    @Test fun tDynamicPartMatch8(){ assertThat( DynamicPart('a', 3).matchExact("aaa", 0,3), `is`(true) )}
    @Test fun tDynamicPartMatch9(){ assertThat( DynamicPart('a', 3).matchExact("baaab", 1,4), `is`(true) )}


}


interface Part {
    fun matchExact(s: String, startIndex: Int, endIndex: Int): Boolean
}

data class FixedPart(val pattern: String, val beginPosition /*inclusive*/: Int, val endPosition /*exclusive*/ : Int ): Part {

    val len = endPosition -  beginPosition

    override fun toString(): String {
        return "(${pattern.substring(beginPosition, endPosition)})"
    }

    override fun matchExact(s: String, startIndex: Int, endIndex: Int): Boolean {
        if ((endIndex - startIndex) != len) return false

        var j = beginPosition
        for (i in startIndex until endIndex) {
            val patternCh = pattern[j]
            if (patternCh != '.' && patternCh != s[i]) return false
            j++
        }

        return true
    }

    fun matchBegin(s: String, startIndex: Int, endIndex: Int) = matchExact(s, startIndex                     , min(startIndex + len, endIndex ))
    fun matchEnd(s: String, startIndex: Int, endIndex: Int)   = matchExact(s, max(startIndex, endIndex - len), endIndex )


}

data class DynamicPart(val dynamicChar: Char, val minLen: Int): Part {
    override fun toString(): String {
        return "($dynamicChar*${if (minLen==0) "" else minLen.toString() })"
    }

    override fun matchExact(s: String, startIndex: Int, endIndex: Int): Boolean {
        if ((endIndex - startIndex) < minLen) return false
        if (dynamicChar == '.') return true
        for (i in startIndex until endIndex) {
            if (s[i] != dynamicChar) return false
        }
        return true
    }

    fun findEndOfPattern(s: String, startIndex: Int, endIndex: Int): Int {
        if (endIndex - startIndex < minLen) return -1

        var i = startIndex
        while(i < endIndex && s[i] == dynamicChar) i++

        if (i - startIndex < minLen) return -1
        return i
    }

    fun findBeginOfPattern(str: String, startIndex: Int, endIndex: Int): Int {
        if (endIndex - startIndex < minLen) return -1

        var e = endIndex-1
        while(e >= startIndex && str[e] == dynamicChar) e--
        e++

        if (endIndex - e < minLen) return -1
        return e
    }
}

class WrongRegexFormat(msg: String): RuntimeException(msg)