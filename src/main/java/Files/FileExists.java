package Files;

import java.io.File;

public class FileExists {
    public static void main(String[] args) {
        String filePath = "/Users/olegsolodovnikov/Desktop";
        String fileName = "Pres_executions.xlsx";
        File file = new File(filePath+"/"+fileName);
        System.out.println(file.exists());
    }
}
