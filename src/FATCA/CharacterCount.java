package FATCA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterCount {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("/Users/MacbookPro/Desktop/TEMP/FTI_2_758475145_S03720190626T184407RMBCLI195_20190626184407.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text);
        }
        String strTest = list.get(7);
        String [] strarr = strTest.split("|");
        int counter = 0;
        System.out.println(strTest);
        for (int i = 0; i < strarr.length; i++) {
            if(strarr[i].equals("|")){
                counter++;
            }
        }
        System.out.println(counter);
    }
}