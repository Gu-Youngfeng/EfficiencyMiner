package cn.edu.whu.cstar.timer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrashNode {
//	/**exception type*/
//	String excep = "";
//	/**stack trace size*/
//	int lines = 0;
	
	/**top class name*/
	private String topClassName = "";
	/**top method name*/
	private String topMethodName = "";
	/**line number in top method*/
	private int topMethodLine = 0;
	
	/**bottom class name*/
	private String bottomClassName = "";
	/**bottom method name*/
	private String bottomMethodName = "";
	/**bottom number in top method*/
	private int bottomMethodLine = 0;
	
	CrashNode(List<String> crash){
		
		for(String line: crash){
			System.out.println(">> " + line);
		}
		System.out.println("++++++++++");
		
		String topLine = getTopLine(crash);
		String bottomLine = getBottomLine(crash);
		
		topClassName = getClassName(topLine);
		topMethodName = getMethodName(topLine);
		topMethodLine = getMethodLine(topLine);
		
		bottomClassName = getClassName(bottomLine);
		bottomMethodName = getMethodName(bottomLine);
		bottomMethodLine = getMethodLine(bottomLine);
	}
	
	/**
	 * <p>to extract class name from the line, we can get <b>java.lang.StringBuilder</b> from the following example,</p>
	 * <pre>at java.lang.StringBuilder.setCharAt(StringBuilder.java:76)</pre>
	 * @param line one line in stack trace
	 * @return
	 */
	public static String getClassName(String line){
		String tcn = "";
		
//		System.out.println(line);
		String reg = "(\\tat\\s)(.*)\\.([^\\.]*)\\((.*):(\\d*)\\)";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()){
			tcn = matcher.group(2);
		}else{
			System.out.println("[ERROR]: Mismatched! " + line);
		}
		
//		if(tcn.contains("$")){ // inner class
//			tcn = tcn.split("$")[0];
//		}
		
		return tcn;
	}

	/**
	 * <p>to extract method name from the line, we can get <b>setChartAt</b> from the following example,</p>
	 * <pre>at java.lang.StringBuilder.setCharAt(StringBuilder.java:76)</pre>
	 * @param line one line in stack trace
	 * @return
	 */
	public static String getMethodName(String line){
		String tmn = "";
		
//		System.out.println(line);
		String reg = "(\\tat\\s)(.*)\\.([^\\.]*)\\((.*):(\\d*)\\)";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()){
			tmn = matcher.group(3);
		}else{
			System.out.println("[ERROR]: Mismatched! " + line);
		}
		
		if(tmn.equals("<init>")){ // constructor
			String cls = getClassName(line);
			String[] clss = cls.split("\\.");
			tmn = clss[clss.length-1];
		}
		
		return tmn;
	}
	
	/**
	 * <p>to extract method line from the line, we can get <b>76</b> from the following example,</p>
	 * <pre>at java.lang.StringBuilder.setCharAt(StringBuilder.java:76)</pre>
	 * @param line one line in stack trace
	 * @return
	 */
	public static int getMethodLine(String line){
		String tml = "";
		int loc = -1;
		
		String reg = "(\\tat\\s)(.*)\\.([^\\.]*)\\((.*):(\\d*)\\)";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()){
			tml = matcher.group(5);
		}else{
			System.out.println("[ERROR]: Mismatched! " + line);
		}
		
		loc = Integer.valueOf(tml); // NumberFormatException
			
		return loc;
	}
	
	/**
	 * <p>To find the top method line of stack trace.</p>
	 * @param crash crash lines list
	 * @return
	 */
	String getTopLine(List<String> crash){
		String topLine = "";
		
		for(int i=0; i<crash.size(); i++){
			if((crash.get(i).startsWith("\tat org.") || crash.get(i).startsWith("\tat com.j256")) 
					&& !crash.get(i).contains("Test.java") && !crash.get(i).contains("com.j256.ormlite.h2.H2DatabaseConnection.queryForOne")){
				topLine = crash.get(i);
				break;
			}
		}		
		System.out.println("[TOP]: " + topLine);
		return topLine;
	}
	
	/**
	 * <p>To find the bottom method line of stack trace.</p>
	 * @param crash crash crash lines list
	 * @return
	 */
	String getBottomLine(List<String> crash){
		String bottomLine = "";
		
		for(int i=0; i<crash.size(); i++){
			if((crash.get(i).startsWith("\tat org.") || crash.get(i).startsWith("\tat com.j256")) 
					&& (crash.get(i).contains("Test.java") || crash.get(i).contains("com.j256.ormlite.h2.H2DatabaseConnection.queryForOne"))){
				bottomLine = crash.get(i-1);
				break;
			}
		}		
		System.out.println("[BOT]: " + bottomLine);
		return bottomLine;
	}
	
	// getter of private variables //////////////////////////////////////////

	public int getBottomMethodLine(){
		return bottomMethodLine;
	}
	
	public String getBottomMethodName(){
		return bottomMethodName;
	}
	
	public String getBottomClassName(){
		return bottomClassName;
	}
	
	public int getTopMethodLine(){
		return topMethodLine;
	}
	
	public String getTopMethodName(){
		return topMethodName;
	}
	
	public String getTopClassName(){
		return topClassName;
	}
	
	public void getBasicInfo(){
		System.out.println("## CRASH ##");
		System.out.println("[TOP]: " + getTopClassName() + ", " + getTopMethodName() + ", " + getTopMethodLine());
		System.out.println("[BOT]: " + getBottomClassName() + ", " + getBottomMethodName() + ", " + getBottomMethodLine());
	}
	
	
	////////////////////////////////////////////
}
