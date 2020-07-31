/*
	static methods
	public static boolean isExist(String input)
	- String �Է��� null, ���� ���ڿ�, "//" �� ��� ���� ���ڿ��� �Ǵ�, false
		�� ���� ���ڿ��� �ִ� ���ڿ��� �Ǵ�, true
	
	public static boolean inIntNext(String input)
	- ���� ������ ���� ���� Ȯ��
	
	public static boolean hasSpace(String input)
	- ���� ���̿� 2�� �̻��� ���� ���� Ȯ��
	
	public static String trimWord(String input)
	- ���� ���� ����
	
	public static String trimSpace(String input)
	- ���� ������ 2�� �̻��� ������ 1���� ����
	
	public static String extString(String input)
	- ���� ������ ���ڿ� ����
	
	public int extInt(String input)
	- ���� ������ ���� ����
*/

public class WordVerifier
{
	public static boolean isExist(String input)
	{
		if(input == null)
			return false;
		input = input.trim();
		if(input.isEmpty())
			return false;
		if(input.length() >= 2 && input.substring(0, 2).equals("//"))
			return false;
		return true;
	}
	public static boolean isIntNext(String input)
	{
		input = input.trim();
		try
		{
			if(input.indexOf(" ") != -1)
				Integer.parseInt(input.substring(0, input.indexOf(" ")));
			else
				Integer.parseInt(input);
			return true;
		}
		catch(NumberFormatException en)
		{
			
		}
		return false;
	}
	public static boolean hasSpace(String input)
	{
		input = input.trim();
		if(input.indexOf("  ") != -1)
			return true;
		return false;
	}
	public static String trimWord(String input)
	{
		input = input.trim();
		if(input.indexOf(" ") != -1)
			return input.substring(input.indexOf(" ")).trim();
		return input;
	}
	public static String trimSpace(String input)
	{
		input = input.trim();
		while(hasSpace(input))
		{
			input = input.substring(0, input.indexOf("  ")) + input.substring(input.indexOf("  ") + 1);
		}
		return input;
	}
	public static String extString(String input)
	{
		input = input.trim();
		if(input.indexOf(" ") != -1)
			return input.substring(0, input.indexOf(" "));
		return input;
	}
	public static int extInt(String input)
	{
		input = input.trim();
		if(input.indexOf(" ") != -1)
			return Integer.parseInt(input.substring(0, input.indexOf(" ")));
		return Integer.parseInt(input);
	}
	public static String separatorToUnix(String input)
	{
		input = input.trim();
		while(input.indexOf("\\") != -1)
		{
			input.replace('\\', '/');
		}
		return input;
	}
	public static String separatorToWin(String input)
	{
		input = input.trim();
		while(input.indexOf("/") != -1)
		{
			input.replace('/', '\\');
		}
		return input;
	}
}