package LeetCode.problems.medium.shortes_substring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 11/29/2018.
 */
@RunWith(Parameterized.class)
public class Tests {


    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Object[][] data = new Object[][] {
                { "be or not to be that is the question", "", new String[]{} },
                { "be or not to be that is the question", "be that is", new String[]{"be", "is"} },
        };

        return Arrays.asList(data);
    }

    private String src;
    private String result;
    private String[] words;
    ShortestSubstring s;

    public Tests(String src, String result, String[] words) {
        this.src = src;
        this.result = result;
        this.words = words;
        this.s = new ShortestSubstring();
    }

    @Test
    public void tb0() {
        assertThat( s.shortest(src, words), is(result) );
    }

    /*
    @Test public void tb0() { assertThat(
                s.shortest("be or not to be that is the question",
                new String[]{}),
                is("")
        ); }

    @Test public void tb1() { assertThat(
            s.shortest("",
                    new String[]{"1", "2"}),
            is("")
    ); }

    @Test public void tb3() { assertThat(
            s.shortest("           ",
                    new String[]{"1", "2"}),
            is("")
    ); }

    @Test public void tb4() { assertThat(
            s.shortest("1 2",
                    new String[]{"1", "2"}),
            is("1 2")
    ); }

    @Test public void tb5() { assertThat(
            s.shortest("1 2",
                    new String[]{"1", "2", "3"}),
            is("")
    ); }

    @Test public void tb6() { assertThat(
            s.shortest("1 2",
                    new String[]{"1 ", "2"}),
            is("")
    ); }

    @Test public void t1() { assertThat(
                s.shortest("be or not to be   that is   the question",
                new String[]{"be", "is"}),
                is("be   that is")
        ); }

    @Test public void t2() { assertThat(
                s.shortest("be or not to be - that is the 1 question ",
                new String[]{"be", "is", "question"}),
                is("be - that is the 1 question")
        ); }

    @Test public void t3() { assertThat(
                s.shortest("be or not to be that is the question",
                new String[]{"be", "is"}),
                is("be that is")
        ); }
*/

}
