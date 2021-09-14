package Theory;

public class Recursion {
    public int counter = 0;
    public static void main(String[] args) {
        recursionFucn();
    }
    public static void recursionFucn() {
        System.out.println("Привет, JavaRush!");
        recursionFucn();
    }
}
