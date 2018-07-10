package ancor2gui.views;

import ancor2gui.Args;
import ancor2gui.controller.Controller;
import ancor2gui.model.AUnit;
import ancor2gui.model.Chaine;
import ancor2gui.model.InvalidLOMException;
import ancor2gui.model.Mention;
import com.democrat.ancortodemocrat.element.Schema;
import com.democrat.ancortodemocrat.element.Unit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import weka.core.stopwords.Null;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Concordancier extends View{

    public Spinner nb_caracteres_context;
    public ListView list_chaines;
    private ancor2gui.model.Concordancier model;

    @FXML
    private Label lom_lbl;

    @FXML
    private Label corp_lbl;

    @FXML
    private TextFlow textflow;

    @FXML
    private ToggleGroup select_system_gold;

    private File LOM;
    private File corp;
    private ArrayList<Integer> keys_order;


    public String getTitle() {
        return "Concordancier";
    }

    public static Concordancier init(Controller c, Stage stage){
        Concordancier view = (Concordancier) View.init(c,stage, "Concordancier", "Concordancier.fxml");

        view.model = new ancor2gui.model.Concordancier(view);
        return view;

    }

    public void fichierOuvrir(ActionEvent actionEvent){

    }

    public void lom_choose_load(ActionEvent actionEvent) {
        FileChooser fchoo = new FileChooser();
        fchoo.setInitialDirectory(new File("/tmp/rjc18/t6/chaines/"));
        fchoo.setTitle("Ouvrir un fichier csv: Liste de mentions");
        fchoo.getExtensionFilters().add(new FileChooser.ExtensionFilter("LOM csv file (*.csv)","*.csv"));
        fchoo.getExtensionFilters().add(new FileChooser.ExtensionFilter("All","*"));
        File lom = fchoo.showOpenDialog(this.stage);

        if(lom == null) {
            return ;
        }
        lom_lbl.setText(lom.getPath());
        model.setLom(lom);
    }

    public void corpus_choose_load(ActionEvent actionEvent) {
        DirectoryChooser dchoo = new DirectoryChooser();
        dchoo.setInitialDirectory(new File("/tp/Augustin/Ancor/"));
        dchoo.setTitle("Ouvrir un corpus: Répertoire contenant aa_fichiers/ at ac_fichiers/");
        File corp = dchoo.showDialog(this.stage);
        if(corp == null)
            return ;
        corp_lbl.setText(corp.getPath());
        model.setCorp(corp);
    }

    public void Fermer(ActionEvent actionEvent) {
    }

    public void addText(List<String> text) {

        List<Node> childs = this.textflow.getChildren();
        Platform.runLater(()-> {
            for (String s : text) {
                Text t = new Text(s+"\n");
                t.setFill(Color.BLACK);
                t.setFont(new Font(12));
                childs.add(t);
            }
        });
        this.textflow.autosize();
    }

    public void updateConcordancier(ActionEvent actionEvent) {
        cleanText();
        this.list_chaines.getItems().clear();
        model.update();
    }

    private void cleanText() {
        Platform.runLater(()-> textflow.getChildren().clear());
    }

    public void setLOM(File LOM) {
        this.lom_lbl.setText(LOM.getPath());
        this.model.setLom(LOM);
        System.out.println("lom set to "+LOM);
    }

    public void setCorp(File corp) {
        this.corp_lbl.setText(corp.getPath());
        this.model.setCorp(corp);
        System.out.println("corp set to "+corp);
    }

    public Integer getContextLen(){
        return Integer.parseInt(this.nb_caracteres_context.getValue().toString());
    }

    public void updateChaines() {
        Platform.runLater(()->{
            this.list_chaines.getItems().clear();
            this.keys_order = new ArrayList<>();
            for(Integer k : this.model.getChaines().keySet()){
                keys_order.add(k);
                Chaine ch = this.model.getChaines().get(k);
                try{
                    this.list_chaines.getItems().add(ch.getPremiereUnit().getText());
                } catch (NullPointerException e){
                    System.err.println("Error for mention "+ch.getPremiereMention().getAncorID()+": no corresponding unit found");
//                    e.printStackTrace();
                }
            }
        });
    }

    public void chainSelect(MouseEvent mouseEvent) {
        this.textflow.getChildren().clear();
        Integer k = this.list_chaines.getSelectionModel().getSelectedIndex();
        Chaine ch = this.model.getChaines().get(k);
        String turn = null, pre = null, mention = null,  suf = null;
        for(AUnit a : ch.getAUnits()){
            try {
                Font defFont = Font.font(Font.getDefault().getFamily()+" Mono");
                turn = a.getTurn();
                pre = a.getPreText(Integer.parseInt(this.nb_caracteres_context.getValue().toString()));
                suf = a.getSufText(Integer.parseInt(this.nb_caracteres_context.getValue().toString())) + "\n";
                mention = a.getText();
                Text turnT = new Text(turn + "    ");
                if (turn == "unkn")
                    turnT.setStroke(Color.RED);
                Text preT = new Text(pre + " ");
                preT.setFont(defFont);
                Text sufT = new Text(" " + suf);
                sufT.setFont(defFont);
                Text mentiontext = new Text(mention);
                mentiontext.setFont(Font.font(defFont.getFamily(), FontWeight.BOLD, defFont.getSize()));

                this.textflow.getChildren().addAll(turnT, preT, mentiontext, sufT);
            }catch(Exception e){
                System.out.println(turn + pre+mention+suf);
                e.printStackTrace();
            }
        }
    }

    public void system_gold_update(ActionEvent actionEvent) {

    }
}
