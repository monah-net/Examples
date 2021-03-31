package CodeWritingExercises;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ArraysTest {
    public static void main(String[] args) {
        int[] first = {2,3,4,5,6,7,8,9,10,111,1112,11113,14,15,16,17,18,19,20,21};
        System.out.println(first[0]);
        ArrayList <String> second = new ArrayList();
        for (int i = 0; i < first.length; i++) {
            for (int j = 1; j <= 1000; j++) {
                second.add(String.valueOf(first[i]) + j);
            }
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("/Users/MacbookPro/Desktop/ApacheExcelFiles/test.txt");
            for (String s:second
            ) {
                writer.write(s);
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
