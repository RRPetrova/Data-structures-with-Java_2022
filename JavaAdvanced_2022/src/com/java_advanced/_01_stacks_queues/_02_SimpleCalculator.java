package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class _02_SimpleCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] input = sc.nextLine().split("\\s+");

        ArrayDeque<String> stack = new ArrayDeque<>(Arrays.asList(input));

        while (stack.size() > 1) {
            int first = Integer.parseInt(stack.pop());
            String operator = stack.pop();
            int second = Integer.parseInt(stack.pop());

            if (operator.equals("+")) {
                stack.push(String.valueOf(first + second));
            } else {
                stack.push(String.valueOf(first - second));
            }

        }
        System.out.println(stack.peek());
    }
}
