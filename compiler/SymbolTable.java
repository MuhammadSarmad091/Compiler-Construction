package compiler;

import java.util.HashMap;
import java.util.Map;

class Entry
{
	String name;
	int lineNo;
	int pos;
	//We'll add more attributes in the next phases
	Entry(String n, int l, int p)
	{
		this.name=n;
		this.lineNo=l;
		this.pos=p;
	}
}

public class SymbolTable 
{
	private static Map<String,Entry> table = new HashMap<>();
	public static void addEntry(String name, int line, int position)
	{
		//Later on we'll check scope and redeclarations
		if(!table.containsKey(name))
		{
			table.put(name,new Entry(name,line,position));
		}
	}
	public static Entry get(String name)
	{
		return table.get(name);
	}
	public static void printTable() 
	{
	    System.out.println("Symbol Table:");
	    System.out.println("------------------------------------------------");
	    System.out.printf("%-15s %-10s %-10s%n", "Identifier", "Line No", "Position");
	    System.out.println("------------------------------------------------");
	    for (Map.Entry<String, Entry> entry : table.entrySet()) 
	    {
	        Entry e = entry.getValue();
	        System.out.printf("%-15s %-10d %-10d%n", e.name, e.lineNo, e.pos);
	    }
	    System.out.println("------------------------------------------------");
	}

}
