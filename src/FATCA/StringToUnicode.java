package FATCA;

import java.io.UnsupportedEncodingException;

public class StringToUnicode {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String original = "my simple text";
        char[] characters = original.toCharArray();
        for (int i = 0; i < characters.length ; i++) {
            char c = characters[i];
            System.out.println(c + " \\u" + (int) c);
        }
    }
}