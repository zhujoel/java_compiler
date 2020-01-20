package fr.ensimag.deca.tree;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.CommonTokenStream;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;

/**
 * Driver to test the Parser (and lexer).
 *
 * @author Ensimag
 * @date 01/01/2020
 */
public class TestUltime {
    
    public static void main(String[] args) throws IOException, ContextualError  {
        // Uncomment the following line to activate debug traces
        // unconditionally for test_synt
        // Logger.getRootLogger().setLevel(Level.DEBUG);
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
            
            //prog.decompile(System.out);
            prog.verifyProgram(decacCompiler);
            prog.prettyPrint(System.out);
            prog.codeGenProgram(decacCompiler);
            String assembleur = decacCompiler.displayIMAProgram(); 
            System.out.println(assembleur);
        }
    }
}
