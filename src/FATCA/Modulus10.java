package FATCA;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Modulus10 {
    public static void main(String[] args) throws IOException {
        String filename = "/Users/olegsolodovnikov/Desktop/CodesForModulus10.xml";
        FileReader fileReader = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        Map <String,String> map = new HashMap<>();
        int listCount = 0;
        String text;
        while ((text = reader.readLine()) != null){
            list.add(text);
            listCount ++;
        }
        int mapCount = 0;
        for (String number:list
        ) {
            if (number != null && number != "")
                map.put(number, getModule(number));
            mapCount++;
        }
        //System.out.println(listCount);
        //System.out.println(mapCount);
        //System.out.println(map);
        try ( PrintWriter writer = new PrintWriter(new File(filename.replaceAll(".xml","_output.xml"))) ) {
            for ( Map.Entry<String, String> entry : map.entrySet() ) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static String getModule (String input){
        String resultM;
        if(input.matches("[0-9]{10}")){
        String odd = "";
        String even = "";
        int oddSum = 0;
        int evenSum = 0;
        int result;
        for (int k = 1; k <= input.length(); k++) {
            if (k % 2 == 0) {
                even += input.substring(k - 1, k);
            } else {
                odd += input.substring(k - 1, k);
            }
        }
        for (int j = 0; j < odd.length(); j++) {
            int oddTemp = Integer.parseInt(odd.substring(j, j + 1)) * 2;
            if (oddTemp > 9) {
                oddTemp = oddTemp - 9;
            }
            oddSum += oddTemp;
        }
        for (int k = 0; k < even.length(); k++) {
            evenSum += Integer.parseInt(even.substring(k, k + 1));
        }
        result = oddSum + evenSum;
        if (result % 10 == 0) {
            resultM = "VALID";
        } else {
            resultM = "Non-Valid";
        }
        return resultM;
    }
        return resultM = "Code is not valid";
    }
}