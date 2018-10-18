package dictionary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;


public class editController {
    @FXML
    TextField txtWord = new TextField();

    @FXML
    TextArea txtExplain;
    @FXML
    Button btnCancel, btnSub;

    public void initialize() {
        txtWord.setText(IndexController.textInput);
    }

    public void SubAction() {
        //Message.Confirmation("edit this word");
        if (txtExplain.getText().isEmpty()) {
            CancelAction();
        } else {
            Optional<ButtonType> result = Message.Confirmation("Do you edit this word ?");
            if (result.get() == ButtonType.OK) {
                String oldExplain = DictionaryManagement.lookUp(txtWord.getText());
                DictionaryManagement.editWord(txtWord.getText(), oldExplain, txtExplain.getText());
                Message.Success("Done!!!");
            }
        }
    }

    public void CancelAction() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();

        stage.close();
    }
}
