package ancor2gui;

public class Main{

    public static final int WIDTH = 900;
    public static final int HEIGHT = 500;
    public static Args params = null;

    /**
     * parse args and launch asked module
     */
    public static void main(String [] args) {
        params = new Args(args);
        if(params.concordancier())
            ConcordancierApp.main(args);
    }


}
