package own.binary_search;

/**
 * Created by grigory@clearscale.net on 9/18/2018.
 */
public class BinarySearch {

    public static int find(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;
        int s = 0, e = arr.length - 1;

        while (s < e) {
            int m = (s + e) >> 1;
            if (arr[m] == target) return m;
            if (arr[m] < target) s = m + 1;
                            else e = m;
        }

        if (arr[s] == target) return s;
        if (arr[s] < target) return -s - 2;
        return -s - 1;
    }

}
