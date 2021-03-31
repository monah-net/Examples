package Tests;

public class RoundTest{
    public static void main(String[] args) {
        int year = 52;
        float yearT = year;
        float centuryTemp = yearT/100;
        int century = (int) centuryTemp;
        if(centuryTemp != century){
            century = century + 1;
        }
        System.out.println(century);
    }
}
