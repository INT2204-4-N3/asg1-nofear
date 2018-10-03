import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class DictionaryManagement extends Dictionary{

    protected int Capacity = 4;

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
            File f = new File("C:\\Users\\cbg2\\MyDictionary_10\\src\\main\\java\\dictionaries.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String data;
            int i = 0;
            while ((data = br.readLine()) != null) {
                data = br.readLine();
                words[i].word_target = data;
                data = br.readLine();
                words[i].word_explain = data;
                i++;
            }

            fr.close();
            br.close();
        }
        catch (Exception ex){
            System.out.println("Loi doc file: " + ex);
        }
    }

}
