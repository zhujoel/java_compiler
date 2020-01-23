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
import fr.ensimag.deca.tree.AbstractIdentifier;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.And;
import fr.ensimag.deca.tree.Assign;
import fr.ensimag.deca.tree.BooleanLiteral;
import fr.ensimag.deca.tree.DeclVar;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.Initialization;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.deca.tree.ListDeclClass;
import fr.ensimag.deca.tree.ListDeclVar;
import fr.ensimag.deca.tree.ListExpr;
import fr.ensimag.deca.tree.ListInst;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.Main;
import fr.ensimag.deca.tree.Or;
import fr.ensimag.deca.tree.Print;
import fr.ensimag.deca.tree.Program;
import fr.ensimag.deca.tree.Tree;
import fr.ensimag.deca.tree.TreeFunction;

public class prog_cours {
	
	private static DecacCompiler compiler = new DecacCompiler(null,null);
	
	static String currentUsersDir = System.getProperty("user.dir");

	/* script pour le test de prog_cours.deca */
    public static AbstractProgram ProgInit() {
        ListInst linst = new ListInst();
        ListDeclVar lDecl = new ListDeclVar();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(lDecl,linst));

        AbstractIdentifier type_bool = new Identifier(compiler.getSymbolTable().create("bool"));
        AbstractIdentifier varName_a = new Identifier(compiler.getSymbolTable().create("a"));
        AbstractIdentifier varName_b = new Identifier(compiler.getSymbolTable().create("b"));
        AbstractIdentifier type_int = new Identifier(compiler.getSymbolTable().create("int"));
        AbstractIdentifier varName_c = new Identifier(compiler.getSymbolTable().create("c"));
        lDecl.add(new DeclVar(type_bool, varName_a, new Initialization(new BooleanLiteral(false))));
        lDecl.add(new DeclVar(type_bool, varName_b, new Initialization(new BooleanLiteral(true))));
        lDecl.add(new DeclVar(type_int, varName_c, new Initialization(new IntLiteral(1))));
        
        linst.add(new Assign(varName_a, new And(varName_a, varName_b)));
        linst.add(new Assign(varName_b, new Or(varName_b, varName_a)));
        ListExpr lexpr1 = new ListExpr();
        ListExpr lexpr2 = new ListExpr();
        lexpr1.add(varName_c);
        lexpr2.add(varName_b);
        linst.add(new Print(false, lexpr1));
        linst.add(new Print(false, lexpr2));


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
        	PrintStream ps = new PrintStream(new FileOutputStream(currentUsersDir + "/obtained/prog_cours.txt" , true));
            prog.prettyPrint(ps);
            ps.close();
        }
    }
    
    public static void genSyntaxTreeManualFile(AbstractProgram source) throws IOException {        
        PrintStream ps = new PrintStream(new FileOutputStream(currentUsersDir + "/expected/prog_cours.txt", true));
        source.prettyPrint(ps);
        ps.close();
        
    }

    public static void main(String args[]) throws IOException {
    	
    	int userspathlength = currentUsersDir.length();
    	String[] fichier_teste = new String[1];
    	String path = currentUsersDir.substring(0, userspathlength - 41) + "/deca/syntax/valid/created/prog_cours.deca";
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


