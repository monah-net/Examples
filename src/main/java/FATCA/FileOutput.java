package FATCA;

import java.io.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class FileOutput {
    public static void main(String[] args) throws IOException {
//        String content = "Some content";
//        String fileName = "/Users/olegsolodovnikov/Desktop/file.txt";
//        FileOutputStream outputStream = new FileOutputStream(new File(fileName));
//        byte[] contentInBytes = content.getBytes();
//        outputStream.write(contentInBytes);
//        outputStream.close();
        ArrayList<String> list = new ArrayList();
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.add("Four");
        writeListToFile(list, "/Users/olegsolodovnikov/Desktop/file.txt");

    }

    public static void writeListToFile(ArrayList<String> content, String outFileName) throws IOException {
        File outFile = new File(outFileName);
        FileWriter filestream = new FileWriter(outFile);
        BufferedWriter writer = new BufferedWriter(filestream);
        for (int i = 0; i < content.size(); i++) {
            writer.write(content.get(i));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
}