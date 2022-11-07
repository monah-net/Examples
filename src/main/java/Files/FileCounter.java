package Files;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileCounter {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\osolodovnikov\\Desktop\\ONPREM\\1899\\Input_templat0s");
        List folderContent = new ArrayList<>();
        folderContent.addAll(Arrays.asList(file.listFiles()));
        System.out.println(folderContent.size());
        for (int i = 0; i < folderContent.size(); i++) {
            System.out.println(folderContent.get(i).toString());
        }
    }
}

