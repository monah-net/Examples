package Tests;

import java.util.HashSet;
import java.util.Set;

public class StringToArray {
    public static void main(String[] args) {
        String symbols = "!|@|#|$|^|&|*|(|)|\\||";
        String[] array = symbols.split("\\|");
        Set <String> set = new HashSet<>();
        for (String res:array
             ) {
            System.out.println(res);
        }
    }
}
