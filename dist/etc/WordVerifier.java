/*
	static methods
	public static boolean isExist(String input)
	- String 입력이 null, 공백 문자열, "//" 일 경우 없는 문자열로 판단, false
		이 외의 문자열은 있는 문자열로 판단, true
	
	public static boolean inIntNext(String input)
	- 다음 마디의 숫자 여부 확인
	
	public static boolean hasSpace(String input)
	- 마디 사이에 2개 이상의 공백 포함 확인
	
	public static String trimWord(String input)
	- 다음 마디 추출
	
	public static String trimSpace(String input)
	- 마디 사이의 2개 이상의 공백을 1개로 정리
	
	public static String extString(String input)
	- 다음 마디의 문자열 추출
	
	public int extInt(String input)
	- 다음 마디의 숫자 추출
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