package cn.edu.whu.cstar.efforter;

import java.util.List;

import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.MEDAnalyzer;

/***
 * <p><b>LineCounter</b> is a utilize class, which provides four kinds of line effort calculation modes. 
 * Given a CrashNode object, following static methods will calculate the line effort developers should spend.</p>
 * <li>MODE 1: {@link#countingAllMethodLine}</li>
 * <li>MODE 2: {@link#countingAllStackTraceLine}</li>
 * <li>MODE 3: {@link#countingExpStackTraceLine}</li>
 * <li>MODE 4: {@link#countingExpMethodLine}</li>
 * @author yongfeng
 *
 */
public class LineCounter {
	
	/***
	 * <p><b>[Mode I]:</b> to count total lines in all methods, which appear in stack trace, in single crash node.</p>
	 * @param crash CrashNode
	 * @param proj project path
	 * @return
	 */
	public static int countingAllMethodLine(String proj, CrashNode crash){
		int lines = 0;
		
		List<String> stackTraces = crash.stackTraces;
		for(String line: stackTraces){
//			System.out.println(line);
			String clsName = CrashNode.getClassName(line);
			String medName = CrashNode.getMethodName(line);
			int medLine = CrashNode.getMethodLine(line);
			
			try {
				MEDAnalyzer analyzer = new MEDAnalyzer(proj, clsName, medName, medLine);
				int cline = analyzer.returnLOC();
				lines += cline;
//				System.out.println("[add  ]: " + cline);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
//		System.out.println(">> ---------------- " + lines);
		
		return lines;
	}
	
	/***
	 * <p><b>[Mode II]:</b> to count total lines of stack trace in single crash node.</p>
	 * @param crash CrashNode
	 * @return
	 */
	public static int countingAllStackTraceLine(CrashNode crash){
		int lines = 0;
		
		List<String> stackTraces = crash.stackTraces;
		
		lines = stackTraces.size();		
//		System.out.println("[add  ]: " + lines);
		
		return lines;
	}
	
	/***
	 * <p><b>[Mode III]:</b> to count total lines of stack trace (till the mutation position) in single crash node.</p>
	 * @param crash CrashNode
	 * @return
	 */
	public static int countingExpStackTraceLine(CrashNode crash){
		int lines = 0;
		
		List<String> stackTraces = crash.stackTraces;	
		if(crash.InTrace < 0){
			// OutTrace
//			System.out.println("[OutTrace]");
			lines = stackTraces.size();
		}else{
			// InTrace
//			System.out.println("[InTrace]");
			lines = crash.InTrace + 1;
		}
		lines = crash.InTrace>-1?crash.InTrace+1:stackTraces.size();
//		System.out.println(">> ---------------- " + lines);
		
		return lines;
	}
	
	/***
	 * <p><b>[Mode IV]:</b> to count total lines of stack trace (till the mutation position) in single crash node.</p>
	 * @param crash CrashNode
	 * @param proj project path
	 * @return
	 */
	public static int countingExpMethodLine(String proj, CrashNode crash){
		int lines = 0;
		
		List<String> stackTraces = crash.stackTraces;	
		if(crash.InTrace < 0){
			// OutTrace
//			System.out.println("[OutTrace]");
			lines = countingAllMethodLine(proj, crash);
		}else{
			// InTrace
//			System.out.println("[InTrace]");
			for(int i=0; i<=crash.InTrace; i++){
//				System.out.println(stackTraces.get(i));
				String clsName = CrashNode.getClassName(stackTraces.get(i));
				String medName = CrashNode.getMethodName(stackTraces.get(i));
				int medLine = CrashNode.getMethodLine(stackTraces.get(i));			
				
				if(i == crash.InTrace){ // exception method position
					MEDAnalyzer analyzer;
					try {
						analyzer = new MEDAnalyzer(proj, clsName, medName, medLine);
						int startLine = analyzer.startLine;
						lines += (medLine - startLine); // add lines till the crash line
//						System.out.println("[start]: " + startLine);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{ // no-exception method position
					try {
						MEDAnalyzer analyzer = new MEDAnalyzer(proj, clsName, medName, medLine);
						int cline = analyzer.returnLOC(); // add all lines of methods
						lines += cline;
//						System.out.println("[add  ]: " + cline);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
				
			}
		}
//		System.out.println(">> ---------------- " + lines);
		return lines;
	}
	
}
