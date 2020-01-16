package fr.ensimag.deca;

import fr.ensimag.ima.pseudocode.Register;
import java.io.File;
import java.io.PrintStream;
import java.text.ParseException;
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

    public boolean getRegisters() {
        return registers;
    }

    public int getX() {
        return x;
    }

    public boolean getParse() {
        return parse;
    }

    public boolean getAllCompilation() {
        return allCompilation;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private boolean verification = false;
    private boolean noCheck = false;
    private boolean parse = false;
    private boolean registers = false;
    private boolean allCompilation = false;
    private int x;
    private List<File> sourceFiles = new ArrayList<File>();

    
    public void parseArgs(String[] args) throws CLIException {
        int i = 0;
        while (i < args.length) {
                switch (args[i]) {
                    //option pour faire toute la compilation et 
                    //generer et montrer le fichier .ass
                    case "-a":
                        allCompilation = true; 
                        break;
                    case "-b":
                        if (i == 0 && args.length == 1) {
                            printBanner = true;
                        } else {
                            System.out.println("The -b option cannot "
                                    + "be used with other options");
                            System.exit(1);
                        }
                        break;
                    case "-p":
                        if (verification) {
                            System.err.println("Les options [-p] et [-v] "
                                    + "sont incompatibles.");
                            System.exit(1);
                        } else {
                        parse = true;
                        System.out.println("Option -p ");
                        }
                        break;
                    case "-P":
                            parallel = true;
                            System.out.println("Option -P");
                        
                        break;
                    case "-d":
                        debug++;
                        break;
                    case "-v":
                        if (parse) {
                            System.err.println("Les options [-p] et [-v] "
                                    + "sont incompatibles.");
                            System.exit(1);
                        } else {
                            verification = true;
                            System.out.println("Option -v");
                        }
                        break;
                    case "-n":
                        noCheck = true;
                        break;
                    case "-r":
                        try {
                            x = Integer.parseInt(args[i + 1]);
                            if (x >= 4 && x <= 16) {// 4 <= X <= 16
                                System.out.println("X = " + x);
                                registers = true;
                                x -= 1;
                            } else {
                                System.out.println("X must have a value between 4 and 16");
                                System.exit(1);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid value for X");
                            System.exit(1);
                        } catch (ArrayIndexOutOfBoundsException a) {
                            System.out.println("No value for X was entered");
                            System.exit(1);
                        }
                        break;
                    default: sourceFiles.add(new File(args[i]));
                }
            
        
            i++;
        }
        
        if(sourceFiles.isEmpty() && args.length > 0 && !printBanner){
            System.err.println("No valid path for the source file was detected");
            System.exit(1);
        }else if(!sourceFiles.isEmpty() && args.length > 0 && parallel && sourceFiles.size() < 2){
            System.err.println("The [-P] option needs at least 2 source files");
            System.exit(1);
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

    protected void displayUsage(){//what should it do?
        //throw new UnsupportedOperationException("not yet implemented");
        System.out.println("");
    }
}

