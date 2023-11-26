package Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class DictApp extends Application {
    @Override
    public void start(final Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/Views/DictionaryGUI.fxml")));
        // real icon later
        stage.getIcons().add(new Image("/Utils/icons/add_32px.png"));
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
