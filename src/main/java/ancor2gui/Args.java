package ancor2gui;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.Arrays;

public class Args extends Options {
    private static final String CONCORDANCIER_ARG = "concordancier";
    private String module = null;
    public File lom = null;
    public File corp = null;
    public boolean update;

    Args(String[] args) throws IllegalArgumentException {
        super();
        System.out.println(Arrays.toString(args));
        if (args.length == 0){
            printHelp();
        }else {

            module = args[0];
            switch (module) {
                case CONCORDANCIER_ARG:
                    addConcordancierOptionGroup();
            }

            addOption(Option.builder("h")
                    .longOpt("help")
                    .hasArg(false)
                    .required(false)
                    .build());

            parse(args);

        }
    }

    private void parse(String[] args) {
        CommandLineParser commandline = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = commandline.parse(this, args);
        } catch (ParseException e) {
            printHelp();
            e.printStackTrace();
            System.exit(0);
        }

        if(cmd.hasOption('h'))
            printHelp();

        if(cmd.hasOption("lom"))
            lom = new File(cmd.getOptionValue("lom"));
        if(cmd.hasOption("c"))
            corp = new File(cmd.getOptionValue("c"));

        update = cmd.hasOption("u");

    }

    private void addConcordancierOptionGroup() {
        addOption(Option.builder("lom")
                .longOpt("list-of-mentions")
                .desc("Csv file containing data in list of mentions.")
                .numberOfArgs(1)
                .required(false)
                .build());

        addOption(Option.builder("c")
                .longOpt("corpus")
                .desc("Repertory containing corpus related to list of mentions")
                .numberOfArgs(1)
                .required(false)
                .build());
        addOption(Option.builder("u")
                .longOpt("update")
                .desc("display on windows openning")
                .hasArg(false)
                .required(false)
                .build());
    }

    private void printHelp() {
        String header = "Availables modules: \n"+CONCORDANCIER_ARG;
        String footer = "";
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("java -jar ancortodemocrat-gui.jar <module>", header, this, footer, true);
        System.exit(1);
    }

    boolean concordancier(){
        return module.equals(CONCORDANCIER_ARG);
    }
}
