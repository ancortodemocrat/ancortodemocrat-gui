package ancor2gui.model;

import com.democrat.ancor.speech.Turn;
import com.democrat.ancortodemocrat.Text;
import com.democrat.ancortodemocrat.element.Annotation;
import com.democrat.ancortodemocrat.element.Schema;
import com.democrat.ancortodemocrat.element.Unit;
import javafx.scene.control.Spinner;

/**
 * Extension de la classe Unit contenant l'annotation correspondante
 */
public class AUnit extends Unit{
    private final Annotation a;
    private Text text;

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

    public String getText(){
        if((Unit)this instanceof Schema)
            return this.text.getContentFromUnit(this.a,((Schema)(Unit)this).getUnitWhereFeatureNotNull(a));
        return this.text.getContentFromUnit(this.a,this);
    }

    public String getContextText(Integer context_len){
        return this.text.getContent().substring(
                this.getPositioning().getStart().getSinglePosition().getIndex() - context_len,
                this.getPositioning().getEnd().getSinglePosition().getIndex() +context_len);
    }

    public String getPreText(Integer context_len) {
        return this.text.getPreUnit(a,this,context_len);
    }

    public String getSufText(Integer context_len) {
        return this.text.getSufUnit(a,this,context_len);
    }

    public String getTurn() {
        Turn t = this.text.getTurnCorresponding(this.a, this);
        return t == null ? "unkn" : t.getSpeaker();
    }
}
