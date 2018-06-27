package cn.edu.whu.cstar.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.RepsUtilier;

public class RandomSimulator {

	public static void main(String[] args) {
		
		CrashIndex[] lsOriginCrashes = getDataset("src/main/resources/crashrep/codec_mutants.txt", 500, 1);
		for(CrashIndex cc: lsOriginCrashes){
			System.out.printf("%-5d", cc.getCrashID());
		}
		System.out.println("\n-----");
		RandomSimulator.RandomizeByRand(lsOriginCrashes, 1);
		for(CrashIndex cc: lsOriginCrashes){
			System.out.printf("%-5d", cc.getCrashID());
		}
		System.out.println("\n-----");
		CrashIndex[] lsFinalCrashes = RandomSimulator.StratifyByFolds(lsOriginCrashes, 10);
		for(CrashIndex cc: lsFinalCrashes){
			System.out.printf("%-5d", cc.getCrashID());
		}
		System.out.println("\n-----");
	}
	
	/**<p>using projects' stack trace file to randomly select 500 crash. 
	 * We just simulate the randomization and selection process.</p>
	 * <p>1. <b>Randomization:</b> swapping the j-th crash and the i-th crash, where i &in; [0, j].</p>
	 * <p>2. <b>Selection:</b> selecting inTrace and outTrace crash according to their ratio, 
	 * and put them into lsFinalCrashes.</p>
	 * @param proj project's stack trace file
	 * @param seed random seed
	 * @return lsFinalCrashes the list of index
	 * */
	public static CrashIndex[] getDataset(String proj, int selNum, int seed){
		
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			lsCrash = RepsUtilier.getSingleCrash(proj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int lenCrash = lsCrash.size();
		if(selNum > lenCrash){
			System.out.printf("[Error]: We cannot select %d from %d in %s.\n", selNum, lenCrash, proj);
			return null;
		}
		
		CrashIndex[] lsCrashIndexes = new CrashIndex[lenCrash]; //
		int crash_0 = 0;
		int crash_1 = 0;
		for(int i=0; i<lenCrash; i++){
			if(lsCrash.get(i).InTrace >= 0){
				CrashIndex cc = new CrashIndex(i, 0); //
				lsCrashIndexes[i] = cc; //
				crash_0++; //
			}else{
				CrashIndex cc = new CrashIndex(i, 1); //
				lsCrashIndexes[i] = cc; //
				crash_1++; //
			}
		}
		
		
		int crash_0_ShouldBe = (int) (selNum * (crash_0 *1.0/(crash_0 + crash_1)));
		int crash_1_ShouldBe = selNum - crash_0_ShouldBe;
		
		RandomizeByRand(lsCrashIndexes, seed); //
		CrashIndex[] lsSyn = synthesizeData(lsCrashIndexes, crash_0_ShouldBe, crash_1_ShouldBe); //
		
		return lsSyn;
	}
	
	/**<p> to randomize the list <b>lsOrigin</b> with random seed <b>seed</b> ,
	 * this method simulates the function of <b>Instances.randomize(seed)</b></p>
	 * @param lsOrigin original integer list
	 * @param seed seed
	 * */
	public static void RandomizeByRand(CrashIndex[] lsOrigin, int seed){
//		public static List<Integer> RandomizeBySeed(List<Integer> lsOrigin, int seed){
//			List<Integer> lsRandom = new ArrayList<Integer>();
			int len = lsOrigin.length;
			Random rand = new Random(seed);
			for(int j = len-1; j>0; j--){
				int changeIndex = rand.nextInt(j+1);
//				System.out.printf(" change [%d] with [%d] \n", j, changeIndex);
				
				CrashIndex temp = lsOrigin[j];
				lsOrigin[j] = lsOrigin[changeIndex];
				lsOrigin[changeIndex] = temp;
			}
			
//			return lsRandom;
	}
	
	public static CrashIndex[] synthesizeData(CrashIndex[] lsOrigin, int crash_0_ShouldBe, int crash_1_ShouldBe){
		CrashIndex[] lsInTraces = new CrashIndex[crash_0_ShouldBe];
		CrashIndex[] lsOutTraces = new CrashIndex[crash_1_ShouldBe];
//		System.out.println(crash_0_ShouldBe + "::" + crash_1_ShouldBe);
		int l = 0;
		int m = 0;
		for(int i=0; i<lsOrigin.length; i++){
			if(l == crash_0_ShouldBe){
				break;
			}else{
				if(lsOrigin[i].getClassVal() == 0){
					lsInTraces[l] = lsOrigin[i];
					l++;
				}
			}
		}
		for(int i=0; i<lsOrigin.length; i++){
			if(m == crash_1_ShouldBe){
				break;
			}else{
				if(lsOrigin[i].getClassVal() == 1){
					lsOutTraces[m] = lsOrigin[i];
					m++;
				}
			}
		}
		CrashIndex[] lsSyn = new CrashIndex[crash_0_ShouldBe + crash_1_ShouldBe];
		for(int i=0; i<(crash_0_ShouldBe + crash_1_ShouldBe); i++){
			if(i < crash_0_ShouldBe){
				lsSyn[i] = lsInTraces[i];
			}else{
				lsSyn[i] = lsOutTraces[i-crash_0_ShouldBe];
			}
		}
		
		return lsSyn;
	}
	
	/**<p> This method simulates the function of <b>Instances.stratify(foldNum)</b>. 
	 * to stratify the list <b>lsOrigin</b> with fold <b>foldNum</b></p>
	 * @param lsOrigin original integer list
	 * @param foldNum number of folds
	 * */
	public static CrashIndex[] StratifyByFolds(CrashIndex[] lsOrigin, int foldNum){
		int len = lsOrigin.length;
		
		int index = 1;
	    while (index < len) {
		    CrashIndex instance1 = lsOrigin[index - 1];
		    for (int j = index; j < len; j++) {
		    	CrashIndex instance2 = lsOrigin[j];
		    	if (instance1.getClassVal() == instance2.getClassVal()) {
		    		CrashIndex temp = lsOrigin[j];
		    		lsOrigin[j] = lsOrigin[index];
		    		lsOrigin[index] = temp;
		    		index++;
		    	}
		    }
	        index++;
	    }
		
	    CrashIndex[] lsGen = new CrashIndex[len];
	    int k = 0;
	    int j, start = 0;
	    while (k < len) {
	    	j = start;
	        while (j < len) {
//	        	System.out.println("[j]: " + j);
	        	lsGen[k] = lsOrigin[j];
	        	k++;
	        	j = j + foldNum;
	        }
	        start++;
	    }
	    
	    return lsGen;
	}
	
	/** to print the integer list <b>ls</b> */
	public static void showList(List<Integer> ls){
		
		for(Integer in: ls){
			System.out.print(in + ", ");
		}
		
		System.out.println("\n----------------------------------------------------");
	}
	
}


