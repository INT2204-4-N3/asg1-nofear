package mydictionary;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AddWordController {
    @FXML
    private TextField typingWordTargetBox;
    @FXML
    private TextArea typingWordExplanationBox;

    public void acceptButtonMouseClickedAction(){

        if (typingWordTargetBox.getText().equals("") || typingWordExplanationBox.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Chú ý!");
            alert.setContentText("Vui lòng điền đầy đủ từ và phần giải nghĩa!");
            alert.showAndWait();
        }
        else {
            if (DictController.modeComboBoxItemId == 0) {
                if (DictController.veDict.isWordExist(typingWordTargetBox.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Chú ý!");
                    alert.setContentText("Từ đã tồn tại!");
                    alert.showAndWait();
                }
                else {
                    DictController.veDict.insertData("ve_Dict", typingWordTargetBox.getText(), typingWordExplanationBox.getText(), DictController.veDict.list.size() + 1);
                    DictController.veDict.list = new ArrayList<>();
                    DictController.veDict.insertDataFromMySql("ve_dict");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Từ đã được thêm thành công!");
                    alert.showAndWait();

                    typingWordTargetBox.setEditable(false);
                    typingWordExplanationBox.setEditable(false);
                }
            } else {
                if (DictController.evDict.isWordExist(typingWordTargetBox.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Chú ý!");
                    alert.setContentText("Từ đã tồn tại!");
                    alert.showAndWait();
                }
                else {
                    DictController.evDict.insertData("ev_Dict", typingWordTargetBox.getText(), typingWordExplanationBox.getText(), DictController.evDict.list.size() + 1);
                    DictController.evDict.list = new ArrayList<>();
                    DictController.evDict.insertDataFromMySql("ev_dict");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Từ đã được thêm thành công!");
                    alert.showAndWait();

                    typingWordTargetBox.setEditable(false);
                    typingWordExplanationBox.setEditable(false);
                }
            }
        }

    }
}
