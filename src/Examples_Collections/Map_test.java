package Examples_Collections;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/*
Перепись населения
*/

public class Map_test {
    public static HashMap<String, String> createMap() {
        HashMap <String, String> hashMap = new HashMap<>();
        hashMap.put("Cherkasova","Galyna");
        hashMap.put("Cherkasov","Vasyl");
        hashMap.put("Chichvarkin","Anton");
        hashMap.put("Solodovnikov","Oleg");
        hashMap.put("Semenov", "Yaz");
        hashMap.put("Savchuk", "Galyna");
        hashMap.put("Selimov", "Sergey");
        hashMap.put("Seldereev", "Yaz");
        hashMap.put("Chermizov", "Gal");
        hashMap.put("Ulanova", "Galyna");
        return hashMap;
    }

    public static int getCountTheSameFirstName(HashMap<String, String> map, String name) {
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        int i = 0;
        while(iterator.hasNext()){
            Map.Entry<String,String> pair = iterator.next();
            String value = pair.getKey();
            if (value == name){
                i++;
            }
        }
        return i;
    }

    public static int getCountTheSameLastName(HashMap<String, String> map, String lastName) {
        int i = 0;
        for (String key: map.values()
             ) {
            if(lastName.equals(key)){
                i++;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        System.out.println(getCountTheSameFirstName(createMap(),"Semenov"));
        System.out.println(getCountTheSameLastName(createMap(),"Galyna"));
    }
}
