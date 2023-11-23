package Views.DictionaryApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class DictApp extends Application {
//    private double xOffset = 0;
//    private double yOffset = 0;

    @Override
    public void start(final Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/Views/DictionaryGUI.fxml")));

        stage.setTitle("Learning English");
        stage.initStyle(StageStyle.DECORATED);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
