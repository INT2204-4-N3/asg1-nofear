public class NewDictionaryCommandLine extends DictionaryCommandline{

    public void dictionaryBasic(){
        insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced(){
        insertFromFile();
        showAllWords();
        dictionaryLookup();
    }
}
