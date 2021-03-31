package FATCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoListsAll {
    public static void main(String[] args) throws IOException {
        String file1 = "/Users/MacbookPro/Desktop/Submissions_testing/1340FATCA/ID_DOM_DET_UNIT_TESTsETALON.xml";
        String file2 = "/Users/MacbookPro/Desktop/Submissions_testing/1340FATCA/ID_DOM_DET_UNIT_TESTsETALON.xml";
        FileReader fileReader = new FileReader(file1);
        FileReader fileReader2 = new FileReader(file2);
        BufferedReader reader = new BufferedReader(fileReader);
        BufferedReader reader2 = new BufferedReader(fileReader2);
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        System.out.println(temp.size());
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text);
        }
        reader.close();
        while((text = reader2.readLine()) != null){
            list2.add(text);
        }
        reader2.close();
        temp.addAll(list);
        temp.removeAll(list2);
        System.out.println(temp);
        System.out.println("**************");
        temp.clear();
        temp.addAll(list2);
        temp.removeAll(list);
        System.out.println(temp.size());
    }
}
