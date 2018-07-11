package ancor2gui.views;

import ancor2gui.Main;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class View {
    Stage stage;

    @FXML
    protected ProgressIndicator footer_progress;

    static View init(Stage stage, @SuppressWarnings("SameParameterValue") String fxml){
        Parent root = null;
        URL location = View.class.getResource("/views/" + fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        View vue = fxmlLoader.getController();

        assert root != null;
        stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        stage.setTitle(vue.getTitle());
        stage.show();

        vue.stage = stage;
        return vue;
    }

    @SuppressWarnings("SameReturnValue")
    protected abstract String getTitle();

    public void bindProgress(Task task) {
        this.footer_progress.progressProperty().bind(task.progressProperty());
    }
}
