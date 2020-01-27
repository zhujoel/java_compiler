package fr.ensimag.deca.context;

import java.io.IOException;

import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tree.AbstractProgram;
/**
 * Cette serie de test se lance avec le bash chain_test_context, 
 * on passe en argument l'ensemble des fichiers à tester
 * Les fichiers que l'on teste sont ceux de src/test/deca/context/...
 * @author flichya
 *
 */

public class ManualChainTestContext {

	public static final String GREEN = "\033[0;32m";
	public static final String RED = "\033[0;31m"; 
	public static final String RESET = "\033[0m";
	
	
	public static void main(String[] args) throws IOException {
        Logger.getRootLogger().setLevel(Level.OFF);//Level.OFF pour desactiver les log
        String[] s = new String[1];
        for (String i : args) {
        	s[0] = i;
	        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(s);
	        CommonTokenStream tokens = new CommonTokenStream(lex);
	        DecaParser parser = new DecaParser(tokens);
	        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), null);
	        parser.setDecacCompiler(compiler);
	        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
	        if (prog == null) {
	            System.exit(1);
	            return; // Unreachable, but silents a warning.
	        }
	        try {
	            prog.verifyProgram(compiler);
	            System.out.println("Fichier: " + i);
	            System.out.println(GREEN + "\t status: correct context" + RESET);
	        
	        }
	        catch (Exception e) {
	        	System.out.println("Fichier: " + i);
	            System.out.println(RED + "\t status: incorrect context" + RESET);
	        }
        }
    }

}
