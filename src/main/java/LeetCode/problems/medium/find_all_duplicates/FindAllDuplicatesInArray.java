package LeetCode.problems.medium.find_all_duplicates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grigory@clearscale.net on 8/29/2018.
 */
public class FindAllDuplicatesInArray {

    class Solution {

        public List<Integer> findDuplicates(int[] nums) {
            List<Integer> list = new ArrayList<>();
            if (nums == null) return list;

            for (int i = 0; i < nums.length; i++) {
                int I = nums[i];

                while (I != 0 && I != (i+1)) {
                    int tmp = nums[I-1];
                    if (tmp == I) {
                        list.add(tmp);
                        nums[i] = 0;
                        break;
                    } else {
                        nums[I-1] = I;
                        nums[i] = tmp;
                        I = tmp;
                    }
                }

            }

            return list;
        }

    }
}
