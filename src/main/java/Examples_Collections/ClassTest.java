package Examples_Collections;

import java.util.HashSet;
import java.util.Set;

public class ClassTest {
    public static void main(String[] args) {
        Set<Cat> cats = new HashSet<>();
        Set<Cat> temp = new HashSet<>();
        Cat cat1 = new Cat();
        Cat cat2 = new Cat("Vasya");
        Cat cat3 = new Cat("Kolya");
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);
        cats.remove(cats.toArray()[0]);
        printCats(cats);
    }

    public static class Cat {
        String name;

        public Cat() {

        }

        public Cat(String name) {
            this.name = name;
        }
    }

    public static void printCats(Set<Cat> cats) {

    }
}
