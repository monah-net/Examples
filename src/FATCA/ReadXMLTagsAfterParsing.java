package FATCA;

import java.io.*;

public class ReadXMLTagsAfterParsing {
    public static void main(String[] args) {
        String folderPath = "/Users/olegsolodovnikov/Desktop/test1/etalon/id003";
        String filename = "1111111311202005130000RESULT.txt";
        File fileForRead = new File(folderPath + "/" + filename);
        StringBuilder stringBuilder = new StringBuilder();
        File fileForLoad = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileForRead), "cp1251"));
            String s;
            boolean firstPart = true;
            int counterAcc = 0;
            while ((s = bufferedReader.readLine()) != null) {
                if (s.contains("AccountData[OPEN]")) {
                    if (firstPart) {
                        fileForLoad = new File(folderPath + "/" + "Header.txt");
                        fileForLoad.createNewFile();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileForLoad), "UTF-8"));
                        bufferedWriter.write(stringBuilder.toString());
                        bufferedWriter.flush();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(s);
                        stringBuilder.append("\n");
                        fileForLoad = new File(folderPath + "/" + s + counterAcc + ".txt");
                        fileForLoad.createNewFile();
                        firstPart = false;
                        counterAcc++;
                    } else {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileForLoad), "UTF-8"));
                        bufferedWriter.write(stringBuilder.toString());
                        bufferedWriter.flush();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(s);
                        stringBuilder.append("\n");
                        fileForLoad = new File(folderPath + "/" + s + counterAcc + ".txt");
                        fileForLoad.createNewFile();
                        counterAcc++;
                    }
                } else {
                    stringBuilder.append(s);
                    stringBuilder.append("\n");
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileForLoad), "UTF-8"));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}