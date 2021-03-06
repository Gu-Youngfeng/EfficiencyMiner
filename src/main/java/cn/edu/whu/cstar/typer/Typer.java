package cn.edu.whu.cstar.typer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import cn.edu.whu.cstar.simulator.CrashIndex;
import cn.edu.whu.cstar.simulator.RandomSimulator;
import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.RepsUtilier;
import weka.classifiers.functions.SGDText.Count;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/***
 * <p>Main entry of package <b>typer</b>. Running {@link#main} method can give 
 * the whole exception types (generated by 7 operator) of each project.</p>
 * @author yongfeng
 *
 */
@SuppressWarnings("unused")
public class Typer {

	public static void main(String[] args) {
		
		//// Finding the exception types that each operator generated in each project (average on 10 datasets)
		for(int i=1;i<=8;i++){
			try {
				List<HashMap<String, Integer>> lsOperatorTypes = typerInOpeators(i);
				
			} catch (Exception e) {
				System.out.println("[Error]: exception is thrown when typing project [" + i  +"].");
				e.printStackTrace();
			}
		}
		
		//// Finding the exception type distribution in each project (average on 10 datasets)
//		for(int i=1;i<=7;i++){
//			try {
//				typerIn500Crashes(i);
//			} catch (Exception e) {
//				System.out.println("[Error]: exception is thrown when typing project [" + i  +"].");
//				e.printStackTrace();
//			}
//		}
		
		//// Finding the crashes generated by each operators (original dataset)
//		for(int i=1;i<=7;i++){
//			try {
//				typerInProject(i);
//			} catch (Exception e) {
//				System.out.println("[Error]: exception is thrown when typing project [" + i  +"].");
//				e.printStackTrace();
//			}
//		}
	
	}
	
	/** Sorting the Map by elements' value */
	public static void sortingMaps(HashMap<String, Integer> mapTest){
		if(mapTest.isEmpty()){
//			System.out.println("[ERROR]: No Elems in Map.");
			System.out.println("--------------");
			return;
		}
		
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(mapTest.entrySet());
		    Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					// TODO Auto-generated method stub
					return o2.getValue().compareTo(o1.getValue());
				}
		    
		});
		
		
		for(Map.Entry<String,Integer> mapping:list){ 
		       System.out.println("[EXP TYPES]:" + mapping.getKey()+" [COUNT]:"+mapping.getValue()); 
		} 	
		System.out.println("--------------");
	}
	
	public static List<HashMap<String,Integer>> typerInOpeators(int projId) throws Exception{
		String path = "";
		String arff = "";
		
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
		case 8:	
			path = "src/main/resources/crashrep/total.txt";
		default:
			System.out.println("[ERROR]: No such project id <" + projId + ">");
			break;
		}
		
		System.out.println("\n[project]: " + path);
		
		/** Read mutation line in MutationInfo.txt */
		File file = new File("src/main/resources/MutationInfo.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		List<String> lsContent = new ArrayList<String>(); // content of MutationInfo.txt
		while((str=br.readLine())!=null) { // for each line in MutationInfo.txt
			if(str.trim().equals("")){ continue; }
			lsContent.add(str);			
		}
		br.close();
		
		/** Crash Node list in stack trace */
		List<CrashNode> lsCrash = RepsUtilier.getSingleCrash(path);
		List<HashMap<String,Integer>> lsOperatorTypes = new ArrayList<HashMap<String, Integer>>();
		
		for(int k=0;k<7;k++){
			lsOperatorTypes.add(new HashMap<String, Integer>());
		}
		
		for(int k=0; k<lsCrash.size(); k++){
			CrashNode currentCrash = lsCrash.get(k);
			String mutLine = currentCrash.MutationLine;
			
			String stkClsName = getInformByStackTraceMutLine(mutLine)[0];
			String stkMedName = getInformByStackTraceMutLine(mutLine)[1];
			String stkLineNum = getInformByStackTraceMutLine(mutLine)[2];
			
			for(int j=0; j<lsContent.size(); j++){ // for each line in MutationInfo.txt
				
				String mutClsName = OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[0];
				String mutMedName = OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[1];
				String mutLineNum = OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[2];
				
				if( stkClsName.equals(mutClsName) && stkMedName.equals(mutMedName) && stkLineNum.equals(mutLineNum)){
					int operator_id = OperatorCollector.getOperatorID(OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[3]);
					String expName = currentCrash.exceptionName;
					
					if (lsOperatorTypes.get(operator_id).keySet().contains(expName)){ // already exist expName
						int sizeBefore = lsOperatorTypes.get(operator_id).get(expName);
						lsOperatorTypes.get(operator_id).put(expName, (sizeBefore+1));
					}else{ // have no keys of expName
						lsOperatorTypes.get(operator_id).put(expName, 1);
					}

					break;
				}
			}
			
		}
		
		for(int i=0; i<lsOperatorTypes.size(); i++){
			System.out.println("[operator]:" + i);
			sortingMaps(lsOperatorTypes.get(i));
		}
		
		return lsOperatorTypes;
		
	}
	
	/**
	 * <p>To count the average operation distribution in 500 crashes in each project.</p>
	 * @param projId
	 * @throws Exception
	 */
	public static void typerIn500Crashes(int projId) throws Exception{
		
		String path = "";
		String arff = "";
		
		switch(projId){
		case 1:
			path = "src/main/resources/crashrep/codec_mutants.txt";
			arff = "src/main/resources/crasharff/generated/codec";
			break;
		case 2:
			path = "src/main/resources/crashrep/collection_mutants.txt";
			arff = "src/main/resources/crasharff/generated/collections";
			break;
		case 3:
			path = "src/main/resources/crashrep/io_mutants.txt";
			arff = "src/main/resources/crasharff/generated/io";
			break;
		case 4:
			path = "src/main/resources/crashrep/jsoup_mutants.txt";
			arff = "src/main/resources/crasharff/generated/jsoup";
			break;
		case 5:
			path = "src/main/resources/crashrep/jsqlpraser_mutants.txt";
			arff = "src/main/resources/crasharff/generated/jsqlparser";
			break;
		case 6:
			path = "src/main/resources/crashrep/mango_mutants.txt";
			arff = "src/main/resources/crasharff/generated/mango";
			break;
		case 7:
			path = "src/main/resources/crashrep/ormlite_mutants.txt"; 
			arff = "src/main/resources/crasharff/generated/ormlite";
			break;
		default:
			System.out.println("[ERROR]: No such project id <" + projId + ">");
			break;
		}
		
		System.out.println("[project]: " + path);
						
		/////////////////////////////////////////
		// WAY 1
//		/** Crash Node list in stack trace */
//		List<CrashNode> lsCrash = RepsUtilier.getSingleCrash(path);
//		
//		for(int i=1; i<=10; i++){
//			/** simulate the generation to get crash indexes */
//			CrashIndex[] crashes = RandomSimulator.getDataset(path, 500, i);
//			RandomSimulator.RandomizeByRand(crashes, 1);
//			CrashIndex[] lsFinalCrashes = RandomSimulator.StratifyByFolds(crashes, 10);
//			
//			int[] arrTypes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
//					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//			
//			for(int k=0; k<lsFinalCrashes.length; k++){
//				int expType = lsCrash.get(lsFinalCrashes[k].getCrashID()).getType();
//				arrTypes[expType]++;
////				System.out.printf(expType + ",");
//			}
//			
//			for(int m = 1; m<arrTypes.length; m++){
//				System.out.printf("[ %-2d : %-4d ] ", m, arrTypes[m]);
//			}
//			
//			System.out.println("");
//			
//		}
//		
//		System.out.println("-------------------------------------------------------------------");
		
		/////////////////////////////////////////
		// WAY 2
		int[] arrTypes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		int[][] sum = new int[11][22];
		
		for(int i=1; i<=10; i++){
			/** simulate the generation to get crash indexes */
			Instances ins = DataSource.read(arff + i + ".arff");
			ins.setClassIndex(ins.numAttributes()-1);
									
			for(int j=0; j<ins.numInstances(); j++){
				int expType = (int) ins.get(j).value(0);
//				arrTypes[expType]++;
				sum[i][expType]++;
			}			
			
//			for(int m = 1; m<arrTypes.length; m++){
//				System.out.printf("[ %-2d : %-4d ] ", m, sum[i][m]);
//			}
//			System.out.println("");
			
			for(int col =1; col<22; col++){
				for(int row = 1; row<11; row++){
					arrTypes[col] += sum[row][col];
					sum[row][col] = 0;
				}
			}
			
		}
		
		for(int m = 1; m<arrTypes.length; m++){
			if(arrTypes[m] > 0){
				System.out.printf("[%d] : %f ", m, arrTypes[m]*1.0/10);
			}
		}
			
		System.out.println("\n");
	}
	
	/**
	 * <p>To count the operation distribution in each project</p>
	 * @param projId
	 * @throws Exception
	 */
	public static void typerInProject(int projId) throws Exception{
		
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
		
		System.out.println("[project]: " + path);
		
		/** Crash Node list in stack trace */
		List<CrashNode> lsCrash = RepsUtilier.getSingleCrash(path);
		
		/** Read mutation line in MutationInfo.txt */
		File file = new File("src/main/resources/MutationInfo.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		int[] expTypes = {0, 0, 0, 0, 0, 0, 0};
		List<String> lsContent = new ArrayList<String>();
		while((str=br.readLine())!=null) { // for each line in MutationInfo.txt
			if(str.trim().equals("")){ continue; }
			lsContent.add(str);			
		}
		
		br.close();
		
		for(int i=0; i<lsCrash.size(); i++){ // for each line in Crash Node list
			String mutLine = lsCrash.get(i).MutationLine;
//			System.out.printf("[mutation information] %s %s %s\n", 
//					getInformByStackTraceMutLine(mutLine)[0], 
//					getInformByStackTraceMutLine(mutLine)[1], 
//					getInformByStackTraceMutLine(mutLine)[2] );
			String stkClsName = getInformByStackTraceMutLine(mutLine)[0];
			String stkMedName = getInformByStackTraceMutLine(mutLine)[1];
			String stkLineNum = getInformByStackTraceMutLine(mutLine)[2];
			
			for(int j=0; j<lsContent.size(); j++){ // for each line in MutationInfo.txt
				
				String mutClsName = OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[0];
				String mutMedName = OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[1];
				String mutLineNum = OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[2];
				
				if( stkClsName.equals(mutClsName) && stkMedName.equals(mutMedName)
						&& stkLineNum.equals(mutLineNum)){
					int operator_id = OperatorCollector.getOperatorID(OperatorCollector.getInformsByFileMutLine(lsContent.get(j))[3]);
					expTypes[operator_id]++;
//					System.out.println("[bingo]: " + stkClsName + stkMedName + stkLineNum);
					break;
				}
			}
		}
		
		/**print the mutation*/
		for(int i=0; i< expTypes.length; i++){
			if(expTypes[i] > 0){
				System.out.println("| " + i + " | " + expTypes[i] + " |");
			}
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
	
	/**
	 * <p>finding the exception type occurrences in give array list. </p>
	 * @param lsOperatorCol array list
	 * @return count int[22]
	 */
	public static int[] FindingTop3(ArrayList<Integer> lsOperatorCol){
		int[] count = new int[22];
		for(int j=0; j<lsOperatorCol.size(); j++){
			count[lsOperatorCol.get(j)]++;
		}
//		for(int i=1; i<22; i++){
//			System.out.print(i + ":" + count[i] + ", ");
//		}
//		System.out.println("");
		
		return count;
	}
	
	/***
	 * <p>adding two 2-dimension arrays</p>
	 * @param array 1
	 * @param array 2
	 * @return the sum of two arrays
	 */
	public static int[][] ArrayListAdding(int[][] lst1, int[][] lst2){
		
		if(lst1 == null || lst2 == null ){
			System.out.println("[Error]: lst1 or lst2 cannot be null!");
			return null;
		}
		
		if(lst1.length != lst2.length || lst1[0].length != lst2[0].length){
			System.out.println("[Error]: lst1 and lst2 should have same dimension!");
			return null;
		}
		
		int lenRow = lst1.length;
		int lenCol = lst1[0].length;
		
		int[][] sum = new int[lenRow][lenCol];
		
		for(int i=0; i<lenRow; i++){
			for(int j=0; j< lenCol; j++){
				sum[i][j] = lst1[i][j] + lst2[i][j];
			}
		}
		
		return sum;
	}

}
