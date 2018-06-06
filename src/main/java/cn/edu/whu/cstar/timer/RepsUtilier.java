package cn.edu.whu.cstar.timer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class RepsUtilier {

	public static void main(String[] args) throws Exception {
		
		List<CrashNode> lsCrash = getSingleCrash("src/main/resources/crashrep/ormlite_mutants.txt");
		System.out.println("[crash size]: " + lsCrash.size());
				
	}
	
	/**
	 * <p>to return the list of single crash</p>
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public static List<CrashNode> getSingleCrash(String path) throws Exception{
		List<CrashNode> lsCrash = new ArrayList<CrashNode>();
		
		// split the xx_mutants.txt in "crashrep"
		File file = new File(path);
		if(!file.exists()){
			System.out.println("[ERROR]: No such file! " + path);
			return null;
		}
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String str = "";
		List<String> singleCrash = new ArrayList<String>();
		while((str=br.readLine())!=null){
			
			if(str.startsWith("MUTATIONID:<<")){ // end of single crash
				singleCrash.add(str);
				CrashNode crash = new CrashNode(singleCrash);
				lsCrash.add(crash);
				singleCrash.clear();
			}else if(!str.trim().equals("") && !str.equals("BUG1")){
				singleCrash.add(str);
			}
		}
		
		br.close();
		fr.close();
		
		return lsCrash;
	}

}
