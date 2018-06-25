package hackerrank.CrackingTheCodingInterview.easy;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static hackerrank.util.TestUtils.withStdStreams;
/**
 * Created by grigory@clearscale.net on 6/25/2018.
 */
public class Arrays_LeftRotation{

    public static class Solution {

        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            int k = in.nextInt();
            int a[] = new int[n];
            for(int a_i=0; a_i < n; a_i++){
                a[a_i] = in.nextInt();
            }

            k = k % n;
            int n_1 = n - 1;
            for(int a_i=0; a_i < n; a_i++){
                System.out.print("" + a[k]);
                if (a_i < n_1) System.out.print(" ");
                k++;
                if (k == n) k = 0;
            }
            System.out.println();
        }
    }

    @Test
    public void test1(){

        withStdStreams( (writer, reader) -> { try {

            writer.write("5 4\n");
            writer.write("1 2 3 4 5\n");
            writer.flush();

            Solution.main(new String[0]);

            String answer = reader.readLine();

            assertEquals("5 1 2 3 4", answer);

        } catch (IOException ioe) { throw new RuntimeException(ioe); } });

    }


}
