package Theory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Sort {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> list = new ArrayList<>();
        try{
            while (true) {
                String s = reader.readLine();
                if (s.isEmpty()) break;
                list.add(s);
            }
        }
        catch (NullPointerException e){
            System.out.println("Something went wrong");
        }

        String[] array = list.toArray(new String[0]);
        sort(array);

        for (String x : array) {
            System.out.println(x);
        }
    }

    public static void sort(String[] array) {
        //массив чисел и массив слов
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if(isNumber(array[i])){
                numbers.add(Integer.parseInt(array[i]));
            }else{
                strings.add(array[i]);
            }
        }
        //сортировка массива чисел
        Collections.sort(numbers);
        Collections.reverse(numbers);
        //сортировка массива строк
        for (int i = 0; i < strings.size()-1 ; i++) {
            if(isGreaterThan(strings.get(i),strings.get(i+1))){
                String temp = strings.get(i);
                strings.add(i,strings.get(i+1));
                strings.add(i+1,temp);
            }
        }
        //сортировка основного массива
        for (int i = 0; i < array.length; i++) {
            if(isNumber(array[i])){
                array[i] = String.valueOf(numbers.get(0));
                numbers.remove(0);
            }else{
                array[i] = String.valueOf(strings.get(0));
                strings.remove(0);
            }
        }
    }

    // Метод для сравнения строк: 'а' больше чем 'b'
    public static boolean isGreaterThan(String a, String b) {
        return a.compareTo(b) > 0;
    }


    // Переданная строка - это число?
    public static boolean isNumber(String s) {
        if (s.length() == 0) return false;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((i != 0 && c == '-') // Строка содержит '-'
                || (!Character.isDigit(c) && c != '-') // или не цифра и не начинается с '-'
                || (chars.length == 1 && c == '-')) // или одиночный '-'
            {
                return false;
            }
        }
        return true;
    }
}