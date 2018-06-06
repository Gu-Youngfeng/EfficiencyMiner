package cn.edu.whu.cstar.timer;

import java.util.List;

import org.junit.*;

public class RepsUtilierTest {

	@Test
	public void testgetSingleCrash_0(){
		try {
			List<CrashNode> lsCrash = RepsUtilier.getSingleCrash("src/main/resources/crashrep/ormlite_mutants.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
