package Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CharArrString {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        char[] sA = s.toCharArray();
        if (sA[0] != ' ') sA[0] = Character.toUpperCase(sA[0]);
        for (int i = 0; i < sA.length - 1; i++) {
            if (sA[i] == ' ' && sA[i + 1] != ' ') sA[i + 1] = Character.toUpperCase(sA[i + 1]);
        }
        System.out.println(sA);
    }
}
