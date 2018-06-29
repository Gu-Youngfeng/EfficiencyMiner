import cn.edu.whu.cstar.efforter.Efforter;
import cn.edu.whu.cstar.timer.Timer;
import cn.edu.whu.cstar.typer.Typer;

/***
 * <pre>
 * This is the main entry of the whole project.
 * </pre>
 * @author yongfeng
 *
 */
public class Launcher {
	
	public static void main(String[] parameters){
		
		/** counting the analysis and prediction time of each crash*/
		Timer.main(null);
		/** counting the line efforts */
		Efforter.main(null);
		/** counting the exception type of each operator */
		Typer.main(null);
	}

}
