package dictionary;

//import com.sun.speech.freetts.Voice;
//import com.sun.speech.freetts.VoiceManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class DictionaryManagement {
    static ArrayList<String> keys = new ArrayList<>();
    static Map<String, String> data = new HashMap<>();
    static String path = null;

    public static void readFile(String path) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            String word, explain;

            while ((line = br.readLine()) != null) {
                String content[] = line.split("<html>");
                word = content[0];
                explain = "<html>" + content[1];
                data.put(word, explain);
                keys.add(word);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String lookUp(String word) {
        return data.get(word);
    }

    static boolean isWordExist(String word) {
        int x = keys.indexOf(word);
        if (x < 0) return false;
        return true;
    }

    private static void Update(String path) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path));
            for (String key : keys) {
                bw.write(key + DictionaryManagement.data.get(key));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public void Update1(String path, String line){
//        Util.writeFile(path,line);
//    }

    public static void addWord(String word, String explain) {
        explain = generateExplain(explain);
        keys.add(word);
        data.put(word, explain);
        Update(path);
    }

    public static void removeWord(String word) {
        keys.remove(word);
        data.remove(word);
        Update(path);
    }

    public static void editWord(String word, String oldExplain, String newExplain) {
        newExplain = generateExplain(newExplain);
        data.replace(word, oldExplain, newExplain);
        Update(path);
    }

    public static ArrayList<String> searcher(String word) {
        ArrayList<String> hint = new ArrayList<>();
        for (String w : keys) {
            if (word.startsWith(w)) {
                hint.add(w);
            }
        }
        return hint;
    }

    private static String generateExplain(String explain) {
        return "<html><ul><li><font color='#cc0000'><b>" + explain + "</b></font><ul></html>";
    }

}
//

                //dc roi day
//?? xong à