package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ex02_BasicStackOps {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] nsx = scanner.nextLine().split("\\s+");

        int numOfElementsToPush = Integer.parseInt(nsx[0]);
        int numOfElementsToPop = Integer.parseInt(nsx[1]);
        String searched = nsx[2];

        ArrayDeque<String> stack = new ArrayDeque<>();
        String[] numbers = scanner.nextLine().split("\\s+");

        for (int i = 0; i < numOfElementsToPush; i++) {
            stack.push(numbers[i]);
        }

        for (int i = 0; i < numOfElementsToPop; i++) {
            stack.pop();
        }

        if (stack.contains(searched)) {
            System.out.println(true);
        } else {
            int min = Integer.MAX_VALUE;
            if (stack.isEmpty()) {
                min = 0;
            } else {
                while (!stack.isEmpty()) {
                    Integer pop = Integer.parseInt(stack.pop());
                    if (pop < min) {
                        min = pop;
                    }
                }
            }
            System.out.println(min);
        }
    }
}
