package Tests;

public class IntBytes {
    public static void main(String[] args) {
        float f = 2147483649f;
        int i = (int) f;
        byte b = (byte) f;
        System.out.println(i);
        System.out.println(f);
        System.out.println(b);
    }
}
