/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }
    private AbstractSymbol filename;
    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	return filename;
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"37:9,36:2,37,36:2,37:18,36,15,37:6,4,8,6,11,9,7,12,10,35:10,14,5,13,1,2,37," +
"17,35:26,37:4,35,37,20,35,18,31,25,28,35,24,22,35:2,19,35,23,30,32,35,26,21" +
",27,34,29,33,35:3,3,37,16,37:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,88,
"0,1,2,1:11,3,1:4,4,1:3,5,6:18,1,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,2" +
"2,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,6,46" +
",47,48,49,50")[0];

	private int yy_nxt[][] = unpackFromString(51,38,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,61,82:2,43,62,82,83,82,84,4" +
"4,82,45,82,85,86,82:2,20,42,-1:40,21,-1:36,22,-1:54,82,87,63,82:15,-1:20,82" +
":6,75,82:11,-1:20,82:18,-1:20,82:3,65,82,23,82:4,24,82:7,-1:20,82:2,70,82,2" +
"5,82:13,-1:20,82:10,26,82:7,-1:20,82:9,27,82:8,-1:20,82:15,28,82:2,-1:20,82" +
":9,29,82:8,-1:20,82:7,30,82:10,-1:20,82:14,31,82:3,-1:20,82:7,32,82:10,-1:2" +
"0,33,82:17,-1:20,82:5,34,82:12,-1:20,82:7,35,82:10,-1:20,82,36,82:16,-1:20," +
"82:3,37,82:14,-1:20,82:7,38,82:10,-1:20,82:7,39,82:10,-1:20,82:13,40,82:4,-" +
"1:20,82:3,41,82:14,-1:20,82:7,46,82:4,64,82:5,-1:20,82:7,47,82:4,48,82:5,-1" +
":20,82:3,49,82:14,-1:20,82:12,50,82:5,-1:20,82:11,74,82:6,-1:20,82:3,51,82:" +
"14,-1:20,82:2,52,82:15,-1:20,82:7,53,82:10,-1:20,82:16,54,82,-1:20,82,76,82" +
":16,-1:20,82:12,55,82:5,-1:20,82:4,77,82:13,-1:20,82:3,56,82:14,-1:20,82:12" +
",78,82:5,-1:20,82:7,79,82:10,-1:20,82:3,57,82:14,-1:20,82,58,82:16,-1:20,82" +
":4,59,82:13,-1:20,82:8,80,82:9,-1:20,82:4,81,82:13,-1:20,82:9,60,82:8,-1:20" +
",82,66,82,67,82:14,-1:20,82:6,68,82,69,82:9,-1:20,82:12,71,82:5,-1:20,82:6," +
"72,82:11,-1:20,82:2,73,82:15,-1:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */
    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Symbol(TokenConstants.EQ); }
					case -3:
						break;
					case 3:
						{ return new Symbol(TokenConstants.LT); }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -5:
						break;
					case 5:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.SEMI); }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.MULT); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.MINUS); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.COMMA); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.DIV); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.PLUS); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.DOT); }
					case -14:
						break;
					case 14:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.COLON); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.NEG); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.AT); }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -20:
						break;
					case 20:
						{ }
					case -21:
						break;
					case 21:
						{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
					case -22:
						break;
					case 22:
						{ return new Symbol(TokenConstants.LE); }
					case -23:
						break;
					case 23:
						{ return new Symbol(TokenConstants.IN ); }
					case -24:
						break;
					case 24:
						{ return new Symbol(TokenConstants.IF ); }
					case -25:
						break;
					case 25:
						{ return new Symbol(TokenConstants.FI ); }
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.OF); }
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.LET ); }
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.NEW ); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.NOT); }
					case -30:
						break;
					case 30:
						{ return new Symbol(TokenConstants.CASE ); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.LOOP ); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.ELSE ); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.ESAC ); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.THEN ); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.BOOL_CONST,true); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.POOL ); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.CLASS ); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.BOOL_CONST,false); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.WHILE ); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.INHERITS ); }
					case -42:
						break;
					case 42:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -50:
						break;
					case 50:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -51:
						break;
					case 51:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -52:
						break;
					case 52:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -53:
						break;
					case 53:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -54:
						break;
					case 54:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -55:
						break;
					case 55:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -56:
						break;
					case 56:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -57:
						break;
					case 57:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -58:
						break;
					case 58:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -59:
						break;
					case 59:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -60:
						break;
					case 60:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -61:
						break;
					case 61:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -62:
						break;
					case 62:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -63:
						break;
					case 63:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -64:
						break;
					case 64:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -65:
						break;
					case 65:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -66:
						break;
					case 66:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -67:
						break;
					case 67:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -68:
						break;
					case 68:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -69:
						break;
					case 69:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -70:
						break;
					case 70:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -71:
						break;
					case 71:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -72:
						break;
					case 72:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -73:
						break;
					case 73:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -74:
						break;
					case 74:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -75:
						break;
					case 75:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -76:
						break;
					case 76:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -77:
						break;
					case 77:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -78:
						break;
					case 78:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -79:
						break;
					case 79:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -80:
						break;
					case 80:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -81:
						break;
					case 81:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -82:
						break;
					case 82:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -83:
						break;
					case 83:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -84:
						break;
					case 84:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -85:
						break;
					case 85:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -86:
						break;
					case 86:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -87:
						break;
					case 87:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -88:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
