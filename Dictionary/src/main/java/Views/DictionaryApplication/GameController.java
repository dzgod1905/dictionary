package Views.DictionaryApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import Manager.DictionaryManager;
import Models.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class GameController implements Initializable {
  private final static int LIMIT = 25;
  private final DictionaryManager dictionaryManager = DictionaryManager.getInstance();
  private static final ObservableList<String> list = FXCollections.observableArrayList();
  private static final List<String> keys = new ArrayList<>();
  private static String current = "";
  private static String inputText = "";
  private static int point = 0;
  private boolean typable = true;
  private final Warnings warnings = new Warnings();

  private void getKeys() {
    try (InputStream is = getClass().getResourceAsStream("/Utils/spellKeys.txt")) {
      BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
      String line;
      while ((line = br.readLine()) != null) keys.add(line);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private int wordLegit(String s) {
    if (s.length() < 4) return -1;
    if (!s.contains(current.substring(0, 1))) return -1;
    for (int i = 0; i < s.length(); i++) {
      if (!current.contains(s.substring(i, i+1))) return -1;
    }
    if (s.length() == 4) return 1;
    boolean pangram = true;
    for (int i = 1; i < 7; i++) {
      if (!s.contains(current.substring(i, i + 1))) {pangram = false; break;}
    }
    return s.length() + (pangram ? 100 : 0);
  }

  private void getHelp() {
    List<Word> wordList = dictionaryManager.getWordList();
    System.out.println("Possible choices:");
    int remain = 0;
    for (Word w: wordList) {
      String s = w.getWordTarget().toUpperCase();
      if (wordLegit(s) != -1 && !wordExisted(s)) {
        System.out.print(s);
        if (++remain == 4) {
          remain = 0;
          System.out.print("\n");
        }
        else System.out.print("     ");
      }
    }
    System.out.println();
  }

  private void setRandomKey() {
    int n = keys.size(), x;
    Random rand = new Random();
    do x = rand.nextInt(n);
    while (keys.get(x).equals(current));
    current = keys.get(x);
    // Initialize
    current = current.toUpperCase();
    point = 0;
    scoreTextArea.setText(getScore());
    list.clear();
    wordsFound.setItems(list);
    inputText = "";
    inputTextField.setText("");
  }

  private void setBeehive() {
    charArea0.setText(current.substring(0, 1));
    charArea1.setText(current.substring(1, 2));
    charArea2.setText(current.substring(2, 3));
    charArea3.setText(current.substring(3, 4));
    charArea4.setText(current.substring(4, 5));
    charArea5.setText(current.substring(5, 6));
    charArea6.setText(current.substring(6, 7));
  }

  private String getBadge(int point) {
    if (point < 6) return "Beginner";
    if (point < 15) return "Novice";
    if (point < 25) return "Okay";
    if (point < 40) return "Good";
    if (point < 65) return "Solid";
    if (point < 90) return "Nice";
    if (point < 120) return "Great";
    if (point < 150) return "Amazing";
    return "Genius";
  }

  private String getScore() {
      return "Score: " + GameController.point + " / 150\n" +
            "Badge: " + getBadge(GameController.point);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (current.isEmpty()) {
      getKeys();
      setRandomKey();
    }
    else {
      wordsFound.setItems(list);
      inputTextField.setText(inputText);
      scoreTextArea.setText(getScore());
    }
    setBeehive();
  }

  @FXML
  private void handleClickButton0() {
    if (!typable || inputText.length() == LIMIT) return;
    inputText += current.charAt(0);
    inputTextField.setText(inputText);
  }

  @FXML
  private void handleClickButton1() {
    if (!typable || inputText.length() == LIMIT) return;
    inputText += current.charAt(1);
    inputTextField.setText(inputText);
  }

  @FXML
  private void handleClickButton2() {
    if (!typable || inputText.length() == LIMIT) return;
    inputText += current.charAt(2);
    inputTextField.setText(inputText);
  }

  @FXML
  private void handleClickButton3() {
    if (!typable || inputText.length() == LIMIT) return;
    inputText += current.charAt(3);
    inputTextField.setText(inputText);
  }

  @FXML
  private void handleClickButton4() {
    if (!typable || inputText.length() == LIMIT) return;
    inputText += current.charAt(4);
    inputTextField.setText(inputText);
  }

  @FXML
  private void handleClickButton5() {
    if (!typable || inputText.length() == LIMIT) return;
    inputText += current.charAt(5);
    inputTextField.setText(inputText);
  }

  @FXML
  private void handleClickButton6() {
    if (!typable || inputText.length() == LIMIT) return;
    inputText += current.charAt(6);
    inputTextField.setText(inputText);
  }
  
  private boolean wordExisted(String word) {
    for (String s: list) if (s.equals(word)) return true;
    return false;
  }

  private String getVerdictFromWord(String word) {
    int point = wordLegit(word);
    GameController.point += (point >= 100 ? point - 93 : point);
    scoreTextArea.setText(getScore());
    if (point == 1) return "Good! +1";
    if (point < 9) return "Great! +" + point;
    if (point < 100) return "Excellent! +" + point;
    return "PANGRAM! + "  + (point - 93);
  }

  @FXML
  private void handleClickEnterButton() {
    if (inputText.isEmpty()) return;
    String verdict;
    if (inputText.length() < 4) verdict = "Too short.";
    else if (!inputText.contains(current.substring(0, 1)))
      verdict = "Missing center letter.";
    else if (wordExisted(inputText)) verdict = "Already found.";
    else {
      Word word = dictionaryManager.findWordAdvance(inputText);
      if (word == null || word.getWordTarget() == null)  verdict = "Invalid word.";
      else {
        verdict = getVerdictFromWord(inputText);
        list.add(inputText);
        wordsFound.setItems(list);
      }
    }
    inputText = "";
    inputTextField.setText(verdict);

    typable = false;
    setTimeout(() -> {
      typable = true;
      inputTextField.setText(inputText);
    });
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
  private void handleClickDeleteButton() {
    if (inputText.isEmpty()) return;
    inputText = inputText.substring(0, inputText.length() - 1);
    inputTextField.setText(inputText);
  }

  @FXML
  private void handleClickReplayButton() {
    if (point > 0) {
      Alert newGame = warnings.alertConfirmation("Replay", "Bạn có chắc muốn chơi lại?\n" +
              "Quá trình chơi hiện tại của bạn sẽ không được lưu.");
      Optional<ButtonType> option = newGame.showAndWait();
      if (option.isEmpty()) return;
      if (option.get() == ButtonType.CANCEL) return;
    }
    setRandomKey();
    setBeehive();
  }

  @FXML
  private void handleClickHelpButton() {
    getHelp();
  }

  @FXML
  private TextField inputTextField;

  @FXML
  private TextArea scoreTextArea;

  @FXML
  private Button enterButton, deleteButton, replayButton, helpButton;

  @FXML
  private Button Button0, Button1, Button2, Button3, Button4, Button5, Button6;

  @FXML
  private TextArea charArea0, charArea1, charArea2, charArea3, charArea4, charArea5, charArea6;

  @FXML
  private ListView<String> wordsFound;
}