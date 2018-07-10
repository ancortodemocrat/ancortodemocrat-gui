package ancor2gui.model.workers;

import ancor2gui.model.InvalidLOMException;
import ancor2gui.model.Mention;
import ancor2gui.model.MentionsList;
import javafx.concurrent.Task;

import java.io.*;
import java.util.regex.Pattern;

public class LomLoader extends Task<Void>{

    public static final String LOM_HEADER = "^ANCOR_ID\\s*CONLL_ID\\s*CHAIN_ID\\s*NUM_ANTECEDENTS_BEFORE_FEST_FIRST";

    public MentionsList getMentionslist() {
        return mentionslist;
    }

    private MentionsList mentionslist;
    private File lom;
    private Callback callback;

    public LomLoader(File lom, Callback callback){
        this.lom = lom;
        this.callback = callback;
    }

    @Override
    public Void call() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(lom));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        mentionslist = new MentionsList();
        try {
            line = in.readLine();
            if(!Pattern.matches(LOM_HEADER, line)){
                throw new InvalidLOMException();
            }
            int n = 1;
            while(null != (line = in.readLine())){
                mentionslist.add(new Mention(line, n++));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidLOMException e) {
            e.printStackTrace();
        }
        callback.callback(this);
        return null;
    }
}
