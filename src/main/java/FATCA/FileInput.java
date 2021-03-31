package FATCA;

import java.io.*;
import java.util.*;

public class FileInput {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("/Users/MacbookPro/Desktop/WORK/Input_code_files/Tax_Reference_numbers_07032018");
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text);
        }
        FileReader fileReader2 = new FileReader("/Users/MacbookPro/Desktop/WORK/Input_code_files/Tax_Reference_numbers_07032019");
        BufferedReader reader2= new BufferedReader(fileReader2);
        while ((text = reader2.readLine()) != null){
            list2.add(text);
        }
        System.out.println(list);
        System.out.println(list2);
        list.removeAll(list2);
        if(list.size() == 0){
            System.out.println("Arrays are identical");
        }else {
            System.out.println("Arrays are not identical");
            System.out.println("Next strings have differences:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
    }
}
