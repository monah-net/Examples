package FATCA;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterCount {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("/Users/olegsolodovnikov/Desktop/AEOIFATCA_ZA/submissions/FTI_2__S04120191011T123751FewEntitites_20191011123751.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        String text;
        HashMap<String,Integer>mapa = new HashMap<>();
        while ((text = reader.readLine()) != null){
            list.add(text);
        }
        String strTest;
        for (int i = 0; i < list.size(); i++) {
            strTest = list.get(i);
            String [] strarr = strTest.split("|");
            int counter = 0;
            for (int j = 0; j < strarr.length; j++) {
                if(strarr[j].equals("|")){
                    counter++;
                }
            }
            mapa.put(list.get(i),counter);
        }
        File output = new File("/Users/olegsolodovnikov/Desktop/output.txt");
        FileOutputStream fop = new FileOutputStream(output);
        String content;
        byte[] contentInBytes;
        for (Map.Entry<String, Integer> pair : mapa.entrySet()) {
            content = pair.getValue() + "/////" + pair.getKey();
            contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.write('\n');
        }
    }
}