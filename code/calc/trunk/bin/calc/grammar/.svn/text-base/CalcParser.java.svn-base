package calc.grammar;
// $ANTLR 3.4 /Users/michaelbar-sinai/Desktop/Calc.g 2012-07-18 01:49:04

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class CalcParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DIV", "MIN", "MUL", "NUMBER", "PLS", "WHITESPACE"
    };

    public static final int EOF=-1;
    public static final int DIV=4;
    public static final int MIN=5;
    public static final int MUL=6;
    public static final int NUMBER=7;
    public static final int PLS=8;
    public static final int WHITESPACE=9;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public CalcParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public CalcParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return CalcParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/michaelbar-sinai/Desktop/Calc.g"; }



    // $ANTLR start "expr"
    // /Users/michaelbar-sinai/Desktop/Calc.g:3:1: expr : subEx ( ( PLS | MIN ) subEx )* ;
    public final void expr() throws RecognitionException {
        try {
            // /Users/michaelbar-sinai/Desktop/Calc.g:3:6: ( subEx ( ( PLS | MIN ) subEx )* )
            // /Users/michaelbar-sinai/Desktop/Calc.g:3:8: subEx ( ( PLS | MIN ) subEx )*
            {
            pushFollow(FOLLOW_subEx_in_expr10);
            subEx();

            state._fsp--;


            // /Users/michaelbar-sinai/Desktop/Calc.g:3:14: ( ( PLS | MIN ) subEx )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==MIN||LA1_0==PLS) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/michaelbar-sinai/Desktop/Calc.g:3:16: ( PLS | MIN ) subEx
            	    {
            	    if ( input.LA(1)==MIN||input.LA(1)==PLS ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_subEx_in_expr20);
            	    subEx();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "expr"



    // $ANTLR start "subEx"
    // /Users/michaelbar-sinai/Desktop/Calc.g:4:1: subEx : atom ( ( DIV | MUL ) atom )* ;
    public final void subEx() throws RecognitionException {
        try {
            // /Users/michaelbar-sinai/Desktop/Calc.g:4:7: ( atom ( ( DIV | MUL ) atom )* )
            // /Users/michaelbar-sinai/Desktop/Calc.g:4:9: atom ( ( DIV | MUL ) atom )*
            {
            pushFollow(FOLLOW_atom_in_subEx30);
            atom();

            state._fsp--;


            // /Users/michaelbar-sinai/Desktop/Calc.g:4:14: ( ( DIV | MUL ) atom )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==DIV||LA2_0==MUL) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/michaelbar-sinai/Desktop/Calc.g:4:15: ( DIV | MUL ) atom
            	    {
            	    if ( input.LA(1)==DIV||input.LA(1)==MUL ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_atom_in_subEx39);
            	    atom();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "subEx"



    // $ANTLR start "atom"
    // /Users/michaelbar-sinai/Desktop/Calc.g:5:1: atom : NUMBER ;
    public final void atom() throws RecognitionException {
        try {
            // /Users/michaelbar-sinai/Desktop/Calc.g:5:6: ( NUMBER )
            // /Users/michaelbar-sinai/Desktop/Calc.g:5:8: NUMBER
            {
            match(input,NUMBER,FOLLOW_NUMBER_in_atom49); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_subEx_in_expr10 = new BitSet(new long[]{0x0000000000000122L});
    public static final BitSet FOLLOW_set_in_expr14 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_subEx_in_expr20 = new BitSet(new long[]{0x0000000000000122L});
    public static final BitSet FOLLOW_atom_in_subEx30 = new BitSet(new long[]{0x0000000000000052L});
    public static final BitSet FOLLOW_set_in_subEx33 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_atom_in_subEx39 = new BitSet(new long[]{0x0000000000000052L});
    public static final BitSet FOLLOW_NUMBER_in_atom49 = new BitSet(new long[]{0x0000000000000002L});

}