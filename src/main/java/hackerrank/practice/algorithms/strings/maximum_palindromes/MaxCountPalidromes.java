package hackerrank.practice.algorithms.strings.maximum_palindromes;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

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

    private static class BinaryTaskAggregator<T> {

        private Function<T, T> merge;
        private Function<Integer, T> calculate;

        public BinaryTaskAggregator(Function<T, T> merge, Supplier<T> calculate) {
            this.merge = merge;
            this.calculate = calculate;
        }

        private List<Map<Integer, T>> layers = new ArrayList<>();

        private T get(int layer, int iFrom, int iTo) {
            if (iFrom > iTo) return null;
            if (iFrom == iTo) return pull(layer, iFrom);

            T leftSolution;
            T centerSolution; int iFromNext, iToNext;
            T rightSolution;

            if (iFrom % 2 == 0) { iFromNext = iFrom / 2;      leftSolution = null;               }
                           else { iFromNext = iFrom / 2 + 1;  leftSolution = pull(layer, iFrom); }

            if (iTo % 2 == 0) { iToNext = iTo / 2 - 1; rightSolution = pull(layer, iTo); }
                         else { iToNext = iTo / 2;     rightSolution = null;             }

            centerSolution = get (layer + 1, iFromNext, iToNext);

            T leftAndCenter = doMerge(leftSolution, centerSolution);
            T soltion  = doMerge(leftSolution, centerSolution);
        }

        private T pull(int layer, int iFrom) {
            if (layer == layers.size()) {
                layers.add(new HashMap<>());
            }

            return layers
                    .get(layer)
                    .computeIfAbsent(iFrom, calculate);

        }

    }


    // Complete the initialize function below.
    private static void initialize(String s) {
        MaxCountPalidromes.s = s.toCharArray();

    }

    // Complete the answerQuery fu nction below.
    private static int answerQuery(final int l, final int r) {
        Map<Character, Integer> letters = new HashMap<>();

        for ( int i=l-1; i < r; i++ ) {
            letters.merge(s[i], 1, (prev, add) -> prev + add );
        }

        if (letters.size() == 1) return 1;

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

        @Test public void tH2() throws IOException  { testByIncomeFile(2); }
        @Test public void tH28() throws IOException  { testByIncomeFile(28); }


        public void testByIncomeFile(int id) throws IOException {
            Iterator<String> input = Files.readAllLines( getFileForTest("tH" + id + "-input.txt") ).iterator();
            Iterator<String> output = Files.readAllLines( getFileForTest("tH" + id + "-output.txt") ).iterator();
            initialize(input.next());

            input.next(); // skip the 2nd line

            while (input.hasNext()) {
                try {
                    int[] in = Arrays.stream(input.next().split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    int out = Integer.parseInt(output.next());

                    System.out.print("C:[" + C_Caash.size() + "] [M:" + Mult_Chash.size() + "] " +
                            "|" + in[0] + " " + in[1] + " -> (" + out + ") -> ");
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
