package LeetCode.problems.easy.roman_to_integer;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Tests {

    RomanToInteger s = new RomanToInteger();


   @Test public void  t1() { assertThat( s.romanToInt("I"),   is( 1) ); }
   @Test public void  t2() { assertThat( s.romanToInt("II"),  is( 2) ); }
   @Test public void  t3() { assertThat( s.romanToInt("III"), is( 3) ); }
   @Test public void  t4() { assertThat( s.romanToInt("IV"),  is( 4) ); }
   @Test public void  t5() { assertThat( s.romanToInt("V"),   is( 5) ); }
   @Test public void  t6() { assertThat( s.romanToInt("VI"),  is( 6) ); }
   @Test public void  t7() { assertThat( s.romanToInt("VII"), is( 7) ); }
   @Test public void  t8() { assertThat( s.romanToInt("VIII"),is( 8) ); }
   @Test public void  t9() { assertThat( s.romanToInt("IX"),  is( 9) ); }
   @Test public void t10() { assertThat( s.romanToInt("X"),   is(10) ); }
   @Test public void t11() { assertThat( s.romanToInt("XI"),  is(11) ); }

}
