package Examples_Collections;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Map_Ex2 {
    public static void main(String[] args) throws ParseException {
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
        String[]array = {"Jun","Jul","Aug"};
        System.out.println(map);
        ArrayList<String> listKeys = new ArrayList<>();
        Iterator<HashMap.Entry<String,Date>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            HashMap.Entry<String, Date> pair = iterator.next();
            String key = pair.getKey();
            Date value = pair.getValue();
            if(value.toString().substring(4,7).equals("Jun") || value.toString().substring(4,7).equals("Jul") || value.toString().substring(4,7).equals("Aug"))
            {listKeys.add(key);}
        }
        System.out.println(listKeys);
        for (String string:listKeys
             ) {
            map.remove(string);
        }
        System.out.println(map);
    }
}