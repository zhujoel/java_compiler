package fr.ensimag.deca;

import java.io.File;
import static java.lang.Runtime.getRuntime;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.util.concurrent.Executors.newFixedThreadPool;
import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) { 
            System.out.println("Équipe gl48");
        }
        if (options.getSourceFiles().isEmpty()) {
            System.out.println("Options pour le compilateur");
            System.out.println(" -b\n    Affiche une bannière indiquant le nom de l’équipe.");
            System.out.println(" -p\n    Arrête decac après l’étape de construction de\n" + 
                    "    l’arbre, et affiche la décompilation de ce dernier.");
            System.out.println(" -v\n    Arrête decac après l’étape de vérifications.");
            System.out.println(" -n\n    Supprime les tests de débordement à l’exécution.");
            System.out.println(" -r\n    Limite les registres banalisés disponibles.");
            System.out.println(" -d");
            System.out.println(" -P");
            //throw new UnsupportedOperationException("decac without argument not yet implemented");
        }
        if (options.getParallel()) {
            // creation d'un ensemble de fils d’exécution travailleurs
            // on utilise getRuntime().availableProcessors() pour obtenir
            // le nombre de processeurs sur la machine et créer le meme
            // nombre de fils d’exécution
            ExecutorService pool = newFixedThreadPool(getRuntime().availableProcessors());
            for (File source : options.getSourceFiles()) {
                // pour chaque fichier à compiler DecacCompiler est instancié
                DecacCompiler compiler = new DecacCompiler(options, source);
                
                Callable<Boolean> task = () -> {
                    boolean r = compiler.compile();
                    return r;
                    
                };
                
                pool.submit(task);
                
            }
            // DONE! A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            //throw new UnsupportedOperationException("Parallel build not yet implemented");
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
