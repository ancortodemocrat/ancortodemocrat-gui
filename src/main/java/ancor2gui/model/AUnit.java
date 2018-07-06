package ancor2gui.model;

import com.democrat.ancortodemocrat.Text;
import com.democrat.ancortodemocrat.element.Annotation;
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
        return this.text.getContentFromUnit(this.a,this);
    }

    public String getContextText(Integer context_len){
        return this.text.getContent().substring(
                this.getPositioning().getStart().getSinglePosition().getIndex() - context_len,
                this.getPositioning().getEnd().getSinglePosition().getIndex() +context_len);
    }

    public String getPreText(Integer context_len) {
        return this.text.getContent().substring(
                this.getPositioning().getStart().getSinglePosition().getIndex() - context_len,
                this.getPositioning().getStart().getSinglePosition().getIndex());
    }

    public String getNexText(Integer context_len) {
        return this.text.getContent().substring(
                this.getPositioning().getEnd().getSinglePosition().getIndex(),
                this.getPositioning().getEnd().getSinglePosition().getIndex() + context_len);
    }
}
