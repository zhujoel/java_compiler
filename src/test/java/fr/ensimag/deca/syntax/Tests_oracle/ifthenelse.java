/* A manual test for the initial sketch of parsing included in 
 * students skeleton.
 * 
 */
package fr.ensimag.deca.syntax.Tests_oracle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.v4.runtime.CommonTokenStream;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tree.*;

public class ifthenelse {
	
	private static DecacCompiler compiler = new DecacCompiler(null,null);
	
	static String currentUsersDir = System.getProperty("user.dir");
    
    /* script pour le test de ifthenelse.deca */
    public static AbstractProgram ProgInit() {
        ListInst linst = new ListInst();
        ListDeclVar lDecl = new ListDeclVar();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(lDecl,linst));
        
        AbstractExpr condition = new Greater(new IntLiteral(5), new IntLiteral(2));
        ListInst then = new ListInst();
        ListExpr then_expr1 = new ListExpr();
        then_expr1.add(new StringLiteral("if boucle"));
        ListExpr then_expr2 = new ListExpr();
        then_expr2.add(new StringLiteral("if boucle2"));
        then.add(new Print(false, then_expr1));
        then.add(new Print(false, then_expr2));
        ListInst els = new ListInst();
        ListExpr else_expr = new ListExpr();
        else_expr.add(new StringLiteral("else boucle"));
        els.add(new Print(false, else_expr));
        linst.add(new IfThenElse(condition, then, els));
		
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
        	PrintStream ps = new PrintStream(new FileOutputStream(currentUsersDir + "/obtained/ifthenelse.txt" , true));
            prog.prettyPrint(ps);
            ps.close();
        }
    }
    
    public static void genSyntaxTreeManualFile(AbstractProgram source) throws IOException {        
        PrintStream ps = new PrintStream(new FileOutputStream(currentUsersDir + "/expected/ifthenelse.txt", true));
        source.prettyPrint(ps);
        ps.close();
        
    }

    public static void main(String args[]) throws IOException {
    	
    	int userspathlength = currentUsersDir.length();
    	String[] fichier_teste = new String[1];
    	String path = currentUsersDir.substring(0, userspathlength - 41) + "/deca/syntax/valid/created/ifthenelse.deca";
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


