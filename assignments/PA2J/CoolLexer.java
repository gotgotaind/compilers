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
	private final int BLOCKCOMMENT = 4;
	private final int YYINITIAL = 0;
	private final int NULLINSTRING = 3;
	private final int LINECOMMENT = 2;
	private final int yy_state_dtrans[] = {
		0,
		69,
		90,
		94,
		97
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
		/* 69 */ YY_NOT_ACCEPT,
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
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NOT_ACCEPT,
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
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"8,9:8,63,7,9,63,64,9:18,63,24,1,9:5,11,13,12,21,19,10,22,20,62:10,23,17,18," +
"14,15,9,26,45,46,47,48,49,38,46,50,51,46:2,52,46,33,53,54,46,55,56,37,57,58" +
",59,46:3,9,2,9:2,60,9,30,3,28,41,35,6,61,34,32,61:2,29,61,5,40,42,61,36,31," +
"4,44,39,43,61:3,16,9,25,27,9,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,177,
"0,1:3,2,1,3,4,1:2,5,1:2,6,1:9,7,8,1,2,1:5,9,2:17,1,10,1:17,11,12,13,14,15,1" +
"4:15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,3" +
"9,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,6" +
"4,65,66,67,68,69,70,14,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,8" +
"8,89,90,91,92,93,94,95,96,97,98,99,100,101,102")[0];

	private int yy_nxt[][] = unpackFromString(103,65,
"1,2,3,4,164,137,70,5,3:2,6,7,8,9,10,3,11,12,13,14,15,16,17,18,19,20,21,22,1" +
"65,143,4:2,91,23,4,166,4,138,71,4,95,4,167,173,4,144:2,148,144,150,144,92,1" +
"52,96,154,144:4,156,3,4,24,25:2,-1:68,4:4,-1:21,4:35,-1:12,27,-1:66,28,-1:6" +
"7,29,-1:59,30,-1:3,31,-1:53,144:4,-1:21,144:7,99,144:4,101,144:8,99,144:3,1" +
"01,144:9,-1:64,24,-1:5,4:4,-1:21,4:6,176,4:15,176,4:12,-1:3,55,56,57,58,59," +
"60,61,-1:57,1,50,51,52:4,53,54,52:55,-1:4,4:4,-1:21,4:2,168,4,26,4:12,168,4" +
":5,26,4:11,-1:5,144:4,-1:21,144:4,72,144:18,72,144:11,-1:5,144:4,-1:21,144:" +
"35,-1:5,144:4,-1:21,144:6,125,144:15,125,144:12,-1:14,67,-1:52,1,62:6,63,62" +
":56,-1:4,4:2,32,33,-1:21,4:3,174,4,32,4:4,33,4:17,174,4:6,-1:5,144:2,73,74," +
"-1:21,144:3,109,144,73,144:4,74,144:17,109,144:6,-1:15,68,-1:51,1,64,51,52:" +
"4,53,52:56,-1:4,4:3,34,-1:21,4:10,34,4:24,-1:5,144:3,75,-1:21,144:10,75,144" +
":24,-1:2,1,65:6,66,65:3,89,93,65:51,-1:4,4:4,-1:21,4:15,35,4:15,35,4:3,-1:5" +
",144:4,-1:21,144:15,76,144:15,76,144:3,-1:5,4,36,4:2,-1:21,4:9,36,4:25,-1:5" +
",144,77,144:2,-1:21,144:9,77,144:25,-1:5,4,37,4:2,-1:21,4:9,37,4:25,-1:5,14" +
"4:4,-1:21,144:7,117,144:13,117,144:13,-1:5,4:2,38,4,-1:21,4:5,38,4:29,-1:5," +
"144:4,-1:21,144:2,145,144:14,145,144:17,-1:5,4:4,-1:21,4:7,39,4:13,39,4:13," +
"-1:5,144:4,-1:21,144:3,119,144:24,119,144:6,-1:5,4:4,-1:21,4:7,40,4:13,40,4" +
":13,-1:5,144:4,-1:21,144:11,146,144:18,146,144:4,-1:5,4:4,-1:21,4:14,41,4:1" +
"1,41,4:8,-1:5,144,78,144:2,-1:21,144:9,78,144:25,-1:5,4:4,-1:21,4:7,42,4:13" +
",42,4:13,-1:5,144:4,-1:21,144:12,127,144:12,127,144:9,-1:5,4:4,-1:21,43,4:1" +
"8,43,4:15,-1:5,144:4,-1:21,144:4,129,144:18,129,144:11,-1:5,4:4,-1:21,4,44," +
"4:22,44,4:10,-1:5,144:2,79,144,-1:21,144:5,79,144:29,-1:5,4:4,-1:21,4:7,45," +
"4:13,45,4:13,-1:5,144:4,-1:21,144:7,80,144:13,80,144:13,-1:5,4:4,-1:21,4:3," +
"46,4:24,46,4:6,-1:5,144:4,-1:21,144:7,82,144:13,82,144:13,-1:5,4:4,-1:21,4:" +
"7,47,4:13,47,4:13,-1:5,144:4,-1:21,83,144:18,83,144:15,-1:5,4:4,-1:21,4:13," +
"48,4:6,48,4:14,-1:5,144:4,-1:21,144:7,131,144:13,131,144:13,-1:5,4:4,-1:21," +
"4:3,49,4:24,49,4:6,-1:5,144:4,-1:21,144:14,81,144:11,81,144:8,-1:5,144:4,-1" +
":21,144,84,144:22,84,144:10,-1:5,144:4,-1:21,144,133,144:22,133,144:10,-1:5" +
",144:4,-1:21,144:3,85,144:24,85,144:6,-1:5,144:4,-1:21,144:8,142,144:18,142" +
",144:7,-1:5,144:4,-1:21,144:4,134,144:18,134,144:11,-1:5,144:4,-1:21,144:7," +
"86,144:13,86,144:13,-1:5,144:4,-1:21,144:13,87,144:6,87,144:14,-1:5,144,136" +
",144:2,-1:21,144:9,136,144:25,-1:5,144:4,-1:21,144:3,88,144:24,88,144:6,-1:" +
"5,4:4,-1:21,4:7,98,4:4,100,4:8,98,4:3,100,4:9,-1:5,144:4,-1:21,144:6,103,14" +
"4:15,103,144:12,-1:5,144:4,-1:21,144:2,123,144:14,123,144:17,-1:5,144:4,-1:" +
"21,144:3,121,144:24,121,144:6,-1:5,144:4,-1:21,144:12,128,144:12,128,144:9," +
"-1:5,144:4,-1:21,144:4,135,144:18,135,144:11,-1:5,4:4,-1:21,4:7,102,4:4,153" +
",4:8,102,4:3,153,4:9,-1:5,144:4,-1:21,144:3,130,144:24,130,144:6,-1:5,144:4" +
",-1:21,144:12,132,144:12,132,144:9,-1:5,4:4,-1:21,4:7,104,4:13,104,4:13,-1:" +
"5,144:4,-1:21,144,105,107,144:14,107,144:6,105,144:10,-1:5,4:4,-1:21,4:16,1" +
"06,4:12,106,4:5,-1:5,144:4,-1:21,144,140,144,139,144:20,140,144:3,139,144:6" +
",-1:5,4:4,-1:21,4:3,108,4:24,108,4:6,-1:5,144:4,-1:21,144:7,111,144:4,113,1" +
"44:8,111,144:3,113,144:9,-1:5,4:4,-1:21,4:12,110,4:12,110,4:9,-1:5,144:4,-1" +
":21,144:12,141,144:12,141,144:9,-1:5,4:4,-1:21,4:3,112,4:24,112,4:6,-1:5,14" +
"4:4,-1:21,144:6,115,144:15,115,144:12,-1:5,4:4,-1:21,4:2,114,4:14,114,4:17," +
"-1:5,4:4,-1:21,4:12,116,4:12,116,4:9,-1:5,4:4,-1:21,4:3,118,4:24,118,4:6,-1" +
":5,4:4,-1:21,4:3,120,4:24,120,4:6,-1:5,4:4,-1:21,4,122,4:22,122,4:10,-1:5,4" +
":4,-1:21,4:4,124,4:18,124,4:11,-1:5,4,126,4:2,-1:21,4:9,126,4:25,-1:5,4:4,-" +
"1:21,4:6,147,4,149,4:13,147,4:4,149,4:7,-1:5,4:4,-1:21,4,169,151,4:14,151,4" +
":6,169,4:10,-1:5,4:4,-1:21,4,155,4,157,4:20,155,4:3,157,4:6,-1:5,4:4,-1:21," +
"4:12,158,4:12,158,4:9,-1:5,4:4,-1:21,4,159,4:22,159,4:10,-1:5,4:4,-1:21,4:2" +
",160,4:14,160,4:17,-1:5,4:4,-1:21,4:4,161,4:18,161,4:11,-1:5,4:4,-1:21,4:12" +
",162,4:12,162,4:9,-1:5,4:4,-1:21,4:4,163,4:18,163,4:11,-1:5,4:4,-1:21,4:6,1" +
"70,4:15,170,4:12,-1:5,4:4,-1:21,4:11,171,4:18,171,4:4,-1:5,4:4,-1:21,4:8,17" +
"2,4:18,172,4:7,-1:5,4:4,-1:21,4:7,175,4:13,175,4:13,-1:2");

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
    case STRING:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR,"EOF in string constant");    
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
						{ curr_lineno++; }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.MINUS); }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.MULT); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.EQ); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.SEMI); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.LT); }
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
						{ return new Symbol(TokenConstants.NOT); }
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
						{ return new Symbol(TokenConstants.NEG); }
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
						{ }
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.FI ); }
					case -27:
						break;
					case 27:
						{
                            yybegin(LINECOMMENT);
                        }
					case -28:
						break;
					case 28:
						{
                            yybegin(BLOCKCOMMENT);
                            block_comment_nb=1;
                        }
					case -29:
						break;
					case 29:
						{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
					case -30:
						break;
					case 30:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.LE); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.IN ); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.IF ); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.OF); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.NEW ); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.NOT); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.LET ); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.THEN ); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.BOOL_CONST,true); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.CASE ); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.LOOP ); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.ELSE ); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.ESAC ); }
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.POOL ); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.BOOL_CONST,false); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.CLASS ); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.WHILE ); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.INHERITS ); }
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
						{string_buf.append(yytext());}
					case -53:
						break;
					case 53:
						{
                        curr_lineno++;
                        string_buf=new StringBuffer();
                        yybegin(YYINITIAL);
                        return new Symbol(TokenConstants.ERROR,"Unterminated string constant");
                    }
					case -54:
						break;
					case 54:
						{
                        yybegin(NULLINSTRING);
                    }
					case -55:
						break;
					case 55:
						{string_buf.append("\"");}
					case -56:
						break;
					case 56:
						{string_buf.append("\\");}
					case -57:
						break;
					case 57:
						{string_buf.append("\b");}
					case -58:
						break;
					case 58:
						{string_buf.append("\t");}
					case -59:
						break;
					case 59:
						{string_buf.append("\n");}
					case -60:
						break;
					case 60:
						{string_buf.append("\f");}
					case -61:
						break;
					case 61:
						{
                        curr_lineno++;
                        string_buf.append("\n");
                    }
					case -62:
						break;
					case 62:
						{}
					case -63:
						break;
					case 63:
						{
                            yybegin(YYINITIAL);
                            curr_lineno++;
                        }
					case -64:
						break;
					case 64:
						{
                            yybegin(YYINITIAL);
                            return new Symbol(TokenConstants.ERROR,"String contains null character.");
                        }
					case -65:
						break;
					case 65:
						{}
					case -66:
						break;
					case 66:
						{curr_lineno++;}
					case -67:
						break;
					case 67:
						{
                            block_comment_nb=block_comment_nb+1;
                        }
					case -68:
						break;
					case 68:
						{
                            block_comment_nb=block_comment_nb-1;
                            if( block_comment_nb==0 ) {
                                yybegin(YYINITIAL);
                            }
                        }
					case -69:
						break;
					case 70:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -70:
						break;
					case 71:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.FI ); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.IN ); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.IF ); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.OF); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.NEW ); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.NOT); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.LET ); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.THEN ); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.CASE ); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.LOOP ); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.ELSE ); }
					case -82:
						break;
					case 83:
						{ return new Symbol(TokenConstants.ESAC ); }
					case -83:
						break;
					case 84:
						{ return new Symbol(TokenConstants.POOL ); }
					case -84:
						break;
					case 85:
						{ return new Symbol(TokenConstants.CLASS ); }
					case -85:
						break;
					case 86:
						{ return new Symbol(TokenConstants.WHILE ); }
					case -86:
						break;
					case 87:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -87:
						break;
					case 88:
						{ return new Symbol(TokenConstants.INHERITS ); }
					case -88:
						break;
					case 89:
						{}
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
						{}
					case -92:
						break;
					case 95:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -93:
						break;
					case 96:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -94:
						break;
					case 98:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -95:
						break;
					case 99:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -96:
						break;
					case 100:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -97:
						break;
					case 101:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -98:
						break;
					case 102:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -99:
						break;
					case 103:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -100:
						break;
					case 104:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -101:
						break;
					case 105:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -102:
						break;
					case 106:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -103:
						break;
					case 107:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -104:
						break;
					case 108:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -105:
						break;
					case 109:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -106:
						break;
					case 110:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -107:
						break;
					case 111:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -108:
						break;
					case 112:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -109:
						break;
					case 113:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -110:
						break;
					case 114:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -111:
						break;
					case 115:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -112:
						break;
					case 116:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -113:
						break;
					case 117:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -114:
						break;
					case 118:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -115:
						break;
					case 119:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -116:
						break;
					case 120:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -117:
						break;
					case 121:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -118:
						break;
					case 122:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -119:
						break;
					case 123:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -120:
						break;
					case 124:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -121:
						break;
					case 125:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -122:
						break;
					case 126:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -123:
						break;
					case 127:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -124:
						break;
					case 128:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -125:
						break;
					case 129:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -126:
						break;
					case 130:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -127:
						break;
					case 131:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -128:
						break;
					case 132:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -129:
						break;
					case 133:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -130:
						break;
					case 134:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -131:
						break;
					case 135:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -132:
						break;
					case 136:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -133:
						break;
					case 137:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -134:
						break;
					case 138:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -135:
						break;
					case 139:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -136:
						break;
					case 140:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -137:
						break;
					case 141:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -138:
						break;
					case 142:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -139:
						break;
					case 143:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -140:
						break;
					case 144:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -141:
						break;
					case 145:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -142:
						break;
					case 146:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -143:
						break;
					case 147:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -144:
						break;
					case 148:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -145:
						break;
					case 149:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -146:
						break;
					case 150:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -147:
						break;
					case 151:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -148:
						break;
					case 152:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -149:
						break;
					case 153:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -150:
						break;
					case 154:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -151:
						break;
					case 155:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -152:
						break;
					case 156:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -153:
						break;
					case 157:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -154:
						break;
					case 158:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -155:
						break;
					case 159:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -156:
						break;
					case 160:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -157:
						break;
					case 161:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -158:
						break;
					case 162:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -159:
						break;
					case 163:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -160:
						break;
					case 164:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -161:
						break;
					case 165:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -162:
						break;
					case 166:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -163:
						break;
					case 167:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -164:
						break;
					case 168:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -165:
						break;
					case 169:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -166:
						break;
					case 170:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -167:
						break;
					case 171:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -168:
						break;
					case 172:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -169:
						break;
					case 173:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -170:
						break;
					case 174:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -171:
						break;
					case 175:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -172:
						break;
					case 176:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -173:
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
