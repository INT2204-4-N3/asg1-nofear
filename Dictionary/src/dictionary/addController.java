package dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class addController {
    @FXML
    private TextArea txtExplain;
    @FXML
    private TextField txtWord;
    @FXML
    private Button btnCancel, btnSub;

    public void SubmitAction() {
        Optional<ButtonType> result = Message.Confirmation("Are you add this word ?");

        if (DictionaryManagement.isWordExist(txtWord.getText())) {
            Message.Error(txtWord.getText() + " is exist");
        } else {
            if (result.get() == ButtonType.OK) {
                DictionaryManagement.addWord(txtWord.getText(), txtExplain.getText());
                Message.Success("Done!!!");
            }
        }
    }


    public void CancelAction() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
