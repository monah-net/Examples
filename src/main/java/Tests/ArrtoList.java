package Tests;

import java.util.Arrays;
import java.util.Collections;

public class ArrtoList {
    public static void main(String[] args) {
        String[] array = {"bob","uncle","avreliy","zanuda","zuppa","banka","kafka"};
        Collections.sort(Arrays.asList(array));
        for (String s:array
             ) {
            System.out.println(s);
        }
    }
}
