public class DictionaryCommandline extends DictionaryManagement{

    public void showAllWords(){

        System.out.println("No    |" + " English        " + "| Vietnamese                ");
        for (int i=0; i<Capacity; i++){
            System.out.println(i+1 + "     | " + words[i].word_target + "     | " + words[i].word_explain);
        }

    }
}
