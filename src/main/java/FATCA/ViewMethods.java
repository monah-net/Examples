package FATCA;

import FATCA.EncryptDecrypt.FATCAFilePreparation;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ViewMethods {
    public void foo() { }

    public int bar() { return 12; }

    public String baz() { return ""; }

    public static void main(String args[]) {
        try {
            Class thisClass = FATCAFilePreparation.class;
            Method[] methods = thisClass.getDeclaredMethods();
            ArrayList<String> outStr = new ArrayList<>();
            for (int i = 0; i < methods.length; i++) {
                outStr.add(methods[i].toString());
            }
            FileOutput.writeListToFile(outStr,"/Users/olegsolodovnikov/Desktop/methods.txt");
        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}