package kt.LeetCode.problems.hard.valid_number

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by grigory@clearscale.net on 11/26/2018.
 */

class Solution {

    companion object {
        enum class Mode {
            BEGIN,

            DIGITS_1_BEGIN,
            DIGITS_1,

            DIGITS_AFTER_POINT_BEGIN,
            DIGITS_AFTER_POINT,

            DIGITS_AFTER_E_SIGN,
            DIGITS_AFTER_E_BEGIN,
            DIGITS_AFTER_E,

            FINAL
        }
    }

    fun isNumber(s: String): Boolean {

        var mode = Mode.BEGIN

        for (ch in s) {

            when (mode) {
                Mode.BEGIN -> when(ch) {
                            ' ' -> { }
                       '+', '-' -> mode = Mode.DIGITS_1_BEGIN
                            '.' -> mode = Mode.DIGITS_AFTER_POINT_BEGIN
                    in '0'..'9' -> mode = Mode.DIGITS_1
                           else -> return false
                }
                Mode.DIGITS_1_BEGIN -> when(ch) {
                    in '0'..'9' -> mode = Mode.DIGITS_1
                            '.' -> mode = Mode.DIGITS_AFTER_POINT_BEGIN
                           else -> return false
                }
                Mode.DIGITS_1 -> when(ch) {
                    in '0'..'9' -> { }
                            '.' -> mode = Mode.DIGITS_AFTER_POINT
                            'e' -> mode = Mode.DIGITS_AFTER_E_SIGN
                            ' ' -> mode = Mode.FINAL
                           else -> return false
                }
                Mode.DIGITS_AFTER_POINT_BEGIN -> when(ch) {
                    in '0'..'9' -> mode = Mode.DIGITS_AFTER_POINT
                           else -> return false
                }
                Mode.DIGITS_AFTER_POINT -> when(ch) {
                    in '0'..'9' -> { }
                            'e' -> mode = Mode.DIGITS_AFTER_E_SIGN
                            ' ' -> mode = Mode.FINAL
                           else -> return false
                }
                Mode.DIGITS_AFTER_E_SIGN -> when(ch) {
                        '-','+' -> mode = Mode.DIGITS_AFTER_E_BEGIN
                    in '0'..'9' -> mode = Mode.DIGITS_AFTER_E
                           else -> return false
                }
                Mode.DIGITS_AFTER_E_BEGIN -> when(ch) {
                    in '0'..'9' -> mode = Mode.DIGITS_AFTER_E
                           else -> return false
                }
                Mode.DIGITS_AFTER_E -> when(ch) {
                    in '0'..'9' -> { }
                            ' ' -> mode = Mode.FINAL
                           else -> return false
                }
                Mode.FINAL -> when(ch) {
                            ' ' -> { }
                           else -> return false
                }
            }

        }

        return when (mode) {
                               Mode.BEGIN -> false

                      Mode.DIGITS_1_BEGIN -> false
                      Mode.DIGITS_1       -> true

            Mode.DIGITS_AFTER_POINT_BEGIN -> false
            Mode.DIGITS_AFTER_POINT       -> true

                Mode.DIGITS_AFTER_E_SIGN  -> false
                Mode.DIGITS_AFTER_E_BEGIN -> false
                Mode.DIGITS_AFTER_E       -> true

                               Mode.FINAL -> true
        }
    }

}

class Solution2 {

    companion object {

        //states
        val er = 0 // ERR
        val ST = 1 // BEGIN
        val db = 2 // DIGITS_1_BEGIN
        val ds = 3 // DIGITS_1
        val pb = 4 // DIGITS_AFTER_POINT_BEGIN
        val ps = 5 // DIGITS_AFTER_POINT
        val em = 6 // DIGITS_AFTER_E_SIGN
        val eb = 7 // DIGITS_AFTER_E_BEGIN
        val es = 8 // DIGITS_AFTER_E
        val fn = 9 // FINAL

        // symbols
        val `0` = 0  // '0'
        val `1` = 1  // '1'
        val `2` = 2  // '2'
        val `3` = 3  // '3'
        val `4` = 4  // '4'
        val `5` = 5  // '5'
        val `6` = 6  // '6'
        val `7` = 7  // '7'
        val `8` = 8  // '8'
        val `9` = 9  // '9'
        val ` ` = 10 // ' '
        val `+` = 11 // '+'
        val `-` = 12 // '-'
        val `d` = 13 // '.'
        val `e` = 14 // 'e'
        val `N` = 15 //  any else symbol
        
        
        val states = arrayOf( //  0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  _,  +,  -,  .,  e,  N   
                /*  0 */ arrayOf( 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0),
                /* ST */ arrayOf(ds, ds, ds, ds, ds, ds, ds, ds, ds, ds, ST, db, db, pb,  0,  0),
                /* db */ arrayOf(ds, ds, ds, ds, ds, ds, ds, ds, ds, ds,  0,  0,  0, pb,  0,  0),
                /* ds */ arrayOf(ds, ds, ds, ds, ds, ds, ds, ds, ds, ds,  0,  0,  0,  0,  0,  0),
                /* pb */ arrayOf( 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0),
                /* ps */ arrayOf( 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0),
                /* em */ arrayOf( 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0),
                /* eb */ arrayOf( 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0),
                /* es */ arrayOf( 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0),
                /* fn */ arrayOf( 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0)
        )
        
    }
}

class Tests {

    private val s = Solution()

    @Test fun t000(){ assertTrue(s.isNumber("0")) }
    @Test fun t001(){ assertTrue(s.isNumber("-1")) }
    @Test fun t002(){ assertTrue(s.isNumber("-100")) }
    @Test fun t003(){ assertTrue(s.isNumber(" +20")) }
    @Test fun t004(){ assertTrue(s.isNumber("0.1 ")) }
    @Test fun t005(){ assertTrue(s.isNumber(" -90e3   ")) }
    @Test fun t006(){ assertTrue(s.isNumber(" 6e-1")) }
    @Test fun t007(){ assertTrue(s.isNumber("53.5e93")) }
    @Test fun t008(){ assertTrue(s.isNumber(" 53.5e93  ")) }
    @Test fun t009(){ assertTrue(s.isNumber("2222222.3")) }
    @Test fun t010(){ assertTrue(s.isNumber("99667  ")) }
    @Test fun t011(){ assertTrue(s.isNumber("       0 ")) }
    @Test fun t012(){ assertTrue(s.isNumber(" 12e12 ")) }

    @Test fun t013(){ assertTrue(s.isNumber(".1")) }
    @Test fun t014(){ assertTrue(s.isNumber("-.12")) }
    @Test fun t015(){ assertTrue(s.isNumber("  +.1 ")) }
    @Test fun t016(){ assertTrue(s.isNumber(" -.5e777  ")) }
    @Test fun t017(){ assertTrue(s.isNumber("3.")) }
    @Test fun t018(){ assertTrue(s.isNumber(" 005047e+6")) }


    @Test fun t100(){ assertFalse(s.isNumber("abc")) }
    @Test fun t101(){ assertFalse(s.isNumber("1 a")) }
    @Test fun t102(){ assertFalse(s.isNumber(" 1e")) }
    @Test fun t103(){ assertFalse(s.isNumber("e3")) }
    @Test fun t104(){ assertFalse(s.isNumber(" 99e2.5")) }
    @Test fun t105(){ assertFalse(s.isNumber(" --6")) }
    @Test fun t106(){ assertFalse(s.isNumber("+-3")) }
    @Test fun t107(){ assertFalse(s.isNumber("-+3")) }
    @Test fun t108(){ assertFalse(s.isNumber("95a54e53")) }
    @Test fun t109(){ assertFalse(s.isNumber(" 12e12e12 ")) }
    @Test fun t110(){ assertFalse(s.isNumber(".")) }
    @Test fun t111(){ assertFalse(s.isNumber(" .")) }
    @Test fun t112(){ assertFalse(s.isNumber(". ")) }
    @Test fun t113(){ assertFalse(s.isNumber(" . ")) }
    @Test fun t114(){ assertFalse(s.isNumber(" ")) }
    @Test fun t115(){ assertFalse(s.isNumber("")) }
    @Test fun t116(){ assertFalse(s.isNumber("")) }
    @Test fun t117(){ assertFalse(s.isNumber("")) }
    @Test fun t118(){ assertFalse(s.isNumber("")) }
    @Test fun t119(){ assertFalse(s.isNumber("")) }

}