package LeetCode.problems.hard.trapping_rain_water;

import java.util.ArrayList;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */
public class TrappingRainWater {

public class Solution{

        public int trap(int[] height) {
            if (height == null) return 0;
            int len = height.length;
            if (len < 2) return 0;
            ArrayList<Integer> stack_p = new ArrayList<>(len / 2);
            ArrayList<Integer> stack_h = new ArrayList<>(len / 2);
            int S = 0;
            int curr = height[0];

            for (int i = 1; i < len; i++){
                int prev = curr;
                curr = height[i];

                if (curr < prev) {
                    stack_p.add(i);
                    stack_h.add(prev);

                } else if (curr > prev) {
                    int L = prev;
                    int headIndex = stack_h.size() - 1;

                    while( L <= curr && headIndex >= 0) {
                        int headPos    = stack_p.get(headIndex);
                        int headHeight = stack_h.get(headIndex);

                        int dLen = (i - headPos);
                        int dHeight = Math.min( curr - L, headHeight - L );

                        int dS = dLen * dHeight;
                        S += dS;

                        L = headHeight;
                        if (L <= curr) {
                            stack_h.remove(headIndex);
                            stack_p.remove(headIndex);
                        }

                        headIndex --;
                    }
                }
            }

            return S;
        }


}

}
