package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ex05_BalancedParantheses {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        ArrayDeque<String> leftSide = new ArrayDeque<>(); //lifo

        boolean isEqual = false;

        if (input.length() % 2 != 0) {
            System.out.println("NO");
            return;
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(' || input.charAt(i) == '['
                    || input.charAt(i) == '{') {
                leftSide.push(String.valueOf(input.charAt(i)));
            } else {
                String currentRight = String.valueOf(input.charAt(i));

                if (leftSide.isEmpty()) {
                    isEqual = false;
                    break;
                }

                if (leftSide.peek().equals("{") && currentRight.equals("}")) {
                    isEqual = true;
                    leftSide.pop();
                } else if (leftSide.peek().equals("(") && currentRight.equals(")")) {
                    isEqual = true;
                    leftSide.pop();
                } else if (leftSide.peek().equals("[") && currentRight.equals("]")) {
                    isEqual = true;
                    leftSide.pop();
                } else {
                    break;
                }
            }
        }
        if (isEqual == true) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}