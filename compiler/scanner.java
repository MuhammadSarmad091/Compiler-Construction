package compiler;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.*;
import compiler.TokenType;
import compiler.Token;
import compiler.Globals;
import compiler.ErrorHandler;
import compiler.SymbolTable;
enum StateType
{
	START_0,
	PLUSMINUS_1,
	ININT_2,
	INFLOAT_3,
	INFLOAT_4,
	INFLOAT_5,
	INFLOAT_6,
	INFLOAT_7,
	INFLOAT_8,
	INFLOAT_9,
	INIDEN_10,
	INDIVIDE_11,
	INSINCOMM_12,
	INMULTCOMM_13,
	INMULTCOMM_14,
	INLESS_15,
	INGREATER_16,
	INCHAR_17,
	INCHAR_18,
	INSTR_19,
	INSTR_20,
	DONE_21
}

class ReservedWords {
    private static final Map<String, TokenType> RESERVED_WORDS = new HashMap<>();

    static {
        RESERVED_WORDS.put("int", TokenType.INT);
        RESERVED_WORDS.put("float", TokenType.FLOAT);
        RESERVED_WORDS.put("char", TokenType.CHAR);
        RESERVED_WORDS.put("if", TokenType.IF);
        RESERVED_WORDS.put("else", TokenType.ELSE);
        RESERVED_WORDS.put("while", TokenType.WHILE);
        RESERVED_WORDS.put("print", TokenType.PRINT);
        RESERVED_WORDS.put("read", TokenType.READ);
        RESERVED_WORDS.put("true", TokenType.BOOL_LTRL);
        RESERVED_WORDS.put("false", TokenType.BOOL_LTRL);
        RESERVED_WORDS.put("bool", TokenType.BOOL);
    }

    public static TokenType getTokenType(String word) {
        return RESERVED_WORDS.getOrDefault(word, TokenType.IDEN);
    }
}


public class scanner 
{
	private Set<TokenType> uniqueTokens;
	int totalTokens;
	private BufferedReader reader;
	int lineNo;
	int pos;
	String buffer;
	
	public scanner(String fileName)
	{
		try {
			this.reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		lineNo=0;
		pos=0;
		buffer=new String();	
		uniqueTokens = new HashSet<>();
		totalTokens = 0;
	}
	
	char getNextCharacter()
	{
		if(buffer == null || buffer.isEmpty() || pos>=buffer.length())
		{
			try {
				buffer = reader.readLine();
				if(buffer==null)
					return '\0';
				buffer += '\n';
				lineNo++;
				pos=0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return buffer.charAt(this.pos++);
		
	}
	void ungetLastChar()
	{
		pos--;
	}
	
	public Token getToken()
	{	
		TokenType currentToken = null;
		String errorMessage = new String();
		String lexeme = new String();
		String errorLine;
		StateType currentState = StateType.START_0;
		char ch;
		boolean toSave = true;
		while(currentState != StateType.DONE_21)
		{
			ch = this.getNextCharacter();
			toSave = true;
			switch(currentState)
			{
			case START_0:
				if(Character.isDigit(ch))
				{
					currentState = StateType.ININT_2;
					currentToken = TokenType.INT_LTRL;
				}
				else if(Character.isLetter(ch) || ch=='_')
				{
					currentState = StateType.INIDEN_10;
					currentToken = TokenType.IDEN;
				}
				else if(ch==' ' || ch=='\t'|| ch=='\n')
				{
					toSave=false;
				}
				else
				{
					switch(ch)
					{
					case '+':
						currentState = StateType.PLUSMINUS_1;
						currentToken = TokenType.PLUS;
						break;
					case '-':
						currentState = StateType.PLUSMINUS_1;
						currentToken = TokenType.MINUS;
						break;
					case '*':
						currentState = StateType.DONE_21;
						currentToken = TokenType.MULTIPLY;
						break;
					case '%':
						currentState = StateType.DONE_21;
						currentToken = TokenType.MOD;
						break;
					case '(':
						currentState = StateType.DONE_21;
						currentToken = TokenType.LPAREN;
						break;
					case ')':
						currentState = StateType.DONE_21;
						currentToken = TokenType.RPAREN;
						break;
					case '{':
						currentState = StateType.DONE_21;
						currentToken = TokenType.LBRACE;
						break;
					case '}':
						currentState = StateType.DONE_21;
						currentToken = TokenType.RBRACE;
						break;
					case '=':
						currentState = StateType.DONE_21;
						currentToken = TokenType.EQUAL;
						break;
					case ':':
						currentState = StateType.DONE_21;
						currentToken = TokenType.ASSIGN;
						break;
					case '!':
						currentState = StateType.DONE_21;
						currentToken = TokenType.NEQUAL;
						break;
					case '<':
						currentState = StateType.INLESS_15;
						currentToken = TokenType.LTHAN;
						break;
					case '>':
						currentState = StateType.INGREATER_16;
						currentToken = TokenType.GTHAN;
						break;
					case '\'':
						currentState = StateType.INCHAR_17;
						currentToken = TokenType.CHAR;
						break;
					case '\"':
						currentState = StateType.INSTR_19;
						currentToken = TokenType.STRING;
						break;
					case ';':
						currentState = StateType.DONE_21;
						currentToken = TokenType.SEMICOLON;
						break;
					case '\0':
						currentState = StateType.DONE_21;	//Replace this
						currentToken = TokenType.ENDFILE;
						break;
					case '/':
						currentState = StateType.INDIVIDE_11;
						currentToken = TokenType.DIVIDE;
						break;
					default:
						currentState = StateType.DONE_21; //Replace this
						currentToken = TokenType.ERROR;
						errorMessage = new String("Undefined Symbol");
						break;
					}
					
				}
				break;
			case PLUSMINUS_1:
				if (Character.isDigit(ch))
				{
					currentState = StateType.ININT_2;
					currentToken = TokenType.INT_LTRL;
				}
				else
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
					if(lexeme.charAt(lexeme.length()-1)=='+')
						currentToken = TokenType.PLUS;
					else currentToken = TokenType.MINUS;
				}
				break;
			case ININT_2:
				if(Character.isDigit(ch))
				{
					//Nothing to change
				}
				else if (ch=='.')
				{
					currentToken = TokenType.FLOAT_LTRL;
					currentState = StateType.INFLOAT_3;
				}
				else
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
				}
			break;
			case INFLOAT_3:
				if(Character.isDigit(ch))
				{
					currentState = StateType.INFLOAT_4;
				}
				else
				{
					currentState = StateType.DONE_21; //Replace this
					currentToken = TokenType.ERROR;
					errorMessage = new String("Must include a number after decimal point '.'");
				}
				break;
			case INFLOAT_4:
				if(Character.isDigit(ch))
				{
					//Nothing to do
				}
				else if(ch=='e')
				{
					currentState = StateType.INFLOAT_5;
				}
				else
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
				}
				break;
			case INFLOAT_5:
				if(Character.isDigit(ch))
				{
					currentState=StateType.INFLOAT_7;
				}
				else if(ch=='+' || ch=='-')
				{
					currentState = StateType.INFLOAT_6;
				}
				else
				{
					currentState = StateType.DONE_21;	//Replace
					currentToken = TokenType.ERROR;
					errorMessage = new String("Must include power after 'e'");
				}
				break;
			case INFLOAT_6:
				if(Character.isDigit(ch))
				{
					currentState=StateType.INFLOAT_7;
				}
				else
				{
					currentState = StateType.DONE_21;	//Replace
					currentToken = TokenType.ERROR;
					errorMessage = new String("Must include a power after sign (+ -)");
				}
				break;
			case INFLOAT_7:
				if(Character.isDigit(ch))
				{
					//Do Nothing
				}
				else if(ch=='.')
				{
					currentState = StateType.INFLOAT_8;
				}
				else
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
				}
				break;
			case INFLOAT_8:
				if(Character.isDigit(ch))
				{
					currentState = StateType.INFLOAT_9;
				}
				else
				{
					currentState = StateType.DONE_21; //Replace
					currentToken = TokenType.ERROR;
					errorMessage = new String("Must include a number after decimal point '.'");
				}
				break;
			case INFLOAT_9:
				if(Character.isDigit(ch))
				{
					//Do Nothing
				}
				else
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
				}
				break;
			case INIDEN_10:
				if(!(Character.isLetter(ch) || Character.isDigit(ch)))
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
				}
				break;
			case INDIVIDE_11:
				switch(ch)
				{
				case '/':
					toSave=false;
					lexeme = lexeme.substring(0, lexeme.length()-1);
					currentState = StateType.INSINCOMM_12;
					break;
				case '*':
					toSave=false;
					lexeme = lexeme.substring(0, lexeme.length()-1);
					currentState = StateType.INMULTCOMM_13;
					break;
				default:
					toSave=false;
					this.ungetLastChar();
					currentState = StateType.DONE_21;
					break;
				}
				break;
			case INSINCOMM_12:
				if(ch=='\n')
				{
					currentState = StateType.START_0;
					toSave=false;
				}
				else
				{
					toSave=false;
				}
				break;
			case INMULTCOMM_13:
				if(ch=='*')
				{
					currentState = StateType.INMULTCOMM_14;
					toSave=false;
				}
				else if(ch=='\0')
				{
					currentState = StateType.DONE_21;
					currentToken = TokenType.ERROR;
					toSave=false;
					buffer="/*";
					errorMessage = new String("Multiline comment must terminate");
				}
				else
				{
					toSave=false;
				}
				break;
			case INMULTCOMM_14:
				if(ch=='/')
				{
					currentState = StateType.START_0;
					toSave=false;
				}
				else if(ch=='*')
				{
					toSave=false;
				}
				else if(ch=='\0')
				{
					currentState = StateType.DONE_21;
					currentToken = TokenType.ERROR;
					toSave=false;
					buffer="/*"
;					errorMessage = new String("Multiline comment must terminate");
				}
				else
				{
					currentState = StateType.INMULTCOMM_13;
					toSave=false;
				}
				break;
			case INLESS_15:
				if(ch=='=')
				{
					currentState = StateType.DONE_21;
					currentToken = TokenType.LEQUAL;
				}
				else
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
				}
				break;
			case INGREATER_16:
				if(ch=='=')
				{
					currentState = StateType.DONE_21;
					currentToken = TokenType.GEQUAL;
				}
				else
				{
					this.ungetLastChar();
					toSave=false;
					currentState = StateType.DONE_21;
				}
				break;
			case INCHAR_17:
				if(Character.isLetter(ch))
				{
					currentState = StateType.INCHAR_18;
				}
				else
				{
					currentState = StateType.DONE_21;	//Replace
					currentToken = TokenType.ERROR;
					errorMessage = new String("Must include an alphabet after '‘'");
				}
				break;
			case INCHAR_18:
				if(ch=='\'')
				{
					currentState = StateType.DONE_21;
				}
				else
				{
					currentState = StateType.DONE_21;	//Replace
					currentToken = TokenType.ERROR;
					errorMessage = new String("Must end with '’'");
				}
				break;
			case INSTR_19:
				if(ch=='\"')
				{
					currentState = StateType.DONE_21;
				}
				else
				{
					//Nothing to do
				}
				break;
			default:	//Can't occur
				System.out.println("There is some issue with case matching :(");		
			}
			
			if(toSave)
			{
				lexeme += ch;
			}
			
			if(currentState == StateType.DONE_21)
			{
				if(currentToken == TokenType.IDEN)
				{
					currentToken = ReservedWords.getTokenType(lexeme);
				}
				if(currentToken == TokenType.IDEN)
				{
					SymbolTable.addEntry(lexeme, this.lineNo, this.pos-lexeme.length());
				}
				Token t = new Token(currentToken,lexeme,this.lineNo,this.pos - lexeme.length());
				
				//Adding it to Error Handler
				if(currentToken == TokenType.ERROR)
				{
					errorLine = new String(buffer);
					ErrorHandler.addError(t.line,t.position+t.lexeme.length()-1,errorLine, errorMessage);
				}
				if(Globals.showScannerDetails)
				{
					t.showToken();
					this.uniqueTokens.add(currentToken);
					this.totalTokens++;
				}
				return t;
			}
			
		}	
		return null;
	}
	public void printNumTokens()
	{
		System.out.printf("Total Tokens : %d\n",this.totalTokens);
		System.out.printf("Unique Tokens : %d\n",this.uniqueTokens.size());
		
	}
	

}
