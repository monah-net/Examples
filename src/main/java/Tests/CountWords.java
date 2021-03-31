package Tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class CountWords {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<String> words = new ArrayList<String>();
            words.add("words1");
            words.add("words3");
            words.add("words4");
            words.add("words2");
            words.add("words2");
            words.add("words1");
            words.add("words3");
            words.add("words3");
            words.add("words2");
            words.add("words4");
            words.add("words2");
            words.add("words3");
            words.add("words2");
            words.add("words1");
            words.add("words1");

        Map<String, Integer> map = countWords(words);

        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    public static Map<String, Integer> countWords(ArrayList<String> list) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        Set<String> set = new HashSet<>();
        for (String string:list
             ) {
            set.add(string);
        }

        for (String string:set
             ) {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                if(string.equals(list.get(i))){
                    count++;
                    result.put(string,count);
                }
            }
        }
        return result;
    }
}
