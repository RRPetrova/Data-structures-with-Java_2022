package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ex06_RecursiveFibonacci {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = Integer.parseInt(scanner.nextLine()) ;
//
//        System.out.println(getFibo(n+1));
//    }
//
//    private static long  getFibo(int n) {
//            if (n <= 1) {
//                return n;
//            } else {
//                return getFibo(n-1 ) + getFibo(n - 2);
//            }
//    }
        Scanner scanner = new Scanner(System.in);

        long n = Long.parseLong(scanner.nextLine());
        ArrayDeque<Long> leftSide = new ArrayDeque<>(); //lifo

        long first = 1;
        long last = 1;
        leftSide.add(first);
        leftSide.add(last);
        for (int i = 1; i < n; i++) {
            leftSide.add(first + last);     // 1 1  2   3   5
            leftSide.remove();
            first = leftSide.peek();
            last = leftSide.peekLast();
        }

        System.out.println(leftSide.peekLast());
    }
}
