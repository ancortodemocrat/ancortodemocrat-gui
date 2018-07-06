package ancor2gui.controller;

import ancor2gui.Args;
import ancor2gui.Main;
import javafx.stage.Stage;

public class Concordancier extends Controller{
    private final ancor2gui.views.Concordancier view;
    private final Args params;

    public Concordancier(Stage stage) {
        super(stage);
        params = Main.params;
        this.view = ancor2gui.views.Concordancier.init(this,stage);
        if(params != null) {
            if (params.lom != null)
                this.view.setLOM(params.lom);
            if (params.corp != null)
                this.view.setCorp(params.corp);
            if(params.update)
                this.view.updateConcordancier(null);
        }
    }
}
