package ancor2gui.controller;

import ancor2gui.views.View;
import javafx.stage.Stage;

public class Controller {

    protected final Stage stage;
    public View view;


    public Controller(Stage stage) {
        this.stage=stage;
    }

    public View getView() {
        return view;
    }
}
