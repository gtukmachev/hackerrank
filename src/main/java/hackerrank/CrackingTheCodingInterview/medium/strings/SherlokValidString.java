package hackerrank.CrackingTheCodingInterview.medium.strings;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by grigory@clearscale.net on 8/4/2018.
 */
public class SherlokValidString {

    private static String isValid(String s) {

        // map letters to frequency:
        int[] frequincies = new int['z' - 'a' + 1];
        s.chars().forEach( ch -> frequincies[ch - 'a']++ );

        Map<Integer, Integer> frequenciesToLettersNember = new HashMap<>();
        Arrays.stream(frequincies)
                .filter( ff -> ff != 0)
                .forEach(ff -> frequenciesToLettersNember.merge( ff, 1, (i, j) -> i + j ));

        int size = frequenciesToLettersNember.size();

        if (size > 2) return "NO";
        if (size < 2) return "YES";


        Integer[] fr = new Integer[2];
        frequenciesToLettersNember.keySet().toArray(fr);

        Integer maxFr = fr[0] > fr[1] ? fr[0] : fr[1];
        Integer minFr = fr[0] > fr[1] ? fr[1] : fr[0];

        if (minFr == 1 && frequenciesToLettersNember.get(minFr) == 1) return "YES";

        if (maxFr - minFr != 1) return "NO";
        if (frequenciesToLettersNember.get(maxFr) != 1) return "NO";

        return "YES";
    }


    static void print(String s) {
        System.out.println(s);
    }

    public static class Tests {

        @Test public void t1() { String s = "qwerty"; assertEquals( "YES", isValid(s) ); }
        @Test public void t2() { String s = "qwertyy"; assertEquals( "YES", isValid(s) ); }
        @Test public void t3() { String s = "qwertyyy"; assertEquals( "NO", isValid(s) ); }
        @Test public void t4() { String s = "qwertyqwerty"; assertEquals( "YES", isValid(s) ); }
        @Test public void t5() { String s = ""; assertEquals( "YES", isValid(s) ); }
        @Test public void t6() { String s = "a"; assertEquals( "YES", isValid(s) ); }
        @Test public void t7() { String s = "qwertyaaaqwertybbb"; assertEquals( "NO", isValid(s) ); }

        @Test public void t13() throws IOException {
            Path path = Paths.get("c:/projects/own/hacker_rank_java8/src/main/java/hackerrank/CrackingTheCodingInterview/easy/strings/input13.txt");
            String line = Files.lines(path).findFirst().orElse("111222222222");

            assertEquals( "YES", isValid(line) );
        }

    }

}
