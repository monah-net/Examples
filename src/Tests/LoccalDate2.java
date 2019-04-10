package Tests;

import java.time.LocalDate;
import java.util.HashMap;

public class LoccalDate2 {
    public static void main(String[] args) {
        System.out.println(isDateOdd("MAY 4 2020"));
    }

    public static boolean isDateOdd(String date) {
        boolean check = true;
        HashMap<Integer, String> datesMap = new HashMap<>();
        datesMap.put(1, "January");
        datesMap.put(2, "February");
        datesMap.put(3, "March");
        datesMap.put(4, "April");
        datesMap.put(5, "May");
        datesMap.put(6, "June");
        datesMap.put(7, "July");
        datesMap.put(8, "August");
        datesMap.put(9, "September");
        datesMap.put(10, "October");
        datesMap.put(11, "November");
        datesMap.put(12, "December");
        int month = 0;
        for (HashMap.Entry<Integer, String> map2 : datesMap.entrySet()
                ) {
            Integer numberOfMonth = map2.getKey();
            String nameOfMonth = map2.getValue();
            if (nameOfMonth.toUpperCase().equals(date.substring(0, date.lastIndexOf(' ') - 2).toUpperCase())) {
                month = numberOfMonth;
            }
        }

        int year = Integer.parseInt(date.substring(date.lastIndexOf(' ') + 1));
//        String test = date.substring(date.lastIndexOf(' ')-1,date.lastIndexOf(' '));
        int day = Integer.parseInt(date.substring(date.lastIndexOf(' ')-1,date.lastIndexOf(' ')));
        LocalDate date1 = LocalDate.of(year, month, day);
        if (date1.getDayOfYear() % 2 == 0) {
            check = false;
        }
        return check;
    }
}
