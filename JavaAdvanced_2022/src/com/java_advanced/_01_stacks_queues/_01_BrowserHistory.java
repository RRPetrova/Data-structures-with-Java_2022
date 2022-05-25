package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class _01_BrowserHistory {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        ArrayDeque<String> urls = new ArrayDeque<>();

        String currURL = "";

        while (!input.equals("Home")) {
            if (input.equals("back")) {
                if (urls.size() < 2) {
                    System.out.println("no previous URLs");
                } else {
                    currURL = urls.pop();

                    System.out.println(urls.peek());
                }
            } else {
                urls.push(input);
                System.out.println(urls.peek());
            }

            input = scanner.nextLine();
        }
    }

}


