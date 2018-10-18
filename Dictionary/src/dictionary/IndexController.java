package dictionary;

import app.Config;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class IndexController  {
    private ObservableList<String> languages = FXCollections.observableArrayList("Viet-Anh", "Anh - Viet");
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button btnEdit, btnDel, btnAudio, btnAdd, btnConvert;
    @FXML
    private TextField txtInput;
    @FXML
    private WebView viewExplain;

    static String textInput;
    @FXML
    private ComboBox<String> dict = new ComboBox<String>();



    @FXML
    public void initialize() {
        DictionaryManagement.keys.clear();
        DictionaryManagement.data.clear();

        dict.getItems().add("English-Vietnamese");
        dict.getItems().add("Viet-Anh");

        dict.setValue("Select Language");

    }
    public void updateDict(){
        String choice = dict.getValue();

        if(choice.equals("Viet-Anh")){
            try {

                FXMLLoader.load(getClass().getResource("index.fxml"));
                DictionaryManagement.path = Config.FILE_DICTIONARY_VE;
                DictionaryManagement.readFile(DictionaryManagement.path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                FXMLLoader.load(getClass().getResource("index.fxml"));
                DictionaryManagement.path = Config.FILE_DICTIONARY_EV;
                DictionaryManagement.readFile(DictionaryManagement.path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void searchAction() {
        textInput = txtInput.getText();
        if (textInput.isEmpty()) {  //neu khong nhap tu in ra loi
            Message.Error("Please enter word");
        }
        //System.out.println(textInput);
        else {
            int findResult = DictionaryManagement.keys.indexOf(textInput);
            if (findResult < 0) {
                Optional<ButtonType> btnResult = Message.Confirmation(txtInput.getText() + " is not exist! Do you add this word ?");
                if (btnResult.get() == ButtonType.OK) {
                    addAction();
                }
            } else {
                WebEngine webEngine = viewExplain.getEngine();
                webEngine.loadContent(DictionaryManagement.data.get(textInput));
                showButtonAction();
            }

        }

    }

    public void showButtonAction() {
        btnAudio.setVisible(true);
        btnEdit.setVisible(true);
        btnDel.setVisible(true);
    }

    public void addAction() {
        Parent root1;
        Stage primaryStage = new Stage();
        try {
            root1 = FXMLLoader.load(getClass().getResource("add.fxml"));
            primaryStage.setTitle("Add new word");
            primaryStage.setScene(new Scene(root1));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //btnAdd.getScene().getWindow().hide();

//        Stage primaryStage = new Stage();
//        primaryStage.setTitle("Add new word");
//
//
//        Button buttonmain = new Button("Click to go to pop up window");
//        buttonmain.setOnAction(e -> popupAddController.display());
//
//        StackPane layout = new StackPane();
//
//        layout.getChildren().add(buttonmain);
//
//
//        Scene scene1 = new Scene(layout, 300, 250);
//        primaryStage.setScene(scene1);
//
//        primaryStage.show();
    }


    public void editAction() {
        Parent root1 = null;
        Stage primaryStage = new Stage();
        try {
            root1 = FXMLLoader.load(getClass().getResource("edit.fxml"));
            primaryStage.setTitle("Edit word");
            primaryStage.setScene(new Scene(root1));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void delAction() {
        Optional<ButtonType> btnResult = Message.Confirmation("Are you delete this word ?");
        if (btnResult.get() == ButtonType.OK) {
            DictionaryManagement.removeWord(textInput);
            Message.Success("Done!!!");
        }
    }

    public void convertAction() {
        Parent root = null;
        Stage primaryStage = new Stage();

        try {
            root = FXMLLoader.load(getClass().getResource("textTranslate.fxml"));
            primaryStage.setTitle("Convert Text");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
