package fr.ensimag.deca;

import java.io.File;
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

    public boolean getNoCheck() {
        return noCheck;
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
                        }
                        break;
                    case "-P":
                            parallel = true;
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
                        }
                        break;
                    case "-n":
                        noCheck = true;
                        break;
                    case "-r":
                        try {
                            x = Integer.parseInt(args[i + 1]);
                            if (x >= 4 && x <= 16) {// 4 <= X <= 16
                                //  System.out.println("X = " + x);
                                registers = true;
                                x = x - 1;
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
                default:
                    if (!args[i].matches("[0-9]|1[0-2]")) {//dans le cas où on met la valeur de X pour la option -r
                        //Si un fichier apparaît plusieurs fois sur la ligne de commande, ne le ajouter que une seule fois
                        if (!sourceFiles.isEmpty()) {
                            boolean isIn = false;
                            for (File file : sourceFiles) {
                                if (file.getPath().matches(args[i])) {
                                    isIn = true;
                                }
                            }
                            if (!isIn) {//if the file is not already in sourceFiles we add it
                                sourceFiles.add(new File(args[i]));
                            }
                        } else {//if sourceFiles is empty, just add it
                            sourceFiles.add(new File(args[i]));
                        }
                    }
            }
            i++;
    }
        
        if(sourceFiles.isEmpty() && args.length > 0 && !printBanner){
            System.err.println("No valid path for the source file was detected");
            System.exit(1);
        }else if(!sourceFiles.isEmpty() && args.length > 0 && parallel && sourceFiles.size() < 2){
            System.err.println("The -P option needs at least 2 source files");
            System.exit(1);
        }
        
        
        // DONE! A FAIRE : parcourir args pour positionner les options correctement.
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
            case QUIET:
            	logger.setLevel(Level.OFF);
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

    protected void displayUsage() {
        System.out.println("\n Options pour le compilateur\n");
        System.out.println("  -a\n    Compiler le fichier source et generer le fichier .ass");
        System.out.println("  -b\n    Affiche une bannière indiquant le nom de l’équipe.");
        System.out.println("  -p\n    Arrête decac après l’étape de construction de\n"
                + "    l’arbre, et affiche la décompilation de ce dernier.");
        System.out.println("  -v\n    Arrête decac après l’étape de vérifications.");
        System.out.println("  -n\n    Supprime les tests de débordement à l’exécution.");
        System.out.println("  -r\n    Limite les registres banalisés disponibles.");
        System.out.println("  -d\n    Active les traces de debug.");
        System.out.println("  -P\n    S’il y a plusieurs fichiers sources,\n"
                + "    lance la compilation des fichiers en parallèle.\n");
    }
}

