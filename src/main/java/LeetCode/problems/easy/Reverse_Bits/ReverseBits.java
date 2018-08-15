package LeetCode.problems.easy.Reverse_Bits;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by grigory@clearscale.net on 8/15/2018.
 */
public class ReverseBits {

    private static final int[] l = new int[256];
    static {
        for (int b = 0; b <= 255; b++) {
            l[b] = reverseByte(b);
        }
    }

    private static int reverseByte(int b) {
        int a = 0;
        for (int i=0; i < 8; i++) {
            a = (a << 1) + (b % 2);
            b = b >> 1;
        }
        return a;
    }

    public int reverseBits(int n) {
        int b1 = (n & 0b11111111000000000000000000000000) >>> 24;
        int b2 = (n & 0b00000000111111110000000000000000) >>> 16;
        int b3 = (n & 0b00000000000000001111111100000000) >>> 8;
        int b4 = (n & 0b00000000000000000000000011111111);
        return l[b1] | (l[b2] << 8) | (l[b3] << 16) | (l[b4] << 24);
    }


    public static class Tests {
        ReverseBits s = new ReverseBits();


        @Test public void t1(){
            //                 1111----2222----3333----4444----                    ----4444----3333----2222----1111
            assertEquals(bin(0b00000000000000000000000000010000), bin(s.reverseBits(0b00001000000000000000000000000000)));
        }

        @Test public void t2(){
            //                 1111----2222----3333----4444----                       ----4444----3333----2222----1111
            assertEquals(bin(0b11000000110000001100000011000000), bin(s.reverseBits(0b00000011000000110000001100000011)));
        }

        @Test public void t3(){
            //                 1111----2222----3333----4444----                       ----4444----3333----2222----1111
            assertEquals(bin(0b11111111111111111111111111111111), bin(s.reverseBits(0b11111111111111111111111111111111)));
        }

        @Test public void t4(){
            //                 1111----2222----3333----4444----                       ----4444----3333----2222----1111
            assertEquals(bin(0b00000000000000000000000011111111), bin(s.reverseBits(0b11111111000000000000000000000000)));
        }

        @Test public void t5(){
            //                 1111----2222----3333----4444----                       ----4444----3333----2222----1111
            assertEquals(bin(0b11111111000000000000000000000000), bin(s.reverseBits(0b00000000000000000000000011111111)));
        }

        private final String bin(int v){
            String s = Integer.toBinaryString(v);
            while (s.length() < 32) s = '0' + s;
            StringBuilder out = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                out.append(s.substring(i*4, (i+1)*4)).append( i % 2 == 0 ? '.' : ' ');
            }
            return out.toString();
        }

    }

}
