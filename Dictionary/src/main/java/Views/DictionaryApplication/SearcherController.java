package Views.DictionaryApplication;

import Manager.DictionaryManager;
import Models.Word;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearcherController implements Initializable {
    private final DictionaryManager dictionaryManager = DictionaryManager.getInstance();
    ObservableList<String> list = FXCollections.observableArrayList();
    private final Warnings warnings = new Warnings();
    private String tempMeaning = "";

    private void resetButtons() {
        soundButton.setVisible(false);
        editButton.setVisible(false);
        deleteButton.setVisible(false);

        englishWord.setText("Nothing yet.");
        explanation.setText("Explanation here.");
        explanation.setEditable(false);
    }

    private void activateButtons(Word word) {
        soundButton.setVisible(true);
        editButton.setVisible(true);
        deleteButton.setVisible(true);

        englishWord.setText(word.getWordTarget());
        explanation.setText(word.getWordExplain());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Number of words: " + dictionaryManager.getDictionarySize());
        // 138481 expected
        resetButtons();
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        notAvailableAlert.setVisible(false);
    }

    @FXML
    private void handleClickCancelButton() {
        searchTerm.clear();
        notAvailableAlert.setVisible(false);
        cancelButton.setVisible(false);
        list.clear();
        listResults.setItems(list);
    }

    @FXML
    private void handleOnKeyTyped() {
        if (searchTerm.getText().isEmpty()) {
            cancelButton.setVisible(false);
            list.clear();
            listResults.setItems(list);
            return;
        }
        cancelButton.setVisible(true);
        String searchKey = searchTerm.getText().trim();
        List<String> searchedWords = new ArrayList<>();
        for (Word word : dictionaryManager.searchWord(searchKey)) {
            searchedWords.add(word.getWordTarget());
            // cap to display max 20 words
            if (searchedWords.size() == 20) break;
        }
        list = FXCollections.observableList(searchedWords);
        notAvailableAlert.setVisible(list.isEmpty());
        listResults.setItems(list);
    }

    @FXML
    private void handleMouseClickAWord() {
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            Word word = dictionaryManager.findWordAdvance(selectedWord);
            activateButtons(word);
        }
    }

    @FXML
    private void handleClickEditButton() {
        DictionaryController.EDITING = true;
        tempMeaning = explanation.getText();
        explanation.setEditable(true);
        saveButton.setVisible(true);
        warnings.showWarningInfo("Information", "Bạn đã cho phép chỉnh sửa nghĩa từ này!");
    }

    @FXML
    private void handleClickSoundButton() throws Exception {
        VoiceProvider tts = new VoiceProvider("ee1a861047db41e3aed6cca75554a826");
        VoiceParameters params = new VoiceParameters(englishWord.getText(), AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage("en-us");
        params.setVoice("Linda");
        params.setRate(0);
        byte[] voice = tts.speech(params);
        FileOutputStream fos = new FileOutputStream("./Dictionary/src/main/resources/audio.wav");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./Dictionary/src/main/resources/audio.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            // Wait for the sound to finish playing
            while (!clip.isRunning()) Thread.sleep(10);
            while (clip.isRunning()) Thread.sleep(10);
            clip.close();
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | IOException
                 | LineUnavailableException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void handleClickSaveButton() {
        Alert alertConfirmation = warnings.alertConfirmation("Update", "Bạn chắc chắn muốn cập nhật nghĩa từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        if (option.isEmpty()) return;
        if (option.get() == ButtonType.OK) {
            String expTemp = explanation.getText();
            if (!expTemp.endsWith("\n")) expTemp += "\n";
            dictionaryManager.changeWord(new Word(englishWord.getText(), expTemp));
            warnings.showWarningInfo("Information", "Cập nhật thành công!");
            DictionaryController.CHANGED = true;
        }
        else {
            warnings.showWarningInfo("Information", "Thay đổi không được công nhận!");
            explanation.setText(tempMeaning);
        }

        DictionaryController.EDITING = false;
        explanation.setEditable(false);
        saveButton.setVisible(false);
    }

    @FXML
    private void handleClickDeleteButton() {
        Alert alertWarning = warnings.alertWarning("Delete", "Bạn chắc chắn muốn xóa từ này?");
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> option = alertWarning.showAndWait();
        if (option.isEmpty()) return;
        if (option.get() == ButtonType.OK) {
            dictionaryManager.removeWord(englishWord.getText());
            for (int i = 0; i < list.size(); i++)
                if (list.get(i).equals(englishWord.getText())) {
                    list.remove(i);
                    break;
                }
            listResults.setItems(list);
            resetButtons();
            warnings.showWarningInfo("Information", "Xóa thành công");
        } else warnings.showWarningInfo("Information", "Thay đổi không được công nhận");
    }

    @FXML
    private TextField searchTerm;

    @FXML
    private Button soundButton, editButton, deleteButton;

    @FXML
    private Button cancelButton, saveButton;

    @FXML
    private Label englishWord, notAvailableAlert;

    @FXML
    private TextArea explanation;

    @FXML
    private ListView<String> listResults;
}
