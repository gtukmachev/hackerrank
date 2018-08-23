package LeetCode.problems.hard.largest_rectangle_in_histogram;

import java.util.ArrayList;

/**
 * Created by grigory@clearscale.net on 8/23/2018.
 */
public class LargestRectangleInHistogram {

    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) return 0;
        if (heights.length == 1) return heights[0];

        ArrayList<Integer> rectStarts = new ArrayList<>(heights.length / 16);
        ArrayList<Integer> rectHeights = new ArrayList<>(heights.length / 16);
        rectHeights.add(heights[0]);
        rectStarts.add(0);
        int max = 0;

        int h;
        int prev = heights[0];

        for(int i = 1; i < heights.length; i++) {
            h = heights[i];
            // if (h == prev) - skip it
            if ( h > prev) {
                rectHeights.add(heights[i]);
                rectStarts.add(i);

            } else if (h < prev) {

                int rHeight = h;
                int rStartedAt;
                int index = rectHeights.size() - 1;

                while (index >= 0 && rectHeights.get(index) > h ) {
                    rHeight = rectHeights.remove(index);
                    rStartedAt = rectStarts.remove(index);
                    index--;

                    max = maxRect(max, rHeight, rStartedAt, i);
                }

                if (rHeight < h || index == -1) {
                    rectHeights.add(h);
                    rectStarts.add(i);
                }

            }
            prev = h;
        }

        for (int i = rectHeights.size() - 1; i >= 0; i--)
            max = maxRect(max, rectHeights.remove(i), rectStarts.remove(i), heights.length);

        return max;


    }

    int maxRect(int max, int height, int start, int end) {
        int S = height * (end - start);
        return max > S ? max : S;
    }


}
