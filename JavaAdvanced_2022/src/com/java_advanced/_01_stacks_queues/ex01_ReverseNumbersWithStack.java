package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ex01_ReverseNumbersWithStack {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split("\\s+");
//        ArrayDeque<String> stack = new ArrayDeque<>(Arrays.asList(input));
        ArrayDeque<String> stack = new ArrayDeque<>();

        for (String s : input) {
            stack.push(s);
        }


        while (!stack.isEmpty()) {
            System.out.printf("%s ", stack.pop());
        }
    }
}
