package Files;

import java.io.File;

public class PathWORKTests {
    public static void main(String[] args) {
        String path = "/Users/MacbookPro/Desktop/WORK3";
        File file = new File(path);
        if (file.exists()){
            System.out.println("Directory was created before");
        }else{
            file.mkdir();
            System.out.println("Directory was created, new");
        }
    }
}
