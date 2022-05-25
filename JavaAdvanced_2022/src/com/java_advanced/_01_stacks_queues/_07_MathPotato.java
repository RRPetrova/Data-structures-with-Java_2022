package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Scanner;

public class _07_MathPotato {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] children = scanner.nextLine().split("\\s+");
        int n = Integer.parseInt(scanner.nextLine());

        ArrayDeque<String> queue = new ArrayDeque<>();
        Collections.addAll(queue, children);

        int count = 1;
        while (queue.size() > 1) {
            for (int i = 1; i < n; i++) {
                queue.add(queue.poll());
            }

            if (isPrime(count)) {
                System.out.println("Prime " + queue.peek());
            } else {
                System.out.println("Removed " + queue.poll());
            }
            count++;
        }
        System.out.println("Last is " + queue.peek());
    }

    private static boolean isPrime(int count) {
        if (count <= 1) {
            return false;
        } else {
            for (int j = 2; j <= Math.sqrt(count); j++) {
                if (count % j == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}

