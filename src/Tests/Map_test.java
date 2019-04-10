package Tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/*
Добрая Зинаида и летние каникулы
*/

public class Map_test {
    public static HashMap<String, Date> createMap() throws ParseException {
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
        return map;
    }

    public static void removeAllSummerPeople(HashMap<String, Date> map) {
        ArrayList<String> arr = new ArrayList<>();
        for (Map.Entry<String, Date> pair : map.entrySet()) {
            Date date = pair.getValue();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if ((localDate.getMonthValue() > 5) & (localDate.getMonthValue() < 9)) {
                arr.add(pair.getKey());
            }
        }
        for (String string : arr
                ) {
            map.remove(string);
        }
    }

    public static void main(String[] args) throws ParseException {
        Map <String,Date> newmap = createMap();
        System.out.println(newmap);
        removeAllSummerPeople((HashMap<String, Date>) newmap);
        System.out.println(newmap);
    }
}