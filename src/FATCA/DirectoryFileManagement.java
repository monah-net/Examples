package FATCA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryFileManagement {
    public static void main(String[] args) {
        System.out.println(getDirectoryContent("/Users/olegsolodovnikov/Desktop/test/etalon/","xml"));
    }
    public static List<String> getDirectoryContent(String xmlFilePath,String extension){
        File dir = new File(xmlFilePath);
        List<String> files = new ArrayList();
        for (int i = 0; i < dir.listFiles().length; i++) {
            if (dir.listFiles()[i].isFile() && dir.listFiles()[i].getName().endsWith(extension)){
                files.add(dir.listFiles()[i].getAbsolutePath());
            }
        }
        return files;
    }
}
