package ancor2gui;

import ancor2gui.controller.Concordancier;
import javafx.application.Application;
import javafx.stage.Stage;

import static ancor2gui.Main.HEIGHT;
import static ancor2gui.Main.WIDTH;

public class ConcordancierApp extends Application{
    private static String[] args;
    private static Args params;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        new Concordancier(stage);
    }

}