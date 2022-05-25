package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class _06_HotPotato {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] kids = sc.nextLine().split("\\s+");
        int n = Integer.parseInt(sc.nextLine());

        ArrayDeque<String> queue = new ArrayDeque<>();
        for (String kid : kids) {
            queue.offer(kid);
        }

        while (queue.size() > 1) {
            for (int i = 1; i < n; i++) {
queue.add(queue.poll());
            }
            System.out.println("Removed "+queue.poll());
        }
        System.out.println("Last is " + queue.pop());

    }
}
