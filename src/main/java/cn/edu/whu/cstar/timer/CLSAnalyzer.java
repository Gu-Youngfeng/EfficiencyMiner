package cn.edu.whu.cstar.timer;

import java.util.List;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtImport;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

public class CLSAnalyzer {

	public static void main(String[] args) {
//		CLSAnalyzer any1 = new CLSAnalyzer("src/main/resources/projs/Codec_parent/","org.apache.commons.codec.binary.Base32");
//		any1.showCLSFeatures();
		
		String fullClass = "E:/workspaceee/EffciencyMinner/src/main/resources/projs/Codec_parent/src/main/java/org/apache/commons/codec/binary/Base32.java";
		Launcher launcher = new Launcher();
		launcher.addInputResource(fullClass);
		launcher.getEnvironment().setAutoImports(true);
		CtModel metaModel = launcher.buildModel();
		List<CtImport> lsLV = metaModel.getElements(new TypeFilter(CtImport.class));
		for(CtImport lv: lsLV){
			System.out.println("[LV]: " + lv.toString());
		}
	}
	/**meta model of spoon*/
	private CtModel metaModel;
	
	/** CT01/CB01: Number of local variables in the top/bottom class */
	int localVariables;
	/** CT02/CB02: Number of fields in the top/bottom class */
	int fielDs;
	/** CT03/CB03: Number of functions (except constructor functions) in the top/bottom class*/
	int methoDs;
	/** CT04/CB04: Number of imported packages in the top/bottom class */
	int packagEs;
	/** CT05/CB05: Whether the top/bottom class is inherited from others */
	int inheriTs;
	/** CT06/CB06: LoC of comments in the top/bottom class */
	int commenTs;
	
	CLSAnalyzer(String proj, String clsName){
		String fullClass = proj + "src/main/java/" + clsName.replaceAll("\\.", "/") + ".java";
		Launcher launcher = new Launcher();
		launcher.addInputResource(fullClass);
		launcher.getEnvironment().setAutoImports(true);
		launcher.buildModel();
		metaModel = launcher.getModel();
		
		/**extraction*/
		List<CtLocalVariable> lsLV = metaModel.getElements(new TypeFilter(CtLocalVariable.class));
		localVariables = lsLV.size();
		
		List<CtField> lsFL = metaModel.getElements(new TypeFilter(CtField.class));
		fielDs = lsFL.size();
		
		List<CtMethod> lsMD = metaModel.getElements(new TypeFilter(CtMethod.class));
		methoDs = lsMD.size();
		
		List<CtImport> lsIP = metaModel.getElements(new TypeFilter(CtImport.class));
		packagEs = lsIP.size();
	}
	
	public void showCLSFeatures(){
		
	}

}
