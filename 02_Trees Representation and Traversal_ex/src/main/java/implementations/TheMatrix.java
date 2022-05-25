package implementations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        fillMatrix(this.startRow, this.startCol);
    }

    private void fillMatrix(int row, int col) {

        if (row >= this.matrix.length || row < 0 || col < 0 || col >= this.matrix[row].length
                || this.matrix[row][col] != this.toBeReplaced) {
            return;
        }

        this.matrix[row][col] = this.fillChar;

        this.fillMatrix(row + 1, col);
        this.fillMatrix(row - 1, col);
        this.fillMatrix(row, col + 1);
        this.fillMatrix(row, col - 1);
    }

    public String toOutputString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < this.matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                sb.append(this.matrix[row][col]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
