package ancor2gui.views;

import ancor2gui.controller.Controller;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Concordancier extends View{

    private ancor2gui.model.Concordancier model;

    public String getTitle() {
        return "Concordancier";
    }
    public static Concordancier init(Controller c, Stage stage){
        Concordancier view = (Concordancier) View.init(c,stage, "Concordancier", "Concordancier.fxml");
        view.model = new ancor2gui.model.Concordancier();
        return view;
    }

    public void fichierOuvrir(ActionEvent actionEvent) {
        FileChooser fchoo = new FileChooser();
        fchoo.setTitle("Ouvrir un fichier csv: Liste de mentions");
        fchoo.getExtensionFilters().add(new FileChooser.ExtensionFilter("LOM csv file (*.csv)","*.csv"));
        fchoo.getExtensionFilters().add(new FileChooser.ExtensionFilter("All","*"));
        File lom = fchoo.showOpenDialog(this.stage);
        model.loadLOM(lom);
    }

    public void Fermer(ActionEvent actionEvent) {
    }
}
