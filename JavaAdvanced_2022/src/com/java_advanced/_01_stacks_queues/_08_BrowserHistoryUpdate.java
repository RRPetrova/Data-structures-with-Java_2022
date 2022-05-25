package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class _08_BrowserHistoryUpdate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        ArrayDeque<String> urls = new ArrayDeque<>();
        ArrayDeque<String> queue = new ArrayDeque<>();

        while (!input.equals("Home")) {
            if (input.equals("back")) {
                if (urls.size() < 2) {
                    System.out.println("no previous URLs");
                } else {
                    queue.addFirst(urls.pop());
                    System.out.println(urls.peek());
                }
            } else if (input.equals("forward")) {
                if (queue.size() <1) {
                    System.out.println("no next URLs");
                } else {
                    System.out.println(queue.peek());
                    urls.push(queue.pop());
                }
            } else {

                System.out.println(input);
                urls.push(input);
                queue.clear();
            }

            input = scanner.nextLine();
        }
    }


}

