package cn.edu.whu.cstar.efforter;

/***
 * <p><b>CrashEffort</b> is used to save the 4 kinds of line efforts of each crash.</p>
 * @author yongfeng
 *
 */
public class CrashEffort {

	public int lineAllMethodLine;
	
	public int lineStackTraceLine;
	
	public int lineExpStackTraceLine;
	
	public int lineExpMethodLine;
	
	CrashEffort(int lineAllMethodLine, int lineStackTraceLine, int lineExpStackTraceLine, int lineExpMethodLine){
		this.lineAllMethodLine = lineAllMethodLine;
		this.lineStackTraceLine = lineStackTraceLine;
		this.lineExpStackTraceLine = lineExpStackTraceLine;
		this.lineExpMethodLine = lineExpMethodLine;
	}
	
	public void showEfforts(){
		System.out.printf("[A: lineAllMethodLine    ]: %-5d [B: lineStackTraceLine]: %-5d\n", lineAllMethodLine, lineStackTraceLine);
		System.out.printf("[C: lineExpStackTraceLine]: %-5d [D: lineExpMethodLine ]: %-5d\n\n", lineExpStackTraceLine, lineExpMethodLine);

	}

}
