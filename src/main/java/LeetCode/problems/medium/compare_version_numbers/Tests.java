package LeetCode.problems.medium.compare_version_numbers;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/28/2018.
 */
public class Tests {

    CompareVersionNumbers.Solution s = new CompareVersionNumbers().new Solution();

    @Test public void t_leet0(){ assertThat(s.compareVersion("01", "1"), is( 0)); }

    @Test public void td1(){ assertThat(s.compareVersion("01.01.0001", "1.1.1"), is( 0));}

    @Test public void t0(){ assertThat(s.compareVersion("1", "1"), is( 0)); }
    @Test public void t1(){ assertThat(s.compareVersion("1", "0"), is( 1)); }
    @Test public void t2(){ assertThat(s.compareVersion("0", "1"), is(-1)); }

    @Test public void t3(){ assertThat(s.compareVersion("1.0.0", "1.0.1"), is(-1)); }
    @Test public void t4(){ assertThat(s.compareVersion("1.0.1", "1.0.0"), is( 1)); }
    @Test public void t5(){ assertThat(s.compareVersion("1.0.1", "1.0.2"), is(-1)); }

    @Test public void t6(){ assertThat(s.compareVersion("2", "1.12.155"), is(1)); }
    @Test public void t7(){ assertThat(s.compareVersion("1.12.155", "1.12.155"), is(0)); }

    @Test public void t8(){ assertThat(s.compareVersion("1111111111111111111111111111111111111111111111.12.155", "1111111111111111111111111111111111111111111111.12.155"), is(0)); }
    @Test public void t9(){ assertThat(s.compareVersion("1111111111111111111111111111111111111111111111.222222222222222222222222222222222222222222222222222.333333333333333333333333333333333333333333333333", "1111111111111111111111111111111111111111111111.2222222222222222222222222222222222222222222222222222.333333333333333333333333333333333333333333333333"), is(-1)); }
    @Test public void t10(){ assertThat(s.compareVersion("1111111111111111111111111111111111111111111111.222222222222222222222222222222222222222222222222222.333333333333333333333333333333333333333333333333", "1111111111111111111111111111111111111111111111.22222222222222222222222222222222222222222222222222.333333333333333333333333333333333333333333333333"), is(1)); }

    @Test public void tn1(){ assertThat(s.compareVersion("", "."), is(-1)); }
    @Test public void tn2(){ assertThat(s.compareVersion(".", ""), is(1)); }
    @Test public void tn3(){ assertThat(s.compareVersion("", ""), is(0)); }


}
