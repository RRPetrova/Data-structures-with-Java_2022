package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class _03_DecimalToBinary {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int input = Integer.parseInt(scanner.nextLine());
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        if (input == 0) {
            System.out.println(0);

        } else {
            while (input != 0) {
                stack.push(input % 2);
                input /= 2;
            }
            while (!stack.isEmpty()) {
                System.out.print(stack.poll());
            }
        }

    }
}
