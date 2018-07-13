package ancor2gui.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mention
 * @author Augustin Voisin-Marras
 */
public class Mention implements Comparable<Mention> {
    private final String ancorID;
    private final Integer conllID;
    private final Integer chainID;

    /**
     *
     * @return The AncorID of the mention
     */
    public final String getAncorID() {
        return ancorID;
    }

    /**
     *
     * @return The ConllID of the mention (ID contained in connll files)
     */
    @SuppressWarnings("unused")
    public final Integer getConllID() {
        return conllID;
    }

    /**
     *
     * @return The chain ID of the mention
     */
    @SuppressWarnings("WeakerAccess")
    public final Integer getChainID() {
        return chainID;
    }

    /**
     *
     * @return The number of possible antecedents before best-first choice during chaining
     */
    @SuppressWarnings("unused")
    public final Integer getNum_antecedents() {
        return num_antecedents;
    }

    private final Integer num_antecedents;
    private static final String MENTION_LINE = "^([a-zA-Z0-9_]+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)";


    /**
     * Constructor
     * @param lom_line LOM-file line to parse for creating this mention
     * @param n Line number. Used for error messages. May be set to a static value.
     * @throws InvalidLOMException If not able to parse
     */
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
     * @param mention la mention avec laquelle comparer this
     * @return résultat de la comparaison
     */
    @Override
    public int compareTo(Mention mention) {
        return getAncorID().compareTo(mention.getAncorID());
    }
}
