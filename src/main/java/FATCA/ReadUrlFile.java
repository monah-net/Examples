package FATCA;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadUrlFile {


    public static void main(String[] args) throws IOException {
        URL url = new URL("https://apps.irs.gov/app/fatcaFfiList/data/FFIListFull.csv");
        File destFile = new File("Test.csv");
        int bufferSize = 8 * 1024;
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()), bufferSize);
        StringBuilder builder = new StringBuilder();
        FileWriter writer = new FileWriter(destFile.getAbsoluteFile());
        while (reader.ready()) {
            String line = reader.readLine();
            System.out.println(line);
        }
    }
}
