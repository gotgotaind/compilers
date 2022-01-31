
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Jan 31 23:03:03 CET 2022
//----------------------------------------------------

import java_cup.runtime.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Jan 31 23:03:03 CET 2022
  */
public class CoolParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public CoolParser() {super();}

  /** Constructor which sets the default scanner. */
  public CoolParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public CoolParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\037\000\002\002\003\000\002\002\004\000\002\003" +
    "\003\000\002\003\004\000\002\004\010\000\002\004\012" +
    "\000\002\005\004\000\002\005\002\000\002\006\014\000" +
    "\002\006\007\000\002\006\005\000\002\011\003\000\002" +
    "\011\005\000\002\011\002\000\002\012\005\000\002\007" +
    "\010\000\002\007\006\000\002\007\005\000\002\007\004" +
    "\000\002\007\004\000\002\007\005\000\002\007\003\000" +
    "\002\007\003\000\002\007\003\000\002\015\003\000\002" +
    "\015\004\000\002\010\004\000\002\013\002\000\002\013" +
    "\003\000\002\014\003\000\002\014\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\104\000\004\004\007\001\002\000\006\002\001\004" +
    "\007\001\002\000\004\002\105\001\002\000\006\002\uffff" +
    "\004\uffff\001\002\000\004\055\010\001\002\000\006\011" +
    "\012\050\011\001\002\000\006\051\ufffa\056\ufffa\001\002" +
    "\000\004\055\013\001\002\000\004\050\014\001\002\000" +
    "\006\051\ufffa\056\ufffa\001\002\000\006\051\020\056\017" +
    "\001\002\000\006\051\ufffb\056\ufffb\001\002\000\006\044" +
    "\022\045\023\001\002\000\004\043\021\001\002\000\006" +
    "\002\ufffc\004\ufffc\001\002\000\004\055\077\001\002\000" +
    "\006\046\ufff4\056\025\001\002\000\004\046\033\001\002" +
    "\000\004\044\031\001\002\000\006\042\027\046\ufff6\001" +
    "\002\000\006\046\ufff4\056\025\001\002\000\004\046\ufff5" +
    "\001\002\000\004\055\032\001\002\000\006\042\ufff3\046" +
    "\ufff3\001\002\000\004\044\034\001\002\000\004\055\035" +
    "\001\002\000\004\050\036\001\002\000\020\024\040\025" +
    "\043\045\045\050\037\052\042\053\046\056\041\001\002" +
    "\000\020\024\040\025\043\045\045\050\037\052\042\053" +
    "\046\056\041\001\002\000\004\055\070\001\002\000\020" +
    "\040\uffec\042\uffec\043\uffec\045\065\046\uffec\051\uffec\056" +
    "\uffec\001\002\000\016\040\uffea\042\uffea\043\uffea\046\uffea" +
    "\051\uffea\056\uffea\001\002\000\020\024\040\025\043\045" +
    "\045\050\037\052\042\053\046\056\041\001\002\000\006" +
    "\040\051\051\062\001\002\000\020\024\040\025\043\045" +
    "\045\050\037\052\042\053\046\056\041\001\002\000\016" +
    "\040\uffeb\042\uffeb\043\uffeb\046\uffeb\051\uffeb\056\uffeb\001" +
    "\002\000\006\040\051\046\050\001\002\000\016\040\uffed" +
    "\042\uffed\043\uffed\046\uffed\051\uffed\056\uffed\001\002\000" +
    "\004\056\052\001\002\000\004\045\053\001\002\000\022" +
    "\024\040\025\043\045\045\046\uffe6\050\037\052\042\053" +
    "\046\056\041\001\002\000\004\046\061\001\002\000\006" +
    "\042\057\046\uffe5\001\002\000\010\040\051\042\uffe4\046" +
    "\uffe4\001\002\000\020\024\040\025\043\045\045\050\037" +
    "\052\042\053\046\056\041\001\002\000\010\040\051\042" +
    "\uffe3\046\uffe3\001\002\000\016\040\ufff2\042\ufff2\043\ufff2" +
    "\046\ufff2\051\ufff2\056\ufff2\001\002\000\004\043\063\001" +
    "\002\000\006\051\ufff9\056\ufff9\001\002\000\016\040\051" +
    "\042\uffee\043\uffee\046\uffee\051\uffee\056\uffee\001\002\000" +
    "\022\024\040\025\043\045\045\046\uffe6\050\037\052\042" +
    "\053\046\056\041\001\002\000\004\046\067\001\002\000" +
    "\016\040\ufff1\042\ufff1\043\ufff1\046\ufff1\051\ufff1\056\ufff1" +
    "\001\002\000\016\040\uffef\042\uffef\043\uffef\046\uffef\051" +
    "\uffef\056\uffef\001\002\000\022\024\uffe9\025\uffe9\045\uffe9" +
    "\050\uffe9\051\uffe9\052\uffe9\053\uffe9\056\uffe9\001\002\000" +
    "\022\024\040\025\043\045\045\050\037\051\076\052\042" +
    "\053\046\056\041\001\002\000\006\040\051\043\074\001" +
    "\002\000\022\024\uffe7\025\uffe7\045\uffe7\050\uffe7\051\uffe7" +
    "\052\uffe7\053\uffe7\056\uffe7\001\002\000\022\024\uffe8\025" +
    "\uffe8\045\uffe8\050\uffe8\051\uffe8\052\uffe8\053\uffe8\056\uffe8" +
    "\001\002\000\016\040\ufff0\042\ufff0\043\ufff0\046\ufff0\051" +
    "\ufff0\056\ufff0\001\002\000\010\023\100\051\ufff7\056\ufff7" +
    "\001\002\000\020\024\040\025\043\045\045\050\037\052" +
    "\042\053\046\056\041\001\002\000\010\040\051\051\ufff8" +
    "\056\ufff8\001\002\000\006\051\103\056\017\001\002\000" +
    "\004\043\104\001\002\000\006\002\ufffd\004\ufffd\001\002" +
    "\000\004\002\000\001\002\000\006\002\ufffe\004\ufffe\001" +
    "\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\104\000\010\002\004\003\003\004\005\001\001\000" +
    "\004\004\105\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\005\101\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\005\014" +
    "\001\001\000\004\006\015\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\006\011\023\012\025\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\006\011\027" +
    "\012\025\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\004\007\043\001\001\000\010\007\072\010" +
    "\070\015\071\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\004\007\063\001\001\000\002\001" +
    "\001\000\004\007\046\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\010\007\055\013\053\014\054\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\007" +
    "\057\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\010\007" +
    "\055\013\065\014\054\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\006\007" +
    "\072\010\074\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\007\100\001\001\000\002\001\001\000\004\006\015" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$CoolParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$CoolParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$CoolParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



    int omerrs = 0;

    public void syntax_error(Symbol cur_token) {
        int lineno = action_obj.curr_lineno();
	String filename = action_obj.curr_filename().getString();
        System.err.print("\"" + filename + "\", line " + lineno + 
		         ": parse error at or near ");
        Utilities.printToken(cur_token);
	omerrs++;
	if (omerrs>50) {
	   System.err.println("More than 50 errors");
	   System.exit(1);
	}
    }

    public void unrecovered_syntax_error(Symbol cur_token) {
    }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$CoolParser$actions {

 

    int curr_lineno() {
	return ((CoolTokenLexer)parser.getScanner()).curr_lineno();
    }

    AbstractSymbol curr_filename() {
	return ((CoolTokenLexer)parser.getScanner()).curr_filename();
    }

  private final CoolParser parser;

  /** Constructor */
  CUP$CoolParser$actions(CoolParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$CoolParser$do_action(
    int                        CUP$CoolParser$act_num,
    java_cup.runtime.lr_parser CUP$CoolParser$parser,
    java.util.Stack            CUP$CoolParser$stack,
    int                        CUP$CoolParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$CoolParser$result;

      /* select the action based on the action number */
      switch (CUP$CoolParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // exprs ::= exprs COMMA expr 
            {
              Expressions RESULT =null;
		Expressions es = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = es.appendElement(e); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("exprs",10, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // exprs ::= expr 
            {
              Expressions RESULT =null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new Expressions(curr_lineno()).appendElement(e); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("exprs",10, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // exprs_opt ::= exprs 
            {
              Expressions RESULT =null;
		Expressions es = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = es; 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("exprs_opt",9, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // exprs_opt ::= 
            {
              Expressions RESULT =null;
		 RESULT = new Expressions(curr_lineno()); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("exprs_opt",9, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // expr_semi ::= expr SEMI 
            {
              Expression RESULT =null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		 RESULT = e; 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr_semi",6, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // expr_semi_list ::= expr_semi_list expr_semi 
            {
              Expressions RESULT =null;
		Expressions esl = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = esl.appendElement(e); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr_semi_list",11, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // expr_semi_list ::= expr_semi 
            {
              Expressions RESULT =null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new Expressions(curr_lineno()).appendElement(e); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr_semi_list",11, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // expr ::= STR_CONST 
            {
              Expression RESULT =null;
		AbstractSymbol s = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new string_const(curr_lineno(),s); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // expr ::= INT_CONST 
            {
              Expression RESULT =null;
		AbstractSymbol i = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new int_const(curr_lineno(),i); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // expr ::= OBJECTID 
            {
              Expression RESULT =null;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new object(curr_lineno(),o); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // expr ::= LPAREN expr RPAREN 
            {
              Expression RESULT =null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		 RESULT = e; 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // expr ::= ISVOID expr 
            {
              Expression RESULT =null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new isvoid(curr_lineno(),e); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // expr ::= NEW TYPEID 
            {
              Expression RESULT =null;
		AbstractSymbol t = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new new_(curr_lineno(),t); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // expr ::= LBRACE expr_semi_list RBRACE 
            {
              Expression RESULT =null;
		Expressions esl = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		 RESULT = new block(curr_lineno(),esl); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // expr ::= OBJECTID LPAREN exprs_opt RPAREN 
            {
              Expression RESULT =null;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-3)).value;
		Expressions es = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		 RESULT = new dispatch(curr_lineno(),new object(curr_lineno(),AbstractTable.idtable.addString("self")),o,es); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // expr ::= expr DOT OBJECTID LPAREN exprs_opt RPAREN 
            {
              Expression RESULT =null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-5)).value;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-3)).value;
		Expressions es = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		 RESULT = new dispatch(curr_lineno(),e,o,es); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("expr",5, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // formal ::= OBJECTID COLON TYPEID 
            {
              Formal RESULT =null;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		AbstractSymbol t = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new formalc(curr_lineno(),o,t); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("formal",8, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // formal_list ::= 
            {
              Formals RESULT =null;
		 RESULT = (new Formals(curr_lineno())); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("formal_list",7, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // formal_list ::= formal COMMA formal_list 
            {
              Formals RESULT =null;
		Formal f = (Formal)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Formals fl = (Formals)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = fl.appendElement(f); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("formal_list",7, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // formal_list ::= formal 
            {
              Formals RESULT =null;
		Formal f = (Formal)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = (new Formals(curr_lineno())).appendElement(f); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("formal_list",7, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // feature ::= OBJECTID COLON TYPEID 
            {
              Feature RESULT =null;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		AbstractSymbol t = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new attr(curr_lineno(),o,t,new no_expr(curr_lineno())); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("feature",4, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // feature ::= OBJECTID COLON TYPEID DARROW expr 
            {
              Feature RESULT =null;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-4)).value;
		AbstractSymbol t = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new attr(curr_lineno(),o,t,e); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("feature",4, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // feature ::= OBJECTID LPAREN formal_list RPAREN COLON TYPEID LBRACE expr RBRACE SEMI 
            {
              Feature RESULT =null;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-9)).value;
		Formals formal_l = (Formals)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-7)).value;
		AbstractSymbol t = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-4)).value;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		 RESULT = new method(curr_lineno(),o,formal_l,t,e); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("feature",4, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // feature_list ::= 
            {
              Features RESULT =null;
		 RESULT = new Features(curr_lineno()); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("feature_list",3, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // feature_list ::= feature_list feature 
            {
              Features RESULT =null;
		Features fl = (Features)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		Feature f = (Feature)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = fl.appendElement(f); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("feature_list",3, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // class ::= CLASS TYPEID INHERITS TYPEID LBRACE feature_list RBRACE SEMI 
            {
              class_c RESULT =null;
		AbstractSymbol n = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-6)).value;
		AbstractSymbol p = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-4)).value;
		Features fl = (Features)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		 RESULT = new class_c(curr_lineno(), n, p, fl, curr_filename()); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("class",2, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // class ::= CLASS TYPEID LBRACE feature_list RBRACE SEMI 
            {
              class_c RESULT =null;
		AbstractSymbol n = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-4)).value;
		Features fl = (Features)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		 RESULT = new class_c(curr_lineno(), n, AbstractTable.idtable.addString("Object"), fl, curr_filename()); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("class",2, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // class_list ::= class_list class 
            {
              Classes RESULT =null;
		Classes cl = (Classes)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		class_c c = (class_c)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = cl.appendElement(c); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("class_list",1, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // class_list ::= class 
            {
              Classes RESULT =null;
		class_c c = (class_c)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = (new Classes(curr_lineno())).appendElement(c); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("class_list",1, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= program EOF 
            {
              Object RESULT =null;
		programc start_val = (programc)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		RESULT = start_val;
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("$START",0, RESULT);
            }
          /* ACCEPT */
          CUP$CoolParser$parser.done_parsing();
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // program ::= class_list 
            {
              programc RESULT =null;
		Classes cl = (Classes)((java_cup.runtime.Symbol) CUP$CoolParser$stack.peek()).value;
		 RESULT = new programc(curr_lineno(), cl); 
              CUP$CoolParser$result = parser.getSymbolFactory().newSymbol("program",0, RESULT);
            }
          return CUP$CoolParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

