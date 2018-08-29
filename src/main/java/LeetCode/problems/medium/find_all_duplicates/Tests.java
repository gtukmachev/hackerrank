package LeetCode.problems.medium.find_all_duplicates;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.sort;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/29/2018.
 */
public class Tests {
    FindAllDuplicatesInArray.Solution s = new FindAllDuplicatesInArray().new Solution();

    @Test public void t_leet_0(){ test(Arrays.asList(4,3,2,7,8,2,3,1), Arrays.asList(2, 3) ); }
    @Test public void t_leet_1(){ test(Arrays.asList(3,11,8,16,4,15,4,17,14,14,6,6,2,8,3,12,15,20,20,5), Arrays.asList(4,14,6,8,3,15,20) ); }

    void test(List<Integer> arr, List<Integer> answer){
        List<Integer> response = s.findDuplicates( arr.stream().mapToInt(i -> i).toArray() );
        sort(response);
        sort(answer);
        assertThat(response, is(answer) );
    }

}
