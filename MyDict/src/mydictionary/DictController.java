package mydictionary;


import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class DictController {

    @FXML
    private Button speechButton;
    @FXML
    private ComboBox<String> modeComboBox;
    @FXML
    private TextField searchBox;
    @FXML
    private ListView wordTargetList;
    @FXML
    private WebView wordExplanation;

    static DictionaryManagement evDict;
    static DictionaryManagement veDict;

    public void initialize(){
        evDict = new DictionaryManagement();
        veDict = new DictionaryManagement();
        evDict.insertDataFromMySql("ev_dict");
        veDict.insertDataFromMySql("ve_dict");
        speechButton.setVisible(false);
    }

    ObservableList<String> list;
    WebEngine webEngine;
    int wordIndex;

    public void textFieldKeyReleasedAction(){
        speechButton.setVisible(false);
        webEngine = wordExplanation.getEngine();
        webEngine.loadContent("");
        if (modeComboBox.getSelectionModel().getSelectedIndex() == 0 ){
            list = FXCollections.observableArrayList(veDict.dictionarySearcher(searchBox.getText()));
            wordTargetList.setItems(list);
            if (!searchBox.getText().equals("")) {
                wordIndex = veDict.getWordIndex(searchBox.getText());
                if (wordIndex != 0) {
                    webEngine.loadContent(veDict.getExplanationDataFrom("ve_dict", wordIndex));
                }
            }
        }
        else {
            list = FXCollections.observableArrayList(evDict.dictionarySearcher(searchBox.getText()));
            wordTargetList.setItems(list);
            if (!searchBox.getText().equals("")) {
                wordIndex = evDict.getWordIndex(searchBox.getText());
                if (wordIndex != 0) {
                    webEngine.loadContent(evDict.getExplanationDataFrom("ev_dict", wordIndex));
                    speechButton.setVisible(true);
                }
            }
        }

    }


    public void comboBoxMouseClickedAction(){

        list = FXCollections.observableArrayList("          Việt - Anh", "          Anh - Việt");
        modeComboBox.setItems(list);
        wordTargetList.setItems(null);
        webEngine = wordExplanation.getEngine();
        webEngine.loadContent("");
        searchBox.setText("");
        speechButton.setVisible(false);
    }

    public void listMouseClickedAction(){

        if (!searchBox.getText().equals("")) {
            searchBox.setText(wordTargetList.getSelectionModel().getSelectedItem().toString());

            if (modeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                list = FXCollections.observableArrayList(veDict.dictionarySearcher(searchBox.getText()));
                wordTargetList.setItems(list);
                webEngine = wordExplanation.getEngine();
                webEngine.loadContent(veDict.getExplanationDataFrom("ve_dict", veDict.getWordIndex(searchBox.getText())));
            } else {
                list = FXCollections.observableArrayList(evDict.dictionarySearcher(searchBox.getText()));
                wordTargetList.setItems(list);
                webEngine = wordExplanation.getEngine();
                webEngine.loadContent(evDict.getExplanationDataFrom("ev_dict", evDict.getWordIndex(searchBox.getText())));
                speechButton.setVisible(true);
            }
        }

    }

    public void speechButtonMouseClickedAction(){
        TextToSpeech.speak(searchBox.getText());
    }

    public void searchTextButtonMouseClickedAction(){
        Parent root;
        Stage primaryStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("searchText.fxml"));
            primaryStage.setTitle("My Dictionary");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static int modeComboBoxItemId = 1;
    public void addWordButtonMouseClickedAction(){
        modeComboBoxItemId = modeComboBox.getSelectionModel().getSelectedIndex();
        Parent root;
        Stage primaryStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("addWord.fxml"));
            primaryStage.setTitle("My Dictionary");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String wordTargetInput, wordExplanationInput;
    static int wordId;
    public void editWordButtonMouseClickedAction(){
        modeComboBoxItemId = modeComboBox.getSelectionModel().getSelectedIndex();

        if (searchBox.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Chú ý!");
            alert.setContentText("Vui lòng chọn từ muốn chỉnh sửa!");
            alert.showAndWait();
        }
        else {
            if (modeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                if (veDict.isWordExist(searchBox.getText())) {
                    wordTargetInput = searchBox.getText();
                    wordId = veDict.getWordIndex(searchBox.getText());
                    wordExplanationInput = veDict.getExplanationDataFrom("ve_dict", wordId);

                    Parent root;
                    Stage primaryStage = new Stage();
                    try {
                        root = FXMLLoader.load(getClass().getResource("editWord.fxml"));
                        primaryStage.setTitle("My Dictionary");
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Chú ý!");
                    alert.setContentText("Vui lòng chọn từ muốn chỉnh sửa!");
                    alert.showAndWait();
                }
            } else {
                if (evDict.isWordExist(searchBox.getText())) {
                    wordTargetInput = searchBox.getText();
                    wordId = evDict.getWordIndex(searchBox.getText());
                    wordExplanationInput = evDict.getExplanationDataFrom("ev_dict", wordId);
                    Parent root;
                    Stage primaryStage = new Stage();
                    try {
                        root = FXMLLoader.load(getClass().getResource("editWord.fxml"));
                        primaryStage.setTitle("My Dictionary");
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Chú ý!");
                    alert.setContentText("Vui lòng chọn từ muốn chỉnh sửa!");
                    alert.showAndWait();
                }
            }
            searchBox.setText("");
            wordTargetList.setItems(null);
            webEngine.loadContent("");
            speechButton.setVisible(false);
        }
    }

    public void deleteWordButtonMouseClickedAction(){
        if (searchBox.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Chú ý!");
            alert.setContentText("Vui lòng chọn từ cần xóa!");
            alert.showAndWait();
        }
        else {
            if (modeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                if (veDict.isWordExist(searchBox.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Từ bạn chọn sẽ bị xóa khỏi từ điển!");
                    alert.setContentText("Xác nhận xóa từ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        veDict.editMySqlData("ve_dict", veDict.getWordIndex(searchBox.getText()), "", "");
                        searchBox.setText("");
                        wordTargetList.setItems(null);
                        webEngine.loadContent("");
                        speechButton.setVisible(false);
                        veDict.list = new ArrayList<>();
                        veDict.insertDataFromMySql("ve_dict");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Chú ý!");
                    alert.setContentText("Vui lòng chọn từ cần xóa!");
                    alert.showAndWait();
                }
            } else {
                if (evDict.isWordExist(searchBox.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Từ bạn chọn sẽ bị xóa khỏi từ điển!");
                    alert.setContentText("Xác nhận xóa từ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        evDict.editMySqlData("ev_dict", evDict.getWordIndex(searchBox.getText()), "", "");
                        searchBox.setText("");
                        wordTargetList.setItems(null);
                        webEngine.loadContent("");
                        speechButton.setVisible(false);
                        evDict.list = new ArrayList<>();
                        evDict.insertDataFromMySql("ev_dict");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Chú ý!");
                    alert.setContentText("Vui lòng chọn từ cần xóa!");
                    alert.showAndWait();
                }
            }
        }
    }

}
