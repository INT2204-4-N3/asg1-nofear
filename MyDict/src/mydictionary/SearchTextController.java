package mydictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class SearchTextController {
    @FXML
    private ComboBox choseLanguageBox;
    @FXML
    private TextArea typingTextArea, translatedTextArea;

    public void choseLanguageBoxMouseClickedAction(){
        ObservableList<String> list = FXCollections.observableArrayList("Tiếng Anh", "Tiếng Việt");
        choseLanguageBox.setItems(list);
        if (choseLanguageBox.getSelectionModel().getSelectedIndex() == 0){
            Translator.langFrom = "en";
            Translator.langTo = "vi";
        }
        else {
            Translator.langFrom = "vi";
            Translator.langTo = "en";
        }
    }

    public void translateButtonMouseClickedAction(){
        try {
            translatedTextArea.setText(Translator.translate(typingTextArea.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
