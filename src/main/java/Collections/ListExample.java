package Collections;

import java.util.ArrayList;
import java.util.List;

public class ListExample {
    public static void main(String[] args) {
        List<Integer> listInteger = new ArrayList<>();
        listInteger.add(12);
        listInteger.add(45);
        listInteger.add(43);
        System.out.println(listInteger.get(1));
        System.out.println(listInteger.get(0));
        listInteger.set(0,120);
        System.out.println(listInteger.get(0));
        System.out.println(listInteger.size());
        listInteger.remove(0);
        System.out.println(listInteger.size());
        List<Integer> listIntNew = new ArrayList<>();
        listIntNew.add(101);
        listIntNew.add(102);
        listInteger.addAll(listIntNew);
        System.out.println(listInteger);
        System.out.println(listInteger.contains(103));
        System.out.println(listInteger.contains(101));
        System.out.println(listInteger.containsAll(listIntNew));
        listInteger.removeAll(listIntNew);
        System.out.println(listIntNew);
    }

}
