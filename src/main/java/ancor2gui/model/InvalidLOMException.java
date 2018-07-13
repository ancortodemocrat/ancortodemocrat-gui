package ancor2gui.model;

/**
 * Exception Throwed when List of mentions file has an invalid format
 * @author Augustin Voisin-Marras
 */
public class InvalidLOMException extends Exception {
    public InvalidLOMException(){this("");}
    InvalidLOMException(String err){
        super("List of Mentions File is invalid: "+err);
    }
}
