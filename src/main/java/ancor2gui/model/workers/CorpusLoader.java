package ancor2gui.model.workers;

import ancor2gui.model.AUnit;
import ancor2gui.views.View;
import com.democrat.ancortodemocrat.Corpus;
import com.democrat.ancortodemocrat.Text;
import com.democrat.ancortodemocrat.element.Annotation;
import com.democrat.ancortodemocrat.element.Schema;
import com.democrat.ancortodemocrat.element.Unit;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class CorpusLoader extends Task<Void> {
    private final File rep;
    private final Callback callback;
    private final HashMap<String,AUnit> unitParId;

    public CorpusLoader(File rep, final View view, Callback callback){
        this.rep = rep;
        this.callback = callback;
        view.bindProgress(this);
        this.unitParId = new HashMap<>();
    }

    public Void call() {
        Corpus corpus = new Corpus(rep.getPath());
        corpus.loadAnnotation();
        corpus.loadText();
        List<Annotation> annotations = corpus.getAnnotation();

        for(Annotation a : annotations) {
            List<Unit> units = a.getUnit();
            for(Schema s : a.getSchema()){
                units.addAll(s.getUnitList(a));
            }

            Path ac_file = Paths.get(rep.getPath(),"ac_fichiers",a.getFileName()+".ac");
            Text text;
            try {
                text = new Text(ac_file.toString(), String.join("",Files.readAllLines(ac_file)));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            double i = 0;
            double max = units.size();
            for (Unit u : units){
                this.unitParId.put(u.getId(),new AUnit(a,text,u));
                if(u.getId().equals("TXT_IMPORTER_1355234345459"))
                    continue;
                super.updateProgress(i,max);
                i++;
            }
            super.updateProgress(1d,1d);
        }
        callback.callback(this);
        return null;
    }

    public HashMap<String, AUnit> getAUnitParId() {
        return unitParId;
    }

}
