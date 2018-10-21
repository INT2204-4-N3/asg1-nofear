package dictionary;

import Speech.Trying_Different_Languages;
import app.Config;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class IndexController {
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
        dict.getItems().add("Việt-Anh");
        dict.setValue("Select dictionary");
        updateDict();

    }

    public void updateDict() {
        String choice = dict.getValue();
        String path;
        if (choice.equals("Việt-Anh")) {
            path = Config.FILE_DICTIONARY_VE;
            try {

                //path = Config.FILE_DICTIONARY_VE;
                FXMLLoader.load(getClass().getResource("index.fxml"));
                DictionaryManagement.readFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choice.equals("English-Vietnamese")) {
            path = Config.FILE_DICTIONARY_EV;
            try {
                FXMLLoader.load(getClass().getResource("index.fxml"));
                DictionaryManagement.readFile(path);
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
        btnEdit.setVisible(true);
        btnDel.setVisible(true);
        btnAudio.setVisible(true);
//        Image image = new Image(getClass().getResourceAsStream("\\icon\\speaker.png"));
//        btnAudio.setGraphic(new ImageView(image));

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

    @FXML
    private ListView<String> listHint;

    public void Showhint(KeyEvent event) {
        listHint.getItems().setAll(DictionaryManagement.searcher(txtInput.getText()));
    }

    @FXML
    private Button btnVoice;

    public void voice() {
        Trying_Different_Languages tdl = new Trying_Different_Languages(txtInput.getText());

    }

}
