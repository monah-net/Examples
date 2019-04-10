package Examples_Collections;

import java.util.*;

public class ArrList {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("Surname1", "Name1");
        map.put("Surname2", "Name1");
        map.put("Surname3", "Name2");
        map.put("Surname4", "Name2");
        map.put("Surname5", "Name2");
        map.put("Surname6", "Name3");
        map.put("Surname7", "Name4");
        map.put("Surname8", "Name5");
        map.put("Surname9", "Name6");
        map.put("Surname9", "Name3");
        System.out.println(map);
        ArrayList<String> list = new ArrayList<>();//лист, в который копируются все элементы Map
        Set<String> unique = new HashSet<>();// Set для записи только уникальных значений, без повторов
        for (Map.Entry<String, String> pair : map.entrySet()
                ) {
            list.add(pair.getValue());
            unique.add(pair.getValue());
        }
        Map<String, Integer> mpcounter = new HashMap<>();//Мапа со значениями из map-ключей, и подсчетом повторений, как значений
        for (String key : unique
                ) {
            mpcounter.put(key, Collections.frequency(list, key));
        }
        System.out.println(mpcounter);
        list.clear();
        unique.clear();
        for (Map.Entry<String,Integer>pair:mpcounter.entrySet()
             ) {
            if(pair.getValue()>1)list.add(pair.getKey());
        }
        System.out.println(list);
        for (Map.Entry<String,String> pair:map.entrySet()
             ) {
        }
        System.out.println(map);
    }
}