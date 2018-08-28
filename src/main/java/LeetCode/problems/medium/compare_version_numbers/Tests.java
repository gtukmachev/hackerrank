package LeetCode.problems.medium.compare_version_numbers;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/28/2018.
 */
public class Tests {

    CompareVersionNumbers.Solution s = new CompareVersionNumbers().new Solution();


    @Test public void t0(){ assertThat(s.compareVersion("1", "1"), is( 0)); }
    @Test public void t1(){ assertThat(s.compareVersion("1", "0"), is( 1)); }
    @Test public void t2(){ assertThat(s.compareVersion("0", "1"), is(-1)); }


}
