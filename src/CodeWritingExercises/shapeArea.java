package CodeWritingExercises;

public class shapeArea {
    public static void main(String[] args) {
        int n = 5;
        double result = ((Math.pow((double) n,(double)2)) + (Math.pow((double) (n-1),(double)2)));
        int intres = (int) result;
        System.out.println(intres);
    }
}
