package hackerrank;

import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Created by grigory@clearscale.net on 6/20/2018.
 */
public class ConnectedCellInAGrid {

    private static int[] around = new int[]{-1,-1, -1,0, -1,1,  0,-1, 0,1,  1,-1, 1,0, 1,1};
    private static int[] stack;
    private static int stackDeep;
    private static int lines;
    private static int columns;

    private static void forAroundCells(int l, int c, BiConsumer<Integer, Integer> f) {
        for (int i=0; i < around.length; i += 2) {
            f.accept(l + around[i], c + around[i+1]);
        }
    }

    private static int calcGroupsSize(int[][] grid){
        int size = 0;
        while (stackDeep > 0) {
            size++;
            int c = stack[--stackDeep];
            int l = stack[--stackDeep];

            forAroundCells(l,c, (ln, cn) -> {
                if ( ln >= 0 && ln < lines && cn >= 0 && cn < columns && grid[ln][cn] == 1) {
                    stack[stackDeep++] = ln; stack[stackDeep++] = cn;
                    grid[ln][cn] = -1;
                }
            });

        }
        return size;
    }

    private static int maxRegion(int[][] grid) {
        int maxSize = 0;

        lines = grid.length;
        columns = grid[0].length;

        stack = new int[lines*columns*2];
        stackDeep = 0;

        for (int l = 0; l < lines; l++) {
            for (int c = 0; c < columns; c++) {
                if (grid[l][c] == 1) {
                    stack[stackDeep++] = l; stack[stackDeep++] = c;
                    grid[l][c] = -1;
                    int size = calcGroupsSize(grid);
                    if (size > maxSize) maxSize = size;
                }
            }
        }
        return maxSize;
    }


    public static void main(String[] args) {

        int[][] g = new int[5][5];

        g[1][1] = 1; g[1][2] = 1; g[2][3] = 1;
        g[4][0] = 1; g[4][1] = 1;


        for (int i = 0; i < g.length; i++) {
            System.out.println(Arrays.toString(g[i]));
        }

        int n = maxRegion(g);

        System.out.println(n);
        for (int i = 0; i < g.length; i++) {
            System.out.println(Arrays.toString(g[i]));
        }

    }

}
