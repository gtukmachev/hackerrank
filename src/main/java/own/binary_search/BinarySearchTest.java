package own.binary_search;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 9/18/2018.
 */
public class BinarySearchTest {

    @Test public void t1() { t(0, 5,     5, 7); }
    @Test public void t2() { t(1, 7,     5, 7); }
    @Test public void t3() { t(-1, 4,     5, 7); }
    @Test public void t4() { t(-2, 6,     5, 7); }
    @Test public void t5() { t(-3, 8,     5, 7); }


    private void t(int... e){
        int requredResult = e[0];
        int target = e[1];
        int[] ar = new int[e.length-2];
        System.arraycopy(e, 2, ar, 0, e.length - 2);

        int result = BinarySearch.find(ar, target);
        //int result = Arrays.binarySearch(ar, target);

        assertThat(result, is(requredResult));
    }

}
