package Views.DictionaryApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchWordButton.setOnAction(event -> showComponent("/Views/SearcherGUI.fxml"));
        addWordButton.setOnAction(event -> showComponent("/Views/AdderGUI.fxml"));
        translateButton.setOnAction(event -> showComponent("/Views/TranslatorGUI.fxml"));

        tooltip1.setShowDelay(Duration.seconds(0.2));
        tooltip2.setShowDelay(Duration.seconds(0.2));
        tooltip3.setShowDelay(Duration.seconds(0.2));
        showComponent("/Views/SearcherGUI.fxml");

        closeButton.setOnMouseClicked(e -> System.exit(0));
    }

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource(path)));
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
