package FATCA;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FolderCreation {
        public static void main(String[] args) {
            File file = new File("C:\\Users\\osolodovnikov\\Desktop\\ONPREM\\1899\\Input_templat0s");
            List folderContent = new ArrayList<>();
            folderContent.addAll(Arrays.asList(file.listFiles()));
            System.out.println(folderContent.size());
            for (int i = 0; i < folderContent.size(); i++) {
                int length = folderContent.get(i).toString().length();
                File fileTemp = new File(folderContent.get(i).toString());
                if(fileTemp.isFile() && getExtensionByStringHandling(folderContent.get(i).toString()).toString().equals("Optional[xlsx]")){
                    int startString = fileTemp.toString().indexOf("TEMPLATE");
                    int lengthString = fileTemp.toString().length();
                    new File("C:\\Users\\osolodovnikov\\Desktop\\ONPREM\\1899\\Input_templat0s\\" + fileTemp.toString().substring(startString,lengthString).replaceAll(".xlsx","")).mkdir();
//                    System.out.println("Start" + startString);
//                    System.out.println("Length" + lengthString);
//                    System.out.println("templatePart" +(lengthString-startString));
//                    System.out.println(fileTemp.toString().substring(74,87).replaceAll(".xlsx",""));
                }
            }
            System.out.println(folderContent.size());
        }

        public static Optional<String> getExtensionByStringHandling(String filename) {
            return Optional.ofNullable(filename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(filename.lastIndexOf(".") + 1));
        }
    }

