package ancor2gui.controller;

import ancor2gui.Args;
import ancor2gui.Main;
import javafx.stage.Stage;

/**
 * Controller for Concordancier module
 * @author Augustin Voisin-Marras
 */
public class Concordancier extends Controller{
    /**
     * Create Concordancier window
     * @param stage The stage to display the concordancier in
     */
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
