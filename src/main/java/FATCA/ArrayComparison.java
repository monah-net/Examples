package FATCA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayComparison {
    public static void main(String[] args) {
        String s1 = "com.axiomsl.server.AxiomEnvironment;com.axiomsl.server.AxiomEnvironmentInsecure;java.text.SimpleDateFormat;java.util.HashMap;java.util.ArrayList;java.io.File;java.nio.charset.StandardCharsets;java.nio.file.Files;java.io.FileOutputStream;java.io.OutputStreamWriter;java.io.BufferedWriter;java.util.Date;java.util.TimeZone;java.io.FileInputStream;java.lang.System;java.net.URL;java.io.InputStreamReader;java.io.BufferedReader;java.io.FileWriter;java.lang.StringBuilder;java.util.zip.ZipOutputStream;java.util.zip.ZipEntry;java.util.HashSet;java.io.FileReader;java.util.UUID;java.lang.Double;java.lang.Math;java.util.StringJoiner;";
        String s2 = "com.axiomsl.server.AxiomEnvironment;com.axiomsl.server.AxiomEnvironmentInsecure;java.text.SimpleDateFormat;java.util.HashMap;java.util.ArrayList;java.io.File;java.nio.charset.StandardCharsets;java.nio.file.Files;java.io.FileOutputStream;java.io.OutputStreamWriter;java.io.BufferedWriter;java.util.Date;java.util.TimeZone;java.io.FileInputStream;java.lang.System;java.net.URL;java.io.InputStreamReader;java.io.BufferedReader;java.io.FileWriter;java.lang.StringBuilder;java.util.zip.ZipOutputStream;java.util.zip.ZipEntry;java.util.HashSet;java.io.FileReader;java.util.UUID;java.lang.Double;java.lang.Math;java.util.StringJoiner";
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.addAll(Arrays.asList(s1.split(";")));
        list2.addAll(Arrays.asList(s2.split(";")));
        System.out.println(list2);
        try {
            list2.removeAll(list1);
            System.out.println(list2);
        }
        catch (Exception e){

        }
//        System.out.println(list2);
    }
}
