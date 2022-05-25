package com.java_advanced._02_multidimensional_arrays;

import java.util.Scanner;

public class _01_CompareMatrices {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] size1 = scanner.nextLine().split("\\s+");
        Integer[][] matrix1 = new Integer[Integer.parseInt(size1[0])][Integer.parseInt(size1[1])];
        fillMatrix(scanner, matrix1);

        String[] size2 = scanner.nextLine().split("\\s+");
        Integer[][] matrix2 = new Integer[Integer.parseInt(size2[0])][Integer.parseInt(size2[1])];
        fillMatrix(scanner, matrix2);


        if (compareMatrix(matrix1, matrix2)) {
            System.out.println("equal");
        } else {
            System.out.println("not equal");
        }
    }

    private static boolean compareMatrix(Integer[][] matrix1, Integer[][] matrix2) {
            if (matrix1.length != matrix2.length) {
                return false;
            }

            for (int row = 0; row < matrix1.length; row++) {
                for (int col = 0; col < matrix1[row].length; col++) {
                    if (matrix1[row][col] != matrix2[row][col]) {
                        return false;
                    }
                }
            }
            return true;
        }



    private static void fillMatrix(Scanner scanner, Integer[][] matrix1) {
        for (int row = 0; row < matrix1.length; row++) {
            String[] input = scanner.nextLine().split("\\s+");
            for (int col = 0; col < matrix1[row].length; col++) {
                matrix1[row][col] = Integer.parseInt(input[col]);
            }
        }
    }
}
