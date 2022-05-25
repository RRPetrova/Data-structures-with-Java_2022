package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class _05_PrinterQueue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        ArrayDeque<String> queue = new ArrayDeque<>();

        String curr = null;
        while (!input.equals("print")) {
            curr = queue.peek();
            if (curr == null && input.equals("cancel")) {
                System.out.println("Printer is on standby");
            } else if (input.equals("cancel")){
                System.out.println("Canceled " + queue.pop());
            } else {

                queue.offer(input);
            }
           input = sc.nextLine();
        }
while (!queue.isEmpty()) {
    System.out.println(queue.pop());
}

    }
}
