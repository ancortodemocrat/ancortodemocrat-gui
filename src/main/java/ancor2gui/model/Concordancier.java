package ancor2gui.model;

import ancor2gui.model.workers.CorpusLoader;
import ancor2gui.model.workers.LomLoader;
import com.democrat.ancortodemocrat.element.Unit;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Concordancier {

    private ancor2gui.views.Concordancier vue;
    private File lom;
    private File corp;

    private HashMap<Integer,Chaine> chainesParId;

    public Concordancier(ancor2gui.views.Concordancier vue){
        this.vue = vue;
    }

    public void loadCorpus() throws FileNotFoundException {

        CorpusLoader loader = new CorpusLoader(
                corp, vue,
                task -> this.vue.addText(((CorpusLoader)task).getText())
        );
        Thread th = new Thread(loader);

        th.start();

    }

    public void setLom(File lom) {
        this.lom = lom;
    }

    public void setCorp(File corp) {
        this.corp = corp;
    }

    public synchronized void update() {
        try {
            if(!lom.exists())
                throw new FileNotFoundException("file does not exists: "+lom.getPath());
            if(!corp.isDirectory())
                throw new FileNotFoundException("Répertoire contenant aa_fichiers et ac_fichiers attendu");


            LomLoader ll = new LomLoader(lom, lltask -> {

                MentionsList mlist = ((LomLoader) lltask).getMentionslist();

                CorpusLoader cl = new CorpusLoader(
                    corp, vue,
                    cltask -> this.concord(mlist, ((CorpusLoader)cltask).getAUnitParId())
                );

                new Thread(cl).start();
            });

            new Thread(ll).start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void concord(MentionsList mlist, HashMap<String, AUnit> unitbyi) {
        if(this.chainesParId == null)
            this.chainesParId = new HashMap<>();
        else
            this.chainesParId.clear();
        // Construction des chaînes
        for(Mention m : mlist){
            if(this.chainesParId.containsKey(m.getChainID())){
                this.chainesParId.get(m.getChainID())
                        .add(m);
            } else {
                this.chainesParId.put(m.getChainID(),new Chaine(unitbyi,m));
            }
        }
        //Tri des chaînes
        for(Chaine ch : this.chainesParId.values()){
            ch.sort();
        }
        this.vue.updateChaines();
    }

    public HashMap<Integer, Chaine> getChaines() {
        return chainesParId;
    }
}
