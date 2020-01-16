/* A manual test for the initial sketch of parsing included in 
 * students skeleton.
 * 
 */
package fr.ensimag.deca.syntax.Tests_oracle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.v4.runtime.CommonTokenStream;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tree.*;

public class assign {
	
	private static DecacCompiler compiler = new DecacCompiler(null,null);
	
	static String currentUsersDir = System.getProperty("user.dir");
	
    /* script pour le test de just-an-int.deca */
    public static AbstractProgram ProgInit() {
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
        AbstractLValue  left_operande = varName;
        AbstractExpr right_operande = new IntLiteral(5);
        linst.add(new Assign(left_operande, right_operande));
		
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
    
    public static void genSyntaxTreeParserFile(String[] sourceName) throws IOException {
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
        	PrintStream ps = new PrintStream(new FileOutputStream("/user/0/cassagth/Documents/gl48/src/test/java/fr/ensimag/deca/syntax/Tests_oracle/obtained/assign.txt", true));
            prog.prettyPrint(ps);
            ps.close();
        }
    }
    
    public static void genSyntaxTreeManualFile(AbstractProgram source) throws IOException {        
        PrintStream ps = new PrintStream(new FileOutputStream("/user/0/cassagth/Documents/gl48/src/test/java/fr/ensimag/deca/syntax/Tests_oracle/expected/assign.txt", true));
        source.prettyPrint(ps);
        ps.close();
        
    }

    public static void main(String args[]) throws IOException {
    	int userspathlength = currentUsersDir.length();
    	String[] fichier_teste = new String[1];
    	String path = "/user/0/cassagth/Documents/gl48/src/test/deca/syntax/valid/created/assign.deca";
        fichier_teste[0] = path;
        
        BufferedReader in = new BufferedReader(new FileReader(fichier_teste[0]));
		String line;
		while ((line = in.readLine()) != null)
		{
	      // Afficher le contenu du fichier
			  System.out.println (line);
		}
		in.close();
		
		AbstractProgram source = ProgInit();
        genSyntaxTreeManualFile(source);
        
        genSyntaxTreeParserFile(fichier_teste);
    }
        
   
}


