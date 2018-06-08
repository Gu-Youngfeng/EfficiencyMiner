package cn.edu.whu.cstar.timer;

public class StackTraceMiner {
	/**ST01: Type of the exception in the crash*/
	int exceptName;
	/**ST02: Number of frames of the stack trace*/
	int loc;
	/**ST03: Number of classes in the stack trace*/
	int classNum;
	/**ST04: Number of methods in the stack trace*/
	int methodNum;
	/**ST05: Whether an overloaded method exists in the stack trace*/
	boolean isOverLoaded;
	/**ST06: Length of the name in the top class*/
	int lenTopClassName;
	/**ST07: Length of the name in the top function*/
	int lenTopMethodName;
	/**ST08: Length of the name in the bottom class*/
	int lenBottomClassName;
	/**ST09: Length of the name in the bottom function*/
	int lenBottomMethodName;
	
	StackTraceMiner(CrashNode crash){
		exceptName = crash.getType();
		loc = crash.getLOC();
		
	}
}
