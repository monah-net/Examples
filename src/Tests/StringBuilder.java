package Tests;

import java.util.Arrays;
import java.util.Collections;

public class StringBuilder {
    public static void main(String[] args) {
        boolean result = false;
        String inputString = "aabaa";
        char[] characters = inputString.toCharArray();
        String output = "";
        for (int i = characters.length - 1; i >= 0; i--)output += characters[i];
        if(inputString.equals(output))result = true;
        System.out.println(result);
    }
}