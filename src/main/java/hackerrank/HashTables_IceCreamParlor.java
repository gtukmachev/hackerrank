package hackerrank;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by grigory@clearscale.net on 6/24/2018.
 */
public class HashTables_IceCreamParlor {

    private static int[] choice(int[] cost, int sum) {
        Map<Integer, List<Integer>> index = createIndex(cost);

        int[] costOrdered = Arrays.copyOf(cost, cost.length); Arrays.sort(costOrdered);

        for (int i = 0; i < costOrdered.length; i++) {
            if (costOrdered[i] <= sum) {
                int j = Arrays.binarySearch(costOrdered, i + 1, costOrdered.length, sum - costOrdered[i]);
                if (j > 0) {

                    int i1 = getFromIndex(index, costOrdered[i], 1);
                    int j1 = getFromIndex(index, costOrdered[j], (costOrdered[i] == costOrdered[j] ? 2 : 1) );

                    return new int[]{i1 <= j1 ? i1 : j1, i1 > j1 ? i1 : j1};
                }
            }
        }

        return new int[0];
    }

    static private Map<Integer, List<Integer>> createIndex(int[] cost_){
        Map<Integer, List<Integer>> index = new HashMap<>(cost_.length);
        for (int i = 0; i < cost_.length; i++) {
            final int id = i;
            index.compute(cost_[i], (key, val) -> {
                List<Integer> l = val == null ? new ArrayList<>(1) : val;
                l.add(id);
                return l;
            });
        }
        return index;
    }

    static private int getFromIndex(Map<Integer, List<Integer>> index, int sum, int time) {
        List<Integer> l = index.get(sum);
        switch (time) {
            case 1: return l.get(0);
            case 2: return l.get( l.size() == 2 ? 1 : 0 );
        }
        throw new IllegalArgumentException("'time' should be = 1 or 2!");
    }

    // Complete the whatFlavors function below.
    static void whatFlavors(int[] cost, int money) {
        int[] vals = choice(cost, money);
        if (vals.length == 0) throw new RuntimeException("No solution found!");
        System.out.println((vals[0]+1) + " " + (vals[1]+1) );
    }





    @Test
    public void test1() {
        int[] m = new int[]{5,2,12,2,11,
                7, //**************
                44,6,
                3, //**************
                1,1,1};
        int[] vals = choice(m, 10);

        Assert.assertArrayEquals(new int[]{5,8}, vals);
        Assert.assertEquals(10, m[vals[0]] + m[vals[1]]);
    }

    @Test
    public void test2() {
        int[] m = new int[]{1,4,5,3,2};
        int[] vals = choice(m, 4);

        Assert.assertArrayEquals(new int[]{0,3}, vals);
        Assert.assertEquals(4, m[vals[0]] + m[vals[1]]);
    }

    @Test
    public void test3() {
        int[] m = new int[]{2,4,3,2};
        int[] vals = choice(m, 4);

        Assert.assertArrayEquals(new int[]{0,3}, vals);
        Assert.assertEquals(4, m[vals[0]] + m[vals[1]]);
    }



}
