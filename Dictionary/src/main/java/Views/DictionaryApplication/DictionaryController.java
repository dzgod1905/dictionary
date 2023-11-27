package Views.DictionaryApplication;

import Manager.DictionaryManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    private String current = "";
    public static boolean CHANGED = false;
    public static boolean EDITING = false;
    private final Warnings warnings = new Warnings();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchWordButton.setOnAction(event -> showComponent("/Views/SearcherGUI.fxml"));
        addWordButton.setOnAction(event -> showComponent("/Views/AdderGUI.fxml"));
        translateButton.setOnAction(event -> showComponent("/Views/TranslatorGUI.fxml"));

        tooltip1.setShowDelay(Duration.seconds(0.2));
        tooltip2.setShowDelay(Duration.seconds(0.2));
        tooltip3.setShowDelay(Duration.seconds(0.2));
        showComponent("/Views/SearcherGUI.fxml");

        closeButton.setOnMouseClicked(e -> quit());
    }

    private void quit() {
        if (EDITING) {
            warnings.showWarningInfo("Warning", "Bạn có thay đổi chưa được ghi nhận.");
            return;
        }
        if (CHANGED) {
            Alert exportConfirm = warnings.alertConfirmation("Export dictionary",
                    "Bạn đã thay đổi nội dung từ điển.\nBạn có muốn lưu lại thay đổi trên tệp văn bản không?");
            Optional<ButtonType> option = exportConfirm.showAndWait();
            if (option.isEmpty()) return;
            if (option.get() == ButtonType.OK) DictionaryManager.getInstance().exportToFile();
        }
        System.exit(0);
    }

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    private void showComponent(String path) {
        if (current.equals(path)) return;
        if (EDITING) {
            warnings.showWarningInfo("Cảnh báo", "Bạn có thay đổi chưa được ghi nhận.");
            return;
        }
        current = path;
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource(current)));
            setNode(component);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private Tooltip tooltip1, tooltip2, tooltip3;

    @FXML
    private Button addWordButton, translateButton, searchWordButton, closeButton;

    @FXML
    private AnchorPane container;
}
