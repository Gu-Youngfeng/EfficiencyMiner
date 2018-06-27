package cn.edu.whu.cstar.timer;

import cn.edu.whu.cstar.evaluation.Evaluator;
import weka.core.Instances;

/***
 * <p>Main entry of package <b>timer</b>. Running {@link#main} method can give 
 * the whole analysis time (in million second) of each project.</p>
 * @author yongfeng
 *
 */
public class Timer {
	
	public static void main(String[] args){
		System.out.println("-------  Time Consuming in each Project  -------");	
		
		// Time Consumption in Analysis Process
//		for(int i=1; i<=7; i++){	
//			long startTime = System.currentTimeMillis();
//			AnalyzationTime(i);
////			ModelingTime(i);
//			long endTime = System.currentTimeMillis();
//			System.out.println("[time   ]: " + (endTime-startTime) + "\n");
//		}
		
		// Time Consumption in Modeling and Prediction Process
		for(int i=1; i<=7; i++){
			ModelingTime(i);
		}
		
	}
	
	/**
	 * <p>To count the analysis time of each project</p>
	 * @param projId
	 */
	public static void AnalyzationTime(int projId){
		
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
			path = "src/main/resources/crashrep/ormlite_mutants.txt"; 
			proj = "src/main/resources/projs/ormlite_parent/";
			break;
		default:
			System.out.println("[ERROR]: No such project id <" + projId + ">");
			break;
		}
		
		// analysis the program and get features
		RepsUtilier.getFeatures(path, proj);
	}

	
	public static void ModelingTime(int projId){
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
		
		double modelTime = 0.0d;
		double predictTime = 0.0d;
		
		for(int i=1; i<=10; i++){
			Evaluator evaluator = new Evaluator();
			Instances ins;
			try {
				ins = evaluator.getDataByArff(arff +i +".arff");
				modelTime += evaluator.runningModel(ins)[0];
				predictTime += evaluator.runningModel(ins)[1];
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
		System.out.println("| " + modelTime*1.0/10 + " | " + predictTime*1.0/10 + " |");
		System.out.println("");
	} 
}
