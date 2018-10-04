import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class DictionaryManagement extends Dictionary{

    protected int Capacity;

    public void insertFromCommandline(){

        Scanner cap = new Scanner(System.in);
        System.out.println("Nhap so luong tu: ");
        Capacity = cap.nextInt();
        words = new Word[Capacity];

        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap " + Capacity + " tu Tieng Anh: ");
        for (int i=0; i<Capacity; i++){
            words[i] = new Word();
            words[i].word_target = sc.nextLine();
        }

        System.out.println("Nhap " + Capacity + " giai nghia cho cac tu Tieng Anh tuong ung: ");
        for(int i=0; i<Capacity; i++){
            words[i].word_explain = sc.nextLine();
        }
    }

    public void  insertFromFile(){

        try {
            File f = new File("");
            String path = f.getAbsolutePath()+"\\src\\main\\java\\dictionaries.txt";
            f = new File(path);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            int totalLine = 0;
            while ((br.readLine()) != null) {
                totalLine++;
            }
            Capacity = totalLine;
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            File f = new File("");
            String path = f.getAbsolutePath()+"\\src\\main\\java\\dictionaries.txt";
            f = new File(path);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] data;
            words = new Word[Capacity];
            int i = 0;
            while ((line = br.readLine()) != null) {
                data = line.split("    ");
                words[i] = new Word();
                words[i].word_target = data[0];
                words[i].word_explain = data[1];
                i++;
            }

            fr.close();
            br.close();
        }
        catch (Exception ex){
            System.out.println("Loi doc file: " + ex);
        }
    }

    public void dictionaryLookup(){
        Scanner w = new Scanner(System.in);
        System.out.println("Nhap tu can tra:");
        String fw = w.nextLine();
        for (int i=0; i<Capacity; i++){
            if(words[i].word_target.equals(fw)){
                System.out.println(words[i].word_target + " : " + words[i].word_explain);
                break;
            }
            else if(words[i].word_explain.equals(fw)){
                System.out.println(words[i].word_explain + " : " + words[i].word_target);
                break;
            }
            else if (i == Capacity -1) System.out.println("Khong tim thay tu!");
        }
    }
    public static void main(String[] args){
        DictionaryManagement a = new DictionaryManagement();
        a.insertFromFile();
    }
}
