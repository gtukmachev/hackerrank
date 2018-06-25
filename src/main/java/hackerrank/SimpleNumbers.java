package hackerrank;

import java.util.Arrays;

/**
 * Created by grigory@clearscale.net on 6/19/2018.
 */
public class SimpleNumbers {


    private static void isSimple(int[] ints) {
        int count = ints.length;

        int[] sorted = Arrays.copyOf(ints, count);
        Arrays.sort(sorted);
        int[] factors = Arrays.stream(sorted).map( n -> (int) Math.sqrt(n) ).toArray();
        int removedCount = 0;

        int maxFactor = factors[factors.length-1];

        int[] nums = new int[maxFactor+1];
        nums[1] = -1;

        int j = 0;
        int n = 2;
        while (n <= maxFactor && removedCount < sorted.length) {
            while (n <= maxFactor && nums[n] != 0) { n++; }
            // the 'n' is simple!

            for (int i=j; i < sorted.length; i++) {
                if (n > factors[i]) {
                    j = i;
                } else if ( (sorted[i] % n) == 0 ) {
                    sorted[i] = -1;
                    removedCount++;
                }
            }

            if (removedCount < sorted.length) {
                for (int i = n + n; i <= maxFactor; ) { nums[i] = -1; i += n; }
            }
            n++;
        }

        int[] simpleNums = new int[ints.length - removedCount];
        int jk = 0;
        for (int i = 0; i < sorted.length; i++) {
            int num = sorted[i];
            if (num > 0) {
                simpleNums[jk] = num; jk++;
            }
        }
        //System.out.println(Arrays.toString(simpleNums));

        Arrays.stream(ints)
                //.forEach( i -> System.out.println( i + " - " + ( (i > 1 && Arrays.binarySearch(simpleNums, i) >= 0) ? "Prime" : "Not prime")) );
                  .forEach( i -> System.out.println(             ( (i > 1 && Arrays.binarySearch(simpleNums, i) >= 0) ? "Prime" : "Not prime")) );
    }

    public static void main(String[] args) {
//        for (int j = 0; j < 100; j++) {
//
//            int[] ints = new int[100];
//            Random rnd = new Random();
//            for (int i = 0; i < ints.length; i++) ints[i] = (int)( rnd.nextDouble() * 2_000_000_000 );
//
//            isSimple(ints);
//
//        }
        isSimple(new int[]{1});
    }


}
