package Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HashMapToFile {
    public static void main(String[] args) throws ParseException, IOException {
        File file = new File("/Users/olegsolodovnikov/Desktop/newfile.txt");
        FileWriter filestream = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(filestream);
        DateFormat df = new SimpleDateFormat("MMMMM d yyyy", Locale.ENGLISH);
        HashMap<String, Date> map = new HashMap<String, Date>();
        map.put("Stallone", df.parse("JUNE 1 1980"));
        map.put("Cherkasova", df.parse("September 29 1989"));
        map.put("Solodovnikova", df.parse("FEBRUARY 10 1962"));
        map.put("Zadriboroda", df.parse("March 2 1938"));
        map.put("Babushka", df.parse("JULY 21 1939"));
        map.put("Alyonka", df.parse("AUGUST 10 1980"));
        map.put("Human1", df.parse("DECEMBER 1 1961"));
        map.put("Human2", df.parse("JANUARY 10 1946"));
        map.put("Human3", df.parse("MARCH 12 1990"));
        map.put("Human4", df.parse("APRIL 10 1975"));
        for (Map.Entry <String,Date>pair:map.entrySet()) {
            writer.write(pair.getKey()+":"+pair.getValue());
        }
        Iterator<Map.Entry<String, Date>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Date> pair = iterator.next();
            String key = pair.getKey();
            Date value = pair.getValue();
            writer.write(key + ":" + value);
        }
        ArrayList<String> mapKeys = new ArrayList<>(map.keySet());
        for (int i = 0; i < mapKeys.size(); i++) {
            System.out.println(mapKeys.get(i)+" : "+map.get(mapKeys.get(i)));
        }
        writer.flush();
        writer.close();
    }
}
