package hackerrank;

import java.util.Arrays;

/**
 * Created by grigory@clearscale.net on 5/14/2018.
 */
public class IntHeap {
    private static int INCREMENT_NUMBER = 100;

    public int[] arr = new int[INCREMENT_NUMBER];
    public int size = 0;
    public int root;
    private boolean isMaxHeap; // if true - max on top

    public IntHeap(boolean isMaxHeap) {
        this.isMaxHeap = isMaxHeap;
    }

    public IntHeap(boolean isMaxHeap, int... heap ) {
        this.isMaxHeap = isMaxHeap;
        for (int v : heap) add(v);
    }

    public void add(int value) {
        if (size == arr.length) {
            int[] newArr = Arrays.copyOf(arr, arr.length + INCREMENT_NUMBER);
            this.arr = newArr;
        }
        arr[size] = value;
        size++;

        siftUp(size-1);
        root = arr[0];
    }

    private int siftUp(int i) {
        int  parentIndex = (i-1)/2;
        int parentValue = arr[parentIndex];

        if ( (isMaxHeap && parentValue < arr[i]) || (!isMaxHeap && parentValue > arr[i]) ) {
            arr[parentIndex] = arr[i];
            arr[i] = parentValue;
            return siftUp(parentIndex);
        } else {
            return i;
        }
    }

    public int removeRoot() {
        if (size == 0) throw new IndexOutOfBoundsException("Can't get root from the empty heap.");
        int v = root;

        size--;
        arr[0] = arr[size];
        siftDown(0);

        root = arr[0];
        return v;
    }

    public void siftDown(int i) {
        int li = i*2+1;
        if (li >= size) return; // the node is leaf

        int target = ( (isMaxHeap && arr[li] > arr[i]) || (!isMaxHeap && arr[li] < arr[i])) ? li : i;
        target = ((isMaxHeap && arr[li+1] > arr[target])|| (!isMaxHeap && arr[li+1] < arr[target])) ? li+1 : target;

        if (i == target) return; // no more deep down needed

        int v = arr[i];
        arr[i] = arr[target]; arr[target] = v;
        siftDown(target);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        int l = size-1;
        if (l >= 0) {
            for (int i = 0; i < l; i++) {
                s.append(arr[i]).append(", ");
            }
            s.append(arr[l]);
        }
        s.append("]");
        return s.toString();
    }

}

