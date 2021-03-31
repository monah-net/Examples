package FATCA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileInputSplit {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("/Users/MacbookPro/Desktop/TEMP/R5TS2T.00307.AE.230AEOIFATCA-1291_TESTv1AEAE31122018-CRS-011-A.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        String text;
        while ((text = reader.readLine()) != null){
            list.addAll(Arrays.asList(text.split("\\s+")));        }
        System.out.println(list);
    }
}
