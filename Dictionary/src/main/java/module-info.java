module Dictionary {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires voicerss.tts;
    requires java.desktop;

    opens Views to javafx.fxml, javafx.graphics;
    opens Views.DictionaryApplication to javafx.fxml, javafx.graphics;
}