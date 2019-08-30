package FATCA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TwoListsStringByString {
    public static void main(String[] args) throws IOException {
        String file1 = "/Users/olegsolodovnikov/Documents/MyDocuments/FATCA/Submissions/tempResults_Expr_PostGres.txt";
        String file2 = "/Users/olegsolodovnikov/Documents/MyDocuments/FATCA/Submissions/tempResults_Expr_MSSQL.txt";
        FileReader fileReader = new FileReader(file1);
        FileReader fileReader2 = new FileReader(file2);
        BufferedReader reader = new BufferedReader(fileReader);
        BufferedReader reader2 = new BufferedReader(fileReader2);
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text);
        }
        while((text = reader2.readLine()) != null){
            list2.add(text);
        }
        for (int i = 0; i < list.size(); i++) {
            if(!(list.get(i).equals(list2.get(i)))){
                list3.add(list2.get(i));
            }
        }
        System.out.println(list3);
    }
}
