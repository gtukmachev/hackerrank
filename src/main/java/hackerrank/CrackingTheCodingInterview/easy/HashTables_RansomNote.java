package hackerrank.CrackingTheCodingInterview.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by grigory@clearscale.net on 6/19/2018.
 */
public class HashTables_RansomNote {

    static void checkMagazine(String[] magazine, String[] note) {
        if (note == null || note.length == 0) { System.out.print("Yes"); return; }
        if (magazine == null || magazine.length < note.length) { System.out.print("No"); return; }

        Map<String, Integer> words = new HashMap<>();
        Arrays.stream(note).forEach( word -> words.compute(word, (k,v) -> v == null ? 1 : (v+1) ) );

        for (int i = 0; i < magazine.length && words.size() > 0; i++ ) {
            String w = magazine[i];
            words.computeIfPresent(w, (k,v) -> v == 1 ? null : (v-1) );
        }

        System.out.print( words.size() == 0 ? "Yes" : "No");
    }

    public static void main(String[] args) {
        checkMagazine(
                new String[]{"world", "Hello"},
                new String[]{"Hello", "world"}
        );
    }


}
