package ancor2gui.model.workers;

import ancor2gui.model.InvalidLOMException;
import ancor2gui.model.Mention;
import ancor2gui.model.MentionsList;
import javafx.concurrent.Task;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Task loading list of mentions (coming from ancor2 core)
 * @author Augustin Voisin-Marras
 */
public class LomLoader extends Task<Void>{

    private static final String LOM_HEADER = "^ANCOR_ID\\s*CONLL_ID\\s*CHAIN_ID\\s*NUM_ANTECEDENTS_BEFORE_FEST_FIRST";

    /**
     * Getter for the mention list
     * @return The mention list
     */
    public MentionsList getMentionslist() {
        return mentionslist;
    }

    private MentionsList mentionslist;
    private final File lom;
    private final Callback callback;


    /**
     * Create a LomLoader task
     * @param lom List of mention file
     * @param callback Callback implementation. Ran when loading is terminated.
     */
    public LomLoader(File lom, Callback callback){
        this.lom = lom;
        this.callback = callback;
    }

    /**
     * Task's core method
     * @return null
     */
    public Void call() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(lom));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert in != null;
        String line;
        mentionslist = new MentionsList();
        //noinspection TryWithIdenticalCatches
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
