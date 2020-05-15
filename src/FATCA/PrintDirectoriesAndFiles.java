package FATCA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintDirectoriesAndFiles {
    public static void main(String[] args) throws IOException {
        String fileName = "/Users/olegsolodovnikov/Desktop/file.txt";
        FileOutputStream outputStream = new FileOutputStream(new File(fileName));
        File folder = new File("/Users/olegsolodovnikov/Desktop/test/");
        String content = printDirectoryTree(folder);
        byte[] contentInBytes = content.getBytes();
        outputStream.write(contentInBytes);
        outputStream.close();
    }

    public static String printDirectoryTree(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printDirectoryTree(folder, indent, sb);
        return sb.toString();
    }

    private static void printDirectoryTree(File folder, int indent,
                                           StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                printDirectoryTree(file, indent + 1, sb);
            } else {
                printFile(file, indent + 1, sb);
            }
        }

    }

    private static void printFile(File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(file.getName());
        sb.append("\n");
    }

    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }
}