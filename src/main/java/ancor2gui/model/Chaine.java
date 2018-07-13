package ancor2gui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Managing Chains
 * @author Augustin Voisin-Marras
 */
public class Chaine extends ArrayList<Mention>{
    private final HashMap<String, AUnit> aUnitById;

    /**
     * Constructor
     * @param aUnitbyi map of AUnits (AUnit associated with their AncorID)
     * @param mention First mention of the chain
     */
    Chaine(HashMap<String, AUnit> aUnitbyi, Mention mention){
        super();
        this.aUnitById=aUnitbyi;
        this.add(mention);
    }

    /**
     *
     * @return the first mention of the chain
     */
    public final Mention getPremiereMention(){
        sort();
        return this.get(0);
    }

    /**
     *
     * @return The first Unit of the chain (Unit corresponding to the first mention)
     */
    public final AUnit getPremiereUnit(){
        return this.aUnitById.get(getPremiereMention().getAncorID());
    }

    /**
     *
     * @return List of AUnits contained in this chain
     */
    public ArrayList<AUnit> getAUnits(){
        ArrayList<AUnit> aunits = new ArrayList<>();
        for(Mention m : this){
            AUnit au = this.aUnitById.get(m.getAncorID());
            if(au == null)
                System.err.println("No unit found for mention id : "+ m.getAncorID());
            else
                aunits.add(au);
        }
        return aunits;
    }

    /**
     * Sorts The AUnits by order of apparition in the text
     */
    public void sort() {
        Collections.sort(this);
    }
}
