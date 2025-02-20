package compiler;
import compiler.TokenType;
public class Token 
{
	TokenType type;
	String lexeme;
	int line;
	int position;
	public Token(TokenType t, String l, int line, int pos)
	{
		type = t;
		lexeme = l;
		this.line = line;
		this.position = pos;
	}
	public void showToken()
	{
		System.out.printf("%-15s %-10s Line: %-4d Position: %-4d%n", 
                "Token:", type, line, position);
		System.out.printf("%-15s \"%s\"%n", "Lexeme:", lexeme);
		System.out.println("-----------------------------------");
	}
}
