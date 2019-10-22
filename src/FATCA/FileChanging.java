package FATCA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileChanging {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/olegsolodovnikov/Desktop/portfolios/";
        String fileName = "fatca_template_input.xml";
        String filePathName = filePath + fileName;
        List <String> list = new ArrayList<>();
        FileReader fileReader = new FileReader(filePathName);
        BufferedReader reader = new BufferedReader(fileReader);
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text.replaceAll("!!!Aggregation","root").replaceAll("root\\.[a-z_]+","\\$root.elem_value"));
        }
        System.out.println(list);
        try ( PrintWriter writer = new PrintWriter(new File(filePathName.replaceAll(".xml","_output.xml"))) ) {
            for ( String arrText : list) {
                writer.write(arrText + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
