package cn.edu.whu.cstar.evaluation;

import java.util.List;
import java.util.Random;

import cn.edu.whu.cstar.simulator.RandomSimulator;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

public class Evaluator {

	public static void main(String[] args) throws Exception {
		
		/** crash index list in codec1.arff */
//		List<Integer> lsFinalCrashes = RandomSimulator.getDataset("src/main/resources/crashrep/codec_mutants.txt", 500, 1);
//		RandomSimulator.RandomizeBySeed(lsFinalCrashes, 1);
//		RandomSimulator.showList(lsFinalCrashes);
//		
//		/** read instances from arff file*/
//		Evaluator evaluator = new Evaluator();
//		Instances ins = evaluator.getDataByArff("src/main/resources/crasharff/generated/codec1.arff");
//		
//		/** model setting and building */	
////		evaluator.evaluateByJ48(ins);
////		evaluator.evaluateByJ48SMOTE(ins);
////		evaluator.evaluateByJ48(ins, true);
//		evaluator.evaluateByJ48SMOTE(ins, true);
		
		Instances ins = DataSource.read("src/main/resources/crasharff/generated/codec1.arff");
		ins.setClassIndex(ins.numAttributes()-1);
		for(int i=0; i<ins.numInstances(); i++){
			System.out.println(ins.get(i));
		}
		System.out.println("--------------------");
		ins.randomize(new Random(1));
		ins.stratify(10);
		for(int i=0; i<ins.numInstances(); i++){
			System.out.println(ins.get(i));
		}
		
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
	public void evaluateByJ48(Instances ins) throws Exception{
		
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
	public void evaluateByJ48SMOTE(Instances ins) throws Exception{
		
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
	public void evaluateByJ48SMOTE(Instances ins, boolean flag) throws Exception{
		if(!flag){
			return;
		}
		
		double precision_0 = 0.0d;
		double precision_1 = 0.0d;
		double recall_0 = 0.0d;
		double recall_1 = 0.0d;
		double fMeasure_0 = 0.0d;
		double fMeasure_1 = 0.0d;
		int cout = 0;
		
		ins.randomize(new Random(1));
		ins.stratify(10);
		
		for(int i=0; i<10; i++){
			
			Instances trainData = ins.trainCV(10, i);
			Instances testData = ins.testCV(10, i);
			
			SMOTE smote = new SMOTE();
			smote.setInputFormat(trainData);
			trainData = Filter.useFilter(trainData, smote);
			
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
			
			System.out.println(eval.toClassDetailsString());
			cout += eval.correct();
			
		}
		
		System.out.println("[correct]" + cout);
		System.out.printf("%4.3f %4.3f %4.3f\n", precision_0*1.0/10, recall_0*1.0/10, fMeasure_0*1.0/10);
		System.out.printf("%4.3f %4.3f %4.3f\n", precision_1*1.0/10, recall_1*1.0/10, fMeasure_1*1.0/10);
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
	public void evaluateByJ48(Instances ins, boolean flag) throws Exception{
		if(!flag){
			return;
		}
		
		double precision_0 = 0.0d;
		double precision_1 = 0.0d;
		double recall_0 = 0.0d;
		double recall_1 = 0.0d;
		double fMeasure_0 = 0.0d;
		double fMeasure_1 = 0.0d;
		int cout = 0;
		
		ins.randomize(new Random(1));
		ins.stratify(10);
		
		for(int i=0; i<10; i++){
			
			Instances testData = ins.testCV(10, i);
			Instances trainData = ins.trainCV(10, i);			
			
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
			
			System.out.println("[correct]" + eval.correct());
			System.out.println(eval.toClassDetailsString());
			cout += eval.correct();
			
		}
		
		System.out.println("[correct]" + cout);
		System.out.printf("%4.3f %4.3f %4.3f\n", precision_0*1.0/10, recall_0*1.0/10, fMeasure_0*1.0/10);
		System.out.printf("%4.3f %4.3f %4.3f\n", precision_1*1.0/10, recall_1*1.0/10, fMeasure_1*1.0/10);
	}
}
