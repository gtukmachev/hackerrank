package hackerrank.CrackingTheCodingInterview.hard;

import hackerrank.IntHeap;

/**
 * Created by grigory@clearscale.net on 5/14/2018.
 */
public class Heaps_FindTheRunningMedian {
    private static IntHeap leftHeap = new IntHeap(true);
    private static IntHeap rightHeap = new IntHeap(false);

    private static float lastMedian = 0;

    private static float nextMedian(int next) {

        // leftHeap.size should be equals to rightHeap.size or to (rightHeap.size + 1)

        if (leftHeap.size == 0 || next <= lastMedian) { leftHeap.add(next);  }
        else                                          { rightHeap.add(next); }

        if      (rightHeap.size > leftHeap.size)        { leftHeap.add (rightHeap.removeRoot() ); }
        else if (leftHeap.size  > (rightHeap.size + 1)) { rightHeap.add( leftHeap.removeRoot() ); }

        lastMedian = leftHeap.size > rightHeap.size
                ? ( (float) leftHeap.root)
                : ( (leftHeap.root + rightHeap.root)/2.0f);

        return lastMedian;
    }

    public static void main(String[] args) {

        int[] a = new int[]{15, 10, 18, 20};
        for(int v : a) {
            float m = nextMedian(v);
            System.out.print("" + v + " => ");
            System.out.print(leftHeap.toString());
            System.out.print(" <" + m + "> ");
            System.out.println(rightHeap.toString());
        }

    }


}
