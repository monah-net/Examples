package FATCA;

public class ID_validation {
    public static void main(String[] args) {
        String temp;
        int res = 0;
        String input = "1911208317187";
        Integer lastdigit = Integer.parseInt(input.substring(12, 13));
        int sum = getevenSum(input) + getoddSum(input);
        sum = 10 - Integer.parseInt(String.valueOf(sum).substring(1, 2));
        if (sum > 9) {
            sum = Integer.parseInt(String.valueOf(sum).substring(1, 2));
        }
        System.out.println(sum == lastdigit);

    }

    public static int getoddSum(String code) {
        int oddSum = 0;
        for (int i = 0; i < code.length()-1; i++) {
            if (i % 2 == 0) {
                oddSum += Integer.parseInt(code.substring(i, i + 1));//even part
            } else {
            }
        }
        return oddSum;
    }

    public static int getevenSum(String code) {
        String evenString = "";

        for (int i = 0; i < code.length(); i++) {
            if (i % 2 != 0) {
                evenString += code.substring(i, i + 1);
            } else {
            }
        }
        int evenDouble = Integer.parseInt(evenString) * 2;
        evenString = String.valueOf(evenDouble);
        evenDouble = 0;
        for (int i = 0; i < evenString.length(); i++) {
            evenDouble += Integer.parseInt(evenString.substring(i, i + 1));
        }
        return evenDouble;
    }
}
