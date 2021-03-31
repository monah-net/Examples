package Files;

import java.io.*;
import java.nio.file.Files;

public class FileCopyEx {
    public static void main(String[] args) {
        copyFile("/Users/olegsolodovnikov/Desktop/Test/Folder1/someText.txt","/Users/olegsolodovnikov/Desktop/Test/Folder2/someText.txt");
    }
    public static void copyFile(String file1, String file2) {
        File copyFrom = new File(file1);
        File copyTo = new File(file2);
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(copyFrom));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(copyTo));
            byte[] buffer = new byte[1024];

            int length;
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0,length);
            }

            in.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
