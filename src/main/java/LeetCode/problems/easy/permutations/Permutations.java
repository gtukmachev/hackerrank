package LeetCode.problems.easy.permutations;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {

    public List<List<Integer>> permute(int[] nums) {
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(numsCopy);
        List<List<Integer>> result = new ArrayList<>();

        do {
            result.add(toList(numsCopy));
        } while(nextPermutation(numsCopy));

        return result;
    }

    private boolean nextPermutation(int[] perm) {
        int i = perm.length - 2;

        while (i >= 0 && perm[i] >= perm[i+1]) i--;
        if (i < 0) return false;

        int ni = perm[i];

        int j = perm.length - 1;
        while (perm[j] <= ni) j--;

        swap(perm, i, j);

        int b=i+1, e=perm.length-1;
        while( b < e) swap(perm, b++, e--);

        return true;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private List<Integer> toList(int[] arr) {
        List<Integer> list = new ArrayList<>(arr.length);
        for (int n: arr) list.add(n);
        return list;
    }

    @Test public void tp0(){
        List<List<Integer>> p = permute(new int[]{1,1,1,4,5,6});

        for(List<Integer> l : p) {
            System.out.println(l);
        }
    }



}
