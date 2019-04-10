package Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ArrString {
    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String s = reader.readLine();
        String s = "мама мыла      раму";

        String[] parts = s.split(" ");

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].matches("\\W+")) {
                parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1) + " ";

                System.out.print(parts[i]);
            }
        }
    }
}