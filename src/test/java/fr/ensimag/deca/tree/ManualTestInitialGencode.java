/* A manual test for the initial sketch of code generation included in 
 * students skeleton.
 * 
 * It is not intended to still work when code generation has been updated.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 *
 * @author Ensimag
 * @date 01/01/2020
 */
public class ManualTestInitialGencode {
	
	private static DecacCompiler compiler = new DecacCompiler(null,null);
    
    public static AbstractProgram initTestPrint() {
        ListInst linst = new ListInst();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(new ListDeclVar(),linst));
        ListExpr lexp1 = new ListExpr();
        ListExpr lexp2 = new ListExpr();
        linst.add(new Print(false,lexp1));
        linst.add(new Println(false,lexp2));
        lexp1.add(new StringLiteral("Hello "));
        lexp2.add(new StringLiteral("everybody !"));
        return source;
    }
    
    public static AbstractProgram initTestPrintInt() {
        ListInst linst = new ListInst();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(new ListDeclVar(),linst));
        ListExpr lexp1 = new ListExpr();
        linst.add(new Print(false,lexp1));
        lexp1.add(new IntLiteral(5));
        return source;
    }
    
    public static AbstractProgram initTestInt() {
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
        
        AbstractIdentifier type2 = new Identifier(compiler.getSymbolTable().create("int"));
        AbstractIdentifier varName2 = new Identifier(compiler.getSymbolTable().create("b"));
        Initialization init2 = new Initialization(new IntLiteral(2));
        lDecl.add(new DeclVar(type2, varName2, init2));

        return source;
    }
    
    /**
     * Test:
     * int a = 5;
     * int b = 2;
     * a + b;
     * @return
     */
    public static AbstractProgram initTestAssignAssignPlus() {
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
        
        AbstractIdentifier type2 = new Identifier(compiler.getSymbolTable().create("int"));
        AbstractIdentifier varName2 = new Identifier(compiler.getSymbolTable().create("b"));
        Initialization init2 = new Initialization(new IntLiteral(2));
        lDecl.add(new DeclVar(type2, varName2, init2));
        
        Plus plus = new Plus(varName, varName2);
        Assign ass = new Assign(varName, plus);
        
        linst.add(ass);

        return source;
    }
    
    public static AbstractProgram initTestImm() {
        ListInst linst = new ListInst();
        ListDeclVar lDecl = new ListDeclVar();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(lDecl,linst));

        Plus plus = new Plus(new IntLiteral(5), new IntLiteral(2));
        linst.add(plus);
		
        return source;
    }
    
    public static AbstractProgram initTestPlusPlus() {
        ListInst linst = new ListInst();
        ListDeclVar lDecl = new ListDeclVar();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(lDecl,linst));

        Plus plus = new Plus(new IntLiteral(5), new IntLiteral(2));
        Plus plus_plus = new Plus(plus, new IntLiteral(3));
        linst.add(plus_plus);
		
        return source;
    }
    
    /**
     * Test:
     * int a = 5;
     * int b = 2;
     * a + b;
     * @return
     */
    public static AbstractProgram initTestProgCours() {
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
        
        AbstractIdentifier type2 = new Identifier(compiler.getSymbolTable().create("int"));
        AbstractIdentifier varName2 = new Identifier(compiler.getSymbolTable().create("b"));
        Initialization init2 = new Initialization(new IntLiteral(2));
        lDecl.add(new DeclVar(type2, varName2, init2));
        
        Plus plus = new Plus(varName, varName2);
        Assign ass = new Assign(varName, plus);
        
        linst.add(ass);

        return source;
    }
    
    public static String gencodeSource(AbstractProgram source) {
        compiler = new DecacCompiler(null,null);
        source.codeGenProgram(compiler);
        return compiler.displayIMAProgram();
    }

    
    public static void test(AbstractProgram source) {
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");        
        String result = gencodeSource(source);
        System.out.println(result);
    }

        
    public static void main(String args[]) {
        AbstractProgram sourcePrint = initTestPrint();
        AbstractProgram sourcePrintInt = initTestPrintInt();
    	AbstractProgram sourceInt = initTestInt();
    	AbstractProgram sourceImm = initTestImm();
    	AbstractProgram sourcePlusPlus = initTestPlusPlus();
    	AbstractProgram sourceAssAssPlus = initTestAssignAssignPlus();
    	
    	//test(sourcePrint);
        //test(sourceInt);
        //test(sourceImm);
        //test(sourcePrintInt);
    	//test(sourcePlusPlus);
    	test(sourceAssAssPlus);
    }
}
             