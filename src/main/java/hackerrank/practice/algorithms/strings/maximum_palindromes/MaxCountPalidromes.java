package hackerrank.practice.algorithms.strings.maximum_palindromes;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Created by grigory@clearscale.net on 8/4/2018.
 */
public class MaxCountPalidromes {

    private static char[] s;
    private static Map<Pair, BigInteger> C_Cash = new HashMap<>();
    private static Map<Pair, BigInteger> Malt_Cash = new HashMap<>();
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

        private BiFunction<Byte, Integer, T> calculateChunk;
        private BiFunction<Integer, Integer, T> calculatePartialChunk;
        private BiFunction<T, T, T> merge;
        private Map<Integer, T>[] layers;
        private byte shift0; // the initial data splits to chunks in 2^shift0 (16 items for 4)
        private int chunkMaxOffset; // the initial data splits to chunks in 2^shift0 (16 items for 4)

        public BinaryTaskAggregator( byte shift0,
                BiFunction<Integer, Integer, T> calculatePartialChunk,
                BiFunction<Byte, Integer, T> calculateChunk,
                BiFunction<T, T, T> merge) {
            this.shift0 = shift0;
            this.chunkMaxOffset = (1 << shift0) - 1;
            this.calculatePartialChunk = calculatePartialChunk;
            this.calculateChunk = calculateChunk;
            this.merge = merge;
            int log2 = (int)(Math.log10(s.length) / Math.log10(2));
            this.layers = (Map<Integer, T>[])new Map[ log2 + 1 ];
        }

        public T get(int iFrom, int iTo) {
            if (iFrom > iTo) return null;

            int iFromChunk = iFrom >> shift0;
            int iToChunk   = iTo >> shift0;

            if (iFromChunk == iToChunk) return calculatePartialChunk.apply(iFrom, iTo);

            int iFromOffset = iFrom - (iFromChunk << shift0);
            int iToOffset   = iTo   - (iToChunk << shift0);

            T leftSolution;
            T centerSolution; int iFromNext, iToNext;
            T rightSolution;

            if (iFromOffset == 0)
                     { iFromNext = iFromChunk;     leftSolution = null;}
                else { iFromNext = iFromChunk + 1; leftSolution = calculatePartialChunk.apply(iFrom, (iFromNext << shift0)-1 ); }

            if (iToOffset == chunkMaxOffset)
                     { iToNext = iToChunk;     rightSolution = null; }
                else { iToNext = iToChunk - 1; rightSolution = calculatePartialChunk.apply( iToChunk << shift0, iTo ); }

            centerSolution = get(1, iFromNext, iToNext);

            T leftAndCenter = doMerge(leftSolution, centerSolution);
            T solution  = doMerge(leftAndCenter, rightSolution);

            return solution;
        }

        private T get(int layer, int iFrom, int iTo) {
            if (iFrom > iTo) return null;
            if (iFrom == iTo) return pull(layer, iFrom);

            T leftSolution;
            T centerSolution; int iFromNext, iToNext;
            T rightSolution;

            if (iFrom % 2 == 0) { iFromNext =  iFrom >> 1;       leftSolution = null;               }
                           else { iFromNext = (iFrom >> 1) + 1;  leftSolution = pull(layer, iFrom); }

            if (iTo % 2 == 0) { iToNext = (iTo >> 1) - 1; rightSolution = pull(layer, iTo); }
                         else { iToNext =  iTo >> 1;      rightSolution = null;             }

            centerSolution = get (layer + 1, iFromNext, iToNext);

            T leftAndCenter = doMerge(leftSolution, centerSolution);
            T solution  = doMerge(leftAndCenter, rightSolution);

            return solution;
        }

        private T pull(final int layer, int iFrom) {
            if (layers[layer] == null) {
                layers[layer] = new HashMap<>();
            }

            return layers[layer]
                    .computeIfAbsent(iFrom, i -> {
                        if (layer > 1) {
                            int prevLayer = layer - 1;
                            int prevLayerIndex = i << 1;
                            return doMerge(pull(prevLayer, prevLayerIndex), pull(prevLayer, prevLayerIndex + 1));
                        } else if (layer == 1) {
                            return calculateChunk.apply(shift0, i);
                        } else {
                            throw new RuntimeException("This is impossible!!! :-)");
                        }
                    });

        }

        private T doMerge(T left, T right) {
            if (left == null && right == null) return null;
            if (left == null)                  return right;
            if (right == null)                 return left;

            return merge.apply(left, right);
        }

    }


    static BinaryTaskAggregator<Map<Character, Integer>> lettersFrequencyAggregator;


    // Complete the initialize function below.
    private static void initialize(String s) {
        MaxCountPalidromes.s = s.toCharArray();
        lettersFrequencyAggregator = new BinaryTaskAggregator<Map<Character, Integer>>(
                (byte)4,
                (iFrom, iTo) -> {
                    Map<Character, Integer> ch = new HashMap<>(1);
                    for (int i = iFrom; i <= iTo; i++) {
                        ch.merge(MaxCountPalidromes.s[i], 1, (a, b) -> a + b);
                    }
                    return ch;

                },
                (shift0, chunkNumber) -> {
                    Map<Character, Integer> ch = new HashMap<>(1);
                    int iTo = ((chunkNumber+1) << shift0) - 1;
                    for (int i = chunkNumber << shift0; i <= iTo; i++) {
                        ch.merge(MaxCountPalidromes.s[i], 1, (a, b) -> a + b);
                    }
                    return ch;
                },
                (l, r) -> {
                    Map<Character, Integer> max, min;
                    if (l.size() > r.size()) { max = l; min = r; }
                    else { max = r; min = l; }

                    Map<Character, Integer> m = new HashMap<>(max);
                    for (Map.Entry<Character, Integer> e : min.entrySet()){
                        m.merge( e.getKey(), e.getValue(), (a,b) -> a + b );
                    }
                    return m;
                }

        );
    }

    // Complete the answerQuery function below.
    private static int answerQuery(final int l, final int r) {

        Map<Character, Integer> letters = lettersFrequencyAggregator.get(l-1, r-1);

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

        return C_Cash.computeIfAbsent( new Pair(n, k), p -> {
            BigInteger division = Mult(n-k, n);
            BigInteger devider = Mult(1, k);
            return division.divide( devider );
        });

    }

    private static BigInteger Mult(final int from, final int to) {
        return Malt_Cash.computeIfAbsent( new Pair(from, to), p -> {
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
        @Test public void tH28() throws IOException  {
            for (int n = 0; n<10; n++) {
                long start = System.nanoTime();
                for (int i = 0; i < 10; i++) testByIncomeFile(28);
                long finish = System.nanoTime();

                double seconds = (double) (finish - start) / 1000000000.0;
                print(String.format("%.2f", seconds));
            }

        }


        public void testByIncomeFile(int id) throws IOException {
            Iterator<String> input = Files.readAllLines( getFileForTest("tH" + id + "-input.txt") ).iterator();
            Iterator<String> output = Files.readAllLines( getFileForTest("tH" + id + "-output.txt") ).iterator();
            initialize(input.next());

            C_Cash = new HashMap<>();
            Malt_Cash = new HashMap<>();

            input.next(); // skip the 2nd line

            while (input.hasNext()) {
                try {
                    int[] in = Arrays.stream(input.next().split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    int out = Integer.parseInt(output.next());

/*
                    System.out.print("C:[" + C_Cash.size() + "] [M:" + Malt_Cash.size() + "] " +
                            "|" + in[0] + " " + in[1] + " -> (" + out + ") -> ");
*/
                    int r = answerQuery(in[0], in[1]);
//                    System.out.print(r);

                    Assert.assertEquals(out, r);
                } finally {
//                    System.out.println();
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
