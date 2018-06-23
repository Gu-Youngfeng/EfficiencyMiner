package cn.edu.whu.cstar.simulator;

/***
 * <p>To save the map message from {@link#crash_id} to {@link#class_value} of single crash, 
 * the crash id is the index from the original stack trace file.</p>
 * @see#getCrashID
 * @see#getClassVal
 * 
 * @author yongfeng
 *
 */
public class CrashIndex{
	
	private int crash_id;	
	private int class_value;
	
	public CrashIndex(int crash_id, int class_value){
		this.crash_id = crash_id;
		this.class_value = class_value;
	}
	
	public void showCrashIndex(){
		System.out.printf("[CARSH ID]: %-4d [CLASS VALUE]: %d\n", crash_id,  class_value);
	}
	/** to get crash id */
	public int getCrashID(){
		return crash_id;
	}
	/** to get class value {0,1}*/
	public int getClassVal(){
		return class_value;
	}
	
}
