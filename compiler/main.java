package compiler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import compiler.scanner;
public class main 
{
	public static void main(String[] args)
	{
		String fileName = "text.txt";
		scanner scan = new scanner(fileName);
		while(scan.getToken().type != TokenType.ENDFILE);
		scan.printNumTokens();
		ErrorHandler.displayAllErrors();
		SymbolTable.printTable();
	}
}
