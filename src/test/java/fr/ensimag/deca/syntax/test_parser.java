/* A manual test for the initial sketch of parsing included in 
 * students skeleton.
 * 
 */
package fr.ensimag.deca.syntax;

import fr.ensimag.deca.tree.*;
import java.io.IOException;

import java.io.File;

import java.io.FileReader;
import java.io.BufferedReader;
import org.antlr.v4.runtime.CommonTokenStream;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;

public class test_parser {
	
	private static DecacCompiler compiler = new DecacCompiler(null,null);
    
	
	
    public static AbstractProgram initTestPrint() {
        ListInst linst = new ListInst();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(new ListDeclVar(),linst));
        ListExpr lexp1 = new ListExpr(), lexp2 = new ListExpr();
        linst.add(new Print(false,lexp1));
        linst.add(new Println(false,lexp2));
        lexp1.add(new StringLiteral("Hello "));
        lexp2.add(new StringLiteral("everybody !"));
        return source;
    }
    
    /* script pour le test de just-an-int.deca */
    public static AbstractProgram just_an_int() {
        ListInst linst = new ListInst();
        ListDeclVar lDecl = new ListDeclVar();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(lDecl,linst));

        AbstractIdentifier type = new Identifier(compiler.getSymbolTable().create("int"));
        AbstractIdentifier varName = new Identifier(compiler.getSymbolTable().create("a"));
        Initialization init = new Initialization(new IntLiteral(5));
        lDecl.add(new DeclVar(type, varName, init));
		
        return source;
    }
    
    public static void deleteLocation(Tree tree) {
    	Location location = null;
    	tree.setLocation(location);
    }
    
    protected static void deleteAllLocations(AbstractProgram prog) {
    	prog.iter(new TreeFunction() {
    		@Override
    		public void apply(Tree t) {
    			deleteLocation(t);
    		}
    	});
    }
    
    public static void genAbstractSyntaxTree(String[] sourceName) throws IOException {
    	DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(sourceName);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        deleteAllLocations(prog);
        if (prog == null) {
            System.exit(1);
        } else {
            prog.prettyPrint(System.out);
        }
    }
    
    public static void test(AbstractProgram source, String path) throws IOException {
        System.out.println("---- From the following program ----");
        String[] fichier_teste = new String[1];
        fichier_teste[0] = path;
        BufferedReader in = new BufferedReader(new FileReader(fichier_teste[0]));
		String line;
		while ((line = in.readLine()) != null)
		{
	      // Afficher le contenu du fichier
			  System.out.println (line);
		}
		in.close();
        System.out.println("---- From the following Abstract Syntax Tree manualy created ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following Abstract Syntax Tree by parsing ----");
        genAbstractSyntaxTree(fichier_teste);
    }

    public static void main(String args[]) throws IOException {
    	AbstractProgram sourceInt = just_an_int();
        test(sourceInt, "/user/0/cassagth/Documents/gl48/src/test/deca/syntax/valid/created/just-an-int.deca");
    }
        
   
}


