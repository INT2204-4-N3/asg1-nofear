package dictionary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.xml.bind.annotation.XmlAnyAttribute;

import java.io.IOException;

import static dictionary.IndexController.textInput;


public class textTranslateController{
    @FXML
    private TextArea txtInput,txtOutput;
//    @FXML
//    WebView txtOuput;
    @FXML
    private Button btnConvert;
    public void convertAction(){
        String output ;
        try {
                output=Translator.translate("en","vi",txtInput.getText());
            txtOutput.setText(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
