package hackerrank.practice.algorithms.strings.maximum_palindromes;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by grigory@clearscale.net on 8/4/2018.
 */
public class MaxCountPalidromes {

    private static char[] s;
    private static Map<Pair, BigInteger> C_Caash = new HashMap<>();
    private static Map<Pair, BigInteger> Mult_Chash = new HashMap<>();

    private static class Pair {
        final int f;
        final int s;

        public Pair(int f, int s) {
            this.f = f;
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return f == pair.f &&
                    s == pair.s;
        }

        @Override
        public int hashCode() {
            return Objects.hash(f, s);
        }
    }

    // Complete the initialize function below.
    private static void initialize(String s) {
        MaxCountPalidromes.s = s.toCharArray();

    }

    // Complete the answerQuery function below.
    private static int answerQuery(final int l, final int r) {
        Map<Character, Integer> letters = new HashMap<>();

        for ( int i=l-1; i < r; i++ ) {
            letters.merge(s[i], 1, (prev, add) -> prev + add );
        }

        List<Integer> pairs = new ArrayList<>(letters.size() / 2);
        int noPairsCount = 0;

        for (Integer fr : letters.values()) {
            int p = fr >> 1;
            if (p > 0) { pairs.add(p); }
            noPairsCount += fr - (p << 1);
        }

        if (pairs.size() == 0) return noPairsCount;

        BigInteger res = BigInteger.ONE;
        int n = pairs.stream().mapToInt(i -> i).sum();

        for(int k : pairs) {
            BigInteger c = C(n, k);
            res = res.multiply( c );
            n -= k;
        }

        if (noPairsCount > 0) {
            res = res.multiply( BigInteger.valueOf(noPairsCount) );
        }

        return res.mod( BigInteger.valueOf(1_000_000_007) ).intValue();
    }

    /**
     *
     *
     * @param n int
     * @param k int
     * @return
     *        n!
     *   -----------
     *   k! * (n-k)!

     */
    private static BigInteger C(final int n, final int k) {
        if (n == k) return BigInteger.ONE;

        return C_Caash.computeIfAbsent( new Pair(n, k), p -> {
            BigInteger division = Mult(n-k, n);
            BigInteger devider = Mult(1, k);
            return division.divide( devider );
        });

    }

    private static BigInteger Mult(final int from, final int to) {
        return Mult_Chash.computeIfAbsent( new Pair(from, to), p -> {
            int current = from;
            BigInteger res = BigInteger.valueOf(current);
            while (current < to) {
                current += 1;
                res = res.multiply( BigInteger.valueOf(current));
            }
            return res;
        });
    }


    public static class Tests {

        @Test public void t1() { Assert.assertEquals(1, getMax("a")); }
        @Test public void t2() { Assert.assertEquals(2, getMax("ab")); }
        @Test public void t3() { Assert.assertEquals(1, getMax("aa")); }
        @Test public void t4() { Assert.assertEquals(1, getMax("aaa")); }
        @Test public void t5() { Assert.assertEquals(1, getMax("aaaa")); }
        @Test public void t6() { Assert.assertEquals(2, getMax("aabb")); }
        @Test public void t7() { Assert.assertEquals(2, getMax("aabbc")); }
        @Test public void t8() { Assert.assertEquals(4, getMax("aabbcd")); }

        @Test public void tH2() throws IOException {
            Iterator<String> input = Files.readAllLines( getFileForTest("tH2-input.txt") ).iterator();
            Iterator<String> output = Files.readAllLines( getFileForTest("tH2-output.txt") ).iterator();
            initialize(input.next());

            input.next(); // skip the 2nd line

            while (input.hasNext()) {
                try {
                    int[] in = Arrays.stream(input.next().split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    int out = Integer.parseInt(output.next());

                    System.out.print("" + in[0] + " " + in[1] + " -> (" + out + ") -> ");
                    int r = answerQuery(in[0], in[1]);
                    System.out.print(r);

                    Assert.assertEquals(out, r);
                } finally {
                    System.out.println();
                }

            }
        }


        private Path getFileForTest(String sufix) {
            return Paths.get("src/main/java/" + MaxCountPalidromes.class.getName().replace(".", "/") + "-" + sufix);
        }

        private static void print(String s) {
            System.out.println(s);
        }

        private static int getMax(String s) {
            initialize(s);
            int r = answerQuery(1, s.length());
            print(s + " -> " + r);
            return r;
        }

    }


}
