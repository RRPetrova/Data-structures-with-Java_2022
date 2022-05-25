package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ex04_BasicQueueOps {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] nsx = scanner.nextLine().split("\\s+");

        int elemToAdd = Integer.parseInt(nsx[0]);
        int elemToRemove = Integer.parseInt(nsx[1]);
        int searched = Integer.parseInt(nsx[2]);

        String[] s = scanner.nextLine().split("\\s+");
        ArrayDeque<Integer> nums = new ArrayDeque<>();

        for (int i = 0; i < elemToAdd; i++) {
            nums.offer(Integer.parseInt(s[i]));
        }

        for (int i = 0; i < elemToRemove; i++) {
            nums.poll();
        }
        if (nums.contains(searched)) {
            System.out.println(true);
        } else {
            int min = Integer.MAX_VALUE;
            if (nums.isEmpty()) {
                min = 0;
            } else {
                while (!nums.isEmpty()) {
                    int curr = nums.peek();
                    if (curr < min) {
                        min = nums.poll();
                    }
                }
            }
            System.out.println(min);
        }
    }


}
