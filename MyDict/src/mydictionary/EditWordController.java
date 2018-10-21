package mydictionary;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;

import static mydictionary.DictController.*;

public class EditWordController {

    @FXML
    private TextField wordTargetText, nextWordTargetText;
    @FXML
    private WebView wordExplanationText;
    @FXML
    private TextArea nextWordExplanationText;

    WebEngine webEngine;

    public void initialize(){
        wordTargetText.setText(wordTargetInput);
        webEngine = wordExplanationText.getEngine();
        webEngine.loadContent(wordExplanationInput);
    }

    public void acceptButtonOnAction(){
        if (nextWordTargetText.getText().equals("") || nextWordExplanationText.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Chú ý!");
            alert.setContentText("Vui lòng điền đầy đủ từ và phần giải nghĩa!");
            alert.showAndWait();
        }
        else {

            if (modeComboBoxItemId == 0) {
                if (nextWordTargetText.getText().equals(wordTargetInput)){
                    veDict.editMySqlData("ve_dict", wordId, nextWordTargetText.getText(), nextWordExplanationText.getText());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Từ đã được sửa thành công!");
                    alert.showAndWait();

                    nextWordTargetText.setEditable(false);
                    nextWordExplanationText.setEditable(false);

                    veDict.list = new ArrayList<>();
                    veDict.insertDataFromMySql("ve_dict");
                }
                else {
                    if (veDict.isWordExist(nextWordTargetText.getText())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText("Chú ý!");
                        alert.setContentText("Từ đã tồn tại!");
                        alert.showAndWait();
                    } else {
                        veDict.editMySqlData("ve_dict", wordId, nextWordTargetText.getText(), nextWordExplanationText.getText());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Từ đã được sửa thành công!");
                        alert.showAndWait();

                        nextWordTargetText.setEditable(false);
                        nextWordExplanationText.setEditable(false);

                        veDict.list = new ArrayList<>();
                        veDict.insertDataFromMySql("ve_dict");
                    }
                }
            }
            else {
                if (nextWordTargetText.getText().equals(wordTargetInput)){
                    evDict.editMySqlData("ev_dict", wordId, nextWordTargetText.getText(), nextWordExplanationText.getText());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Từ đã được sửa thành công!");
                    alert.showAndWait();

                    nextWordTargetText.setEditable(false);
                    nextWordExplanationText.setEditable(false);

                    evDict.list = new ArrayList<>();
                    evDict.insertDataFromMySql("ev_dict");
                }
                else {
                    if (evDict.isWordExist(nextWordTargetText.getText())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText("Chú ý!");
                        alert.setContentText("Từ đã tồn tại!");
                        alert.showAndWait();
                    } else {
                        evDict.editMySqlData("ev_dict", wordId, nextWordTargetText.getText(), nextWordExplanationText.getText());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Từ đã được sửa thành công!");
                        alert.showAndWait();

                        nextWordTargetText.setEditable(false);
                        nextWordExplanationText.setEditable(false);

                        evDict.list = new ArrayList<>();
                        evDict.insertDataFromMySql("ev_dict");
                    }
                }
            }
        }
    }
}
