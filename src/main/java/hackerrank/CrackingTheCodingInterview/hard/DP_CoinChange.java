package hackerrank.CrackingTheCodingInterview.hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grigory@clearscale.net on 6/21/2018.
 */
public class DP_CoinChange {

    static long ways(int n, int[] coins) {
        return ways_(n, ordered(coins, coins.length), 0, new HashMap<>());
    }

    static long ways_(final int sum, int[] coins, int iCoin, final Map<List<Integer>, Long> cache) {
        if (iCoin == coins.length) return 0;
        int coin = coins[iCoin];
        if (coin == 0) return ways_(sum, coins, iCoin+1, cache);
        if (coin < 0) throw new IllegalArgumentException("coins array shouldn't contains negative digits");
        if (coin > sum) return 0;

        return cache.computeIfAbsent(Arrays.asList(sum, iCoin), key -> {
            long count = 0;
            int sum_ = sum;

            while (sum_ >= 0) {
                count += ways_(sum_, coins, iCoin+1, cache);
                sum_ -= coin; if (sum_ == 0) { count++; }
            }
            return count;
        });
    }

    static int[] ordered(int[] a, int length) {
        int[] ordered = Arrays.copyOf(a,length);
        Arrays.sort(ordered);
        return ordered;
    }

    public static void main(String[] args) {

        System.out.println(
                ways(245, new int[]{16,30,9,17,40,13,42,5,25,49,7,23,1,44,4,11,33,12,27,2,38,24,28,32,14,50})
        ); //64 027 917 156

/*
        System.out.println(
                ways(166, new int[]{5, 37, 8, 39, 33, 17, 22, 32, 13, 7, 10, 35, 40, 2, 43, 49, 46, 19, 41, 1, 12, 11, 28})
        ); //96190959

*/
/*
        System.out.println(
                ways(10, new int[]{2, 5, 3, 6})
        ); // 5
*/

    }

}
