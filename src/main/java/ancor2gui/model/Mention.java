package ancor2gui.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mention implements Comparable<Mention> {
    private final String ancorID;
    private final Integer conllID;
    private final Integer chainID;

    public final String getAncorID() {
        return ancorID;
    }

    public final Integer getConllID() {
        return conllID;
    }

    public final Integer getChainID() {
        return chainID;
    }

    public final Integer getNum_antecedents() {
        return num_antecedents;
    }

    private final Integer num_antecedents;
    private static final String MENTION_LINE = "^([a-zA-Z0-9_]+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)";


    public Mention(String lom_line, int n) throws InvalidLOMException {
        Pattern p = Pattern.compile(MENTION_LINE);
        Matcher m = p.matcher(lom_line);
        if(!m.matches() || m.groupCount() != 4)
            throw new InvalidLOMException(m.groupCount()+" columns only while 4 expected on line "+n+": \n"+lom_line);
        else{
            ancorID = m.group(1);
            conllID = Integer.parseInt(m.group(2));
            chainID = Integer.parseInt(m.group(3));
            this.num_antecedents = Integer.parseInt(m.group(4));
        }
    }

    /**
     * Compare deux mentions: ordre dans le texte
     * @param mention
     * @return
     */
    @Override
    public int compareTo(Mention mention) {
        return getAncorID().compareTo(mention.getAncorID());
    }
}
