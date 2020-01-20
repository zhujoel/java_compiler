parser grammar DecaParser;

options {
    // Default language but name it anyway
    //
    language  = Java;

    // Use a superclass to implement all helper
    // methods, instance variables and overrides
    // of ANTLR default methods, such as error
    // handling.
    //
    superClass = AbstractDecaParser;

    // Use the vocabulary generated by the accompanying
    // lexer. Maven knows how to work out the relationship
    // between the lexer and parser and will build the
    // lexer before the parser. It will also rebuild the
    // parser if the lexer changes.
    //
    tokenVocab = DecaLexer;

}

// which packages should be imported?
@header {
    import fr.ensimag.deca.tree.*;
    import java.io.PrintStream;
}

@members {
    @Override
    protected AbstractProgram parseProgram() {
        return prog().tree;
    }
}

prog returns[AbstractProgram tree]
    : list_classes main EOF {
            assert($list_classes.tree != null);
            assert($main.tree != null);
            $tree = new Program($list_classes.tree, $main.tree);
            setLocation($tree, $list_classes.start);
        }
    ;

main returns[AbstractMain tree]
    : /* epsilon */ {
            $tree = new EmptyMain();
        }
    | block {
            assert($block.decls != null);
            assert($block.insts != null);
            $tree = new Main($block.decls, $block.insts);
            setLocation($tree, $block.start);
        }
    ;

block returns[ListDeclVar decls, ListInst insts]
    : OBRACE list_decl list_inst CBRACE {
            assert($list_decl.tree != null);
            assert($list_inst.tree != null);
            $decls = $list_decl.tree;
            $insts = $list_inst.tree;
        }
    ;

list_decl returns[ListDeclVar tree]
@init   {
            $tree = new ListDeclVar();
        }
    : decl_var_set[$tree]*
    ;

decl_var_set[ListDeclVar l]
    : type list_decl_var[$l,$type.tree] SEMI
    ;

list_decl_var[ListDeclVar l, AbstractIdentifier t]
    : dv1=decl_var[$t] {
    	assert($dv1.tree != null);
        $l.add($dv1.tree);
        } (COMMA dv2=decl_var[$t] {
        	assert($dv2.tree != null);
        	// on ajoute dans la liste 
        	$l.add($dv2.tree);
        }
      )*
    ;

decl_var[AbstractIdentifier t] returns[AbstractDeclVar tree]
@init   {
        }
    : i=ident {
    		assert($i.tree != null);
        }
      ((EQUALS e=expr {
      		assert($e.tree != null);
      		Initialization init = new Initialization($e.tree);
      		$tree = new DeclVar($t, $i.tree, init);
      		setLocation($tree, $EQUALS);
      		setLocation(init, $e.start);
        }
      ) | {
			// nouvelle déclaration
			$tree = new DeclVar($t, $i.tree, new NoInitialization());
    		setLocation($tree, $i.start);
        })
    ;

list_inst returns[ListInst tree]
@init {
	$tree = new ListInst();
}
    : (inst {
    		assert($inst.tree != null);
    		$tree.add($inst.tree);
        }
      )*
    ;

inst returns[AbstractInst tree]
    : e1=expr SEMI {
            assert($e1.tree != null);
            $tree = $e1.tree;
            setLocation($tree, $e1.start);
        }
    | SEMI {
    		$tree = new NoOperation();
            setLocation($tree, $SEMI);
        }
    | PRINT OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
            $tree = new Print(false, $list_expr.tree);
            setLocation($tree, $PRINT);
        }
    | PRINTLN OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
            $tree = new Println(false, $list_expr.tree);
            setLocation($tree, $PRINTLN);
        }
    | PRINTX OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
            $tree = new Print(true, $list_expr.tree);
            setLocation($tree, $PRINTX);
        }
    | PRINTLNX OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
            $tree = new Println(true, $list_expr.tree);
            setLocation($tree, $PRINTLNX);
        }
    | if_then_else {
            assert($if_then_else.tree != null);
            $tree = $if_then_else.tree;
            setLocation($tree, $if_then_else.start);
        }
    | WHILE OPARENT condition=expr CPARENT OBRACE body=list_inst CBRACE {
            assert($condition.tree != null);
            assert($body.tree != null);
            $tree = new While($condition.tree, $body.tree);
            setLocation($tree, $WHILE);
        }
    | RETURN expr SEMI {
            assert($expr.tree != null);
            $tree = new Return($expr.tree);
            setLocation($tree, $RETURN);
        }
    ;

if_then_else returns[IfThenElse tree]
@init {
	ListInst elseBranch = new ListInst();
	ListInst elseIfElseBranch = new ListInst();
	boolean hasIfElse = false;
}
    : if1=IF OPARENT condition=expr CPARENT OBRACE li_if=list_inst CBRACE {
    		assert($condition.tree != null);
    		assert($li_if.tree != null);
    		$tree = new IfThenElse($condition.tree, $li_if.tree, elseBranch);
            setLocation($tree, $if1);
        }
      (ELSE elsif=IF OPARENT elsif_cond=expr CPARENT OBRACE elsif_li=list_inst CBRACE {
      		assert($elsif_cond.tree != null);
      		assert($elsif_li.tree != null);
      		if(!hasIfElse){
      			IfThenElse ifelse = new IfThenElse($elsif_cond.tree, $elsif_li.tree, elseIfElseBranch);
        		elseBranch.add(ifelse);
        		hasIfElse = true;
        		setLocation(ifelse, $ELSE);
        	}
        	else{
        		// on ajoute les else if en + récursivement dans la boucle else du else if précédent
        		ListInst elseIfElseRecBranch = new ListInst();
        		IfThenElse recElse = new IfThenElse($elsif_cond.tree, $elsif_li.tree, elseIfElseRecBranch);
        		elseIfElseBranch.add(recElse);
        		setLocation(recElse, $ELSE);
        	}
        }
      )*
      (ELSE OBRACE li_else=list_inst CBRACE {
      		assert($li_else.tree != null);
      		if(!elseBranch.isEmpty()){
      			for(AbstractInst i : $li_else.tree.getList()){
      				elseIfElseBranch.add(i);
      			}
      		}
      		else{
      			for(AbstractInst i : $li_else.tree.getList()){
      				elseBranch.add(i);
      			}
      		}
        }
      )?
    ;

list_expr returns[ListExpr tree]
@init   {
            $tree = new ListExpr();
        }
    : (e1=expr {
    		assert($e1.tree != null);
    		$tree.add($e1.tree);
        }
       (COMMA e2=expr {
       		assert($e2.tree != null);
       		$tree.add($e2.tree);
        }
       )* )?
    ;

expr returns[AbstractExpr tree]
    : assign_expr {
            assert($assign_expr.tree != null);
            $tree = $assign_expr.tree;
			setLocation($tree, $assign_expr.start);
        }
    ;

assign_expr returns[AbstractExpr tree]
    : e=or_expr (
        /* condition: expression e must be a "LVALUE" */ {
    		assert($e.tree != null);
            if (! ($e.tree instanceof AbstractLValue)) {
                throw new InvalidLValue(this, $ctx);
            }
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
        EQUALS e2=assign_expr {
            assert($e2.tree != null);
			$tree = new Assign((AbstractLValue)$e.tree, $e2.tree);
			setLocation($tree, $EQUALS);
            
        }
      | /* epsilon */ {
            $tree = $e.tree;
        }
      )
    ;

or_expr returns[AbstractExpr tree]
    : e=and_expr {
            assert($e.tree != null);
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
    | e1=or_expr OR e2=and_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Or($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
       }
    ;

and_expr returns[AbstractExpr tree]
    : e=eq_neq_expr {
            assert($e.tree != null);
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
    |  e1=and_expr AND e2=eq_neq_expr {
            assert($e1.tree != null);                         
            assert($e2.tree != null);
            $tree = new And($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    ;


eq_neq_expr returns[AbstractExpr tree]
    : e=inequality_expr {
            assert($e.tree != null);
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
    | e1=eq_neq_expr EQEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Equals($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=eq_neq_expr NEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new NotEquals($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    ;


inequality_expr returns[AbstractExpr tree]
    : e=sum_expr {
            assert($e.tree != null);
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
    | e1=inequality_expr LEQ e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new LowerOrEqual($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=inequality_expr GEQ e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new GreaterOrEqual($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=inequality_expr GT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Greater($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=inequality_expr LT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Lower($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=inequality_expr INSTANCEOF type {
            assert($e1.tree != null);
            assert($type.tree != null);
            $tree = new InstanceOf($e1.tree, $type.tree);
			setLocation($tree, $e1.start);
        }
    ;



sum_expr returns[AbstractExpr tree]
    : e=mult_expr {
            assert($e.tree != null);
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
    | e1=sum_expr PLUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Plus($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=sum_expr MINUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Minus($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    ;

mult_expr returns[AbstractExpr tree]
    : e=unary_expr {
            assert($e.tree != null);
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
    | e1=mult_expr TIMES e2=unary_expr {
            assert($e1.tree != null);                                         
            assert($e2.tree != null);
            $tree = new Multiply($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=mult_expr SLASH e2=unary_expr {
            assert($e1.tree != null);                                         
            assert($e2.tree != null);
            $tree = new Divide($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    | e1=mult_expr PERCENT e2=unary_expr {
            assert($e1.tree != null);                                                                          
            assert($e2.tree != null);
            $tree = new Modulo($e1.tree, $e2.tree);
			setLocation($tree, $e1.start);
        }
    ;

unary_expr returns[AbstractExpr tree]
    : op=MINUS e=unary_expr {
            assert($e.tree != null);
            $tree = new UnaryMinus($e.tree);
			setLocation($tree, $op);
        }
    | op=EXCLAM e=unary_expr {
            assert($e.tree != null);
            $tree = new Not($e.tree);
			setLocation($tree, $op);
        }
    | select_expr {
            assert($select_expr.tree != null);
            $tree = $select_expr.tree;
			setLocation($tree, $select_expr.start);
        }
    ;

select_expr returns[AbstractExpr tree]
    : e=primary_expr {
            assert($e.tree != null);
            $tree = $e.tree;
			setLocation($tree, $e.start);
        }
    | e1=select_expr DOT i=ident {
            assert($e1.tree != null);
            assert($i.tree != null);
            // soit on a l'appel d'une méthode, soit on prend l'attribut
        }
        (o=OPARENT args=list_expr CPARENT {
            // we matched "e1.i(args)"
            // on fait appel à une méthode
            assert($args.tree != null);
			$tree = new MethodCall($e1.tree, $i.tree, $args.tree);
			setLocation($tree, $o);
        }
        | /* epsilon */ {
            // we matched "e.i"
            // on prend l'attribut'
			$tree = new Selection($e1.tree, $i.tree);
			setLocation($tree, $e1.start);
        }
        )
    ;

primary_expr returns[AbstractExpr tree]
    : ident {
            assert($ident.tree != null);
            $tree = $ident.tree;
			setLocation($tree, $ident.start);
        }
    | m=ident OPARENT args=list_expr CPARENT {
            assert($args.tree != null);
            assert($m.tree != null);
			$tree = new MethodCall($m.tree, $args.tree);
			setLocation($tree, $m.start);
            
        }
    | OPARENT expr CPARENT {
            assert($expr.tree != null);
            $tree = $expr.tree;
			setLocation($tree, $OPARENT);
        }
    | READINT OPARENT CPARENT {
    		$tree = new ReadInt();
			setLocation($tree, $READINT);
        }
    | READFLOAT OPARENT CPARENT {
    		$tree = new ReadFloat();
			setLocation($tree, $READFLOAT);
        }
    | NEW ident OPARENT CPARENT {
            assert($ident.tree != null);
            $tree = $ident.tree;
			setLocation($tree, $NEW);
        }
    | cast=OPARENT type CPARENT OPARENT expr CPARENT {
            assert($type.tree != null);
            assert($expr.tree != null);
            $tree = new Cast($type.tree, $expr.tree);
            setLocation($tree, $cast);
        }
    | literal {
            assert($literal.tree != null);
            $tree = $literal.tree;
			setLocation($tree, $literal.start);
        }
    ;

type returns[AbstractIdentifier tree]
    : ident {
            assert($ident.tree != null);
            $tree = $ident.tree;
			setLocation($tree, $ident.start);
        }
    ;

literal returns[AbstractExpr tree]
    : INT {
    		$tree = new IntLiteral(Integer.parseInt($INT.text));
			setLocation($tree, $INT);
        }
    | fd=FLOAT {
    		$tree = new FloatLiteral(Float.parseFloat($fd.text));
			setLocation($tree, $fd);
        }
    | STRING {
    		$tree = new StringLiteral($STRING.text);
			setLocation($tree, $STRING);
        }
    | TRUE {
    		$tree = new BooleanLiteral(true);
			setLocation($tree, $TRUE);
        }
    | FALSE {
    		$tree = new BooleanLiteral(false);
			setLocation($tree, $FALSE);
        }
    | THIS {
    		if ($THIS.text.equals("this")){
    			// si on a un this, alors on fait un appel explicite à l'attribut
    			$tree = new This(true);
    		}
    		else{
    			// si on en a pas, alors on fait un appel implicite (verif contextuelle)
    			$tree = new This(false);
    		}
    		setLocation($tree, $THIS);
        }
    | NULL {
    		$tree = new Null();
			setLocation($tree, $NULL);
    		}
    ;

ident returns[AbstractIdentifier tree]
    : IDENT {
    		// on crée un identifier
    		$tree = new Identifier(super.getDecacCompiler().getSymbolTable().create($IDENT.text));
			setLocation($tree, $IDENT);
        }
    ;

/****     Class related rules     ****/

// Liste de classes
list_classes returns[ListDeclClass tree]
@init{
	$tree = new ListDeclClass();
}
    :
      (c1=class_decl {
      		assert($c1.tree != null);
      		$tree.add($c1.tree);
      		setLocation($tree, $c1.start);
        }
      )*
    ;

// Déclaration d'une classe
class_decl returns[AbstractDeclClass tree]
    : CLASS name=ident superclass=class_extension OBRACE class_body CBRACE {
            assert($ident.tree != null);
            assert($superclass.tree != null);
            assert($class_body.lfields != null);
            assert($class_body.lmethods != null);
    		$tree = new DeclClass($ident.tree, $superclass.tree, 
    			$class_body.lfields, $class_body.lmethods);
    		setLocation($tree, $CLASS);
        }
    ;

// Extension d'une classe
class_extension returns[AbstractIdentifier tree]
    : EXTENDS ident {
    		assert($ident.tree != null);
    		// si la classe étend une autre
    		$tree = $ident.tree;
    		setLocation($tree, $EXTENDS);
        }
    | /* epsilon */ {
    		// si elle étend rien, bah elle étend quand meme Object
    		$tree = new Identifier(super.getDecacCompiler().getSymbolTable().create("Object"));
        }
    ;

// Corps de la classe, avec des attributs et des méthodes
class_body returns[ListDeclField lfields, ListDeclMethod lmethods]
@init{
	$lmethods = new ListDeclMethod();
	$lfields = new ListDeclField();
}
    : (m=decl_method {
    		assert($m.tree != null);
    		$lmethods.add($m.tree);
        }
      // passe la liste en paramètre pour que les fils le remplisse
      | decl_field_set[$lfields]
      )*
    ;

// Un attribut de la classe
decl_field_set[ListDeclField lfields]
    : v=visibility t=type list_decl_field[$lfields, $v.tree, $t.tree] SEMI
    ;

// visibilité d'un attribut ou méthode
visibility returns[Visibility tree]
    : /* epsilon */ {
    		// par défaut c'est public
    		$tree = Visibility.PUBLIC;
        }
    | PROTECTED {
    		$tree = Visibility.PROTECTED;
        }
    ;

// on ajoute l'attribut dans la liste d'attributs de la classe
list_decl_field[ListDeclField lfields, Visibility v, AbstractIdentifier t]
    : dv1=decl_field[$t, $v]{
    	assert($dv1.tree != null);
    	$lfields.add($dv1.tree);
    }
        (COMMA dv2=decl_field[$t, $v]{
        	assert($dv2.tree != null);
        	$lfields.add($dv2.tree);
        }
      )*
    ;

// déclaration de l'attribut de la classe avec initialisation ou non
decl_field[AbstractIdentifier t, Visibility v] returns[AbstractDeclField tree]
    : i=ident {
    		assert($i.tree != null);
        }
       // si il y a une initialisation à l'attribut
      ((EQUALS e=expr {
      		assert($e.tree != null);
      		Initialization init = new Initialization($e.tree);
      		$tree = new DeclField($v, $t, $i.tree, init);
      		setLocation($tree, $EQUALS);
        }
      )
      // pas d'initialisation 
      | {
      		$tree = new DeclField($v, $t, $i.tree, new NoInitialization());
    		setLocation($tree, $i.start);
        })
    ;

decl_method returns[AbstractDeclMethod tree]
@init {
	ListDeclParam params = new ListDeclParam();
}
	// déclaration d'une méthode
    : type ident OPARENT params=list_params[params] CPARENT (block {
    		assert($type.tree != null);
    		assert($ident.tree != null);
    		assert($block.decls != null);
    		assert($block.insts != null);
    		$tree = new DeclMethod($type.tree, $ident.tree, params,
    			$block.decls, $block.insts);
    		setLocation($tree, $type.start);
        }
      |
      // TODO: faire l'ASM pour mettre de l'assembleur en tant que méthode de la classe 
      ASM OPARENT code=multi_line_string CPARENT SEMI {
        }
      ) {
        }
    ;

list_params[ListDeclParam params]
    : (p1=param {
    		assert($p1.tree != null);
    		params.add($p1.tree);
        } (COMMA p2=param {
        	assert($p2.tree != null);
        	params.add($p2.tree);
        }
      )*)?
    ;
    
// TODO: compléter ça?
multi_line_string returns[String text, Location location]
    : s=STRING {
            $text = $s.text;
            $location = tokenLocation($s);
        }
    | s=MULTI_LINE_STRING {
            $text = $s.text;
            $location = tokenLocation($s);
        }
    ;

param returns[AbstractDeclParam tree]
    : type ident {
    	assert($type.tree != null);
    	assert($ident.tree != null);
    	$tree = new DeclParam($type.tree, $ident.tree);
    	setLocation($tree, $type.start);
        }
    ;
    