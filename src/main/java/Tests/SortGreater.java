package Tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class SortGreater {
    public static void main(String[] args) throws Exception {
//        ..BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] array = new String[20];
        array = new String[]{"WordWordWord", "A", "Cd", "B", "Def", "efgh", "Zxyatrteer", "PPERET"};
//        for (int i = 0; i < array.length; i++) {
//            array[i] = reader.readLine();
//    }
        sort(array);
        for (String x : array) {
            System.out.println(x);
        }
    }

    public static void sort(String[] array) {
        Collections.sort(Arrays.asList(array));
        String temp;
        for (int i = 0; i < array.length-2; i++) {
                if (isGreaterThan(array[i], array[i + 1])) {
                    temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;

                }
        }
    }

    //Метод для сравнения строк: 'а' больше чем 'b'
    public static boolean isGreaterThan(String a, String b) {
        return a.compareTo(b) > 0;
    }
}
