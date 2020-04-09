package FATCA;

import java.io.*;
import java.io.FileOutputStream;
import java.util.Map;

public class FileOutput {
    public static void main(String[] args) throws IOException {
        String content = "Some content";
        String fileName = "/Users/olegsolodovnikov/Desktop/file.txt";
        FileOutputStream outputStream = new FileOutputStream(new File(fileName));
        byte[] contentInBytes = content.getBytes();
        outputStream.write(contentInBytes);
        outputStream.close();
    }
}