package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tree.AbstractProgram;
import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Driver to test the Parser (and lexer).
 *
 * @author Ensimag
 * @date 01/01/2020
 */
public class ManualTestSynt {
    
    public static void main(String[] args) throws IOException, ContextualError {
        // Uncomment the following line to activate debug traces
        // unconditionally for test_synt
        Logger.getRootLogger().setLevel(Level.OFF);
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
        } else {
            prog.prettyPrint(System.out);
            prog.checkAllLocations();
            prog.verifyProgram(decacCompiler);
            prog.decompile(System.out);
            prog.codeGenProgram(decacCompiler);
            String assembleur = decacCompiler.displayIMAProgram(); 
            System.out.println(assembleur);
        }
    }
}
