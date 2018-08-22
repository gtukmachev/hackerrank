package LeetCode.problems.medium.is_subsequence;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */
public class Tests {
    IsSubsequence s = new IsSubsequence();

    @Test public void tn0(){ assertFalse( s.isSubsequence("abh", "") ); }
    @Test public void tn1(){ assertFalse( s.isSubsequence("abh", null) ); }
    @Test public void tn2(){ assertFalse( s.isSubsequence("", null) ); }
    @Test public void tn3(){ assertFalse( s.isSubsequence(null, null) ); }

    @Test public void ts1(){ assertTrue( s.isSubsequence("", "") ); }
    @Test public void ts2(){ assertTrue( s.isSubsequence(null, "") ); }
    @Test public void ts3(){ assertTrue( s.isSubsequence("", "abc") ); }
    @Test public void ts4(){ assertTrue( s.isSubsequence(null, "abc") ); }

    @Test public void t1(){ assertTrue ( s.isSubsequence("abc", "ahbgdc") ); }
    @Test public void t2(){ assertFalse( s.isSubsequence("abh", "ahbgdc") ); }

    @Test public void te1(){ assertFalse( s.isSubsequence("fe", "abcdef") ); }
    @Test public void te2(){ assertTrue ( s.isSubsequence("ef", "abcdef") ); }

    @Test public void tl1(){ assertFalse( s.isSubsequence("abcdef", "ef") ); }
    @Test public void tl2(){ assertTrue( s.isSubsequence("ef", "ef") ); }

    @Test public void td1(){ assertTrue( s.isSubsequence("efffffffdddddddaaaagggg", "e__ffff_fffddd1ddddaadaaggggg") ); }

}
