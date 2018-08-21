package LeetCode.problems.hard.trapping_rain_water;

/**
 * Created by grigory@clearscale.net on 8/21/2018.
 */
public class TrappingRainWater_faster {

    class Solution {
        public int trap(int[] height) {
            if(height == null || height.length == 0){
                return 0;
            }
            int leftTrap = 0;
            int rightTrap = 0;

            int left = 0, right = height.length - 1;
            int leftMax = height[0], rightMax = height[right];
            while(left < right){
                leftMax = Math.max(leftMax, height[left]);
                rightMax = Math.max(rightMax, height[right]);
                if(leftMax < rightMax){
                    leftTrap += (leftMax - height[left++]);
                }else{
                    rightTrap += (rightMax - height[right--]);
                }
            }
            return leftTrap + rightTrap;
        }
    }
}
