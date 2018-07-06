package ancor2gui.model.workers;

import ancor2gui.model.AUnit;
import ancor2gui.views.View;
import com.democrat.ancor.speech.Turn;
import com.democrat.ancortodemocrat.Corpus;
import com.democrat.ancortodemocrat.Text;
import com.democrat.ancortodemocrat.element.Annotation;
import com.democrat.ancortodemocrat.element.Unit;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CorpusLoader implements Runnable {
    private final File rep;
    private final Callback callback;
    private final SimpleDoubleProperty progress;
    private ArrayList<String> text;
    private HashMap<String,AUnit> unitParId;

    public CorpusLoader(File rep, final View view, Callback callback){
        this.rep = rep;
        this.callback = callback;
        this.text = new ArrayList<>();
        this.progress = new SimpleDoubleProperty();
        view.bindProgress(this.progress);
        this.unitParId = new HashMap<>();
    }

    public void run() {
        Corpus corpus = new Corpus(rep.getPath());
        corpus.loadAnnotation();
        corpus.loadText();
        List<Annotation> annotations = corpus.getAnnotation();

        for(Annotation a : annotations) {
            List<Unit> units = a.getUnit();
            Path ac_file = Paths.get(rep.getPath(),"ac_fichiers",a.getFileName()+".ac");
            Text text = null;
            try {
                text = new Text(ac_file.toString(), String.join("",Files.readAllLines(ac_file)));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            double i = 0;
            double max = units.size();
            for (Unit u : units){
                this.unitParId.put(u.getId(),new AUnit(a,text,u));
                if(u.getId().equals("TXT_IMPORTER_1355234345459"))
                    continue;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.progress.set(i / max);
                i++;

                String content = text.getContentFromUnit(a,u);

                String spk="undefined";
                Turn turn = null;
                if(null != (turn = text.getTurnCorresponding(a,u)))
                    spk = turn.getSpeaker();

                String line = String.join("\t",spk,content);
                this.text.add(line);
            }
            this.progress.set(1d);
        }
        callback.callback(this);
        return;
    }

    public List<String> getText() {
        return this.text;
    }

    public HashMap<String, AUnit> getAUnitParId() {
        return unitParId;
    }

}
