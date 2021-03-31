package FATCA;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesRemovingFromFolder {
    public static void main(String[] args) {
        File dir = new File("/Users/olegsolodovnikov/Desktop/CHECK1");
        List<File> files = new ArrayList<>();
        files.addAll(Arrays.asList(dir.listFiles()));
        for (int i = 0; i < files.size(); i++) {
            File temp = new File(files.get(i).getAbsolutePath());
            temp.delete();
        }
        System.out.println("Removing files is finished");
    }
}
