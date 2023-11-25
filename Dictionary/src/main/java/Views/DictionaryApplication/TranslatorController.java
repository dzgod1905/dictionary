package Views.DictionaryApplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class TranslatorController implements Initializable {
    private String sourceLanguage = "en";
    private String toLanguage = "vi";
    private boolean isToVietnameseLang = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translateButton.setOnAction(event -> {
            try {
                handleOnClickTranslateButton();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        sourceLangField.setOnKeyTyped(keyEvent -> translateButton.setDisable(sourceLangField.getText().trim().isEmpty()));

        translateButton.setDisable(true);
        toLangField.setEditable(false);
    }

    @FXML
    private void handleOnClickTranslateButton() throws IOException {
        // api dich o day
        String urlStr = "https://script.google.com/macros/s/AKfycbzBKkxMK2SOdTwasuAqdTUXEqAGRn7XdmVN4jMAmIkFbwlT-NMkfyKkKnESiFPa1uE18A/exec" +
                "?q=" + URLEncoder.encode(sourceLangField.getText(), "UTF-8") +
                "&target=" + toLanguage +
                "&source=" + sourceLanguage;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        toLangField.setText(response.toString());
    }

    @FXML
    private void handleOnClickSwitchToggle() {
        sourceLangField.clear();
        toLangField.clear();
        if (isToVietnameseLang) {
            englishLabel.setLayoutX(443);
            vietnameseLabel.setLayoutX(97);
            sourceLangField.setPromptText("Hãy nhập gì đó...");
            sourceLanguage = "vi";
            toLanguage = "en";
        } else {
            englishLabel.setLayoutX(115);
            vietnameseLabel.setLayoutX(425);
            sourceLangField.setPromptText("Type something...");
            sourceLanguage = "en";
            toLanguage = "vi";
        }
        isToVietnameseLang = !isToVietnameseLang;
    }

    @FXML
    private TextArea sourceLangField, toLangField;

    @FXML
    private Button translateButton, switchToggle;

    @FXML
    private Label englishLabel, vietnameseLabel;
}
