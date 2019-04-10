package Tests;

import java.util.HashMap;

public class StringtoMap {
    public static void main(String[] args) {
        String s1 = "aabbbbcccceee";
        String s2 = "abcd";
        System.out.println(commonCharacterCount(s1,s2));
    }
    static int commonCharacterCount(String s1, String s2){
        HashMap<String,Integer>mapa1 = mapToString(s1);
        HashMap<String,Integer>mapa2 = mapToString(s2);
        int result = 0;
        int temp = 0;
        for (HashMap.Entry<String,Integer> mapa:mapa1.entrySet()
             ) {
            if(mapa2.containsKey(mapa.getKey())){
                temp = Math.min(mapa1.get(mapa.getKey()),mapa2.get(mapa.getKey()));
                result = result + temp;
            }
        }
        return result;
    }
    private static HashMap<String, Integer>mapToString(String s)
    {
        HashMap<String, Integer> mapa = new HashMap();
        for (int i = 0; i < s.length(); i++) {
            if (mapa.containsKey(s.substring(i, i + 1))) {
                mapa.put(s.substring(i, i + 1), mapa.get(s.substring(i, i + 1)) + 1);
            } else {
                mapa.put(s.substring(i, i + 1), 1);
            }
        }
        return mapa;
    }
}
