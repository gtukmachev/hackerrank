package LeetCode.problems.easy.reverse_integer;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/13/2018.
 */
public class ReverseInteger {

    public int reverse(int x) {
        int src = x;
        long dst = 0;

        while (src != 0 ) {
            int d = src % 10;
            src /= 10;

            dst = dst*10 + d;
        }

        return (dst > Integer.MAX_VALUE || dst < Integer.MIN_VALUE) ? 0 : ((int)dst);
    }

    public static class Tests{

        ReverseInteger ri = new ReverseInteger();


        @Test public void tm1(){ assertThat( ri.reverse(  123), is(  321)); }
        @Test public void tm2(){ assertThat( ri.reverse(-1234), is(-4321)); }

        @Test public void tMax1(){
            assertThat( ri.reverse(2099999999), is(0));
        }

    }


}
