package Tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Jukebox {
    ArrayList<String> songList = new ArrayList<String>();

    public static void main(String[] args) {
        new Jukebox().go();

    }

    public void go() {
        getSongs();
        System.out.println(songList);
        Collections.sort(songList);
        System.out.println(songList);
    }

    void getSongs() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/MacbookPro/Desktop/EXAMPLES/Songs.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                addSong(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void addSong(String lineToParse) {
        String[] tokens = lineToParse.split(" /");
        songList.add(tokens[0]);
    }
    public TreeSet<String> sortSongs (ArrayList <String> list){
        TreeSet<String>set = new TreeSet<>();
        for (String s:list
             ) {
            set.add(s);
        }
        return set;
    }
}