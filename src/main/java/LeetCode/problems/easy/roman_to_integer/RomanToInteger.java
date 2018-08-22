package LeetCode.problems.easy.roman_to_integer;

// https://leetcode.com/problems/roman-to-integer/description/
/**
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 *
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * For example, two is written as II in Roman numeral, just two one's added together.
 * Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII.
 * Instead, the number four is written as IV.
 * Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:
 *
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
 *
 * Example 1:
 *      Input: "III"
 *      Output: 3
 *
 * Example 2:
 *      Input: "IV"
 *      Output: 4
 *
 * Example 3:
 *      Input: "IX"
 *      Output: 9
 *
 * Example 4:
 *      Input: "LVIII"
 *      Output: 58
 *      Explanation: C = 100, L = 50, XXX = 30 and III = 3.
 *
 * Example 5:
 *      Input: "MCMXCIV"
 *      Output: 1994
 *      Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 */
public class RomanToInteger {

    static short[] rti;
    static {
        rti = new short['Z' - 'A' + 2];
        rti['I'-'A'] =    1;
        rti['V'-'A'] =    5;
        rti['X'-'A'] =   10;
        rti['L'-'A'] =   50;
        rti['C'-'A'] =  100;
        rti['D'-'A'] =  500;
        rti['M'-'A'] = 1000;
        rti['Z'-'A'+1] = 10000;
    }

    public int romanToInt(String s) {
        if (s == null) return 0;
        int L = s.length();
        if (L == 0) return 0;

        int prev = 'Z'-'A'+1;
        int segmentAmount = 0;
        int amount = 0;

        for (int i = 0; i < L; i++){
           int curr = s.charAt(i) - 'A';
           if (curr == prev) {
               segmentAmount += rti[curr];
           } else {
               if (rti[curr] < rti[prev]) amount += segmentAmount;
                                     else amount -= segmentAmount;
               segmentAmount = rti[curr];
           }
           prev = curr;
        }

        return amount + segmentAmount;

    }

}
