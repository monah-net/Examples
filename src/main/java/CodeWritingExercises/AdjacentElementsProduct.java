package CodeWritingExercises;

import java.util.HashMap;
import java.util.Map;

public class AdjacentElementsProduct {
    public static void main(String[] args) {
        int[] inputarray = {3,6,-2,-5,7,3};
        HashMap <Integer,Integer> mapa = new HashMap<>();
        for (int i = 0; i < inputarray.length-1; i++) {
            mapa.put(i,(inputarray[i]*inputarray[i+1]));
        }
        System.out.println(mapa);
        int value = 0;
        int key = 0;
        for (Map.Entry<Integer,Integer>res:mapa.entrySet()
             ) {
            if(value < res.getValue()){
                value = res.getValue();
                key = res.getKey();
            }
        }
        String result;
        result = inputarray[key] + " " + inputarray [key+1];
        System.out.println(result);
    }
}
