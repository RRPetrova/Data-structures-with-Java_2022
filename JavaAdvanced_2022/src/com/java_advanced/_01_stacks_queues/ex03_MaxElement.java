package com.java_advanced._01_stacks_queues;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ex03_MaxElement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());

        ArrayDeque<Integer> nums = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            String[] cmd = scanner.nextLine().split("\\s+");
            if (cmd[0].equals("2")) {
                nums.pop();
            } else if (cmd[0].equals("3")) {
                int max = Integer.MIN_VALUE;
                Object[] array = nums.toArray();

                for (int j = 0; j < array.length; j++) {
                    if ((Integer)array[j] > max) {
                        max = (Integer) array[j];
                    }
                }
                System.out.println(max);

                //  System.out.println(maxElement(nums));

            } else if (cmd[0].equals("1")){

                nums.push(Integer.parseInt(cmd[1]));
            }

        }




    }

//    private static int maxElement(ArrayDeque<Integer> nums) {
//        int max = Integer.MIN_VALUE;
//        while (!nums.isEmpty()) {
//            int curr = nums.peek();
//            if (curr > max) {
//                max = curr;
//            }
//            nums.pop();
//        }
//        return max;
//    }
}
