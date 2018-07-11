package ancor2gui.controller;

import ancor2gui.Args;
import ancor2gui.Main;
import javafx.stage.Stage;

public class Concordancier extends Controller{

    public Concordancier(Stage stage) {
        super();
        Args params = Main.params;
        ancor2gui.views.Concordancier view = ancor2gui.views.Concordancier.init(stage);
        if(params != null) {
            if (params.lom != null)
                view.setLOM(params.lom);
            if (params.corp != null)
                view.setCorp(params.corp);
            if(params.update)
                view.updateConcordancier();
        }
    }
}
