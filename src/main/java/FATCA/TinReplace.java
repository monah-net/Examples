package FATCA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TinReplace {
    public static void main(String[] args) throws IOException {
        String fileName = "/Users/olegsolodovnikov/Desktop/COMPARISON/ID_DOM_AGG_UNIT_TESTs.xml";
        File file = new File(fileName);
        FileReader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);
        List <String> list = new ArrayList<>();
        List <String> list2 = new ArrayList<>();
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text);
        }
        for (String textList:list
             ) {
            if (textList.contains("IdentitasUnik")){
                textList = textList.replaceAll("<IdentitasUnik>.*</IdentitasUnik>","<IdentitasUnik>123</IdentitasUnik>");
            }
            list2.add(textList);
        }
        printWriter(fileName, (ArrayList<String>) list2);
    }
    private static void printWriter (String filename, ArrayList <String> content){
        try (
                PrintWriter writer = new PrintWriter(new File(filename)) ) {
            for (int counter = 0; counter < content.size(); counter++) {
                writer.write(content.get(counter) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
