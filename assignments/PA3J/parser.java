
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Jan 03 11:41:27 CET 2022
//----------------------------------------------------

import java_cup.runtime.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Jan 03 11:41:27 CET 2022
  */
public class parser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\007\000\002\002\003\000\002\002\004\000\002\003" +
    "\003\000\002\003\004\000\002\004\010\000\002\004\012" +
    "\000\002\005\002" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\022\000\004\004\007\001\002\000\006\002\001\004" +
    "\007\001\002\000\004\002\023\001\002\000\006\002\uffff" +
    "\004\uffff\001\002\000\004\055\010\001\002\000\006\011" +
    "\012\050\011\001\002\000\004\051\ufffb\001\002\000\004" +
    "\055\013\001\002\000\004\050\014\001\002\000\004\051" +
    "\ufffb\001\002\000\004\051\016\001\002\000\004\043\017" +
    "\001\002\000\006\002\ufffc\004\ufffc\001\002\000\004\051" +
    "\021\001\002\000\004\043\022\001\002\000\006\002\ufffd" +
    "\004\ufffd\001\002\000\004\002\000\001\002\000\006\002" +
    "\ufffe\004\ufffe\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\022\000\010\002\004\003\003\004\005\001\001\000" +
    "\004\004\023\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\005\017\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\005\014" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
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
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
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
class CUP$parser$actions {

 

    int curr_lineno() {
	return ((CoolTokenLexer)parser.getScanner()).curr_lineno();
    }

    AbstractSymbol curr_filename() {
	return ((CoolTokenLexer)parser.getScanner()).curr_filename();
    }

  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // dummy_feature_list ::= 
            {
              Features RESULT =null;
		 RESULT = new Features(curr_lineno()); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("dummy_feature_list",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // class ::= CLASS TYPEID INHERITS TYPEID LBRACE dummy_feature_list RBRACE SEMI 
            {
              class_c RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-6)).left;
		int nright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-6)).right;
		AbstractSymbol n = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-6)).value;
		int pleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)).right;
		AbstractSymbol p = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-4)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Features f = (Features)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		 RESULT = new class_c(curr_lineno(), n, p, f, curr_filename()); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("class",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-7)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // class ::= CLASS TYPEID LBRACE dummy_feature_list RBRACE SEMI 
            {
              class_c RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)).left;
		int nright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)).right;
		AbstractSymbol n = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-4)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Features f = (Features)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		 RESULT = new class_c(curr_lineno(), n, 
		                   AbstractTable.idtable.addString("Object"), 
				   f, curr_filename()); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("class",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-5)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // class_list ::= class_list class 
            {
              Classes RESULT =null;
		int clleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int clright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Classes cl = (Classes)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int cleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		class_c c = (class_c)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = cl.appendElement(c); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("class_list",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // class_list ::= class 
            {
              Classes RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		class_c c = (class_c)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = (new Classes(curr_lineno())).appendElement(c); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("class_list",1, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= program EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		programc start_val = (programc)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // program ::= class_list 
            {
              programc RESULT =null;
		int clleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int clright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Classes cl = (Classes)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new programc(curr_lineno(), cl); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("program",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

