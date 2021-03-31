package Theory;

public class IFThenTest {
    public static void main(String[] args) {
        test(0);
        System.out.println(test(1));
        System.out.println(test(2));
        System.out.println(test(3));
        System.out.println(test(0));
        System.out.println(test(200));
    }
    public static boolean test (int val){
        boolean result = false;
        if (val == 1) {
            return true;
        }
        return result;
    }
}
