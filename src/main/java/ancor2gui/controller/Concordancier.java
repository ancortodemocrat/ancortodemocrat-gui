package ancor2gui.controller;

import javafx.stage.Stage;

public class Concordancier extends Controller{
    private final ancor2gui.views.Concordancier view;

    public Concordancier(Stage stage) {
        super(stage);
        this.view = ancor2gui.views.Concordancier.init(this,stage);
    }
}
