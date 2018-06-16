package cn.edu.whu.cstar.timer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * <p><b>CrashBode</b> is a crash node class, which save the basic information from single crash.
 * This information includes {@link#exceptionType}, {@link#loc}, {@link#topClassName}, {@link#topMethodName}, {@link#topMethodLine}, 
 *  {@link#bottomClassName}, {@link#bottomMethodName}, {@link#bottomMethodLine}.
 * </p>
 * @author youngfeng
 * @see#CrashNode
 */
public class CrashNode {
	
	/**exception type*/
	private int exceptionType;
	/**stack trace size*/
	private int loc;
	/**number of classes in the stack trace*/
	private int classNum;
	/**number of methods in the stack trace*/
	private int methodNum;
	/**whether an overloaded method exists in the stack trace*/
	private boolean isOverLoaded;
	
	/**top class name*/
	private String topClassName;
	/**top method name*/
	private String topMethodName;
	/**line number in top method*/
	private int topMethodLine;
	
	/**bottom class name*/
	private String bottomClassName;
	/**bottom method name*/
	private String bottomMethodName;
	/**bottom number in top method*/
	private int bottomMethodLine;
	
	/**
	 * <p>to initialize a crash node object from crash, the crash is a single crash in the following format,</p>
	 * <pre>
	 * --- org.apache.commons.codec.XXXTest::testXX
	 * java.lang.XXXException:ABC
	 * 	at org.apache.commons.codec.XX.XXX(XX.java:abc)
	 * 	at org.apache.commons.codec.XXXTest.testXX(XXXTest.java:abc)
	 * </pre>
	 * @param crash lines list of single crash
	 */
	CrashNode(List<String> crash){
		
		String topLine = getTopLine(crash);
		String bottomLine = getBottomLine(crash);
		
		topClassName = getClassName(topLine);
		topMethodName = getMethodName(topLine);
		topMethodLine = getMethodLine(topLine);
		
		bottomClassName = getClassName(bottomLine);
		bottomMethodName = getMethodName(bottomLine);
		bottomMethodLine = getMethodLine(bottomLine);
		
		String exceptName = getExceptionName(crash.get(1));
//		System.out.println("exception type: " + exceptName);
		exceptionType = getExceptionType(exceptName);
		loc = crash.size()-3;
		classNum = getClassNum(crash);
		methodNum = getMethodNum(crash);
		isOverLoaded = isOverLoaded(crash);
	}
	
	/**
	 * <p>to extract class name from the line, we can get <b>java.lang.StringBuilder</b> from the following example,</p>
	 * <pre>at java.lang.StringBuilder.setCharAt(StringBuilder.java:76)</pre>
	 * <p>note that you ignore the inner class, and use the outer class name.</p>
	 * @param line frame line in stack trace
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
		
//		System.out.println(">> " + tcn);
		
		if(tcn.contains("$")){ // inner class
			tcn = tcn.split("\\$")[0];
//			System.out.println(tcn.split("\\$")[0]);
		}
		
		return tcn;
	}

	/**
	 * <p>to extract method name from the line, we can get <b>setChartAt</b> from the following example,</p>
	 * <pre>at java.lang.StringBuilder.setCharAt(StringBuilder.java:76)</pre>
	 * <p>Note that the &lt;init&gt; will be replaced with the true class name.</p>
	 * @param line frame line in stack trace
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
	 * @param line frame line in stack trace
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
	 * <p>To get Exception Type of the line, we can get <b>IllegalArgumentException</b> from the following line,</p>
	 * <pre>java.lang.IllegalArgumentException: Negative initial size: 1024</pre>
	 * @param line second line in stack trace
	 * @return
	 */
	public static String getExceptionName(String line){
		String eName = "";
		
//		if(!line.contains(":")){
//			System.out.println("[ERROR]: No Exception Pattern in: " + line);
//		}
		line = line.split(":")[0];
		
		String reg = "(.*)\\.([^\\.]*)";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()){
			eName = matcher.group(2);
		}
		
		return eName;
	}
	
	public static int getExceptionType(String ExType){
		int num = -1;
		switch(ExType){
		case "NumberFormatException":
			num = 1;
			break;
		case "IllegalArgumentException":
			num = 2;
			break;
		case "StringIndexOutOfBoundsException":
			num = 3;
			break;	
		case "ArrayIndexOutOfBoundsException":
			num = 4;
			break;	
		case "SerializationException":
			num = 5;
			break;
		case "NullPointerException":
			num = 6;
			break;
		case "ClassCastException":
			num = 7;
			break;	
		case "ArrayStoreException":
			num = 8;
			break;
		case "OutOfMemoryError":
			num = 9;
			break;	
		case "UnsupportedOperationException":
			num = 10;
			break;
		case "IllegalFieldValueException":
			num = 11;
			break;
		case "IndexOutOfBoundsException":
			num = 12;
			break;
		case "UnknownKeyException":
			num = 13;
			break;
		default:
			num = 14;
			break;
		}
		return num;
	}
	
	/**
	 * <p>To find the top method line of stack trace.</p>
	 * @param crash crash lines list
	 * @return
	 */
	String getTopLine(List<String> crash){
		String topLine = "";
		
		for(int i=0; i<crash.size(); i++){
			if(isMethodLine(crash.get(i))){
				topLine = crash.get(i);
				break;
			}
		}		
//		System.out.println("[TOP]: " + topLine);
		return topLine;
	}
	
	boolean isMethodLine(String line){
		boolean flag = false;
		
		if ( (line.startsWith("\tat org.") || line.startsWith("\tat com.j256"))
				&& !line.contains("Test.java") && !line.contains("com.j256.ormlite.h2.H2DatabaseConnection.queryForOne") 
				&& !line.contains("TestCase.java") && !line.contains("TestUtils.java")
				&& !line.contains("TestData.java")){
			flag = true;
		}
		
		return flag;
	}
	
	boolean isTestLine(String line){
		boolean flag = false;
		
		if ( (line.startsWith("\tat org.") || line.startsWith("\tat com.j256")) 
				&& (line.contains("Test.java") || line.contains("com.j256.ormlite.h2.H2DatabaseConnection.queryForOne") 
						|| line.contains("TestCase.java") || line.contains("TestUtils.java")
						|| line.contains("TestData.java")) ){
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * <p>To find the bottom method line of stack trace.</p>
	 * @param crash crash crash lines list
	 * @return
	 */
	String getBottomLine(List<String> crash){
		String bottomLine = "";
		
		int k = 0;
		for(k=0; k<crash.size(); k++){
			if(isMethodLine(crash.get(k))){
				break;
			}
		}
		
		for(int i=k; i<crash.size(); i++){
			if(isTestLine(crash.get(i))){
//				bottomLine = crash.get(i-1);
				int m = i-1;
				while(!isMethodLine(crash.get(m))){
					m--;
				}
				bottomLine = crash.get(m);
				break;
			}
		}		
//		System.out.println("[BOT]: " + bottomLine);
		return bottomLine;
	}
	
	/**
	 * <p>To find the unique classes reside in stack trace.</p>
	 * @param crash crash crash lines list
	 * @return
	 */
	int getClassNum(List<String> crash){
		int count=0;
		Set<String> setCls = new HashSet<String>();
		for(String line: crash){
			if(isMethodLine(line)){
				setCls.add(getClassName(line));
			}
		}
		count = setCls.size();
//		for(String cls: setCls){System.out.println("-- " + cls);}
		return count;
	}
	
	/**
	 * <p>To find the unique method reside in stack trace.</p>
	 * @param crash crash crash lines list
	 * @return
	 */
	int getMethodNum(List<String> crash){
		int count=0;
		Set<String> setMel = new HashSet<String>();
		for(String line: crash){
			if(isMethodLine(line)){
				String fullMethodName = getClassName(line) + "." + getMethodName(line);
				setMel.add(fullMethodName);
			}
		}
		count = setMel.size();
//		for(String cls: setMel){System.out.println("-- " + cls);}
		return count;
	}
	
	/**
	 * <p>To find the overloaded  reside in stack trace.</p>
	 * @param crash crash crash lines list
	 * @return
	 */
	boolean isOverLoaded(List<String> crash){
		
		Set<String> setMel = new HashSet<String>();
		int count = 0;
		for(String line: crash){
			if(isMethodLine(line)){
				count ++;
				String fullMethodName = getClassName(line) + "." + getMethodName(line);
				setMel.add(fullMethodName);
			}
		}
		
		return count > setMel.size();
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
	
	public int getLOC(){
		return loc;
	}
	
	public int getType(){
		return exceptionType;
	}
	
	public int getClassNum(){
		return classNum;
	}
	
	public int getMethodNum(){
		return methodNum;
	}
	
	public boolean getisOverLoaded(){
		return isOverLoaded;
	}
	
	public void showBasicInfo(){
		System.out.println("------------------## CRASH ##------------------");
		System.out.println("[TYP]: " + getType());
		System.out.println("[LOC]: " + loc);
		System.out.println("[CLS]: " + getClassNum());
		System.out.println("[MED]: " + getMethodNum());
		System.out.println("[OLD]: " + getisOverLoaded());
		System.out.println("[TOP]: " + getTopClassName() + ", " + getTopMethodName() + ", " + getTopMethodLine());
		System.out.println("[BOT]: " + getBottomClassName() + ", " + getBottomMethodName() + ", " + getBottomMethodLine());
		System.out.println("------------------------------------------------\n");
	}
	
	public void showStackTrace(List<String> lines){
		for(String line: lines){
			System.out.println(">> " + line);
		}
		System.out.println("++++++++++");
	}
	
	
	////////////////////////////////////////////
}
