package fr.ensimag.deca;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class DecacMain {

    private static Logger LOG = Logger.getLogger(DecacMain.class);

    public static void main(String[] args) throws InterruptedException {
        // example log4j message.
    	
        //LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {//the command-line options are incorrect
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }

        if (options.getPrintBanner() && args.length == 1) {//option -b
            System.out.println("Équipe gl48");
        }

        if (options.getSourceFiles().isEmpty() && args.length == 0) {//sans arguments
            options.displayUsage();
        }

        if (options.getParallel()) {//option -P
            //System.out.println("Compilation en parallel");
            // creation d'un ensemble de fils d’exécution travailleurs
            // on utilise getRuntime().availableProcessors() pour obtenir
            // le nombre de processeurs sur la machine et créer le meme
            // nombre de fils d’exécution
            ExecutorService pool = newFixedThreadPool(getRuntime().availableProcessors());
            List<Callable<Boolean>> taskList = new ArrayList();
            
            try {
                for (File source : options.getSourceFiles()) {
                    // pour chaque fichier à compiler DecacCompiler est instancié
                    DecacCompiler compiler;
                    if (options.getRegisters()) {//for when the option -r is activated
                        compiler = new DecacCompiler(options, source, options.getX());
                    } else {//when the option -r is not activated
                        compiler = new DecacCompiler(options, source);
                    }
                    
                    Callable<Boolean> task = () -> {
                        boolean r = false;
                        if(options.getParse()){//arrête decac après l’étape de construction del’arbre
                           r = compiler.compileDecompile(); 
                        }else if(options.getAllCompilation()){//faire tout la compilation et generer le fichier .ass
                            r = compiler.compile();
                        }else if(options.getVerification()){
                            r = compiler.verify();
                        }
                        return r;
                    };
                    
                    taskList.add(task);

                }
                List<Future<Boolean>> results = pool.invokeAll(taskList);
            } catch (ExceptionInInitializerError e) {//Error when the source file path is invalid
                System.out.println("Error in the file path");
            }
        } else {//one or more files to compile but NOT in paralell
            try {
                for (File source : options.getSourceFiles()) {
                    DecacCompiler compiler;
                    if (options.getRegisters()) {//for when the option -r is activated
                        compiler = new DecacCompiler(options, source, options.getX());
                    } else {
                        compiler = new DecacCompiler(options, source);
                    }
                    if (options.getParse()) {//option -p is activated
                    	if (compiler.compileDecompile()) {
                            error = true;
                        }
                    } else if (options.getAllCompilation()) {//option -a is activated
                        //pour faire tout la compilation et generer le fichier .ass
                        if (compiler.compile()) {
                            error = true;
                        }
                    } else if (options.getVerification()) {//option -v is activated
                    	if(compiler.verify()){
                            error = true;
                        }
                    }
                }
            } catch (ExceptionInInitializerError e) {//Error when the source file path is invalid
                System.out.println("Error in the file path");
            }

        }
        System.exit(error ? 1 : 0);
    }
}
