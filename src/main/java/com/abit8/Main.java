package com.abit8;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int[] a = {1, 0, 2, 3, 0, 4, 5, 0};
        int[][] model = new int[3][3];

        System.out.println(Arrays.toString(duplicateZeros(a)));
        System.out.println(Arrays.toString(model[0]));
    }

    public static int[] duplicateZeros(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {

                for (int j = arr.length - 2; j >= i + 1; j--) {
                    arr[j + 1] = arr[j];
                }
                arr[i+1] = 0;
                i++;
            }
        }
        return arr;
    }
}