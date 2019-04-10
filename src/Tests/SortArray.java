package Tests;

import com.sun.tools.javac.util.ArrayUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.random;

public class SortArray {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] array = {200,300,400,500,700,900,1,2,3,5,7,23,44,45,59,99,93,94};
        int temp;
//        for (int i = 0; i < array.length; i++) {
//            temp = Integer.parseInt(String.valueOf(Math.random()).substring(2,5));
//            array[i] = temp;
//            System.out.println(array[i]);
//        }
        System.out.println("======================Sort of array=====================");
        sort(array);

        System.out.println(array[0]);
        System.out.println(array[1]);
        System.out.println(array[2]);
        System.out.println(array[3]);
        System.out.println(array[4]);
    }

    public static void sort(int[] array) {
        int temp = 0;
        for (int i = 0; i < array.length-1; i++) {
            for (int j = i; j < array.length; j++) {
                if(array[i] < array[j]){
                    temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
    }
}