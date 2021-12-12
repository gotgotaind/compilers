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
		71,
		93,
		98,
		101
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
		/* 71 */ YY_NOT_ACCEPT,
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
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NOT_ACCEPT,
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
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"9,10:8,64,11,64:2,7,10:13,8,10:4,64,10,1,10:5,13,15,14,23,21,12,24,22,63:10" +
",25,19,20,16,17,10,27,46,47,48,49,50,39,47,51,52,47:2,53,47,34,54,55,47,56," +
"57,38,58,59,60,47:3,10,2,10:2,61,10,31,3,29,42,36,6,62,35,33,62:2,30,62,5,4" +
"1,43,62,37,32,4,45,40,44,62:3,18,10,26,28,10,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,181,
"0,1:3,2,1:2,3,4,5,1,6,1:2,7,1:8,8,9,2,1:6,10,2:17,1,11,1:19,12,13,14,15,16," +
"15:15,1,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,3" +
"9,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,6" +
"4,65,66,67,68,69,70,71,72,15,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,8" +
"8,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104")[0];

	private int yy_nxt[][] = unpackFromString(105,65,
"1,2,3,4,168,141,72,5,3:3,6,7,8,9,10,11,3,12,13,14,15,16,17,18,19,20,21,22,1" +
"69,147,4:2,94,23,4,170,4,142,73,4,99,4,171,177,4,148:2,152,148,154,148,95,1" +
"56,100,158,148:4,160,3,4,24,5,-1:68,4:4,-1:22,4:35,-1:13,26,-1:66,27,-1:65," +
"28,-1:66,29,-1:59,30,-1:3,31,-1:51,148:4,-1:22,148:7,103,148:4,105,148:8,10" +
"3,148:3,105,148:9,-1:64,24,-1:4,4:4,-1:22,4:6,180,4:15,180,4:12,-1:2,56,57," +
"58,59,60,61,-1,57,62,57,63,57:53,1,50,51,91:4,52,53,54,91,55,91:53,-1:3,4:4" +
",-1:22,4:2,172,4,25,4:12,172,4:5,25,4:11,-1:4,148:4,-1:22,148:4,74,148:18,7" +
"4,148:11,-1:4,148:4,-1:22,148:35,-1:4,148:4,-1:22,148:6,129,148:15,129,148:" +
"12,-1:15,69,-1:50,1,64:6,-1,64:3,65,64:53,-1:3,4:2,32,33,-1:22,4:3,178,4,32" +
",4:4,33,4:17,178,4:6,-1:4,148:2,75,76,-1:22,148:3,113,148,75,148:4,76,148:1" +
"7,113,148:6,-1:2,56,57,58,59,60,61,-1,57:3,63,57:53,-1:15,70,-1:49,1,66,96," +
"91:4,52,53,91:2,55,91:53,-1:3,4:3,34,-1:22,4:10,34,4:24,-1:4,148:3,77,-1:22" +
",148:10,77,148:24,-1,1,67:6,-1,67:3,68,67,92,97,67:50,-1:3,4:4,-1:22,4:15,3" +
"5,4:15,35,4:3,-1:4,148:4,-1:22,148:15,78,148:15,78,148:3,-1:4,4,36,4:2,-1:2" +
"2,4:9,36,4:25,-1:4,148,79,148:2,-1:22,148:9,79,148:25,-1:4,4,37,4:2,-1:22,4" +
":9,37,4:25,-1:4,148:4,-1:22,148:7,121,148:13,121,148:13,-1:4,4:2,38,4,-1:22" +
",4:5,38,4:29,-1:4,148:4,-1:22,148:2,149,148:14,149,148:17,-1:4,4:4,-1:22,4:" +
"7,39,4:13,39,4:13,-1:4,148:4,-1:22,148:3,123,148:24,123,148:6,-1:4,4:4,-1:2" +
"2,4:7,40,4:13,40,4:13,-1:4,148:4,-1:22,148:11,150,148:18,150,148:4,-1:4,4:4" +
",-1:22,4:14,41,4:11,41,4:8,-1:4,148,80,148:2,-1:22,148:9,80,148:25,-1:4,4:4" +
",-1:22,4:7,42,4:13,42,4:13,-1:4,148:4,-1:22,148:12,131,148:12,131,148:9,-1:" +
"4,4:4,-1:22,43,4:18,43,4:15,-1:4,148:4,-1:22,148:4,133,148:18,133,148:11,-1" +
":4,4:4,-1:22,4,44,4:22,44,4:10,-1:4,148:2,81,148,-1:22,148:5,81,148:29,-1:4" +
",4:4,-1:22,4:7,45,4:13,45,4:13,-1:4,148:4,-1:22,148:7,82,148:13,82,148:13,-" +
"1:4,4:4,-1:22,4:3,46,4:24,46,4:6,-1:4,148:4,-1:22,148:7,84,148:13,84,148:13" +
",-1:4,4:4,-1:22,4:7,47,4:13,47,4:13,-1:4,148:4,-1:22,85,148:18,85,148:15,-1" +
":4,4:4,-1:22,4:13,48,4:6,48,4:14,-1:4,148:4,-1:22,148:7,135,148:13,135,148:" +
"13,-1:4,4:4,-1:22,4:3,49,4:24,49,4:6,-1:4,148:4,-1:22,148:14,83,148:11,83,1" +
"48:8,-1:4,148:4,-1:22,148,86,148:22,86,148:10,-1:4,148:4,-1:22,148,137,148:" +
"22,137,148:10,-1:4,148:4,-1:22,148:3,87,148:24,87,148:6,-1:4,148:4,-1:22,14" +
"8:8,146,148:18,146,148:7,-1:4,148:4,-1:22,148:4,138,148:18,138,148:11,-1:4," +
"148:4,-1:22,148:7,88,148:13,88,148:13,-1:4,148:4,-1:22,148:13,89,148:6,89,1" +
"48:14,-1:4,148,140,148:2,-1:22,148:9,140,148:25,-1:4,148:4,-1:22,148:3,90,1" +
"48:24,90,148:6,-1:4,4:4,-1:22,4:7,102,4:4,104,4:8,102,4:3,104,4:9,-1:4,148:" +
"4,-1:22,148:6,107,148:15,107,148:12,-1:4,148:4,-1:22,148:2,127,148:14,127,1" +
"48:17,-1:4,148:4,-1:22,148:3,125,148:24,125,148:6,-1:4,148:4,-1:22,148:12,1" +
"32,148:12,132,148:9,-1:4,148:4,-1:22,148:4,139,148:18,139,148:11,-1:4,4:4,-" +
"1:22,4:7,106,4:4,157,4:8,106,4:3,157,4:9,-1:4,148:4,-1:22,148:3,134,148:24," +
"134,148:6,-1:4,148:4,-1:22,148:12,136,148:12,136,148:9,-1:4,4:4,-1:22,4:7,1" +
"08,4:13,108,4:13,-1:4,148:4,-1:22,148,109,111,148:14,111,148:6,109,148:10,-" +
"1:4,4:4,-1:22,4:16,110,4:12,110,4:5,-1:4,148:4,-1:22,148,144,148,143,148:20" +
",144,148:3,143,148:6,-1:4,4:4,-1:22,4:3,112,4:24,112,4:6,-1:4,148:4,-1:22,1" +
"48:7,115,148:4,117,148:8,115,148:3,117,148:9,-1:4,4:4,-1:22,4:12,114,4:12,1" +
"14,4:9,-1:4,148:4,-1:22,148:12,145,148:12,145,148:9,-1:4,4:4,-1:22,4:3,116," +
"4:24,116,4:6,-1:4,148:4,-1:22,148:6,119,148:15,119,148:12,-1:4,4:4,-1:22,4:" +
"2,118,4:14,118,4:17,-1:4,4:4,-1:22,4:12,120,4:12,120,4:9,-1:4,4:4,-1:22,4:3" +
",122,4:24,122,4:6,-1:4,4:4,-1:22,4:3,124,4:24,124,4:6,-1:4,4:4,-1:22,4,126," +
"4:22,126,4:10,-1:4,4:4,-1:22,4:4,128,4:18,128,4:11,-1:4,4,130,4:2,-1:22,4:9" +
",130,4:25,-1:4,4:4,-1:22,4:6,151,4,153,4:13,151,4:4,153,4:7,-1:4,4:4,-1:22," +
"4,173,155,4:14,155,4:6,173,4:10,-1:4,4:4,-1:22,4,159,4,161,4:20,159,4:3,161" +
",4:6,-1:4,4:4,-1:22,4:12,162,4:12,162,4:9,-1:4,4:4,-1:22,4,163,4:22,163,4:1" +
"0,-1:4,4:4,-1:22,4:2,164,4:14,164,4:17,-1:4,4:4,-1:22,4:4,165,4:18,165,4:11" +
",-1:4,4:4,-1:22,4:12,166,4:12,166,4:9,-1:4,4:4,-1:22,4:4,167,4:18,167,4:11," +
"-1:4,4:4,-1:22,4:6,174,4:15,174,4:12,-1:4,4:4,-1:22,4:11,175,4:18,175,4:4,-" +
"1:4,4:4,-1:22,4:8,176,4:18,176,4:7,-1:4,4:4,-1:22,4:7,179,4:13,179,4:13,-1");

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
						{ }
					case -6:
						break;
					case 6:
						{ curr_lineno++; }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.MINUS); }
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
						{ return new Symbol(TokenConstants.ERROR,"Unmatched *)"); }
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
                            if( string_buf.length() > 1024 ) {
                                return new Symbol(TokenConstants.ERROR,"String constant too long");
                            }
                            else
                            {
                                return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(string_buf.toString()));
                            }
                        }
					case -51:
						break;
					case 51:
						{string_buf.append(yytext());}
					case -52:
						break;
					case 52:
						{string_buf.append("\r");}
					case -53:
						break;
					case 53:
						{string_buf.append("\033");}
					case -54:
						break;
					case 54:
						{
                        yybegin(NULLINSTRING);
                    }
					case -55:
						break;
					case 55:
						{
                        curr_lineno++;
                        string_buf=new StringBuffer();
                        yybegin(YYINITIAL);
                        return new Symbol(TokenConstants.ERROR,"Unterminated string constant");
                    }
					case -56:
						break;
					case 56:
						{string_buf.append("\"");}
					case -57:
						break;
					case 57:
						{string_buf.append(yytext().substring(1,2));}
					case -58:
						break;
					case 58:
						{string_buf.append("\b");}
					case -59:
						break;
					case 59:
						{string_buf.append("\t");}
					case -60:
						break;
					case 60:
						{string_buf.append("\n");}
					case -61:
						break;
					case 61:
						{string_buf.append("\f");}
					case -62:
						break;
					case 62:
						{
                        yybegin(NULLINSTRING);
                    }
					case -63:
						break;
					case 63:
						{
                        curr_lineno++;
                        string_buf.append("\n");
                    }
					case -64:
						break;
					case 64:
						{}
					case -65:
						break;
					case 65:
						{
                            yybegin(YYINITIAL);
                            curr_lineno++;
                        }
					case -66:
						break;
					case 66:
						{
                            yybegin(YYINITIAL);
                            return new Symbol(TokenConstants.ERROR,"String contains null character.");
                        }
					case -67:
						break;
					case 67:
						{}
					case -68:
						break;
					case 68:
						{curr_lineno++;}
					case -69:
						break;
					case 69:
						{
                            block_comment_nb=block_comment_nb+1;
                        }
					case -70:
						break;
					case 70:
						{
                            block_comment_nb=block_comment_nb-1;
                            if( block_comment_nb==0 ) {
                                yybegin(YYINITIAL);
                            }
                        }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.FI ); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.IN ); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.IF ); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.OF); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.NEW ); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.NOT); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.LET ); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.THEN ); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.CASE ); }
					case -82:
						break;
					case 83:
						{ return new Symbol(TokenConstants.LOOP ); }
					case -83:
						break;
					case 84:
						{ return new Symbol(TokenConstants.ELSE ); }
					case -84:
						break;
					case 85:
						{ return new Symbol(TokenConstants.ESAC ); }
					case -85:
						break;
					case 86:
						{ return new Symbol(TokenConstants.POOL ); }
					case -86:
						break;
					case 87:
						{ return new Symbol(TokenConstants.CLASS ); }
					case -87:
						break;
					case 88:
						{ return new Symbol(TokenConstants.WHILE ); }
					case -88:
						break;
					case 89:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -89:
						break;
					case 90:
						{ return new Symbol(TokenConstants.INHERITS ); }
					case -90:
						break;
					case 91:
						{string_buf.append(yytext());}
					case -91:
						break;
					case 92:
						{}
					case -92:
						break;
					case 94:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -93:
						break;
					case 95:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -94:
						break;
					case 96:
						{string_buf.append(yytext());}
					case -95:
						break;
					case 97:
						{}
					case -96:
						break;
					case 99:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -97:
						break;
					case 100:
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
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -125:
						break;
					case 129:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -126:
						break;
					case 130:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
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
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
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
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -138:
						break;
					case 142:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -139:
						break;
					case 143:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
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
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
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
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -155:
						break;
					case 159:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -156:
						break;
					case 160:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
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
					case 177:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -174:
						break;
					case 178:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -175:
						break;
					case 179:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -176:
						break;
					case 180:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -177:
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
