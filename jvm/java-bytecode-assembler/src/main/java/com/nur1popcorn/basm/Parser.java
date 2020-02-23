/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

   Copyright (C) 2007-2015 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

package  com.nur1popcorn.basm ;
/* First part of user declarations.  */

/* "./Parser.java":37  */ /* lalr1.java:91  */

/* "./Parser.java":39  */ /* lalr1.java:92  */
/* "%code imports" blocks.  */
/* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":25  */ /* lalr1.java:93  */

    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.IOException;

    import static com.nur1popcorn.basm.classfile.AccessFlags.*;

/* "./Parser.java":49  */ /* lalr1.java:93  */

/**
 * A Bison parser, automatically generated from <tt>/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
class  Parser 
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "3.0.4";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";


  /**
   * True if verbose error messages are enabled.
   */
  private boolean yyErrorVerbose = true;

  /**
   * Return whether verbose error messages are enabled.
   */
  public final boolean getErrorVerbose() { return yyErrorVerbose; }

  /**
   * Set the verbosity of error messages.
   * @param verbose True to request verbose error messages.
   */
  public final void setErrorVerbose(boolean verbose)
  { yyErrorVerbose = verbose; }




  

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt> Parser </tt>.
   */
  public interface Lexer {
    /** Token returned by the scanner to signal the end of its input.  */
    public static final int EOF = 0;

/* Tokens.  */
    /** Token number,to be returned by the scanner.  */
    static final int IMPORT = 258;
    /** Token number,to be returned by the scanner.  */
    static final int CLASS = 259;
    /** Token number,to be returned by the scanner.  */
    static final int FIELD = 260;
    /** Token number,to be returned by the scanner.  */
    static final int METHOD = 261;
    /** Token number,to be returned by the scanner.  */
    static final int L_BRACE = 262;
    /** Token number,to be returned by the scanner.  */
    static final int R_BRACE = 263;
    /** Token number,to be returned by the scanner.  */
    static final int L_PAREN = 264;
    /** Token number,to be returned by the scanner.  */
    static final int R_PAREN = 265;
    /** Token number,to be returned by the scanner.  */
    static final int COMMA = 266;
    /** Token number,to be returned by the scanner.  */
    static final int COLON = 267;
    /** Token number,to be returned by the scanner.  */
    static final int PUBLIC = 268;
    /** Token number,to be returned by the scanner.  */
    static final int PRIVATE = 269;
    /** Token number,to be returned by the scanner.  */
    static final int PROTECTED = 270;
    /** Token number,to be returned by the scanner.  */
    static final int STATIC = 271;
    /** Token number,to be returned by the scanner.  */
    static final int FINAL = 272;
    /** Token number,to be returned by the scanner.  */
    static final int SYNCHRONIZED = 273;
    /** Token number,to be returned by the scanner.  */
    static final int VOLATILE = 274;
    /** Token number,to be returned by the scanner.  */
    static final int TRANSIENT = 275;
    /** Token number,to be returned by the scanner.  */
    static final int NATIVE = 276;
    /** Token number,to be returned by the scanner.  */
    static final int INTERFACE = 277;
    /** Token number,to be returned by the scanner.  */
    static final int ABSTRACT = 278;
    /** Token number,to be returned by the scanner.  */
    static final int STRICT = 279;
    /** Token number,to be returned by the scanner.  */
    static final int SYNTHETIC = 280;
    /** Token number,to be returned by the scanner.  */
    static final int ANNOTATION = 281;
    /** Token number,to be returned by the scanner.  */
    static final int ENUM = 282;
    /** Token number,to be returned by the scanner.  */
    static final int SUPER = 283;
    /** Token number,to be returned by the scanner.  */
    static final int BRIDGE = 284;
    /** Token number,to be returned by the scanner.  */
    static final int VARARGS = 285;
    /** Token number,to be returned by the scanner.  */
    static final int MANDATED = 286;
    /** Token number,to be returned by the scanner.  */
    static final int IDENTIFIER = 287;
    /** Token number,to be returned by the scanner.  */
    static final int STRING_LIT = 288;


    

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.
     */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    int yylex () throws java.io.IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * 
     * @param msg The string for the error message.
     */
     void yyerror (String msg);
  }

  /**
   * The object doing lexical analysis for us.
   */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public  Parser  (Lexer yylexer) 
  {
    
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  /**
   * Print an error message via the lexer.
   *
   * @param msg The error message.
   */
  public final void yyerror (String msg)
  {
    yylexer.yyerror (msg);
  }


  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value                            ) {
      height++;
      if (size == height)
        {
          int[] newStateStack = new int[size * 2];
          System.arraycopy (stateStack, 0, newStateStack, 0, height);
          stateStack = newStateStack;
          

          Object[] newValueStack = new Object[size * 2];
          System.arraycopy (valueStack, 0, newValueStack, 0, height);
          valueStack = newValueStack;

          size *= 2;
        }

      stateStack[height] = state;
      
      valueStack[height] = value;
    }

    public final void pop () {
      pop (1);
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
        java.util.Arrays.fill (valueStack, height - num + 1, height + 1, null);
        
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++)
        {
          out.print (' ');
          out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).
   */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).
   */
  public static final int YYABORT = 1;



  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.
   */
  public static final int YYERROR = 2;

  /**
   * Internal return codes that are not supported for user semantic
   * actions.
   */
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;


  private int yyerrstatus_ = 0;


  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.
   */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  /** Compute post-reduction state.
   * @param yystate   the current state
   * @param yysym     the nonterminal to push on the stack
   */
  private int yy_lr_goto_state_ (int yystate, int yysym)
  {
    int yyr = yypgoto_[yysym - yyntokens_] + yystate;
    if (0 <= yyr && yyr <= yylast_ && yycheck_[yyr] == yystate)
      return yytable_[yyr];
    else
      return yydefgoto_[yysym - yyntokens_];
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) 
  {
    Object yyval;
    

    /* If YYLEN is nonzero, implement the default value of the action:
       '$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);

    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
          case 3:
  if (yyn == 3)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":131  */ /* lalr1.java:489  */
    {  };
  break;
    

  case 4:
  if (yyn == 4)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":132  */ /* lalr1.java:489  */
    { yyval = ((String)(yystack.valueAt (1-(1)))); };
  break;
    

  case 5:
  if (yyn == 5)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":133  */ /* lalr1.java:489  */
    { yyval = ((String)(yystack.valueAt (1-(1)))); };
  break;
    

  case 8:
  if (yyn == 8)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":139  */ /* lalr1.java:489  */
    {  };
  break;
    

  case 10:
  if (yyn == 10)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":143  */ /* lalr1.java:489  */
    { yyval = ACC_PUBLIC; };
  break;
    

  case 11:
  if (yyn == 11)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":144  */ /* lalr1.java:489  */
    { yyval = ACC_FINAL; };
  break;
    

  case 12:
  if (yyn == 12)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":145  */ /* lalr1.java:489  */
    { yyval = ACC_SUPER; };
  break;
    

  case 13:
  if (yyn == 13)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":146  */ /* lalr1.java:489  */
    { yyval = ACC_INTERFACE; };
  break;
    

  case 14:
  if (yyn == 14)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":147  */ /* lalr1.java:489  */
    { yyval = ACC_ABSTRACT; };
  break;
    

  case 15:
  if (yyn == 15)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":148  */ /* lalr1.java:489  */
    { yyval = ACC_SYNTHETIC; };
  break;
    

  case 16:
  if (yyn == 16)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":149  */ /* lalr1.java:489  */
    { yyval = ACC_ANNOTATION; };
  break;
    

  case 17:
  if (yyn == 17)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":150  */ /* lalr1.java:489  */
    { yyval = ACC_ENUM; };
  break;
    

  case 18:
  if (yyn == 18)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":154  */ /* lalr1.java:489  */
    { yyval = ((Integer)(yystack.valueAt (2-(1)))) | ((Integer)(yystack.valueAt (2-(2)))); };
  break;
    

  case 19:
  if (yyn == 19)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":155  */ /* lalr1.java:489  */
    { yyval = 0; };
  break;
    

  case 24:
  if (yyn == 24)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":164  */ /* lalr1.java:489  */
    {  };
  break;
    

  case 25:
  if (yyn == 25)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":167  */ /* lalr1.java:489  */
    { yyval = ACC_PUBLIC; };
  break;
    

  case 26:
  if (yyn == 26)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":168  */ /* lalr1.java:489  */
    { yyval = ACC_PRIVATE; };
  break;
    

  case 27:
  if (yyn == 27)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":169  */ /* lalr1.java:489  */
    { yyval = ACC_PROTECTED; };
  break;
    

  case 28:
  if (yyn == 28)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":170  */ /* lalr1.java:489  */
    { yyval = ACC_STATIC; };
  break;
    

  case 29:
  if (yyn == 29)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":171  */ /* lalr1.java:489  */
    { yyval = ACC_FINAL; };
  break;
    

  case 30:
  if (yyn == 30)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":172  */ /* lalr1.java:489  */
    { yyval = ACC_VOLATILE; };
  break;
    

  case 31:
  if (yyn == 31)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":173  */ /* lalr1.java:489  */
    { yyval = ACC_TRANSIENT; };
  break;
    

  case 32:
  if (yyn == 32)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":174  */ /* lalr1.java:489  */
    { yyval = ACC_SYNTHETIC; };
  break;
    

  case 33:
  if (yyn == 33)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":175  */ /* lalr1.java:489  */
    { yyval = ACC_ENUM; };
  break;
    

  case 34:
  if (yyn == 34)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":178  */ /* lalr1.java:489  */
    { yyval = ((Integer)(yystack.valueAt (2-(1)))) | ((Integer)(yystack.valueAt (2-(2)))); };
  break;
    

  case 35:
  if (yyn == 35)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":179  */ /* lalr1.java:489  */
    { yyval = 0; };
  break;
    

  case 36:
  if (yyn == 36)
    /* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":182  */ /* lalr1.java:489  */
    {  };
  break;
    


/* "./Parser.java":583  */ /* lalr1.java:489  */
        default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    int yystate = yy_lr_goto_state_ (yystack.stateAt (0), yyr1_[yyn]);
    yystack.push (yystate, yyval);
    return YYNEWSTATE;
  }


  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
              if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }


  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
                                 Object yyvaluep                                 )
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
              + yytname_[yytype] + " ("
              + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }


  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
   public boolean parse () throws java.io.IOException

  {
    


    /* Lookahead and lookahead in internal form.  */
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;
    YYStack yystack = new YYStack ();
    int label = YYNEWSTATE;

    /* Error handling.  */
    int yynerrs_ = 0;
    

    /* Semantic value of the lookahead.  */
    Object yylval = null;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;

    /* Initialize the stack.  */
    yystack.push (yystate, yylval );



    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
           pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);

        /* Accept?  */
        if (yystate == yyfinal_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yy_pact_value_is_default_ (yyn))
          {
            label = YYDEFAULT;
            break;
          }

        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {


            yycdebug ("Reading a token: ");
            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal ();

          }

        /* Convert token to internal form.  */
        if (yychar <= Lexer.EOF)
          {
            yychar = yytoken = Lexer.EOF;
            yycdebug ("Now at end of input.\n");
          }
        else
          {
            yytoken = yytranslate_ (yychar);
            yy_symbol_print ("Next token is", yytoken,
                             yylval);
          }

        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;

        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
            if (yy_table_value_is_error_ (yyn))
              label = YYERRLAB;
            else
              {
                yyn = -yyn;
                label = YYREDUCE;
              }
          }

        else
          {
            /* Shift the lookahead token.  */
            yy_symbol_print ("Shifting", yytoken,
                             yylval);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval);
            label = YYNEWSTATE;
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
        yystate = yystack.stateAt (0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs_;
            if (yychar == yyempty_)
              yytoken = yyempty_;
            yyerror (yysyntax_error (yystate, yytoken));
          }

        
        if (yyerrstatus_ == 3)
          {
        /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

        if (yychar <= Lexer.EOF)
          {
          /* Return failure if at end of input.  */
          if (yychar == Lexer.EOF)
            return false;
          }
        else
            yychar = yyempty_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `-------------------------------------------------*/
      case YYERROR:

        
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;       /* Each real token shifted decrements this.  */

        for (;;)
          {
            yyn = yypact_[yystate];
            if (!yy_pact_value_is_default_ (yyn))
              {
                yyn += yyterror_;
                if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
                  {
                    yyn = yytable_[yyn];
                    if (0 < yyn)
                      break;
                  }
              }

            /* Pop the current state because it cannot handle the
             * error token.  */
            if (yystack.height == 0)
              return false;

            
            yystack.pop ();
            yystate = yystack.stateAt (0);
            if (yydebug > 0)
              yystack.print (yyDebugStream);
          }

        if (label == YYABORT)
            /* Leave the switch.  */
            break;



        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
                         yylval);

        yystate = yyn;
        yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
}




  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (yyErrorVerbose)
      {
        /* There are many possibilities here to consider:
           - If this state is a consistent state with a default action,
             then the only way this function was invoked is if the
             default action is an error action.  In that case, don't
             check for expected tokens because there are none.
           - The only way there can be no lookahead present (in tok) is
             if this state is a consistent state with a default action.
             Thus, detecting the absence of a lookahead is sufficient to
             determine that there is no unexpected or expected token to
             report.  In that case, just report a simple "syntax error".
           - Don't assume there isn't a lookahead just because this
             state is a consistent state with a default action.  There
             might have been a previous inconsistent state, consistent
             state with a non-default action, or user semantic action
             that manipulated yychar.  (However, yychar is currently out
             of scope during semantic actions.)
           - Of course, the expected token list depends on states to
             have correct lookahead information, and it depends on the
             parser not to perform extra reductions after fetching a
             lookahead from the scanner and before detecting a syntax
             error.  Thus, state merging (from LALR or IELR) and default
             reductions corrupt the expected token list.  However, the
             list is correct for canonical LR with one exception: it
             will still contain any token that will not be accepted due
             to an error action in a later state.
        */
        if (tok != yyempty_)
          {
            /* FIXME: This method of building the message is not compatible
               with internationalization.  */
            StringBuffer res =
              new StringBuffer ("syntax error, unexpected ");
            res.append (yytnamerr_ (yytname_[tok]));
            int yyn = yypact_[yystate];
            if (!yy_pact_value_is_default_ (yyn))
              {
                /* Start YYX at -YYN if negative to avoid negative
                   indexes in YYCHECK.  In other words, skip the first
                   -YYN actions for this state because they are default
                   actions.  */
                int yyxbegin = yyn < 0 ? -yyn : 0;
                /* Stay within bounds of both yycheck and yytname.  */
                int yychecklim = yylast_ - yyn + 1;
                int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
                int count = 0;
                for (int x = yyxbegin; x < yyxend; ++x)
                  if (yycheck_[x + yyn] == x && x != yyterror_
                      && !yy_table_value_is_error_ (yytable_[x + yyn]))
                    ++count;
                if (count < 5)
                  {
                    count = 0;
                    for (int x = yyxbegin; x < yyxend; ++x)
                      if (yycheck_[x + yyn] == x && x != yyterror_
                          && !yy_table_value_is_error_ (yytable_[x + yyn]))
                        {
                          res.append (count++ == 0 ? ", expecting " : " or ");
                          res.append (yytnamerr_ (yytname_[x]));
                        }
                  }
              }
            return res.toString ();
          }
      }

    return "syntax error";
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yy_pact_value_is_default_ (int yyvalue)
  {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code>
   * value indicates a syntax error.
   * @param yyvalue the value to check
   */
  private static boolean yy_table_value_is_error_ (int yyvalue)
  {
    return yyvalue == yytable_ninf_;
  }

  private static final byte yypact_ninf_ = -9;
  private static final byte yytable_ninf_ = -1;

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final byte yypact_[] = yypact_init();
  private static final byte[] yypact_init()
  {
    return new byte[]
    {
      -9,    11,     4,    -9,    -9,    -9,    -9,     2,    -9,    16,
      -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,
      -9,    -9,     3,    -9,    -9,    -9,    -9,     1,    -9,    -9,
      -9,    -9,    -9,    -1,    -9,    -9,    -9,    -9,    -9,    -9,
      -9,    -9,    -9,    -9,    -9
    };
  }

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
  private static final byte yydefact_[] = yydefact_init();
  private static final byte[] yydefact_init()
  {
    return new byte[]
    {
       9,     0,     0,     1,     6,     7,    19,     0,     2,     0,
       3,     4,     5,     8,    10,    11,    13,    14,    15,    16,
      17,    12,     0,    18,    20,    21,    38,     0,    22,    35,
      23,    24,    37,     0,    25,    26,    27,    28,    29,    30,
      31,    32,    33,    36,    34
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] = yypgoto_init();
  private static final byte[] yypgoto_init()
  {
    return new byte[]
    {
      -9,    -9,    -8,    -9,    -9,    -9,    -9,    -9,    -9,    -9,
      -9,    -9,    -9,    -9
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final byte yydefgoto_[] = yydefgoto_init();
  private static final byte[] yydefgoto_init()
  {
    return new byte[]
    {
      -1,     1,    13,     7,     2,    23,     9,    26,    31,     8,
      44,    33,    32,    27
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final byte yytable_[] = yytable_init();
  private static final byte[] yytable_init()
  {
    return new byte[]
    {
      10,    22,    28,    10,    24,     4,    29,     5,     6,    30,
      25,     3,    34,    35,    36,    37,    38,    10,    39,    40,
       0,     0,     0,     0,    41,    43,    42,     0,     0,    14,
       0,    11,    12,    15,    11,    12,     0,     0,    16,    17,
       0,    18,    19,    20,    21,     0,     0,     0,    11,    12
    };
  }

private static final byte yycheck_[] = yycheck_init();
  private static final byte[] yycheck_init()
  {
    return new byte[]
    {
       1,     9,     1,     1,     1,     1,     5,     3,     4,     8,
       7,     0,    13,    14,    15,    16,    17,     1,    19,    20,
      -1,    -1,    -1,    -1,    25,    33,    27,    -1,    -1,    13,
      -1,    32,    33,    17,    32,    33,    -1,    -1,    22,    23,
      -1,    25,    26,    27,    28,    -1,    -1,    -1,    32,    33
    };
  }

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
  private static final byte yystos_[] = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,    35,    38,     0,     1,     3,     4,    37,    43,    40,
       1,    32,    33,    36,    13,    17,    22,    23,    25,    26,
      27,    28,    36,    39,     1,     7,    41,    47,     1,     5,
       8,    42,    46,    45,    13,    14,    15,    16,    17,    19,
      20,    25,    27,    36,    44
    };
  }

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte yyr1_[] = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    34,    35,    36,    36,    36,    37,    37,    38,    38,
      39,    39,    39,    39,    39,    39,    39,    39,    40,    40,
      41,    41,    42,    42,    43,    44,    44,    44,    44,    44,
      44,    44,    44,    44,    45,    45,    46,    47,    47
    };
  }

/* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
  private static final byte yyr2_[] = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     1,     1,     1,     1,     1,     3,     0,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     0,
       1,     1,     1,     1,     6,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     2,     0,     3,     2,     0
    };
  }

  /* YYTOKEN_NUMBER[YYLEX-NUM] -- Internal symbol number corresponding
      to YYLEX-NUM.  */
  private static final short yytoken_number_[] = yytoken_number_init();
  private static final short[] yytoken_number_init()
  {
    return new short[]
    {
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288
    };
  }

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] = yytname_init();
  private static final String[] yytname_init()
  {
    return new String[]
    {
  "$end", "error", "$undefined", "\".import\"", "\".class\"",
  "\".field\"", "\".method\"", "\"{\"", "\"}\"", "\"(\"", "\")\"", "\",\"",
  "\":\"", "\"public\"", "\"private\"", "\"protected\"", "\"static\"",
  "\"final\"", "\"synchronized\"", "\"volatile\"", "\"transient\"",
  "\"native\"", "\"interface\"", "\"abstract\"", "\"strict\"",
  "\"synthetic\"", "\"annotation\"", "\"enum\"", "\"super\"", "\"bridge\"",
  "\"varargs\"", "\"mandated\"", "IDENTIFIER", "STRING_LIT", "$accept",
  "program", "identifier", "import", "imports", "class_modifier",
  "class_modifiers", "l_brace", "r_brace", "class_declaration",
  "field_modifier", "field_modifiers", "field", "directives", null
    };
  }

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] = yyrline_init();
  private static final short[] yyrline_init()
  {
    return new short[]
    {
       0,   128,   128,   131,   132,   133,   136,   136,   139,   140,
     143,   144,   145,   146,   147,   148,   149,   150,   154,   155,
     158,   158,   161,   161,   164,   167,   168,   169,   170,   171,
     172,   173,   174,   175,   178,   179,   182,   185,   186
    };
  }


  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
              + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
                       yystos_[yystack.stateAt(yynrhs - (yyi + 1))],
                       ((yystack.valueAt (yynrhs-(yyi + 1)))));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] = yytranslate_table_init();
  private static final byte[] yytranslate_table_init()
  {
    return new byte[]
    {
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33
    };
  }

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 49;
  private static final int yynnts_ = 14;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 3;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 34;

  private static final int yyuser_token_number_max_ = 288;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */
/* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":33  */ /* lalr1.java:1066  */

    private static final class LexerAdapter implements Lexer {
        private final FlexLexer lexer;
        private Yytoken last;

        public LexerAdapter(InputStream in) {
            lexer = new FlexLexer(new InputStreamReader(in));
        }

        /**
         * Method to retrieve the semantic value of the last scanned token.
         *
         * @return the semantic value of the last scanned token.
         */
        @Override
        public Object getLVal() {
            return last.getLVal();
        }

        /**
         * Entry point for the scanner.  Returns the token identifier corresponding
         * to the next token and prepares to return the semantic value
         * of the token.
         *
         * @return the token identifier corresponding to the next token.
         */
        @Override
        public int yylex() throws IOException {
            return (last = lexer.yylex()).getToken();
        }

        /**
         * Entry point for error reporting.  Emits an error
         * in a user-defined way.
         *
         * @param msg The string for the error message.
         */
        @Override
        public void yyerror(String msg) {
            System.out.println(msg);
        }
    }

    public static void main(String args[]) throws IOException {
        Parser parser= new Parser(new LexerAdapter(System.in));
        parser.setDebugLevel(100);
        parser.parse();
    }

/* "./Parser.java":1294  */ /* lalr1.java:1066  */

}

/* "/home/ben/Desktop/Things/Projects/Present/BASM/jvm/java-bytecode-assembler/src/main/yacc/parser.y":188  */ /* lalr1.java:1070  */


final class Yytoken<T> {
    private int token;
    private T lVal;

    public Yytoken(int token) {
        this.token = token;
    }

    public Yytoken(int token, T lVal) {
        this.token = token;
        this.lVal = lVal;
    }

    public int getToken() {
        return token;
    }

    public T getLVal() {
        return lVal;
    }
}
