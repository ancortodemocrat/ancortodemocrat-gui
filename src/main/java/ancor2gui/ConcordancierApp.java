package ancor2gui;

import ancor2gui.controller.Concordancier;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static ancor2gui.Main.HEIGHT;
import static ancor2gui.Main.WIDTH;

public class ConcordancierApp extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.getIcons().add(new Image(ConcordancierApp.class.getResourceAsStream("/icon.png")));
        new Concordancier(stage);
    }

}
