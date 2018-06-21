package cn.edu.whu.cstar.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.edu.whu.cstar.timer.CrashNode;
import cn.edu.whu.cstar.timer.RepsUtilier;

public class RandomSimulator {

	public static void main(String[] args) {
		
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/codec_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Integer> lsIndex = new ArrayList<Integer>();
		List<Integer> lsInTrace = new ArrayList<Integer>();
		List<Integer> lsOutTrace = new ArrayList<Integer>();
		
		for(int i=0; i<lsCrash.size(); i++){
			if(lsCrash.get(i).InTrace >= 0){
				lsInTrace.add(i);
			}else{
				lsOutTrace.add(i);
			}
			lsIndex.add(i);
		}
		
		showList(lsIndex);
		showList(lsInTrace);
		showList(lsOutTrace);
		RandomizeBySeed(lsIndex, 1);
		showList(lsIndex);
	}
	
	/** to print the integer list */
	public static void showList(List<Integer> ls){
		
		for(Integer in: ls){
			System.out.print(in + ", ");
		}
		
		System.out.println("\n----------------------------------------------------");
	}
	
	public static void RandomizeBySeed(List<Integer> lsOrigin, int seed){
//	public static List<Integer> RandomizeBySeed(List<Integer> lsOrigin, int seed){
//		List<Integer> lsRandom = new ArrayList<Integer>();
		int len = lsOrigin.size();
		for(int j = len-1; j>0; j--){
			int changeIndex = (new Random(seed)).nextInt(j+1);
			System.out.println(" change with : " + changeIndex);
			
			int temp = lsOrigin.get(j);
			lsOrigin.set(j, lsOrigin.get(changeIndex));
			lsOrigin.set(changeIndex, temp);
		}
		
//		return lsRandom;
	}
	

}
