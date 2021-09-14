package Tests;

import java.util.ArrayList;

public class OtcyIDety {
    public static void main(String[] args) {
        ArrayList <Human> children = new ArrayList<>();
        ArrayList <Human> parents1 = new ArrayList<>();
        ArrayList <Human> parents2 = new ArrayList<>();
        ArrayList <Human> grandParents1 = new ArrayList<>();
        ArrayList <Human> grandParents2 = new ArrayList<>();
        ArrayList <Human> family = new ArrayList<>();
        children.add(new Human("Child1",true,8));
        children.add(new Human("Child2",true,8));
        children.add(new Human("Child3",false,4));
        parents1.add(new Human("Father",true,44,children));
        parents2.add(new Human("Mother",false,32,children));
        grandParents1.add(new Human("GrannyFather",false,88,parents1));
        grandParents1.add(new Human("GrandPaFather",true,88,parents1));
        grandParents2.add(new Human("GrannyMother",false,102,parents1));
        grandParents2.add(new Human("GrandPaMother",true,104,parents1));
        family.addAll(children);
        family.addAll(parents1);
        family.addAll(parents2);
        family.addAll(grandParents1);
        family.addAll(grandParents2);
        for (Human h:family
                ) {
            System.out.println(h.toString());
        }
    }

    public static class Human {
        public String name;
        public boolean sex;
        public int age;
        public ArrayList<Human> children;

        public Human(String name, boolean sex,int age){
            this.name = name;
            this.sex = sex;
            this.age = age;
        }
        public Human(String name, boolean sex,int age,ArrayList<Human> children){
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.children = children;
        }

        public String toString() {
            String text = "";
            text += "Имя: " + this.name;
            text += ", пол: " + (this.sex ? "мужской" : "женский");
            text += ", возраст: " + this.age;

            if (this.children != null) {
                int childCount = this.children.size();
                if (childCount > 0) {
                    text += ", дети: " + this.children.get(0).name;

                    for (int i = 1; i < childCount; i++) {
                        Human child = this.children.get(i);
                        text += ", " + child.name;
                    }
                }
            }
            return text;
        }
    }
}