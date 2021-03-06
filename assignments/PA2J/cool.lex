/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

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
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now

%init}

%eofval{

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
%eofval}

%class CoolLexer
%cup
%full
%state STRING,LINECOMMENT,NULLINSTRING
%state BLOCKCOMMENT

%%

<YYINITIAL>\"           {
                            yybegin(STRING);
                            string_buf=new StringBuffer();
                        }

<STRING,NULLINSTRING>\\b              {string_buf.append("\b");}
<STRING,NULLINSTRING>\\t              {string_buf.append("\t");}
<STRING,NULLINSTRING>\\n              {string_buf.append("\n");}
<STRING,NULLINSTRING>\\f              {string_buf.append("\f");}
<STRING,NULLINSTRING>\\\"              {string_buf.append("\"");}
<STRING,NULLINSTRING>\r              {string_buf.append("\r");}
<STRING,NULLINSTRING>\033              {string_buf.append("\033");}
<STRING>\\\0          {
                        yybegin(NULLINSTRING);
                    }
<STRING,NULLINSTRING>\\.              {string_buf.append(yytext().substring(1,2));}
<STRING>\"              {
                            yybegin(YYINITIAL);
                            if( string_buf.length() > 1024 ) {
                                return new Symbol(TokenConstants.ERROR,"String constant too long");
                            }
                            else
                            {
                                return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(string_buf.toString()));
                            }
                        }
<NULLINSTRING>\"              {
                            yybegin(YYINITIAL);
                            return new Symbol(TokenConstants.ERROR,"String contains null character.");
                        }
<STRING,NULLINSTRING>\\\n          {
                        curr_lineno++;
                        string_buf.append("\n");
                    }
<STRING,NULLINSTRING>\n          {
                        curr_lineno++;
                        string_buf=new StringBuffer();
                        yybegin(YYINITIAL);
                        return new Symbol(TokenConstants.ERROR,"Unterminated string constant");
                    }
<STRING>\0          {
                        yybegin(NULLINSTRING);
                    }
<STRING,NULLINSTRING>.        {string_buf.append(yytext());}

<YYINITIAL>--           {
                            yybegin(LINECOMMENT);
                        }
<LINECOMMENT>.          {}
<LINECOMMENT>\n         {
                            yybegin(YYINITIAL);
                            curr_lineno++;
                        }

<YYINITIAL>\(\*         {
                            yybegin(BLOCKCOMMENT);
                            block_comment_nb=1;
                        }
<BLOCKCOMMENT>\(\*      {
                            block_comment_nb=block_comment_nb+1;
                        }                        
<BLOCKCOMMENT>\*\)      {
                            block_comment_nb=block_comment_nb-1;
                            if( block_comment_nb==0 ) {
                                yybegin(YYINITIAL);
                            }
                        }  

<BLOCKCOMMENT>\015              {string_buf.append("\015");}
<BLOCKCOMMENT>\016              {string_buf.append("\016");}
<BLOCKCOMMENT>\017              {string_buf.append("\017");}
<BLOCKCOMMENT>\018              {string_buf.append("\018");}
<BLOCKCOMMENT>\019              {string_buf.append("\019");}
<BLOCKCOMMENT>\020              {string_buf.append("\020");}
<BLOCKCOMMENT>\021              {string_buf.append("\021");}
<BLOCKCOMMENT>\022              {string_buf.append("\022");}
<BLOCKCOMMENT>\023              {string_buf.append("\023");}
<BLOCKCOMMENT>\024              {string_buf.append("\024");}
<BLOCKCOMMENT>\025              {string_buf.append("\025");}
<BLOCKCOMMENT>\026              {string_buf.append("\026");}
<BLOCKCOMMENT>\027              {string_buf.append("\027");}
<BLOCKCOMMENT>\028              {string_buf.append("\028");}
<BLOCKCOMMENT>\029              {string_buf.append("\029");}
<BLOCKCOMMENT>\030              {string_buf.append("\030");}
<BLOCKCOMMENT>\031              {string_buf.append("\031");}
<BLOCKCOMMENT>\032              {string_buf.append("\032");}
<BLOCKCOMMENT>\033              {string_buf.append("\033");}
<BLOCKCOMMENT>\034              {string_buf.append("\034");}
<BLOCKCOMMENT>\035              {string_buf.append("\035");}
<BLOCKCOMMENT>\036              {string_buf.append("\036");}
<BLOCKCOMMENT>\n        {curr_lineno++;} 
<BLOCKCOMMENT>.         {} 


<YYINITIAL>"=>"			{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }

<YYINITIAL>"{"			{ return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>"("			{ return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>";"			{ return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>"*"			{ return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"-"			{ return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>")"			{ return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>"<"			{ return new Symbol(TokenConstants.LT); }
<YYINITIAL>"<="			{ return new Symbol(TokenConstants.LE); }
<YYINITIAL>","			{ return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>"/"			{ return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"+"			{ return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>"."			{ return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"="			{ return new Symbol(TokenConstants.EQ); }
<YYINITIAL>":"			{ return new Symbol(TokenConstants.COLON); }
<YYINITIAL>"}"			{ return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>"@"			{ return new Symbol(TokenConstants.AT); }
<YYINITIAL>"~"			{ return new Symbol(TokenConstants.NEG); }
<YYINITIAL>"<-"			{ return new Symbol(TokenConstants.ASSIGN); }

<YYINITIAL>[cC][lL][aA][sS][sS]     { return new Symbol(TokenConstants.CLASS ); }
<YYINITIAL>[iI][nN][hH][eE][rR][iI][tT][sS]     { return new Symbol(TokenConstants.INHERITS ); }
<YYINITIAL>[eE][lL][sS][eE]      { return new Symbol(TokenConstants.ELSE ); }
<YYINITIAL>[fF][iI]      { return new Symbol(TokenConstants.FI ); }
<YYINITIAL>[iI][fF]      { return new Symbol(TokenConstants.IF ); }
<YYINITIAL>[iI][nN]      { return new Symbol(TokenConstants.IN ); }
<YYINITIAL>[iI][sS][vV][oO][iI][dD]			{ return new Symbol(TokenConstants.ISVOID); }
<YYINITIAL>[lL][eE][tT]      { return new Symbol(TokenConstants.LET ); }
<YYINITIAL>[lL][oO][oO][pP]      { return new Symbol(TokenConstants.LOOP ); }
<YYINITIAL>[pP][oO][oO][lL]      { return new Symbol(TokenConstants.POOL ); }
<YYINITIAL>[tT][hH][eE][nN]      { return new Symbol(TokenConstants.THEN ); }
<YYINITIAL>[wW][hH][iI][lL][eE]      { return new Symbol(TokenConstants.WHILE ); }
<YYINITIAL>[cC][aA][sS][eE]      { return new Symbol(TokenConstants.CASE ); }
<YYINITIAL>[eE][sS][aA][cC]      { return new Symbol(TokenConstants.ESAC ); }
<YYINITIAL>[nN][eE][wW]      { return new Symbol(TokenConstants.NEW ); }
<YYINITIAL>[nN][oO][tT]			{ return new Symbol(TokenConstants.NOT); }
<YYINITIAL>[oO][fF]			{ return new Symbol(TokenConstants.OF); }
<YYINITIAL>t[rR][uU][eE]			{ return new Symbol(TokenConstants.BOOL_CONST,true); }
<YYINITIAL>f[aA][lL][sS][eE]			{ return new Symbol(TokenConstants.BOOL_CONST,false); }

<YYINITIAL>[A-Z][a-zA-Z0-9_]*      { return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
<YYINITIAL>[a-z][a-zA-Z0-9_]*      { return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
<YYINITIAL>[0-9]+                  { return new Symbol(TokenConstants.INT_CONST,AbstractTable.inttable.addString(yytext())); }


<YYINITIAL>\*\)                     { return new Symbol(TokenConstants.ERROR,"Unmatched *)"); }
<YYINITIAL>[ \t\v\f\r\013]      { }
<YYINITIAL>\n      { curr_lineno++; }
.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  //System.err.println("LEXER BUG - UNMATCHED: " + yytext());
                                  return new Symbol(TokenConstants.ERROR,yytext()); }
