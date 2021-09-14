package Tests;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayOfYear {
    public static void main(String[] args) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        System.out.println(dateFormat.format(currentDate));
    }
}
