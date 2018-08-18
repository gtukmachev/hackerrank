package LeetCode.problems.medium.Surrounded_Regions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by grigory@clearscale.net on 8/16/2018.
 */
public class SurroundedRegions_2 {

    static class P{
        final int l;
        final int c;
        P next;

        P(int l, int c) {
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
        P queueHead = null;
        P queueLast = null;

        Solver(char[][] board) {
            this.board = board;
            if (board == null || board.length == 0) return;

            this.L = board.length;
            this.L_1 = L - 1;

            this.C = board[0].length;
            this.C_1 = C - 1;
        }

        public void solve() {
            if (board == null || board.length == 0 || C == 0) return;
            if (L < 3 || C < 3) return;


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
            queueHead = new P(l,c);
            queueLast = queueHead;

            while (queueHead != null) {
                P p = queueHead;
                add(p.l-1, p.c  );
                add(p.l  , p.c+1);
                add(p.l+1, p.c  );
                add(p.l  , p.c-1);
                queueHead = p.next;
            }
        }

        void add(int l, int c) {
            if (l < 0 || c < 0 || l > L_1 || c > C_1) return;
            if (board[l][c] == 'O') {
                board[l][c] = '_';
                queueLast.next = new P(l,c);
                queueLast = queueLast.next;
            }
        }

    }

    public void solve(char[][] board) {
        new Solver(board).solve();
    }


    public static class Tests{

        SurroundedRegions_2 s = new SurroundedRegions_2();

        @Test public void t0() {
            char[][] solved = b("O");
            char[][] answer = b("O");
            s.solve(solved);
            assertThat( solved, is(answer)  );
        }

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

        @Test public void t2() {
            char[][] solved = b("XOOXXOOXXOOXXOOXXOOXXOOX");
            char[][] answer = b("XOOXXOOXXOOXXOOXXOOXXOOX");
            s.solve(solved);
            assertThat( solved, is(answer)  );
        }

        @Test public void t3() {
            char[][] solved = b("XOOXXOOXXOOXXOOXXOOXXOOX", "XOOXXOOXXOOXXOOXXOOXXOOX");
            char[][] answer = b("XOOXXOOXXOOXXOOXXOOXXOOX", "XOOXXOOXXOOXXOOXXOOXXOOX");
            s.solve(solved);
            assertThat( solved, is(answer)  );
        }

        @Test public void t4() {
            char[][] solved = b("X", "O", "X", "O", "X", "O", "X", "O", "X", "O");
            char[][] answer = b("X", "O", "X", "O", "X", "O", "X", "O", "X", "O");
            s.solve(solved);
            assertThat( solved, is(answer)  );
        }

        @Test public void t5() {
            char[][] solved = b("XX", "XO", "XX", "OX", "XX", "OX", "XX", "OX", "XX", "OX");
            char[][] answer = b("XX", "XO", "XX", "OX", "XX", "OX", "XX", "OX", "XX", "OX");
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
