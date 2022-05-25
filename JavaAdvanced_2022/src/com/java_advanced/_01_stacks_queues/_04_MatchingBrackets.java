package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class _04_MatchingBrackets {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                stack.push(i);
            } else if (input.charAt(i) == ')') {
                int startIndex = stack.pop();
                String substring = input.substring(startIndex, i + 1);
                System.out.println(substring);
            }

        }
    }
}