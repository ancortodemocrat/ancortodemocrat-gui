package ancor2gui.model;

import ancor2gui.model.workers.CorpusLoader;
import ancor2gui.model.workers.LomLoader;
import com.democrat.ancortodemocrat.element.Unit;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Concordancier {

    private ancor2gui.views.Concordancier vue;
    private File lom;
    private File corp;

    boolean computing = false;

    private HashMap<Integer,Chaine> chainesParId;
    private MentionsList mlist;

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

    public void update() {
        if (!computing) {
            computing = true;
            try {
                if (!lom.exists())
                    throw new FileNotFoundException("file does not exists: " + lom.getPath());
                if (!corp.isDirectory())
                    throw new FileNotFoundException("Répertoire contenant aa_fichiers et ac_fichiers attendu");

                CorpusLoader cl = new CorpusLoader(
                        corp, vue,
                        cltask -> {
                            this.concord(((CorpusLoader) cltask).getAUnitParId());
                            computing = false;
                        }
                );

                LomLoader ll = new LomLoader(lom, lltask -> {
                    mlist = ((LomLoader) lltask).getMentionslist();
                    new Thread(cl).run();
                });

                vue.bindProgress(cl);
                new Thread(ll).start();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Already computing...");
        }
    }

    private void concord(HashMap<String, AUnit> unitbyi) {
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
