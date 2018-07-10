package ancor2gui.model;

import com.democrat.ancortodemocrat.element.Unit;
import javafx.collections.transformation.SortedList;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Chaine extends ArrayList<Mention>{
    private final HashMap<String, AUnit> aUnitById;

    public Chaine(HashMap<String, AUnit> aUnitbyi, Mention mention){
        super();
        this.aUnitById=aUnitbyi;
        this.add(mention);
    }

    public final Mention getPremiereMention(){
        sort();
        return this.get(0);
    }

    public final AUnit getPremiereUnit(){
        AUnit au =  this.aUnitById.get(getPremiereMention().getAncorID());
        return au;
    }

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

    public void sort() {
        Collections.sort(this);
    }
}
