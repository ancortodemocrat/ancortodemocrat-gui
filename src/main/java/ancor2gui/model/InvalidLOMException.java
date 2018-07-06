package ancor2gui.model;

public class InvalidLOMException extends Exception {
    public InvalidLOMException(){this("");}
    public InvalidLOMException(String err){
        super("List of Mentions File is invalid: "+err);
    }
}
