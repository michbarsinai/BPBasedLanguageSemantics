package calc.grammar;
// $ANTLR 3.4 /Users/michaelbar-sinai/Desktop/Calc.g 2012-07-18 01:49:04

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class CalcLexer extends Lexer {
    public static final int EOF=-1;
    public static final int DIV=4;
    public static final int MIN=5;
    public static final int MUL=6;
    public static final int NUMBER=7;
    public static final int PLS=8;
    public static final int WHITESPACE=9;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public CalcLexer() {} 
    public CalcLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CalcLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/Users/michaelbar-sinai/Desktop/Calc.g"; }

    // $ANTLR start "PLS"
    public final void mPLS() throws RecognitionException {
        try {
            int _type = PLS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/michaelbar-sinai/Desktop/Calc.g:6:5: ( '+' )
            // /Users/michaelbar-sinai/Desktop/Calc.g:6:7: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLS"

    // $ANTLR start "MIN"
    public final void mMIN() throws RecognitionException {
        try {
            int _type = MIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/michaelbar-sinai/Desktop/Calc.g:7:5: ( '-' )
            // /Users/michaelbar-sinai/Desktop/Calc.g:7:7: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MIN"

    // $ANTLR start "MUL"
    public final void mMUL() throws RecognitionException {
        try {
            int _type = MUL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/michaelbar-sinai/Desktop/Calc.g:8:5: ( '*' )
            // /Users/michaelbar-sinai/Desktop/Calc.g:8:7: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MUL"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/michaelbar-sinai/Desktop/Calc.g:9:5: ( '/' )
            // /Users/michaelbar-sinai/Desktop/Calc.g:9:7: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/michaelbar-sinai/Desktop/Calc.g:10:8: ( ( '0' .. '9' )+ )
            // /Users/michaelbar-sinai/Desktop/Calc.g:10:10: ( '0' .. '9' )+
            {
            // /Users/michaelbar-sinai/Desktop/Calc.g:10:10: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/michaelbar-sinai/Desktop/Calc.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/michaelbar-sinai/Desktop/Calc.g:12:13: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // /Users/michaelbar-sinai/Desktop/Calc.g:12:17: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHITESPACE"

    public void mTokens() throws RecognitionException {
        // /Users/michaelbar-sinai/Desktop/Calc.g:1:8: ( PLS | MIN | MUL | DIV | NUMBER | WHITESPACE )
        int alt2=6;
        switch ( input.LA(1) ) {
        case '+':
            {
            alt2=1;
            }
            break;
        case '-':
            {
            alt2=2;
            }
            break;
        case '*':
            {
            alt2=3;
            }
            break;
        case '/':
            {
            alt2=4;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt2=5;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt2=6;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 2, 0, input);

            throw nvae;

        }

        switch (alt2) {
            case 1 :
                // /Users/michaelbar-sinai/Desktop/Calc.g:1:10: PLS
                {
                mPLS(); 


                }
                break;
            case 2 :
                // /Users/michaelbar-sinai/Desktop/Calc.g:1:14: MIN
                {
                mMIN(); 


                }
                break;
            case 3 :
                // /Users/michaelbar-sinai/Desktop/Calc.g:1:18: MUL
                {
                mMUL(); 


                }
                break;
            case 4 :
                // /Users/michaelbar-sinai/Desktop/Calc.g:1:22: DIV
                {
                mDIV(); 


                }
                break;
            case 5 :
                // /Users/michaelbar-sinai/Desktop/Calc.g:1:26: NUMBER
                {
                mNUMBER(); 


                }
                break;
            case 6 :
                // /Users/michaelbar-sinai/Desktop/Calc.g:1:33: WHITESPACE
                {
                mWHITESPACE(); 


                }
                break;

        }

    }


 

}