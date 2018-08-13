package hackerrank.practice.algorithms.graph_theory.bead_ornaments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by grigory@clearscale.net on 8/9/2018.
 */
public class BeadOrnaments {

    static final long MOD = (long)(1e18 + 7);
    static final int MAX_N = 10; // max number fof colors (sub-graphs with nodes of the same color)
    static final int MAX_B = 30; //max number of nodes in each color graph

    //static long[] factorial;
    static long[] variants;

    static {
        //factorial = buildFactorialsTable(MAX_B);
        variants = buildGraphVariantsTable(MAX_N);
    }

/*
    private static long[] buildFactorialsTable(int n) {
        long[] f = new long[n+1];
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            f[i] = ( f[i-1] * i ) % MOD;
        }
        return f;
    }
*/

    private static long[] buildGraphVariantsTable(int n) {
        long[] v = new long[n+1];
        v[0] = 1;
        v[1] = 1;
        v[2] = 1;
        for (int i = 3; i <= n; i++) {
            v[i] = i;
            for (int j = 2; j <= i - 2; j++) {
                v[i] = (v[i] * i) % MOD;
            }
        }
        return v;
    }

    static long beadOrnaments(int[] b) {
        if (b.length == 1) return variants[ b[1] ];
        long R = 1;


        for (int i = 0; i < b.length; i++) {
            long V = variants[ b[i] ];
            R = (((R * V) % MOD) * b[i]) % MOD;
        }

        return R;
    }

    public static class Tests {

        @Test public void tv1() { assertEquals(  1, beadOrnaments(new int[]{1})); }
        @Test public void tv2() { assertEquals(  1, beadOrnaments(new int[]{2})); }
        @Test public void tv3() { assertEquals(  3, beadOrnaments(new int[]{3})); }

        @Test public void tc2() { assertEquals(  beadOrnaments(new int[]{2}), beadOrnaments(new int[]{1,1})); }
        @Test public void tc3() { assertEquals(  beadOrnaments(new int[]{3}), beadOrnaments(new int[]{1,1,1})); }
        @Test public void tc5() { assertEquals(  beadOrnaments(new int[]{5}), beadOrnaments(new int[]{1,1,1,1,1})); }
        @Test public void tc10(){ assertEquals(  beadOrnaments(new int[]{10}),beadOrnaments(new int[]{1,1,1,1,1,1,1,1,1,1})); }


        @Test public void tm1() { assertEquals(2, beadOrnaments(new int[]{2,1})); }
        @Test public void tm5() { assertEquals(125, beadOrnaments(new int[]{1,1,1,1,1})); }

/*
        @Test
        public void t1() throws IOException {
            testByIncomeFile(1);
        }


        private void testByIncomeFile(int id) throws IOException {
            Iterator<String> input = Files.readAllLines(getFileForTest("t" + id + "-in.txt")).iterator();
            Iterator<String> output = Files.readAllLines(getFileForTest("t" + id + "-out.txt")).iterator();


        }


        private Path getFileForTest(String sufix) {
            return Paths.get("src/main/java/" + BeadOrnaments.class.getName().replace(".", "/") + "-" + sufix);
        }
*/
    }


}
