package CodeWritingExercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class AllLongestStrings {
    public static void main(String[] args) {
        String[] strings = {"aba",
                "aa",
                "ad",
                "vcd",
                "aba"};
        allLongestStrings(strings);

    }

    static String[] allLongestStrings(String[] inputArray) {
        int maxlengthOfArray = 0;
        int numberElementsOfResArr = 0;
        for (int i = 0; i < inputArray.length; i++) {//Определяем максимальную длину элемента массива
            if (inputArray[i].length() > maxlengthOfArray) {
                maxlengthOfArray = inputArray[i].length();
            }
        }
        for (int i = 0; i < inputArray.length; i++) {//определяем сколько элементов будет содержать возвращаемый массив
            if (inputArray[i].length() == maxlengthOfArray) {
                numberElementsOfResArr++;
            }
        }
        String[] allLongestStrings = new String[numberElementsOfResArr];
        int numberOfCurrentElem = 0;
        for (int i = 0; i < inputArray.length; i++) {
            //добавлем элементы в новый массив, длина которых соотвествует ранее найденным элементам
            if (inputArray[i].length() == maxlengthOfArray) {
                if (numberOfCurrentElem == 0) {
                    allLongestStrings[0] = inputArray[i];
                    numberOfCurrentElem++;
                } else {
                    allLongestStrings[numberOfCurrentElem] = inputArray[i];
                    numberOfCurrentElem++;
                }
            }
        }
        return allLongestStrings;
    }
}