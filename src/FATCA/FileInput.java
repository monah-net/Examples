package FATCA;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInput {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("/Users/MacbookPro/Desktop/WORK/Input_code_files/Tax_Reference_numbers_07032018");
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        while (reader.readLine() != null){
            list.add(reader.readLine());
        }
        System.out.println(list);
        Map <String,String> map = new HashMap<>();
        for (String number:list
             ) {
            if(number != null && number != "")
            map.put(number,"Status");
        }
        System.out.println(map);
    }
}
