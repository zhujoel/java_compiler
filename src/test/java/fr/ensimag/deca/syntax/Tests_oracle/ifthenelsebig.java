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

public class ifthenelsebig {
	
	private static DecacCompiler compiler = new DecacCompiler(null,null);
	
	static String currentUsersDir = System.getProperty("user.dir");
    
    /* script pour le test de ifthenelse++.deca */
    public static AbstractProgram ProgInit() {
        ListInst linst = new ListInst();
        ListDeclVar lDecl = new ListDeclVar();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(lDecl,linst));
        
        //boucle if1
        AbstractExpr condition1 = new Greater(new IntLiteral(5), new IntLiteral(2));
        ListInst then1 = new ListInst();
        ListExpr then_expr1 = new ListExpr();
        then_expr1.add(new StringLiteral("\"of\""));
        then1.add(new Print(false, then_expr1));
        //boucle else1
        ListInst else1 = new ListInst();
        linst.add(new IfThenElse(condition1, then1, else1));
        
        //boucle if2
        AbstractExpr condition2 = new Lower(new IntLiteral(2), new IntLiteral(5));
        ListInst then2 = new ListInst();
        ListInst else2 = new ListInst();
        
        AbstractExpr condition3 = new Lower(new IntLiteral(3), new IntLiteral(2));
        ListExpr then3_expr = new ListExpr();
        then3_expr.add(new StringLiteral("\"if\""));
        ListInst then3 = new ListInst();
        then3.add(new Print(false, then3_expr));
        ListInst else3 = new ListInst();
        
        ListInst then4 = new ListInst();
        ListInst else4 = new ListInst();
        AbstractExpr condition4 = new Lower(new IntLiteral(2), new IntLiteral(5));
        
        ListInst then5 = new ListInst();
        ListInst else5 = new ListInst();
        AbstractExpr condition5 = new Equals(new IntLiteral(2), new IntLiteral(5));
        
        else4.add(new IfThenElse(condition5, then5, else5));
        else3.add(new IfThenElse(condition4, then4, else4));
        then2.add(new IfThenElse(condition3, then3, else3));
        
        linst.add(new IfThenElse(condition2, then2, else2));
		
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
        	PrintStream ps = new PrintStream(new FileOutputStream(currentUsersDir + "/obtained/ifthenelsebig.txt" , true));
            prog.prettyPrint(ps);
            ps.close();
        }
    }
    
    public static void genSyntaxTreeManualFile(AbstractProgram source) throws IOException {        
        PrintStream ps = new PrintStream(new FileOutputStream(currentUsersDir + "/expected/ifthenelsebig.txt", true));
        source.prettyPrint(ps);
        ps.close();
        
    }

    public static void main(String args[]) throws IOException {
    	
    	int userspathlength = currentUsersDir.length();
    	String[] fichier_teste = new String[1];
    	String path = currentUsersDir.substring(0, userspathlength - 41) + "/deca/syntax/valid/created/ifthenelse++.deca";
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


