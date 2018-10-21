package dictionary;

import Speech.Trying_Different_Languages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.IOException;


public class textTranslateController<tLang> {
    @FXML
    private TextArea txtInput = new TextArea();
    @FXML
    private TextArea txtOutput = new TextArea();
    @FXML
    private Button btnConvert;
    @FXML
    private ComboBox<String> fromLang = new ComboBox<String>();
    @FXML
    private ComboBox<String> toLang = new ComboBox<String>();

    @FXML
    public void initialize() {


//        dict.getItems().add("Vietnamese");
//        dict.getItems().add("Viet-Anh");
        fromLang.getItems().addAll("Vietnamese", "English", "Japanese");
        fromLang.setValue("Select Language");
        toLang.getItems().addAll("Vietnamese", "English", "Japanese");
        toLang.setValue("Select Language");
    }

    public String toCodeLang(String in) {
        String out = null;
        if (in.equals("Vietnamese")) out = "vi";
        else if (in.equals("English")) out = "en";
        else if (in.equals("Japanese")) out = "ja";
        return out;
    }

    public void convertAction() {
        String fLang, tLang;
        fLang = toCodeLang(fromLang.getValue());
        tLang = toCodeLang(toLang.getValue());
        try {
            txtOutput.setText(Translator.translate(fLang, tLang, txtInput.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void voice(){
        Trying_Different_Languages tdl = new Trying_Different_Languages(txtInput.getText());

    }
}