package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }
    
    public boolean getVerification(){
        return verification;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private boolean verification = false;
    private boolean noCheck = false;
    private List<File> sourceFiles = new ArrayList<File>();

    
    public void parseArgs(String[] args) throws CLIException {
        int i = 0;
        while (i < args.length) {
            if (args[i].matches("[a-zA-ZÀ-ÿ0-9]+/[a-zA-ZÀ-ÿ0-9]+(.{1}d{1}e{1}c{1}a{1})")) {
                sourceFiles.add(new File(args[i]));
                //System.out.println("File added");
            } else {
                switch (args[i]) {
                    case "-b":
                        printBanner = true;
                        break;
                    case "-p":
                        System.out.println("Option -p ");
                        break;
                    case "-P":
                        parallel = true;
                        System.out.println("Option -P");
                        break;
                    case "-d":
                        debug++;
                        break;
                    case "-v":
                        verification = true;
                        System.out.println("Option -v");
                        break;
                    case "-n":
                        noCheck = true;
                        break;

                }
            }

            i++;
        }
        
        
        // DONE! A FAIRE : parcourir args pour positionner les options correctement.
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
            case QUIET:
                break; // keep default
            case INFO:
                logger.setLevel(Level.INFO);
                break;
            case DEBUG:
                logger.setLevel(Level.DEBUG);
                break;
            case TRACE:
                logger.setLevel(Level.TRACE);
                break;
            default:
                logger.setLevel(Level.ALL);
                break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

    }

    protected void displayUsage() {//what should it do?
        //throw new UnsupportedOperationException("not yet implemented");
        System.out.println("");
    }
}
