package ExceptionsEx;

public class ExceptionExample2 {
    public static void main(String[] args) {
        System.out.println("Program starts");

        try {
            System.out.println("Before method1 calling");
            method1();
            System.out.println("After method1 calling. Never will be shown ");
        } catch (NullPointerException e) {
            System.out.println("Reference is null. Exception has been caught");
        } catch (ArithmeticException e) {
            System.out.println("Division by zero. Exception has been caught");
        } catch (Exception e) {
            System.out.println("Any other errors. Exception has been caught");
        }
        System.out.println("Program is still running");
    }

    public static void method1() {
        int a = 100;
        int b = -200;
        System.out.println(a / b);
    }
}
