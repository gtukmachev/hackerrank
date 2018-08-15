package LeetCode.problems.medium.Surrounded_Regions;

import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/16/2018.
 */
public class SurroundedRegions_1 {

    static class P{
        final int l;
        final int c;
        P(int l, int c){
            this.l = l;
            this.c = c;
        }
    }

    static class Solver {
        int L;
        int L_1;

        int C;
        int C_1;

        char[][] board;
        LinkedList<P> queue = new LinkedList<>();

        Solver(char[][] board) {
            this.board = board;
            if (board == null || board.length == 0) return;

            this.L = board.length;
            this.L_1 = L - 1;

            this.C = board[0].length;
            this.C_1 = C - 1;

        }

        public void solve() {
            if (board == null || board.length == 0) return;


            for(int c = 0; c < C; c++){
                check(0,   c);
                check(L_1, c);
            }

            for(int l = 1; l < L_1; l++){
                check(l, 0);
                check(l, C_1);
            }

            for(int l = 0; l < L; l++)
                for(int c = 0; c < C; c++)
                    switch(board[l][c]) {
                        case 'O': board[l][c] = 'X'; break;
                        case '_': board[l][c] = 'O'; break;
                    }
        }

        void check(int l, int c) {
            if (board[l][c] != 'O') return;

            board[l][c] = '_';
            queue.add(new P(l,c));

            while (queue.size() > 0) {
                P p = queue.remove();
                add(p.l-1, p.c  );
                add(p.l  , p.c+1);
                add(p.l+1, p.c  );
                add(p.l  , p.c-1);
            }
        }

        void add(int l, int c) {
            if (l < 0 || c < 0 || l > L_1 || c > C_1) return;
            if (board[l][c] == 'O') {
                board[l][c] = '_';
                queue.add(new P(l,c));
            }
        }

    }

    public void solve(char[][] board) {
        new Solver(board).solve();
    }


    public static class Tests{

        SurroundedRegions_1 s = new SurroundedRegions_1();

        @Test public void t1() {
            char[][] solved = b(
                    "XXXX",
                    "XOOX",
                    "XXOX",
                    "XOXX");
            char[][] answer = b(
                    "XXXX",
                    "XXXX",
                    "XXXX",
                    "XOXX");

            s.solve(solved);


            assertThat( solved, is(answer)  );
        }


        char[][] b(String... s){
            char[][] r = new char[s.length][s[0].length()];

            for (int l = 0; l < s.length; l++) {
                String chars = s[l];
                for (int c = 0; c < chars.length(); c++) {
                    r[l][c] = chars.charAt(c);

                }
            }
            return r;
        }

    }

}
