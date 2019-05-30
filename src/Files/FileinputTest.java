package Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileinputTest {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("/Users/MacbookPro/Desktop/WORK/Input_code_files/Tax_Reference_numbers_07032018");
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        String text;
        while ((text = reader.readLine()) != null) {
            list.add(text);
        }
        System.out.println(list);
        Map<String, String> map = new HashMap<>();
        for (String number : list
        ) {
            if (number != null && number != "")
                map.put(number, "Status");
        }
        System.out.println(map);
    }
}
