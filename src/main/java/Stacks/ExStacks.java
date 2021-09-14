package Stacks;

public class ExStacks {
    public static void main(String[] args) throws Exception {
        int deep = getStackTraceDeep();
        System.out.println(deep);
    }

    public static int getStackTraceDeep() {
        StackTraceElement[] elems = Thread.currentThread().getStackTrace();
        System.out.println(elems.length);
        return elems.length;
    }
}
