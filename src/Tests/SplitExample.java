package Tests;

public class SplitExample {
    public static void main(String[] args) {
        String replaceablePart = "aPAss10A|B| |C|D|E|F|G|H|S|";
        String [] test = replaceablePart.split("[|]");
        String functionName = "REPLACE(";
        String replacement = "X";
        String result1 = "";
        String result2 = "";
        for (int i = 0; i < test.length; i++) {
            result1 += functionName;
        }
        System.out.println(result1);
        for (int j = 0; j < test.length; j++) {
               result2 += ",'" + test[j] + "'," + "'" + replacement + "')";
        }
        System.out.println(result2);
        System.out.println(result1 + result2);
    }
}
