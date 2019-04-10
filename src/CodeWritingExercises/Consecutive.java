package CodeWritingExercises;

import java.util.Arrays;
import java.util.Collections;

public class Consecutive {
    public static void main(String[] args) {
        int[] statues = {6,2,3,8};
        int min = statues[0];
        int max = statues[0];
        for (int i = 0; i < statues.length; i++) {
            if(min > statues[i])min = statues[i];
            if(max < statues[i])max = statues[i];
        }
        int total = max - min;
        System.out.println((total - statues.length)+1);
    }
}
