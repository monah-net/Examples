package FATCA;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CheckAlphaNumericSymbols {
    public static void main(String[] args) {
        Pattern latinPattern = Pattern.compile("^[\\p{Print}\\p{IsLatin}]*$");
        String[] inputs = { "CRS_OECD.MessageSpec.CorrMessageRefId","abcDE 123", "!@#$%^&*", "aaàààäää", "ベビードラ", "😀😃😄😆","Сitizen or Resident (YES/NO)","Паляниця" };
        for (String input : inputs) {
            System.out.print("\"" + input + "\": ");
            Matcher matcher = latinPattern.matcher(input);
            if (! matcher.find()) {
                System.out.println("is NON latin");
            } else {
                System.out.println("is latin");
            }
        }
    }
}