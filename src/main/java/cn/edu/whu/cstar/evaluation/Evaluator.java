package cn.edu.whu.cstar.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import cn.edu.whu.cstar.simulator.CrashIndex;
import cn.edu.whu.cstar.simulator.RandomSimulator;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

public class Evaluator {

	public static void main(String[] args) throws Exception {
		

		/** crash index list in codec1.arff */
		CrashIndex[] lsOriginCrashes = RandomSimulator.getDataset("src/main/resources/crashrep/codec_mutants.txt", 500, 1);		
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
		Instances ins = evaluator.getDataByArff("src/main/resources/crasharff/generated/codec1.arff");
		
		/** model setting and building */	
//		evaluator.evaluateByCrossWithJ48(ins);
		evaluator.evaluateByFoldsWithJ48(ins);
	//	evaluator.evaluateByCrossWithJ48SMOTE(ins);
//		evaluator.evaluateByFoldsWithJ48SMOTE(ins);
		
		/** get InTrace crash predicted as InTrace list*/
//			List<ArrayList<Integer>> InTraceAsInTrace = evaluator.getITAsIT();
	//		for(ArrayList<Integer> ls: InTraceAsInTrace)
	//			RandomSimulator.showList(ls);
		
		List<Integer> finals = evaluator.getFinalCrashIndex(lsCrashIndex);
		RandomSimulator.showList(finals);
		
	}
	
	/**<p>Crash, which is InTrace, predicted as InTrace. 
	 * <b>InTraceAsInTrace</b> contains the index of crashes in test process in each fold.</p>
	 * */
	private List<ArrayList<Integer>> InTraceAsInTrace = new ArrayList<ArrayList<Integer>>();
	
	public List<ArrayList<Integer>> getITAsIT(){
		return InTraceAsInTrace;
	}
	
	/**
	 * <p>Get the Instances from the given path.</p>
	 * @param path arff path
	 * @return ins
	 * @throws Exception
	 */
	public Instances getDataByArff(String path) throws Exception{
		Instances ins = DataSource.read(path);
		int lenAttr = ins.numAttributes();
		ins.setClassIndex(lenAttr-1);
		
		return ins;
	}
	
	/**
	 * <p>Evaluate model build from ins using C4.5.</p>
	 * <p>-------------------<br>
	 * This results are in consistent with Weka.
	 * </p>
	 * @param ins instances
	 * @throws Exception
	 */
	public void evaluateByCrossWithJ48(Instances ins) throws Exception{
		
		J48 j48 = new J48();
		j48.buildClassifier(ins);
		
		Evaluation eval = new Evaluation(ins);
		eval.crossValidateModel(j48, ins, 10, new Random(1));
		
		System.out.println(eval.toSummaryString());
		System.out.println(eval.toClassDetailsString());
	}
	
	/**
	 * <p>Evaluate model build from ins using C4.5 as well as SMOTE.</p>
	 * <p>-------------------<br>
	 * This results are in consistent with Weka.
	 * </p>
	 * @param ins instances
	 * @throws Exception
	 */
	public void evaluateByCrossWithJ48SMOTE(Instances ins) throws Exception{
		
		SMOTE smote = new SMOTE();
		J48 j48 = new J48();
		
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(smote);
		fc.setClassifier(j48);
		fc.buildClassifier(ins);
		
		Evaluation eval = new Evaluation(ins);
		Random rand = new Random(1);
		eval.crossValidateModel(fc, ins, 10, rand);
		
		System.out.println(eval.toSummaryString());
		System.out.println(eval.toClassDetailsString());
	}
	
	/**
	 * <p>Evaluate model build from ins using C4.5 as well as SMOTE.</p>
	 * <p>-------------------<br>
	 * This results are NOT in consistent with Weka.
	 * </p>
	 * @param ins instances
	 * @param flag whether using average results
	 * @throws Exception
	 */
	public void evaluateByFoldsWithJ48SMOTE(Instances ins) throws Exception{
		
//		double precision_0 = 0.0d;
//		double precision_1 = 0.0d;
//		double recall_0 = 0.0d;
//		double recall_1 = 0.0d;
//		double fMeasure_0 = 0.0d;
//		double fMeasure_1 = 0.0d;
//		double fpRate_0 = 0.0d;
//		double fpRate_1 = 0.0d;
//		int cout = 0;
		
		ins.randomize(new Random(1));
		ins.stratify(10);
		
		for(int i=0; i<10; i++){
			ArrayList<Integer> lsITAsIT = new ArrayList<Integer>();
			
			Instances testData = ins.testCV(10, i);
			Instances trainData = ins.trainCV(10, i);	
			
			SMOTE smote = new SMOTE();
			smote.setInputFormat(trainData);
			Filter.useFilter(trainData, smote);
			
			J48 j48 = new J48();
			j48.buildClassifier(trainData);
			
			Evaluation eval = new Evaluation(trainData);
//			eval.evaluateModel(j48, testData);
//			
//			precision_0 += eval.precision(0);
//			precision_1 += eval.precision(1);
//			recall_0 += eval.recall(0);
//			recall_1 += eval.recall(1);
//			fMeasure_0 += eval.fMeasure(0);
//			fMeasure_1 += eval.fMeasure(1);
//			fpRate_0 += eval.falsePositiveRate(0);
//			fpRate_1 += eval.falsePositiveRate(1);
//			
//			System.out.println("[correct]" + eval.correct());
//			System.out.println(eval.toClassDetailsString());
//			cout += eval.correct();
			
			for(int k=0; k<testData.numInstances(); k++){ // for each instance in test set
				Instance currentIns = testData.get(k);
				double predictedValue = eval.evaluateModelOnce(j48, currentIns);
//				System.out.println(currentIns);
				if(currentIns.classValue() == predictedValue && currentIns.classValue() == 0){
					// crash which is InTrace, predicted as InTrace
//					System.out.print(k + ",");
					lsITAsIT.add(k);
				}
			}
			
//			System.out.println(" |----------- " + lsITAsIT.size());
			InTraceAsInTrace.add(lsITAsIT);
			
		}
		
//		System.out.println("[correct]" + cout);
//		System.out.printf("%4.3f | %4.3f %4.3f %4.3f\n", fpRate_0*1.0/10, precision_0*1.0/10, recall_0*1.0/10, fMeasure_0*1.0/10);
//		System.out.printf("%4.3f | %4.3f %4.3f %4.3f\n", fpRate_1*1.0/10, precision_1*1.0/10, recall_1*1.0/10, fMeasure_1*1.0/10);

	}
	
	/**
	 * <p>Evaluate model build from ins using C4.5.</p>
	 * <p>-------------------<br>
	 * This results are NOT in consistent with Weka.
	 * </p>
	 * @param ins instances
	 * @param flag whether using average results
	 * @throws Exception
	 */
	public void evaluateByFoldsWithJ48(Instances ins) throws Exception{
		
		double precision_0 = 0.0d;
		double precision_1 = 0.0d;
		double recall_0 = 0.0d;
		double recall_1 = 0.0d;
		double fMeasure_0 = 0.0d;
		double fMeasure_1 = 0.0d;
		double fpRate_0 = 0.0d;
		double fpRate_1 = 0.0d;
		int cout = 0;
				
		ins.randomize(new Random(1));
		ins.stratify(10);
						
		for(int i=0; i<10; i++){
			
			ArrayList<Integer> lsITAsIT = new ArrayList<Integer>();

			Instances trainData = ins.trainCV(10, i, new Random(1));
			Instances testData = ins.testCV(10, i);					
			
			J48 j48 = new J48();
			j48.buildClassifier(trainData);
			
			Evaluation eval = new Evaluation(trainData);
			eval.evaluateModel(j48, testData);
			
			precision_0 += eval.precision(0);
			precision_1 += eval.precision(1);
			recall_0 += eval.recall(0);
			recall_1 += eval.recall(1);
			fMeasure_0 += eval.fMeasure(0);
			fMeasure_1 += eval.fMeasure(1);
			fpRate_0 += eval.falsePositiveRate(0);
			fpRate_1 += eval.falsePositiveRate(1);
//			
//			System.out.println("[correct]" + eval.correct());
//			System.out.println(eval.toClassDetailsString());
//			cout += eval.correct();
			
			for(int k=0; k<testData.numInstances(); k++){ // for each instance in test set
				Instance currentIns = testData.get(k);
				double predictedValue = eval.evaluateModelOnce(j48, currentIns);
//				System.out.println(currentIns);
				if(currentIns.classValue() == predictedValue && currentIns.classValue() == 0){
					// crash which is InTrace, predicted as InTrace
//					System.out.print(k + ",");
					lsITAsIT.add(k);
				}
			}
			
//			System.out.println(" |----------- " + lsITAsIT.size());
			InTraceAsInTrace.add(lsITAsIT);
		}
		
		System.out.println("[correct]" + cout);
		System.out.printf("%4.3f | %4.3f %4.3f %4.3f\n", fpRate_0*1.0/10, precision_0*1.0/10, recall_0*1.0/10, fMeasure_0*1.0/10);
		System.out.printf("%4.3f | %4.3f %4.3f %4.3f\n", fpRate_1*1.0/10, precision_1*1.0/10, recall_1*1.0/10, fMeasure_1*1.0/10);
	}

	/***
	 * <p></p>
	 * @param lsCrashes
	 * @return
	 */
	public List<Integer> getFinalCrashIndex(int[] lsCrashes){
		
		if(InTraceAsInTrace == null){
			System.out.println("[Error]: We cannot collect InTraceAsInTrace (InTraceAsInTrace=null).");
		}
		
		List<Integer> lsFinals = new ArrayList<Integer>();
		
		for(int i=0; i<10; i++){
			for(int j=0; j<InTraceAsInTrace.get(i).size(); j++){
				int relativeIndex = InTraceAsInTrace.get(i).get(j);
				int absoluteIndex = lsCrashes[i*50 + relativeIndex];
				lsFinals.add(absoluteIndex);
			}
		}
		
		return lsFinals;
	}
}
