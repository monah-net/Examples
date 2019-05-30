package FATCA;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

    class TestingValuesElSub {
        public static void main(String[] args) throws IOException {

            String xml_address = "/Users/MacbookPro/Desktop/WORK/Input_code_files/Tin_test/2017----------20190111001.xml";
            String codes_address = "/Users/MacbookPro/Desktop/WORK/Input_code_files/Tin_test/TINs.txt";
            BufferedReader reader = new BufferedReader(new FileReader(xml_address));
            String line;
            TreeSet<String> lines = new TreeSet<>();
            while ((line = reader.readLine()) != null) {
                if (line.matches(".*<crs:IN.*") || (line.matches(".*<crs:TIN.*"))) {
                    String temp = line.substring(line.indexOf(">") + 1, line.indexOf("</"));
                    lines.add(temp.trim());
                }
            }
            reader.close();

            BufferedReader reader2 = new BufferedReader(new FileReader(codes_address));
            @SuppressWarnings("unused")
            String line_code;
            TreeSet<String> lines_code = new TreeSet<>();
            while ((line = reader2.readLine()) != null) {
                String temp = line.replaceAll("\t", "").replaceAll("\n", "").trim();
                if (temp.length() > 0) {
                    lines_code.add(temp);
                }
            }
            reader2.close();
            TreeSet < String > result_pos = new TreeSet<>();
            TreeSet<String> result_neg = new TreeSet<>();

            for (String s1 : lines_code
            ) {
                int counter = 0;
                m:
                for (String s2 : lines
                ) {
                    if (s2.equals(s1)) {
                        counter++;
                        break m;
                    }
                }
                if (counter > 0)
                    result_pos.add(s1);
                else {
                    result_neg.add(s1);
                }
            }
            for (String s : result_pos
            ) {
                System.out.println(s + " code is present");
            }
            System.out.println("*******************************************");
            for (String s : result_neg
            ) {
                System.out.println(s + " code is not present");
            }

        }
    }
