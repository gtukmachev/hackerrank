package LeetCode.problems.easy.remove_element;

import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by grigory@clearscale.net on 11/25/2018.
 */
public class RemoveElement {

    public static class Solution {

        public int removeElement(int[] nums, int val) {
            int iStart = -1;
            boolean moveMode = true;

            for (int i=0; i < nums.length; i++) {
                boolean isTarget = nums[i] == val;

                if (moveMode) {
                    if (isTarget) {
                        moveMode = false;
                        iStart = (iStart == -1)
                                ? i
                                : reorder(nums, iStart, i, val);
                    }
                } else {
                    if (!isTarget) moveMode = true;
                }
            }

            iStart = (iStart == -1)
                    ? nums.length
                    : reorder(nums, iStart, nums.length, val);

            return iStart;
        }


        int reorder(int[] nums, int iStart, int iEnd, int val) {
            iEnd--;

            while (iEnd > iStart && nums[iStart] == val && nums[iEnd] != val) {
                nums[iStart] = nums[iEnd];
                nums[iEnd] = val;
                iStart++;
                iEnd--;
            }

            return (nums[iStart] == val)
                    ? iStart
                    : (iEnd + 1);
        }

    }


    public static class Tests {

        private Solution s = new Solution();

        @Test public void leet_1() { genericTest( new int[]{3,2,2,3}, 3,
                                                  new int[]{  2,2  } );}

        @Test public void t1() { genericTest( new int[]{0,1,2,3,0,5,6}, 0,
                                              new int[]{  1,2,3,  5,6} );}

        @Test public void t2() { genericTest( new int[]{0,0,0,0,1,0,0,0,0,0,0,0,5,6,0,0,0,0,0}, 0,
                                              new int[]{        1,              5,6          } );}

        @Test public void t3() { genericTest( new int[]{}, 0,
                                              new int[]{} );}

        @Test public void t4() { genericTest( new int[]{}, 1,
                                              new int[]{} );}

        @Test public void t5() { genericTest( new int[]{1,1,1}, 1,
                                              new int[]{} );}

        @Test public void t6() { genericTest( new int[]{2}, 2,
                                              new int[]{} );}

        @Test public void t7() { genericTest( new int[]{1}, 0,
                                              new int[]{1} );}

        @Test public void t8() { genericTest( new int[]{1,2,3}, 0,
                                              new int[]{1,2,3} );}

        @Test public void t9() { genericTest( new int[]{1,2,3,8,8,8}, 8,
                                              new int[]{1,2,3} );}

        private void genericTest(int[] arr, int targetVal, int[] neededArbitraryArr) {
            int len = s.removeElement(arr, targetVal);

            assertThat(len, is( neededArbitraryArr.length ));

            LinkedList<Integer> neededArbitraryArrAsList = new LinkedList<>();
            for (int i : neededArbitraryArr) neededArbitraryArrAsList.add(i);


            for (int i= 0; i < len; i++) {
                assertThat(neededArbitraryArrAsList.remove( Integer.valueOf( arr[i] )), is(true));
            }
            assertTrue(neededArbitraryArrAsList.isEmpty());

        }

    }

}
