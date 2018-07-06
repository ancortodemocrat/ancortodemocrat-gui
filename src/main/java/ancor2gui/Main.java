package ancor2gui;

public class Main{

    public static int WIDTH = 900;
    public static int HEIGHT = 500;
    public static Args params = null;
    public static String args = null;

    public static void main(String [] args) {
        params = new Args(args);
        args = args;
        if(params.concordancier())
            ConcordancierApp.main(args);
    }


}
