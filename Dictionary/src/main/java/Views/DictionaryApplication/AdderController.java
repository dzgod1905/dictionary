package Views.DictionaryApplication;

import Manager.DictionaryManager;
import Models.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdderController implements Initializable {
    private final DictionaryManager dictionaryManager = new DictionaryManager();
    private final Warnings warnings = new Warnings();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManager.insertFromFile();
        if (explanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty()) addButton.setDisable(true);

        wordTargetInput.setOnKeyTyped(keyEvent -> addButton.setDisable(explanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty()));
        explanationInput.setOnKeyTyped(keyEvent -> addButton.setDisable(explanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty()));

        successAlert.setVisible(false);
    }

    @FXML
    private void handleClickAddButton() {
        Alert alertConfirm = warnings.alertConfirmation("Add word", "Bạn chắc chắn muốn thêm từ này?");
        Optional<ButtonType> option = alertConfirm.showAndWait();
        String englishWord = wordTargetInput.getText().trim();
        String meaning = explanationInput.getText().trim();
        System.err.println(englishWord);

        if (option.isEmpty()) return;
        if (option.get() == ButtonType.OK) {
            System.err.println("ok");

            Word word = new Word(englishWord, meaning);
            if (dictionaryManager.findWordAdvance(englishWord) != null) {
                Alert selectionAlert = warnings.alertConfirmation("This word already exists",
                        "Từ này đã tồn tại.\nThay thế hoặc bổ sung nghĩa vừa nhập cho nghĩa cũ.");
                selectionAlert.getButtonTypes().clear();
                ButtonType replaceButton = new ButtonType("Thay thế");
                ButtonType insertButton = new ButtonType("Bổ sung");
                selectionAlert.getButtonTypes().addAll(replaceButton, insertButton, ButtonType.CANCEL);
                Optional<ButtonType> selection = selectionAlert.showAndWait();

                if (selection.isEmpty()) return;
                if (selection.get() == replaceButton) {
                    // need adding
                    showSuccess();
                }
                if (selection.get() == insertButton) {
                    // need adding
                    showSuccess();
                }
                if (selection.get() == ButtonType.CANCEL) warnings.showWarningInfo("Information", "Thay đổi không được công nhận.");
            } else {
                dictionaryManager.addWord(word);
                showSuccess();
            }
            addButton.setDisable(true);
            resetInput();
        }
        else {
            System.err.println("wrong");
            warnings.showWarningInfo("Information", "Thay đổi không được công nhận.");
        }
    }

    private void resetInput() {
        wordTargetInput.setText("");
        explanationInput.setText("");
    }

    private void showSuccess() {
        successAlert.setVisible(true);
        setTimeout(() -> successAlert.setVisible(false));
    }

    private void setTimeout(Runnable runnable) {
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    @FXML
    private Button addButton;

    @FXML
    private TextField wordTargetInput;

    @FXML
    private TextArea explanationInput;

    @FXML
    private Label successAlert;
}