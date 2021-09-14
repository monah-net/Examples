package Tests;

import java.util.Arrays;
import java.util.Scanner;

public class CAlphabet_lab5 {

    Scanner sc = new Scanner(System.in);
    String str, wordsArr[];
    char space = ' ';
    int beginOfWord = 0, w = 0, reallength;
    //StringBuilder out = new StringBuilder();

    void receiveWords()
    {
        System.out.print("Введите строку из нескольких слов: ");
        str = sc.nextLine();

        String[] wordsArr = new String[15];


        for (int i=0; i<str.length(); i++)
        {
            if (str.charAt(i) == space)
            {
                //out.append(str.substring(beginOfWord,i));
                wordsArr[w] = str.substring(beginOfWord,i);
                w++;
                beginOfWord = i+1;
            }
        }

        wordsArr[w] = str.substring(beginOfWord,str.length());
        reallength = w+1;

        for (int d=0; d<reallength; d++)
            System.out.print(wordsArr[d]+" ");
    }

    void sortWords()
    {
        System.out.println("До сортировки: " + Arrays.toString(wordsArr));
        Arrays.sort(wordsArr);
        System.out.println("После сортировки: "+Arrays.toString(wordsArr));

        /*for (int j = 0; j < reallength; j++)
        {
            for (int i = j + 1; i < reallength; i++)
            {
                if (wordsArr[i].compareTo(wordsArr[j]) < 0)
                {
                    String t = wordsArr[j];
                    wordsArr[j] = wordsArr[i];
                    wordsArr[i] = t;
                }
            }
        }*/
    }

}
