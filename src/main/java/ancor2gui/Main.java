package ancor2gui;

import ancor2gui.controller.Concordancier;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    public static int WIDTH = 700;
    public static int HEIGHT = 500;

    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        new Concordancier(stage);
    }


}
