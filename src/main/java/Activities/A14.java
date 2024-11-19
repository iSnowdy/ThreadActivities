package Activities;

// Given two 3x3 arrays, calculate their multiplication
// Each thread calculates one element of the resulting matrix

class ArrayMultiplicator extends Thread {
    private final int[] row;
    private final int[] col;
    private int result = 0;

    public ArrayMultiplicator(final int[] row, final int[] col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        for (int i = 0; i < row.length; i++) {
            result += row[i] * col[i];
        }
    }

    public int getResult() {
        return result;
    }
}

public class A14 {
    public static void main(String[] args) throws InterruptedException {
        final int[][] FIRST_ARRAY = {
                {2, 5, 1},
                {4, -2, 0},
                {1, 6, 2}
        };
        final int[][] SECOND_ARRAY = {
                {1, 2, 3},
                {3, 4, 1},
                {1, -4, 2}
        };

        final int ROWS = FIRST_ARRAY.length;
        final int COLS = SECOND_ARRAY[0].length;
        final int[][] RESULT_ARRAY = new int[ROWS][COLS];

        // Each thread will be represented just like the array
        ArrayMultiplicator[][] threads = new ArrayMultiplicator[ROWS][COLS];

        // Start threads
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int[] row = FIRST_ARRAY[i];
                int[] col = getColumn(SECOND_ARRAY, j);
                threads[i][j] = new ArrayMultiplicator(row, col);
                threads[i][j].start();
            }
        }
        // Wait
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                threads[i][j].join();
                RESULT_ARRAY[i][j] = threads[i][j].getResult();
            }
        }
        // Print the resulting array
        printArray(RESULT_ARRAY);
    }

    // Helper method to extract a column from an array
    private static int[] getColumn(int[][] array, int colIndex) {
        int[] column = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            column[i] = array[i][colIndex];
        }
        return column;
    }
    // Print array
    private static void printArray(int[][] array) {
        System.out.println("Resulting Array:");
        for (int[] row : array) {
            for (int value : row) {
                System.out.print(value + "\t"); // \t TAB
            }
            System.out.println();
        }
    }
}