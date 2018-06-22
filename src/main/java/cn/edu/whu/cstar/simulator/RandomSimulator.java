package cn.edu.whu.cstar.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.RepsUtilier;

public class RandomSimulator {

	public static void main(String[] args) {
		
		List<Integer> lsFinalCrashes = getDataset("src/main/resources/crashrep/codec_mutants.txt", 500, 1);
		showList(lsFinalCrashes);
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
	public static List<Integer> getDataset(String proj, int selNum, int seed){
		
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			lsCrash = RepsUtilier.getSingleCrash(proj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Integer> lsIndex = new ArrayList<Integer>();
		List<Integer> lsInTrace = new ArrayList<Integer>();
		List<Integer> lsOutTrace = new ArrayList<Integer>();
		
		int lenCrash = lsCrash.size();
		if(selNum > lenCrash){
			System.out.printf("[Error]: We cannot select %d from %d in %s.\n", selNum, lenCrash, proj);
			return null;
		}
		
		for(int i=0; i<lenCrash; i++){
			if(lsCrash.get(i).InTrace >= 0){
				lsInTrace.add(i);
			}else{
				lsOutTrace.add(i);
			}
			lsIndex.add(i);
		}
		
//		showList(lsIndex); // all crashes 
//		showList(lsInTrace); // crashes in trace
//		showList(lsOutTrace); // crashes out trace
		
		int numInTrace = lsInTrace.size();
		int numOutTrace = lsOutTrace.size();
		int numInTraceShouldBe = (int) (selNum * (numInTrace *1.0/(numOutTrace + numInTrace)));
		int numOutTraceShouldBe = selNum - numInTraceShouldBe;
		
//		System.out.println("[numInTraceShouldBe ]" + numInTraceShouldBe);
//		System.out.println("[numOutTraceShouldBe]" + numOutTraceShouldBe);
		
		RandomizeBySeed(lsIndex, 1); // randomize all crashes
//		showList(lsIndex);
		
		List<Integer> lsInTraceAfterMize = new ArrayList<Integer>(); // crashes in trace after randomize
		List<Integer> lsOutTraceAfterMize = new ArrayList<Integer>(); // crashes in trace after randomize
		
		for(Integer crash: lsIndex){
			if(lsInTrace.contains(crash)){
				lsInTraceAfterMize.add(crash);
			}else{
				lsOutTraceAfterMize.add(crash);
			}
		}
		
//		showList(lsInTraceAfterMize);
//		showList(lsOutTraceAfterMize);
		
		List<Integer> lsFinalCrashes = new ArrayList<Integer>();
		
		for(int i=0; i<numInTraceShouldBe; i++){
			lsFinalCrashes.add(lsInTraceAfterMize.get(i));
		}
		for(int j=0;j<numOutTraceShouldBe; j++){
			lsFinalCrashes.add(lsOutTraceAfterMize.get(j));
		}
		
//		showList(lsFinalCrashes);
		
		return lsFinalCrashes;
	}
	
	/** to print the integer list <b>ls</b> */
	public static void showList(List<Integer> ls){
		
		for(Integer in: ls){
			System.out.print(in + ", ");
		}
		
		System.out.println("\n----------------------------------------------------");
	}
	
	/** to randomize the list <b>lsOrigin</b> with random seed <b>seed</b> */
	public static void RandomizeBySeed(List<Integer> lsOrigin, int seed){
//	public static List<Integer> RandomizeBySeed(List<Integer> lsOrigin, int seed){
//		List<Integer> lsRandom = new ArrayList<Integer>();
		int len = lsOrigin.size();
		Random rand = new Random(seed);
		for(int j = len-1; j>0; j--){
			int changeIndex = rand.nextInt(j+1);
//			System.out.printf(" change [%d] with [%d] \n", j, changeIndex);
			
			int temp = lsOrigin.get(j);
			lsOrigin.set(j, lsOrigin.get(changeIndex));
			lsOrigin.set(changeIndex, temp);
		}
		
//		return lsRandom;
	}
	

}
