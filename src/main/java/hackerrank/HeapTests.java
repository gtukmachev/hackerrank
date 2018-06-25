package hackerrank;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

/**
 * Created by grigory@clearscale.net on 5/14/2018.
 */
public class HeapTests {

    @Test public void HeapTestAdding() {
        IntHeap h = new IntHeap(true);

        h.add(10); assertEquals(h.size, 1);
        assertArrayEquals(Arrays.copyOf(h.arr, h.size), new int[]{10});

        h.add(15); assertEquals(h.size, 2);
        assertArrayEquals(Arrays.copyOf(h.arr, h.size), new int[]{15, 10});

        h.add(20); assertEquals(h.size, 3);
        assertArrayEquals(Arrays.copyOf(h.arr, h.size), new int[]{20, 10, 15});

        h.add(18); assertEquals(h.size, 4);
        assertArrayEquals(Arrays.copyOf(h.arr, h.size), new int[]{20, 18, 15, 10});

    }

    @Test public void testHeapGrowing() {
        IntHeap h = new IntHeap(true);
        for (int i = 1; i<1000; i++) {
            h.add(i);
            assertEquals(i, h.size);
        }
    }

    @Test public void testMaxRoot() {
        IntHeap h = new IntHeap(true);
        for (int i = 0; i < 50; i++) h.add(i);
        h.add(100);
        assertEquals(100, h.arr[0]);
    }

    @Test public void testMinRoot() {
        IntHeap h = new IntHeap(false);
        for (int i = 100; i > 10; i--) h.add(i);
        h.add(-1);
        assertEquals(-1, h.arr[0]);
    }

    @Test public void siftDownTest1() {

        int[] arr = new int[]{10,8,7,3,1,6,4};
        IntHeap h = new IntHeap(true, arr);
        assertArrayEquals(arr, Arrays.copyOf(h.arr, h.size));

        h.arr[0] = 2;
        h.siftDown(0);

        assertArrayEquals(new int[]{8,3,7,2,1,6,4}, Arrays.copyOf(h.arr, h.size));
    }

    @Test public void siftDownTest2() {
        int[] arr = new int[]{100,80,70,30,10,60,40};
        IntHeap h = new IntHeap(true, arr);
        assertArrayEquals(arr, Arrays.copyOf(h.arr, h.size));

        h.arr[0] = 64;
        h.siftDown(0);

        assertArrayEquals(new int[]{80,64,70,30,10,60,40}, Arrays.copyOf(h.arr, h.size));
    }

    @Test public void siftDownTest3() {
        int[] arr = new int[]{100,80,90,50,60,30,40};
        IntHeap h = new IntHeap(true, arr);
        assertArrayEquals(arr, Arrays.copyOf(h.arr, h.size));

        h.arr[0] = 60;
        h.siftDown(0);

        assertArrayEquals(new int[]{90,80,60,50,60,30,40}, Arrays.copyOf(h.arr, h.size));
    }

}
