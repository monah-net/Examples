package FATCA;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FilesRemovingFromDirExt {
    public static void main(String[] args) {
        String dir = "/Users/olegsolodovnikov/Desktop/test1/etalon copy";
        File temp = new File(dir);
        deleteFilesAtFolderExt(temp, ".xml");
    }

    public static void deleteFilesAtFolderExt(File directory, String extensionSkip) {
        List<File> content = Arrays.asList(directory.listFiles());
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).isFile() && !content.get(i).getName().endsWith(extensionSkip)) {
                content.get(i).delete();
            } else if (content.get(i).isDirectory() && content.get(i).listFiles().length != 0){
                deleteFilesAtFolder(content.get(i));
                content.get(i).delete();
            } else if (content.get(i).isDirectory() && content.get(i).listFiles().length == 0){
                content.get(i).delete();
            }
        }
    }

    public static void deleteFilesAtFolder(File dir) {
        List<File> content = Arrays.asList(dir.listFiles());
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).isFile()) {
                content.get(i).delete();
                content.get(i).isDirectory();
            }else if(content.get(i).isDirectory() && content.get(i).listFiles().length != 0){
                deleteFilesAtFolder(content.get(i));
                content.get(i).delete();
            }
        }
    }
}
