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
    int block_comment_nb;
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
	private final int BLOCKCOMMENT = 3;
	private final int YYINITIAL = 0;
	private final int LINECOMMENT = 2;
	private final int yy_state_dtrans[] = {
		0,
		49,
		62,
		85
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
		/* 62 */ YY_NOT_ACCEPT,
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
		/* 85 */ YY_NOT_ACCEPT,
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
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"8:9,63,11,8,63,9,8:18,63,25,1,8:5,12,14,13,22,20,10,23,21,62:10,24,18,19,15" +
",16,8,27,45,46,47,48,49,38,46,50,51,46:2,52,46,33,53,54,46,55,56,37,57,58,5" +
"9,46:3,8,2,8:2,60,8,30,3,28,41,35,6,61,34,32,61:2,29,61,5,40,42,61,36,31,4," +
"44,39,43,61:3,17,7,26,8:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,170,
"0,1:3,2,1,3,1,4,1:2,5,1:2,6,1:8,7,8,2,1:5,9,2:17,10,1:12,11,12,13,14,15,16," +
"15:15,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39," +
"40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64," +
"65,66,67,68,69,70,15,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88," +
"89,90,91,92,93,94,95,96,97,98,99,100,101,102")[0];

	private int yy_nxt[][] = unpackFromString(103,64,
"1,2,3,4,157,130,64,3:2,5,6,7,8,9,10,11,3,12,13,14,15,16,17,18,19,20,21,22,1" +
"58,136,4:2,86,23,4,159,4,131,65,4,89,4,160,166,4,137:2,141,137,143,137,87,1" +
"45,90,147,137:4,149,3,4,24,5,-1:67,4:4,-1:21,4:35,-1:11,26,-1:66,27,-1:66,2" +
"8,-1:57,29,-1:4,30,-1:51,137:4,-1:21,137:7,92,137:4,94,137:8,92,137:3,94,13" +
"7:9,-1:63,24,-1:4,4:4,-1:21,4:6,169,4:15,169,4:12,-1,1,50,63,83:4,3,83:56,1" +
",56:8,-1,56,57,56:52,-1,51:2,52,53,54,55,51:2,-1,51,-1,51:52,-1:3,4:4,-1:21" +
",4:2,161,4,25,4:12,161,4:5,25,4:11,-1:4,137:4,-1:21,137:4,66,137:18,66,137:" +
"11,-1:4,137:4,-1:21,137:35,-1:4,137:4,-1:21,137:6,118,137:15,118,137:12,-1:" +
"4,83:4,-1,83:56,-1:13,60,-1:50,1,58:8,-1,58,59,84,88,58:50,-1:3,4:2,31,32,-" +
"1:21,4:3,167,4,31,4:4,32,4:17,167,4:6,-1:4,137:2,67,68,-1:21,137:3,102,137," +
"67,137:4,68,137:17,102,137:6,-1:15,61,-1:52,4:3,33,-1:21,4:10,33,4:24,-1:4," +
"137:3,69,-1:21,137:10,69,137:24,-1:4,4:4,-1:21,4:15,34,4:15,34,4:3,-1:4,137" +
":4,-1:21,137:15,70,137:15,70,137:3,-1:4,4,35,4:2,-1:21,4:9,35,4:25,-1:4,137" +
",71,137:2,-1:21,137:9,71,137:25,-1:4,4,36,4:2,-1:21,4:9,36,4:25,-1:4,137:4," +
"-1:21,137:7,110,137:13,110,137:13,-1:4,4:2,37,4,-1:21,4:5,37,4:29,-1:4,137:" +
"4,-1:21,137:2,138,137:14,138,137:17,-1:4,4:4,-1:21,4:7,38,4:13,38,4:13,-1:4" +
",137:4,-1:21,137:3,112,137:24,112,137:6,-1:4,4:4,-1:21,4:7,39,4:13,39,4:13," +
"-1:4,137:4,-1:21,137:11,139,137:18,139,137:4,-1:4,4:4,-1:21,4:14,40,4:11,40" +
",4:8,-1:4,137,72,137:2,-1:21,137:9,72,137:25,-1:4,4:4,-1:21,4:7,41,4:13,41," +
"4:13,-1:4,137:4,-1:21,137:12,120,137:12,120,137:9,-1:4,4:4,-1:21,42,4:18,42" +
",4:15,-1:4,137:4,-1:21,137:4,122,137:18,122,137:11,-1:4,4:4,-1:21,4,43,4:22" +
",43,4:10,-1:4,137:2,73,137,-1:21,137:5,73,137:29,-1:4,4:4,-1:21,4:7,44,4:13" +
",44,4:13,-1:4,137:4,-1:21,137:7,74,137:13,74,137:13,-1:4,4:4,-1:21,4:3,45,4" +
":24,45,4:6,-1:4,137:4,-1:21,137:7,76,137:13,76,137:13,-1:4,4:4,-1:21,4:7,46" +
",4:13,46,4:13,-1:4,137:4,-1:21,77,137:18,77,137:15,-1:4,4:4,-1:21,4:13,47,4" +
":6,47,4:14,-1:4,137:4,-1:21,137:7,124,137:13,124,137:13,-1:4,4:4,-1:21,4:3," +
"48,4:24,48,4:6,-1:4,137:4,-1:21,137:14,75,137:11,75,137:8,-1:4,137:4,-1:21," +
"137,78,137:22,78,137:10,-1:4,137:4,-1:21,137,126,137:22,126,137:10,-1:4,137" +
":4,-1:21,137:3,79,137:24,79,137:6,-1:4,137:4,-1:21,137:8,135,137:18,135,137" +
":7,-1:4,137:4,-1:21,137:4,127,137:18,127,137:11,-1:4,137:4,-1:21,137:7,80,1" +
"37:13,80,137:13,-1:4,137:4,-1:21,137:13,81,137:6,81,137:14,-1:4,137,129,137" +
":2,-1:21,137:9,129,137:25,-1:4,137:4,-1:21,137:3,82,137:24,82,137:6,-1:4,4:" +
"4,-1:21,4:7,91,4:4,93,4:8,91,4:3,93,4:9,-1:4,137:4,-1:21,137:6,96,137:15,96" +
",137:12,-1:4,137:4,-1:21,137:2,116,137:14,116,137:17,-1:4,137:4,-1:21,137:3" +
",114,137:24,114,137:6,-1:4,137:4,-1:21,137:12,121,137:12,121,137:9,-1:4,137" +
":4,-1:21,137:4,128,137:18,128,137:11,-1:4,4:4,-1:21,4:7,95,4:4,146,4:8,95,4" +
":3,146,4:9,-1:4,137:4,-1:21,137:3,123,137:24,123,137:6,-1:4,137:4,-1:21,137" +
":12,125,137:12,125,137:9,-1:4,4:4,-1:21,4:7,97,4:13,97,4:13,-1:4,137:4,-1:2" +
"1,137,98,100,137:14,100,137:6,98,137:10,-1:4,4:4,-1:21,4:16,99,4:12,99,4:5," +
"-1:4,137:4,-1:21,137,133,137,132,137:20,133,137:3,132,137:6,-1:4,4:4,-1:21," +
"4:3,101,4:24,101,4:6,-1:4,137:4,-1:21,137:7,104,137:4,106,137:8,104,137:3,1" +
"06,137:9,-1:4,4:4,-1:21,4:12,103,4:12,103,4:9,-1:4,137:4,-1:21,137:12,134,1" +
"37:12,134,137:9,-1:4,4:4,-1:21,4:3,105,4:24,105,4:6,-1:4,137:4,-1:21,137:6," +
"108,137:15,108,137:12,-1:4,4:4,-1:21,4:2,107,4:14,107,4:17,-1:4,4:4,-1:21,4" +
":12,109,4:12,109,4:9,-1:4,4:4,-1:21,4:3,111,4:24,111,4:6,-1:4,4:4,-1:21,4:3" +
",113,4:24,113,4:6,-1:4,4:4,-1:21,4,115,4:22,115,4:10,-1:4,4:4,-1:21,4:4,117" +
",4:18,117,4:11,-1:4,4,119,4:2,-1:21,4:9,119,4:25,-1:4,4:4,-1:21,4:6,140,4,1" +
"42,4:13,140,4:4,142,4:7,-1:4,4:4,-1:21,4,162,144,4:14,144,4:6,162,4:10,-1:4" +
",4:4,-1:21,4,148,4,150,4:20,148,4:3,150,4:6,-1:4,4:4,-1:21,4:12,151,4:12,15" +
"1,4:9,-1:4,4:4,-1:21,4,152,4:22,152,4:10,-1:4,4:4,-1:21,4:2,153,4:14,153,4:" +
"17,-1:4,4:4,-1:21,4:4,154,4:18,154,4:11,-1:4,4:4,-1:21,4:12,155,4:12,155,4:" +
"9,-1:4,4:4,-1:21,4:4,156,4:18,156,4:11,-1:4,4:4,-1:21,4:6,163,4:15,163,4:12" +
",-1:4,4:4,-1:21,4:11,164,4:18,164,4:4,-1:4,4:4,-1:21,4:8,165,4:18,165,4:7,-" +
"1:4,4:4,-1:21,4:7,168,4:13,168,4:13,-1");

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
    case BLOCKCOMMENT:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR,"EOF in comment");
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
                                  //System.err.println("LEXER BUG - UNMATCHED: " + yytext());
                                  return new Symbol(TokenConstants.ERROR,yytext()); }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -5:
						break;
					case 5:
						{ }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.MINUS); }
					case -7:
						break;
					case 7:
						{ curr_lineno++; }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.MULT); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.EQ); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.SEMI); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.LT); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.COMMA); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.DIV); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.PLUS); }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.DOT); }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.COLON); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.NEG); }
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -22:
						break;
					case 22:
						{ return new Symbol(TokenConstants.AT); }
					case -23:
						break;
					case 23:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -24:
						break;
					case 24:
						{ return new Symbol(TokenConstants.INT_CONST,AbstractTable.inttable.addString(yytext())); }
					case -25:
						break;
					case 25:
						{ return new Symbol(TokenConstants.FI ); }
					case -26:
						break;
					case 26:
						{
                            yybegin(LINECOMMENT);
                        }
					case -27:
						break;
					case 27:
						{
                            yybegin(BLOCKCOMMENT);
                            block_comment_nb=1;
                        }
					case -28:
						break;
					case 28:
						{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -30:
						break;
					case 30:
						{ return new Symbol(TokenConstants.LE); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.IN ); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.IF ); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.OF); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.NEW ); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.NOT); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.LET ); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.THEN ); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.BOOL_CONST,true); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.CASE ); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.LOOP ); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.ELSE ); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.ESAC ); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.POOL ); }
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.BOOL_CONST,false); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.CLASS ); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.WHILE ); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.INHERITS ); }
					case -49:
						break;
					case 49:
						{string_buf.append(yytext());}
					case -50:
						break;
					case 50:
						{
                            yybegin(YYINITIAL);
                            return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(string_buf.toString()));
                        }
					case -51:
						break;
					case 51:
						{string_buf.append(yytext().substring(1,1));}
					case -52:
						break;
					case 52:
						{string_buf.append("\b");}
					case -53:
						break;
					case 53:
						{string_buf.append("\t");}
					case -54:
						break;
					case 54:
						{string_buf.append("\n");}
					case -55:
						break;
					case 55:
						{string_buf.append("\f");}
					case -56:
						break;
					case 56:
						{}
					case -57:
						break;
					case 57:
						{
                            yybegin(YYINITIAL);
                            curr_lineno++;
                        }
					case -58:
						break;
					case 58:
						{}
					case -59:
						break;
					case 59:
						{curr_lineno++;}
					case -60:
						break;
					case 60:
						{
                            block_comment_nb=block_comment_nb+1;
                        }
					case -61:
						break;
					case 61:
						{
                            block_comment_nb=block_comment_nb-1;
                            if( block_comment_nb==0 ) {
                                yybegin(YYINITIAL);
                            }
                        }
					case -62:
						break;
					case 63:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  //System.err.println("LEXER BUG - UNMATCHED: " + yytext());
                                  return new Symbol(TokenConstants.ERROR,yytext()); }
					case -63:
						break;
					case 64:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -64:
						break;
					case 65:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -65:
						break;
					case 66:
						{ return new Symbol(TokenConstants.FI ); }
					case -66:
						break;
					case 67:
						{ return new Symbol(TokenConstants.IN ); }
					case -67:
						break;
					case 68:
						{ return new Symbol(TokenConstants.IF ); }
					case -68:
						break;
					case 69:
						{ return new Symbol(TokenConstants.OF); }
					case -69:
						break;
					case 70:
						{ return new Symbol(TokenConstants.NEW ); }
					case -70:
						break;
					case 71:
						{ return new Symbol(TokenConstants.NOT); }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.LET ); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.THEN ); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.CASE ); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.LOOP ); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.ELSE ); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.ESAC ); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.POOL ); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.CLASS ); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.WHILE ); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.INHERITS ); }
					case -82:
						break;
					case 83:
						{string_buf.append(yytext());}
					case -83:
						break;
					case 84:
						{}
					case -84:
						break;
					case 86:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -85:
						break;
					case 87:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -86:
						break;
					case 88:
						{}
					case -87:
						break;
					case 89:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -88:
						break;
					case 90:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -89:
						break;
					case 91:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -90:
						break;
					case 92:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -91:
						break;
					case 93:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -92:
						break;
					case 94:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -93:
						break;
					case 95:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -94:
						break;
					case 96:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -95:
						break;
					case 97:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -96:
						break;
					case 98:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -97:
						break;
					case 99:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -98:
						break;
					case 100:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -99:
						break;
					case 101:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -100:
						break;
					case 102:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -101:
						break;
					case 103:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -102:
						break;
					case 104:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -103:
						break;
					case 105:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -104:
						break;
					case 106:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -105:
						break;
					case 107:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -106:
						break;
					case 108:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -107:
						break;
					case 109:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -108:
						break;
					case 110:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -109:
						break;
					case 111:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -110:
						break;
					case 112:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -111:
						break;
					case 113:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -112:
						break;
					case 114:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -113:
						break;
					case 115:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -114:
						break;
					case 116:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -115:
						break;
					case 117:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -116:
						break;
					case 118:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -117:
						break;
					case 119:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -118:
						break;
					case 120:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -119:
						break;
					case 121:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -120:
						break;
					case 122:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -121:
						break;
					case 123:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -122:
						break;
					case 124:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -123:
						break;
					case 125:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -124:
						break;
					case 126:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -125:
						break;
					case 127:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -126:
						break;
					case 128:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -127:
						break;
					case 129:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -128:
						break;
					case 130:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -129:
						break;
					case 131:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -130:
						break;
					case 132:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -131:
						break;
					case 133:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -132:
						break;
					case 134:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -133:
						break;
					case 135:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -134:
						break;
					case 136:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -135:
						break;
					case 137:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -136:
						break;
					case 138:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -137:
						break;
					case 139:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -138:
						break;
					case 140:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -139:
						break;
					case 141:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -140:
						break;
					case 142:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -141:
						break;
					case 143:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -142:
						break;
					case 144:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -143:
						break;
					case 145:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -144:
						break;
					case 146:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -145:
						break;
					case 147:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -146:
						break;
					case 148:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -147:
						break;
					case 149:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -148:
						break;
					case 150:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -149:
						break;
					case 151:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -150:
						break;
					case 152:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -151:
						break;
					case 153:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -152:
						break;
					case 154:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -153:
						break;
					case 155:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -154:
						break;
					case 156:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -155:
						break;
					case 157:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -156:
						break;
					case 158:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -157:
						break;
					case 159:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -158:
						break;
					case 160:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -159:
						break;
					case 161:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -160:
						break;
					case 162:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -161:
						break;
					case 163:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -162:
						break;
					case 164:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -163:
						break;
					case 165:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -164:
						break;
					case 166:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -165:
						break;
					case 167:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -166:
						break;
					case 168:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -167:
						break;
					case 169:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -168:
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
