package FATCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileComparison {
    public static void main(String[] args) throws IOException {
        String file1 = "/Users/olegsolodovnikov/Desktop/AEOIFATCA-1324/R5TS2T.00307.SG.2302016083118022401MSSQL.xml";
        String file2 = "/Users/olegsolodovnikov/Desktop/AEOIFATCA-1324/R5TS2T.00307.SG.2302016083118022401_POSTGRES.xml";
        if(file1.equals(file2)){
            System.out.println("You are trying to compare similar files");
        }
        FileReader fileReader = new FileReader(file1);
        FileReader fileReader2 = new FileReader(file2);
        BufferedReader reader = new BufferedReader(fileReader);
        BufferedReader reader2 = new BufferedReader(fileReader2);
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text);
        }
        while((text = reader2.readLine()) != null){
            list2.add(text);
        }
        Collections.sort(list);
        Collections.sort(list2);
        list.removeAll(list2);
        System.out.println(list);
    }

}
