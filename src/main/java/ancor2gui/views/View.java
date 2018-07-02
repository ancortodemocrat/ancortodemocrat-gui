package ancor2gui.views;

import ancor2gui.Main;
import ancor2gui.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class View {
    private Controller controller;
    public Stage stage;

    public static View init(Controller c, Stage stage, String title, String fxml){
        Parent root = null;
        URL location = View.class.getResource("/views/" + fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        stage.setTitle(title);
        stage.show();

        View vue = fxmlLoader.getController();
        vue.setController(c);
        vue.stage = stage;
        return vue;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public abstract String getTitle();

}
