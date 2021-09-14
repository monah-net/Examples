package Examples_Collections;

import java.util.Vector;
public class VectorExample {
    Vector vector=new Vector();
    public void addCharacterandPrint(){
        vector.add("Weasley");
        vector.add("Potter");
        for(int i=0;i<vector.size();i++){
            System.out.println("The characters are\t"+vector.get(i));
        }
    }
    public static void main(String args[]){
        VectorExample example=new VectorExample();
        example.addCharacterandPrint();
    }
}