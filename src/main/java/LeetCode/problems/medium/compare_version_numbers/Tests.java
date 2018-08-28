package LeetCode.problems.medium.compare_version_numbers;

import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/28/2018.
 */
public class Tests {

    CompareVersionNumbers.Solution s = new CompareVersionNumbers().new Solution();

    @Test public void tt_iterator_1() {
        CompareVersionNumbers.VersionIterator it = new CompareVersionNumbers.VersionIterator("11.22.333..4.5");
        assertThat(it.next(), is("11"));
        assertThat(it.next(), is("22"));
        assertThat(it.next(), is("333"));
        assertThat(it.next(), is(""));
        assertThat(it.next(), is("4"));
        assertThat(it.next(), is("5"));
        assertNull(it.next());
    }


    @Test public void t0(){ assertThat(s.compareVersion("1", "1"), is( 0)); }
    @Test public void t1(){ assertThat(s.compareVersion("1", "0"), is( 1)); }
    @Test public void t2(){ assertThat(s.compareVersion("0", "1"), is(-1)); }


}
