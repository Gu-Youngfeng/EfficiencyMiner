package cn.edu.whu.cstar.timer;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

public class RepsUtilierTest {

	@Test
	public void testgetSingleCrash_0(){
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			 lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/codec_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int crashSize = lsCrash.size();
		Assert.assertEquals(610, crashSize); // 610 crashes

	}
	
	@Test
	public void testgetSingleCrash_1(){
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			 lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/collection_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int crashSize = lsCrash.size();
		Assert.assertEquals(1350, crashSize); // 1350 crashes

	}
	
	@Test
	public void testgetSingleCrash_2(){
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			 lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/io_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int crashSize = lsCrash.size();
		Assert.assertEquals(686, crashSize); // 686 crashes

	}
	
	@Test
	public void testgetSingleCrash_3(){
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			 lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/joup_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int crashSize = lsCrash.size();
		Assert.assertEquals(601, crashSize); // 601 crashes

	}
	
	@Test
	public void testgetSingleCrash_4(){
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			 lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/mango_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int crashSize = lsCrash.size();
		Assert.assertEquals(733, crashSize); // 733 crashes

	}
	
	@Test
	public void testgetSingleCrash_5(){
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			 lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/ormlite_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int crashSize = lsCrash.size();
		Assert.assertEquals(1303, crashSize); // 686 crashes

	}
	
	@Test
	public void testgetSingleCrash_6(){
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		try {
			 lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/ormlite_mutants.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int crashSize = lsCrash.size();
		Assert.assertEquals(1303, crashSize);

	}
}
