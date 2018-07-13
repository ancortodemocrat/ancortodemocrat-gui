package ancor2gui.model;

import com.democrat.ancor.speech.Turn;
import com.democrat.ancortodemocrat.Text;
import com.democrat.ancortodemocrat.element.Annotation;
import com.democrat.ancortodemocrat.element.Schema;
import com.democrat.ancortodemocrat.element.Unit;

/**
 * Unit containing it's annotation and able to give it's text / pre/suftext
 * @author Augustin Voisin-Marras
 */
public class AUnit extends Unit{
    private final Annotation a;
    private final Text text;

    /**
     * Constructor
     * @param a Annotation corresponding to this
     * @param text Text instance initialised for this instance
     * @param u unit to copy
     */
    public AUnit(Annotation a, Text text, Unit u){
        super();
        this.text = text;
        super.setRefGoldChain(u.getRefGoldChain());
        super.setPositioning(u.getPositioning());
        super.setMetadata(u.getMetadata());
        super.setIdMention(u.getIdMention());
        super.setId(u.getId());
        super.setCharacterisation(u.getCharacterisation());
        this.a = a;
    }

    /**
     * Gives the text corresponding to this unit
     * @return The text corresponding to this unit
     */
    public String getText(){
        if((Unit)this instanceof Schema)
            return this.text.getContentFromUnit(this.a,((Schema)(Unit)this).getUnitWhereFeatureNotNull(a));
        return this.text.getContentFromUnit(this.a,this);
    }

    /**
     * Gives the text contained before this unit
     * @param context_len Number of characters to return
     * @return The text contained before this unit
     */
    public String getPreText(Integer context_len) {
        return this.text.getPreUnit(a,this,context_len);
    }

    /**
     * Gives the text contained after this unit
     * @param context_len Number of characters to return
     * @return The text contained after this unit
     */
    public String getSufText(Integer context_len) {
        return this.text.getSufUnit(a,this,context_len);
    }

    /**
     * Get turn name
     * @return String containing turn name. If unable do determine turn name, unkn will be returned
     */
    public String getTurn() {
        Turn t = this.text.getTurnCorresponding(this.a, this);
        return t == null ? "unkn" : t.getSpeaker();
    }
}
