package cn.edu.whu.cstar.efforter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.whu.cstar.evaluation.Evaluator;
import cn.edu.whu.cstar.simulator.CrashIndex;
import cn.edu.whu.cstar.simulator.RandomSimulator;
import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.RepsUtilier;
import weka.core.Instances;

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
		
		/** STEP1: Set the path of stack trace, project, and arff. **/
		String path = "";
		String proj = "";
		String arff = "";
		
		switch(projId){
		case 1:
			path = "src/main/resources/crashrep/codec_mutants.txt";
			proj = "src/main/resources/projs/Codec_parent/";
			arff = "src/main/resources/crasharff/generated/codec";
			break;
		case 2:
			path = "src/main/resources/crashrep/collection_mutants.txt";
			proj = "src/main/resources/projs/Collection_4.1_parent/";
			arff = "src/main/resources/crasharff/generated/collections";
			break;
		case 3:
			path = "src/main/resources/crashrep/io_mutants.txt";
			proj = "src/main/resources/projs/Commons-io-2.5_parent/";
			arff = "src/main/resources/crasharff/generated/io";
			break;
		case 4:
			path = "src/main/resources/crashrep/jsoup_mutants.txt";
			proj = "src/main/resources/projs/jsoup_parent/";
			arff = "src/main/resources/crasharff/generated/jsoup";
			break;
		case 5:
			path = "src/main/resources/crashrep/jsqlpraser_mutants.txt";
			proj = "src/main/resources/projs/JSQL_parent/";
			arff = "src/main/resources/crasharff/generated/jsqlparser";
			break;
		case 6:
			path = "src/main/resources/crashrep/mango_mutants.txt";
			proj = "src/main/resources/projs/mango_parent/";
			arff = "src/main/resources/crasharff/generated/mango";
			break;
		case 7:
			path = "src/main/resources/crashrep/ormlite_mutants.txt"; 
			proj = "src/main/resources/projs/ormlite_parent/";
			arff = "src/main/resources/crasharff/generated/ormlite";
			break;
		default:
			System.out.println("[ERROR]: No such project id <" + projId + ">");
			break;
		}
		
		System.out.println("[project]: " + proj + "\n");
		
		/** STEP2: To calculate 4 kinds of line efforts of all stack traces in project **/
		// crash nodes, record info of each crash
		List<CrashNode> lsCrash = RepsUtilier.getSingleCrash(path);
		// crash efforts, record line efforts of each crash
		List<CrashEffort> lsEfforts = new ArrayList<CrashEffort>();
		for(CrashNode crash: lsCrash){
			int lineAllMethodLine = LineCounter.countingAllMethodLine(proj, crash);
			int lineStackTraceLine = LineCounter.countingAllStackTraceLine(crash);
			int lineExpStackTraceLine = LineCounter.countingExpStackTraceLine(crash);
			int lineExpMethodLine = LineCounter.countingExpMethodLine(proj, crash);
					
//			System.out.printf("| %-5d | %-5d | %-5d | %-5d |\n", lineAllMethodLine, lineStackTraceLine, lineExpStackTraceLine, lineExpMethodLine);

			lsEfforts.add(new CrashEffort(lineAllMethodLine, lineStackTraceLine, lineExpStackTraceLine, lineExpMethodLine));
		}
		
		int lineAllMethodLineSum = 0, lineStackTraceLineSum = 0, lineExpStackTraceLineSum = 0, lineExpMethodLineSum = 0;
		
		/** STEP3: Simulate the Weka processes of data synthesize, randomization, stratify. 
		 *         Get the crash, which is InTrace, is predicted as InTrace in each 500 dataset.
		 * **/
		for(int seed = 1; seed <= 10; seed++){
			/** crash index list in codec1.arff */
			CrashIndex[] lsOriginCrashes = RandomSimulator.getDataset(path, 500, seed);		
			RandomSimulator.RandomizeByRand(lsOriginCrashes, 1);
			CrashIndex[] lsFinalCrashes = RandomSimulator.StratifyByFolds(lsOriginCrashes, 10);
			int[] lsCrashIndex = new int[lsFinalCrashes.length];
			
			for(int i=0; i<lsFinalCrashes.length; i++){
				lsCrashIndex[i] = lsFinalCrashes[i].getCrashID();
			}
			
			/** read instances from arff file*/
			Evaluator evaluator = new Evaluator();
			Instances ins = evaluator.getDataByArff(arff + seed +".arff");
			
			/** model setting and building */	
			evaluator.evaluateByFoldsWithJ48SMOTE(ins);

			/** get all crash indexes which is InTrace predicted as InTrace in arff seed in proj */
			List<Integer> lsfinals = evaluator.getFinalCrashIndex(lsCrashIndex);
//			RandomSimulator.showList(finals);
			
			int lineAllMethodLine = 0, lineStackTraceLine = 0, lineExpStackTraceLine = 0, lineExpMethodLine = 0;
			
			for(int index: lsfinals){
				lineAllMethodLine += lsEfforts.get(index).lineAllMethodLine;
				lineStackTraceLine += lsEfforts.get(index).lineStackTraceLine;
				lineExpStackTraceLine += lsEfforts.get(index).lineExpStackTraceLine;
				lineExpMethodLine += lsEfforts.get(index).lineExpMethodLine;
			}
			
			System.out.printf("| " + seed +" | %-10d | %-10d | %-10d | %-10d |\n", 
					lineAllMethodLine, lineStackTraceLine, lineExpStackTraceLine, lineExpMethodLine);
			
			lineAllMethodLineSum += lineAllMethodLine;
			lineStackTraceLineSum += lineStackTraceLine;
			lineExpStackTraceLineSum += lineExpStackTraceLine;
			lineExpMethodLineSum += lineExpMethodLine;
		}
		
		System.out.printf("| Sum | %-10d | %-10d | %-10d | %-10d |\n", 
				lineAllMethodLineSum, lineStackTraceLineSum, lineExpStackTraceLineSum, lineExpMethodLineSum);
		System.out.printf("| Ave | %-10f | %-10f | %-10f | %-10f |\n", 
				lineAllMethodLineSum*1.0/10, lineStackTraceLineSum*1.0/10, lineExpStackTraceLineSum*1.0/10, lineExpMethodLineSum*1.0/10);
		System.out.println("");
	}

}
