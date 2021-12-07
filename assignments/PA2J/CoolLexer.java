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
	private final int STRING = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0,
		43
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
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"8:9,42,9,8,42,9,8:18,42,24,1,8:5,13,17,15,20,18,16,21,19,41:10,23,14,22,10," +
"11,8,26,41:26,8,2,8:2,41,8,29,3,27,37,33,6,41,32,31,41:2,28,41,5,36,38,41,3" +
"4,30,4,40,35,39,41:3,12,7,25,8:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,98,
"0,1:3,2,1,3,1:15,2,1:2,4,2:17,5,1:6,6,7,8,9,10,11,12,13,14,15,16,17,18,19,2" +
"0,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,4" +
"5,46,47,48,49,50,51,52,53")[0];

	private int yy_nxt[][] = unpackFromString(54,43,
"1,2,3,4,85,71,51,3:2,5,6,7,8,9,10,11,12,13,14,15,16,17,50,18,19,20,21,86,72" +
",4:2,54,4,87,4:2,55,4,88,94,4:2,5,-1:46,4:4,-1:20,4:15,-1:12,23,-1:34,4:4,-" +
"1:20,4:5,97,4:9,-1,1,44,53,52:4,3,52:35,-1:10,24,-1:35,4:4,-1:20,4:2,89,4,2" +
"2,4:10,-1:4,52:4,-1,52:35,-1,45:2,46,47,48,49,45:2,-1,45:33,-1:3,4:2,25,26," +
"-1:20,4:3,95,4:11,-1:4,4:3,27,-1:20,4:15,-1:4,4:4,-1:20,4:12,28,4:2,-1:4,4," +
"29,4:2,-1:20,4:15,-1:4,4,30,4:2,-1:20,4:15,-1:4,4:2,31,4,-1:20,4:15,-1:4,4:" +
"4,-1:20,4:6,32,4:8,-1:4,4:4,-1:20,4:6,33,4:8,-1:4,4:4,-1:20,4:11,34,4:3,-1:" +
"4,4:4,-1:20,4:6,35,4:8,-1:4,4:4,-1:20,36,4:14,-1:4,4:4,-1:20,4,37,4:13,-1:4" +
",4:4,-1:20,4:6,38,4:8,-1:4,4:4,-1:20,4:3,39,4:11,-1:4,4:4,-1:20,4:6,40,4:8," +
"-1:4,4:4,-1:20,4:10,41,4:4,-1:4,4:4,-1:20,4:3,42,4:11,-1:4,4:4,-1:20,4:6,56" +
",4:2,57,4:5,-1:4,4:4,-1:20,4:6,58,4:2,76,4:5,-1:4,4:4,-1:20,4:6,59,4:8,-1:4" +
",4:4,-1:20,4:13,60,4,-1:4,4:4,-1:20,4:3,61,4:11,-1:4,4:4,-1:20,4:9,62,4:5,-" +
"1:4,4:4,-1:20,4:3,63,4:11,-1:4,4:4,-1:20,4:2,64,4:12,-1:4,4:4,-1:20,4:9,65," +
"4:5,-1:4,4:4,-1:20,4:3,66,4:11,-1:4,4:4,-1:20,4:3,67,4:11,-1:4,4:4,-1:20,4," +
"68,4:13,-1:4,4:4,-1:20,4:4,69,4:10,-1:4,4,70,4:2,-1:20,4:15,-1:4,4:4,-1:20," +
"4:5,73,4,74,4:7,-1:4,4:4,-1:20,4,90,75,4:12,-1:4,4:4,-1:20,4,77,4,78,4:11,-" +
"1:4,4:4,-1:20,4:9,79,4:5,-1:4,4:4,-1:20,4,80,4:13,-1:4,4:4,-1:20,4:2,81,4:1" +
"2,-1:4,4:4,-1:20,4:4,82,4:10,-1:4,4:4,-1:20,4:9,83,4:5,-1:4,4:4,-1:20,4:4,8" +
"4,4:10,-1:4,4:4,-1:20,4:5,91,4:9,-1:4,4:4,-1:20,4:8,92,4:6,-1:4,4:4,-1:20,4" +
":7,93,4:7,-1:4,4:4,-1:20,4:6,96,4:8,-1");

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
						{
                            yybegin(STRING);
                            string_buf=new StringBuffer();
                        }
					case -3:
						break;
					case 3:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -5:
						break;
					case 5:
						{ }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.EQ); }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.LT); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.SEMI); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.MULT); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.MINUS); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.COMMA); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.DIV); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.PLUS); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.DOT); }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.COLON); }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.NEG); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.AT); }
					case -22:
						break;
					case 22:
						{ return new Symbol(TokenConstants.FI ); }
					case -23:
						break;
					case 23:
						{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
					case -24:
						break;
					case 24:
						{ return new Symbol(TokenConstants.LE); }
					case -25:
						break;
					case 25:
						{ return new Symbol(TokenConstants.IN ); }
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.IF ); }
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.OF); }
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
						{ return new Symbol(TokenConstants.LET ); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.THEN ); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.BOOL_CONST,true); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.CASE ); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.LOOP ); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.ELSE ); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.ESAC ); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.POOL ); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.BOOL_CONST,false); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.CLASS ); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.WHILE ); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.INHERITS ); }
					case -43:
						break;
					case 43:
						{string_buf.append(yytext());}
					case -44:
						break;
					case 44:
						{
                            yybegin(YYINITIAL);
                            return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(string_buf.toString()));
                        }
					case -45:
						break;
					case 45:
						{string_buf.append(yytext().substring(1,1));}
					case -46:
						break;
					case 46:
						{string_buf.append("\b");}
					case -47:
						break;
					case 47:
						{string_buf.append("\t");}
					case -48:
						break;
					case 48:
						{string_buf.append("\n");}
					case -49:
						break;
					case 49:
						{string_buf.append("\f");}
					case -50:
						break;
					case 50:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -51:
						break;
					case 51:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -52:
						break;
					case 52:
						{string_buf.append(yytext());}
					case -53:
						break;
					case 53:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
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
					case 88:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -89:
						break;
					case 89:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -90:
						break;
					case 90:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -91:
						break;
					case 91:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -92:
						break;
					case 92:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -93:
						break;
					case 93:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -94:
						break;
					case 94:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -95:
						break;
					case 95:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -96:
						break;
					case 96:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -97:
						break;
					case 97:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -98:
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
