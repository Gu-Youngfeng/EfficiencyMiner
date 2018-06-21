package cn.edu.whu.cstar.efforter;

import java.util.List;

import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.RepsUtilier;

/***
 * <p>Main entry of package <b>efforter</b>. Running {@link#main} method can give 
 * the whole line efforts of each CrashNode in each project.</p>
 * @author yongfeng
 *
 */
public class Efforter {

	public static void main(String[] args) {
		System.out.println("-------  Line Effort for Each Crash in Each Project  -------");
		// Counting Process
		for(int i=1; i<=7; i++){

			try {
				counter(i);
			} catch (Exception e) {
				System.out.println("[Error]: exception is thrown when counting project [" + i  +"].");
				e.printStackTrace();
				return;
			}

		}

	}
	
	public static void counter(int projId) throws Exception{
		String path = "";
		String proj = "";
		
		switch(projId){
		case 1:
			path = "src/main/resources/crashrep/codec_mutants.txt";
			proj = "src/main/resources/projs/Codec_parent/";
			break;
		case 2:
			path = "src/main/resources/crashrep/collection_mutants.txt";
			proj = "src/main/resources/projs/Collection_4.1_parent/";
			break;
		case 3:
			path = "src/main/resources/crashrep/io_mutants.txt";
			proj = "src/main/resources/projs/Commons-io-2.5_parent/";
			break;
		case 4:
			path = "src/main/resources/crashrep/jsoup_mutants.txt";
			proj = "src/main/resources/projs/jsoup_parent/";
			break;
		case 5:
			path = "src/main/resources/crashrep/jsqlpraser_mutants.txt";
			proj = "src/main/resources/projs/JSQL_parent/";
			break;
		case 6:
			path = "src/main/resources/crashrep/mango_mutants.txt";
			proj = "src/main/resources/projs/mango_parent/";
			break;
		case 7:
			path = "src/main/resources/crashrep/ormlite_mutants.txt"; //need complete dependencies libs
			proj = "src/main/resources/projs/ormlite_parent/";
			break;
		default:
			System.out.println("[ERROR]: No such project id <" + projId + ">");
			break;
		}
		
		System.out.println("[project]: " + proj + "\n");
		
		// count total
		List<CrashNode> lsCrash = RepsUtilier.getSingleCrash(path);
		for(CrashNode crash: lsCrash){
			int lineAllMethodLine = LineCounter.countingAllMethodLine(proj, crash);
			int lineStackTraceLine = LineCounter.countingAllStackTraceLine(crash);
			int lineExpStackTraceLine = LineCounter.countingExpStackTraceLine(crash);
			int lineExpMethodLine = LineCounter.countingExpMethodLine(proj, crash);
			
			System.out.printf("[A: lineAllMethodLine    ]: %-5d [B: lineStackTraceLine]: %-5d\n", lineAllMethodLine, lineStackTraceLine);
			System.out.printf("[C: lineExpStackTraceLine]: %-5d [D: lineExpMethodLine ]: %-5d\n\n", lineExpStackTraceLine, lineExpMethodLine);
		}
	}

}
