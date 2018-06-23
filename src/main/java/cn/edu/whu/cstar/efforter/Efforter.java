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
		
		// crash nodes
		List<CrashNode> lsCrash = RepsUtilier.getSingleCrash(path);
		// crash efforts
		List<CrashEffort> lsEfforts = new ArrayList<CrashEffort>();
		for(CrashNode crash: lsCrash){
			int lineAllMethodLine = LineCounter.countingAllMethodLine(proj, crash);
			int lineStackTraceLine = LineCounter.countingAllStackTraceLine(crash);
			int lineExpStackTraceLine = LineCounter.countingExpStackTraceLine(crash);
			int lineExpMethodLine = LineCounter.countingExpMethodLine(proj, crash);
						
			lsEfforts.add(new CrashEffort(lineAllMethodLine, lineStackTraceLine, lineExpStackTraceLine, lineExpMethodLine));
		}
		
//		for(CrashEffort effort: lsEfforts){
//			effort.showEfforts();
//		}
		
		// each project, randomly generate 10 datasets using random seed from 1 to 10
		for(int seed = 1; seed <= 10; seed++){
			/** crash index list in codec1.arff */
			CrashIndex[] lsOriginCrashes = RandomSimulator.getDataset("src/main/resources/crashrep/codec_mutants.txt", 500, seed);		
			RandomSimulator.RandomizeByRand(lsOriginCrashes, 1);
			CrashIndex[] lsFinalCrashes = RandomSimulator.StratifyByFolds(lsOriginCrashes, 10);
			int[] lsCrashIndex = new int[lsFinalCrashes.length];
			
			for(int i=0; i<lsFinalCrashes.length; i++){
		//			System.out.printf("%-5d", lsFinalCrashes[i].getCrashID());
				lsCrashIndex[i] = lsFinalCrashes[i].getCrashID();
			}
			
		//		System.out.println("\n-----------");
		//		
		//		for(int i=0; i<lsCrashIndex.length; i++){
		//			System.out.printf("%-5d", lsCrashIndex[i]);
		//		}
		//		
		//		System.out.println("\n-----------");
			
			/** read instances from arff file*/
			Evaluator evaluator = new Evaluator();
			Instances ins = evaluator.getDataByArff("src/main/resources/crasharff/generated/codec" + seed +".arff");
			
			/** model setting and building */	
		//		evaluator.evaluateByCrossWithJ48(ins);
		//		evaluator.evaluateByFoldsWithJ48(ins);
		//		evaluator.evaluateByCrossWithJ48SMOTE(ins);
			evaluator.evaluateByFoldsWithJ48SMOTE(ins);
			
			/** get InTrace crash predicted as InTrace list*/
//			List<ArrayList<Integer>> InTraceAsInTrace = evaluator.getITAsIT();
		//		for(ArrayList<Integer> ls: InTraceAsInTrace)
		//			RandomSimulator.showList(ls);
			
			List<Integer> finals = evaluator.getFinalCrashIndex(lsCrashIndex);
			RandomSimulator.showList(finals);
			
			for(CrashEffort effort:lsEfforts){
				
			}
		}
		
		
		
	}

}
