package cn.edu.whu.cstar.timer;

/***
 * <p>Main entry of package <b>timer</b>. Running {@link#main} method can give 
 * the whole analysis time (in million second) of each project.</p>
 * @author yongfeng
 *
 */
public class Timer {
	
	public static void main(String[] args){
		System.out.println("-------  Time Consuming of each Project  -------");	
		
		// Analysis Process
		for(int i=1; i<=7; i++){
			long startTime = 0l;
			long endTime = 0l;		
			startTime = System.currentTimeMillis();
			analyzing(i);
			endTime = System.currentTimeMillis();
			System.out.println("[time   ]: " + (endTime-startTime) + "\n");
		}
		
	}
	
	public static void analyzing(int projId){
		
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
		
		// analysis the program and get features
		RepsUtilier.getFeatures(path, proj);
	}

}
