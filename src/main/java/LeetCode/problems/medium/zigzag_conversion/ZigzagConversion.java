package LeetCode.problems.medium.zigzag_conversion;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/13/2018.
 */
public class ZigzagConversion {

    public String convert(String s, int numRows) {
        if (numRows < 1) throw new RuntimeException("'numRows' parameter value should be larger than 0!");
        if (numRows == 1) return s;

        int len = s.length();

        if (len == 0) return s;
        char[] out = new char[len];

        char[] st = s.toCharArray();

        int step1 = (numRows - 1) << 1;
        int step2 = 0;

        int p = 0; // position in source string
        int j = 0; // position in target string

        // the first line
        while (p < len) { out[j++] = st[p]; p += step1; }

        int nr_1 = numRows - 1;
        for(int i = 1; i < nr_1; i++){
            p = i;
            step1 -= 2;
            step2 += 2;
            while (p < len) {
                out[j++] = st[p]; p += step1; // a char from the diagonal
                if (p < len) {
                    out[j++] = st[p]; p += step2; // a char from the vertical
                }
            }
        }

        // the last line
        p = nr_1;
        step2 += 2;
        while (p < len) { out[j++] = st[p]; p += step2; }

        return new String(out);
    }


    public static class Tests{

        private ZigzagConversion zc = new ZigzagConversion();

        @Test public void t1(){  assertThat( zc.convert("123456789AB",3), is("1592468A37B") ); }
        @Test public void t2(){  assertThat( zc.convert("123456789ABCDEF",5), is("1928A37BF46CE5D") ); }

    }

}
