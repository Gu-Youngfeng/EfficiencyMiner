package cn.edu.whu.sctar.typer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.RepsUtilier;

public class Typer {

	public static void main(String[] args) {
		
		for(int i=1;i<=1;i++){
			try {
				typer(i);
			} catch (Exception e) {
				System.out.println("[Error]: exception is thrown when typing project [" + i  +"].");
				e.printStackTrace();
			}
		}
	}
	
	public static void typer(int projId) throws Exception{
		
		String path = "";
		
		switch(projId){
		case 1:
			path = "src/main/resources/crashrep/codec_mutants.txt";
			break;
		case 2:
			path = "src/main/resources/crashrep/collection_mutants.txt";
			break;
		case 3:
			path = "src/main/resources/crashrep/io_mutants.txt";
			break;
		case 4:
			path = "src/main/resources/crashrep/jsoup_mutants.txt";
			break;
		case 5:
			path = "src/main/resources/crashrep/jsqlpraser_mutants.txt";
			break;
		case 6:
			path = "src/main/resources/crashrep/mango_mutants.txt";
			break;
		case 7:
			path = "src/main/resources/crashrep/ormlite_mutants.txt"; 
			break;
		default:
			System.out.println("[ERROR]: No such project id <" + projId + ">");
			break;
		}
		
		/** Crash Node list in stack trace */
		List<CrashNode> lsCrash = RepsUtilier.getSingleCrash(path);
		
		/** Read mutation line in MutationInfo.txt */
		File file = new File("src/main/resources/MutationInfo.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		int[] expTypes = {0, 0, 0, 0, 0, 0, 0};
		while((str=br.readLine())!=null) { // for each line in MutationInfo.txt
			if(str.trim().equals("")){ continue; }
//			System.out.println("[class]: " + OperatorCollector.getInformsByFileMutLine(str)[0]
//					+ " [method]: " + OperatorCollector.getInformsByFileMutLine(str)[1]
//					+ " [line]: " + OperatorCollector.getInformsByFileMutLine(str)[2]
//					+ " [operator]: " + OperatorCollector.getOperatorID(OperatorCollector.getInformsByFileMutLine(str)[3]));
			
			String mutClsName = OperatorCollector.getInformsByFileMutLine(str)[0];
			String mutMedName = OperatorCollector.getInformsByFileMutLine(str)[1];
			String mutLineNum = OperatorCollector.getInformsByFileMutLine(str)[2];			
			
			for(int i=0; i<lsCrash.size(); i++){ // for each line in Crash Node list
				String mutLine = lsCrash.get(i).MutationLine;
//				System.out.printf("[mutation information] %s %s %s\n", 
//						getInformByStackTraceMutLine(mutLine)[0], 
//						getInformByStackTraceMutLine(mutLine)[1], 
//						getInformByStackTraceMutLine(mutLine)[2] );
				String stkClsName = getInformByStackTraceMutLine(mutLine)[0];
				String stkMedName = getInformByStackTraceMutLine(mutLine)[1];
				String stkLineNum = getInformByStackTraceMutLine(mutLine)[2];
				if( stkClsName.equals(mutClsName) && stkMedName.equals(mutMedName)
						&& stkLineNum.equals(mutLineNum)){
					int operator_id = OperatorCollector.getOperatorID(OperatorCollector.getInformsByFileMutLine(str)[3]);
					expTypes[operator_id]++;
//					System.out.println("[bingo]: " + stkClsName + stkMedName + stkLineNum);
					break;
				}
			}
			
		}
		
		br.close();
		
		/**print the mutation*/
		for(int i=0; i< expTypes.length; i++){
			System.out.println("[operator type]: " + i + " [mutants number]: " + expTypes[i]);
		}
		
	}
	
	/***
	 * <p>to get class, method, line number from the mutation line in stack trace file, e.g.,</p>
	 * <pre>--------------------------------------------------------------------
	 *MUTATIONID:&lt;&lt;net.sf.jsqlparser.util.TablesNamesFinder,visit ,507&gt;&gt;
	 *--------------------------------------------------------------------</pre>
	 * <table>
	 * <tr><td><b>[class]:</b>  </td> <td>net.sf.jsqlparser.util.TablesNamesFinder</td></tr>
	 * <tr><td><b>[method]:</b> </td> <td>visit</td></tr>
	 * <tr><td><b>[line]:</b>   </td> <td>507</td></tr>
	 * </table>
	 * @param mutline mutation line in st
	 * @return informs [0]: class, [1]: method, [2]: line number
	 */
	public static String[] getInformByStackTraceMutLine(String mutline){
		
		String[] informs = new String[3];
		
		String reg = "MUTATIONID:<<(.*),(.*),(\\d*)>>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(mutline);
		if(matcher.find()){
			String cls = matcher.group(1).trim();
			String med = matcher.group(2).trim();
			String lin = matcher.group(3).trim();
			
			informs[0] = cls;
			informs[1] = med;
			informs[2] = lin;
		}
		
		return informs;
		
	}

}
