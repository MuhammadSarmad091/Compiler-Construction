package compiler;
import compiler.TokenType;

import java.util.ArrayList;
import java.util.List;

import compiler.Globals;
import compiler.Token;
public class ErrorHandler 
{
	private static class Error
	{
		int lineNo;
		int errorPosition;
		String errorLine;
		String message;
		Error(int lin,int errorPos,String errorL,String m)
		{
			this.lineNo=lin;
			this.errorPosition=errorPos;
			message = m;
			errorLine = errorL;
		}
		void print()
		{
		    System.out.println("Syntax Error:");
		    System.out.printf("  -> Line %d, Position %d%n", lineNo, errorPosition);
		    System.out.println("  -> " + errorLine.trim());
		    
		    StringBuilder pointer = new StringBuilder();
		    for (int i = 0; i < errorPosition; i++) {
		        pointer.append(" ");
		    }
		    pointer.append("^"); // Mark the error position
		    
		    System.out.println("     " + pointer.toString());
		    System.out.printf("  -> Message: %s%n", message);
		    System.out.println("-----------------------------------");
		}
	}
	
	private static List<Error> errors = new ArrayList<Error>();
	
	public static void addError(int lin,int errorPos,String errorL,String m)
	{
		errors.add(new Error(lin,errorPos,errorL,m));
	}
	
	public static void displayAllErrors()
	{
		if (errors.isEmpty())
		{
			return;
		}
		System.out.println("Erros Found in the program!");
		for (Error err : errors)
		{
			err.print();
		}
	}

}
