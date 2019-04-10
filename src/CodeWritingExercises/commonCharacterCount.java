package CodeWritingExercises;

import java.util.HashMap;

public class commonCharacterCount {
    public static void main(String[] args) {
        String s1 = "adcaa";
        String s2 = "aabcc";
    }
   private void commonCharacterCount(String s1, String s2) {
        HashMap<String, Integer>mapa1 = new HashMap();
        HashMap<Character, Integer>mapa2 = new HashMap();
       int num = 1;
        for (int i = 0; i < s1.length(); i++) {
           if (i ==0){
               mapa1.put(s1.substring(i),num);
           }else{
               if(mapa1.containsKey(s1.substring(i,i+1))){
                   mapa1.put(s1.substring(i,i+1),num+1);
               }
           }
       }
    }
}
