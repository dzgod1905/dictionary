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
//        String rootAPI = "https://clients5.google.com/translate_a/t?client=dict-chrome-ex&sl=" + sourceLanguage + "&tl=" + toLanguage + "&dt=t&q=";
//        String srcText = sourceLangField.getText();
//        String urlString = rootAPI + srcText;
//        urlString = urlString.replace(" ", "%20");
//
//        URL url = new URL(urlString);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
//        con.setRequestMethod("GET");
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String line;
//        StringBuilder content = new StringBuilder();
//        while ((line = in.readLine()) != null) content.append(line);
//
//        in.close();
//        con.disconnect();
//
//        JSONParser jsonParse = new JSONParser();
//        try {
//            JSONObject data = (JSONObject) jsonParse.parse(content.toString());
//            JSONArray sentences = (JSONArray) data.get("sentences");
//            JSONObject jsonObject = (JSONObject) sentences.get(0);
//            String trans = (String) jsonObject.get("trans");
//            toLangField.setText(trans);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
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
