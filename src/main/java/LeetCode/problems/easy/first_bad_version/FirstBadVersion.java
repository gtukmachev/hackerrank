package LeetCode.problems.easy.first_bad_version;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/28/2018.
 */
public class FirstBadVersion {
    public int badVersdion = 0;

    public FirstBadVersion(int badVersdion) {
        this.badVersdion = badVersdion;
    }

    public class VersionControl {

        public boolean isBadVersion(int n) {
            return n >= badVersdion;
        }
    }

    public class Solution extends VersionControl {
            public int firstBadVersion(int n) {
                if (n == 0) throw new RuntimeException("No versions detected at all!!!");
                if (isBadVersion(1)) return 1;
                if (!isBadVersion(n)) throw new RuntimeException("There is no a bad version detected!");

                long l = 1;
                long r = n;
                long m = (l + r) >> 1;

                do {
                    if (isBadVersion((int)m)) {
                        r = m;
                    } else {
                        l = m;
                    }
                    m = (l + r) / 2;
                } while ((r - l) > 1);

                return isBadVersion((int)l) ? (int)l : (int)r;
            }

    }


    public static class Tests{

        @Test public void t1(){ test(1, 1); }

        @Test public void t2a(){ test(2, 1); }
        @Test public void t2b(){ test(2, 2); }

        @Test public void t3a(){ test(3, 1); }
        @Test public void t3b(){ test(3, 2); }
        @Test public void t3c(){ test(3, 3); }


        @Test public void t_leet_1(){ test(2126753390, 1702766719); }


        private void test(int totalVersions, int brokenVersion){
            FirstBadVersion fv = new FirstBadVersion(brokenVersion);
            Solution s = fv.new Solution();
            assertThat( s.firstBadVersion(totalVersions), is(brokenVersion) );

        }

    }
}
