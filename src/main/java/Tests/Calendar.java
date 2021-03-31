package Tests;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Calendar {
    public static void main(String[] args) {
        String date ="MAY 2 2013";
//        System.out.println(date);
        boolean check = true;
        HashMap <Integer, String> map = new HashMap<>();
        map.put(1,"January");
        map.put(2,"February");
        map.put(3,"March");
        map.put(4,"April");
        map.put(5,"May");
        map.put(6,"June");
        map.put(7,"July");
        map.put(8,"August");
        map.put(9,"September");
        map.put(10,"November");
        map.put(11,"October");
        map.put(12,"December");
        int month = 0;
//        System.out.println(date.substring(0,date.lastIndexOf(' ')-2));
//        System.out.println(date.substring(date.lastIndexOf(' ')+1));
//        System.out.println(date.substring(date.lastIndexOf(' ')+1,date.lastIndexOf(' ')+2));

        for (HashMap.Entry<Integer,String> map2:map.entrySet()
             ) {
            Integer numberOfMonth = map2.getKey();
            String nameOfMonth = map2.getValue();
            if(nameOfMonth.toUpperCase().equals(date.substring(0,date.lastIndexOf(' ')-2).toUpperCase())){
                month = numberOfMonth;
            }
        }

        int year = Integer.parseInt(date.substring(date.lastIndexOf(' ')+1));
        int day = Integer.parseInt(date.substring((date.lastIndexOf(' ')+1),date.lastIndexOf(' ')+2));
        LocalDate date1 = LocalDate.of(year,month,day);
        if(date1.getDayOfYear() % 2 == 0){
            check = false;
        }
      System.out.println(check);
    }
}
