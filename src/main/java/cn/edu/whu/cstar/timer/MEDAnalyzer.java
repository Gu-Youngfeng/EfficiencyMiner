package cn.edu.whu.cstar.timer;

import java.util.List;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTry;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.visitor.filter.TypeFilter;

public class MEDAnalyzer {

	public static void main(String[] args) {
		MEDAnalyzer iii = new MEDAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.Sha2Crypt", "sha256Crypt", 105);
		iii.showMEDFeatures();
	}
	
	/** CT07/CB07: LoC of the top/bottom function */
	int loc;
	/** CT08/CB08: Number of parameters in the top/bottom function */
	int params;
	/** CT09/CT09: Number of local variables in the top/bottom function */
	int locals;
	/** CT10:CB10: Number of if-statements in the top/bottom function */
	int ifs;
	/** CT11:CB11: Number of loops in the top/bottom function */
	int loops;
	/** CT12:CB12: Number of for statements in the top/bottom function */
	int fors;
	/** CT13:CB13: Number of for-each statements in the top/bottom function */
	int for_eachs;
	/** CT14:CB14: Number of while statements in the top/bottom function */
	int whiles;
	/** CT15/CB15: Number of do-while statements in the top/bottom function */
	int do_whiles;
	/** CT16/CB16: Number of try blocks in the top/bottom function */
	int try_blocks;
	/** CT17/CT17: Number of catch blocks in the top/bottom function */
	int catchs;
	/** CT18:CB18: Number of finally blocks in the top/bottom function */
	int finally_blocks;
	/** CT19:CB19: Number of assignment statements in the top/bottom function */
	int assignments;
	/** CT20:CB20: Number of method calls in the top/bottom function */
	int method_calls;
	/** CT21:CB21: Number of return statements in the top/bottom function */
	int returns;
	/** CT22:CB22: Number of unary operators in the top/bottom function */
	int unary_operators;
	/** CT23:CB23: Number of binary operators in the top/bottom function */
	int binary_operators;
	
	@SuppressWarnings("rawtypes")
	public int getLoc(CtMethod method){
		int count=0;
		int startLine = method.getBody().getPosition().getLine();
		int endLine = method.getBody().getPosition().getEndLine();
		count = endLine - startLine;
		System.out.println("[start]: " + startLine + ", [end]: " + endLine);
		return count;
	}
	@SuppressWarnings("rawtypes")
	public int getLoc(CtConstructor method){
		int count=0;
		int startLine = method.getBody().getPosition().getLine();
		int endLine = method.getBody().getPosition().getEndLine();
		count = endLine - startLine;
		System.out.println("[start]: " + startLine + ", [end]: " + endLine);
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getParams(CtMethod method){
		int count=0;
		List<CtParameter> lsParams = method.getElements(new TypeFilter(CtParameter.class));
		for(CtParameter param: lsParams){
			System.out.println("[param]: " + param.getSimpleName());
		}
		count = lsParams.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getParams(CtConstructor method){
		int count=0;
		List<CtParameter> lsParams = method.getElements(new TypeFilter(CtParameter.class));
		for(CtParameter param: lsParams){
			System.out.println("[param]: " + param.getSimpleName());
		}
		count = lsParams.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getLocals(CtMethod method){
		int count=0;
		List<CtLocalVariable> lsLocals = method.getElements(new TypeFilter(CtLocalVariable.class));
		for(CtLocalVariable param: lsLocals){
			System.out.println("[local variable]: " + param.getSimpleName());
		}
		count = lsLocals.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getLocals(CtConstructor method){
		int count=0;
		List<CtLocalVariable> lsLocals = method.getElements(new TypeFilter(CtLocalVariable.class));
		for(CtLocalVariable param: lsLocals){
			System.out.println("[local variable]: " + param.getSimpleName());
		}
		count = lsLocals.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getIfs(CtMethod method){
		int count=0;
		List<CtIf> lsIfs = method.getElements(new TypeFilter(CtIf.class));
		for(CtIf param: lsIfs){
			System.out.println("[if]: " + param.toString());
		}
		count = lsIfs.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getIfs(CtConstructor method){
		int count=0;
		List<CtIf> lsIfs = method.getElements(new TypeFilter(CtIf.class));
		for(CtIf param: lsIfs){
			System.out.println("[if]: " + param.toString());
		}
		count = lsIfs.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getLoops(CtMethod method){
		int count=0;
		List<CtLoop> lsLoops = method.getElements(new TypeFilter(CtLoop.class));
		for(CtLoop param: lsLoops){
			System.out.println("[loop]: " + param.toString());
		}
		count = lsLoops.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getLoops(CtConstructor method){
		int count=0;
		List<CtLoop> lsLoops = method.getElements(new TypeFilter(CtLoop.class));
		for(CtLoop param: lsLoops){
			System.out.println("[loop]: " + param.toString());
		}
		count = lsLoops.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getFors(CtMethod method){
		int count=0;
		List<CtFor> lsFors = method.getElements(new TypeFilter(CtFor.class));
		for(CtFor param: lsFors){
			System.out.println("[for]: " + param.toString());
		}
		count = lsFors.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getFors(CtConstructor method){
		int count=0;
		List<CtFor> lsFors = method.getElements(new TypeFilter(CtFor.class));
		for(CtFor param: lsFors){
			System.out.println("[for]: " + param.toString());
		}
		count = lsFors.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getForEachs(CtMethod method){
		int count=0;
		List<CtForEach> lsForEachs = method.getElements(new TypeFilter(CtForEach.class));
		for(CtForEach param: lsForEachs){
			System.out.println("[for each]: " + param.toString());
		}
		count = lsForEachs.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getForEachs(CtConstructor method){
		int count=0;
		List<CtForEach> lsForEachs = method.getElements(new TypeFilter(CtForEach.class));
		for(CtForEach param: lsForEachs){
			System.out.println("[for each]: " + param.toString());
		}
		count = lsForEachs.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getWhiles(CtMethod method){
		int count=0;
		List<CtWhile> lsWhiles = method.getElements(new TypeFilter(CtWhile.class));
		for(CtWhile param: lsWhiles){
			System.out.println("[while]: " + param.toString());
		}
		count = lsWhiles.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getWhiles(CtConstructor method){
		int count=0;
		List<CtWhile> lsWhiles = method.getElements(new TypeFilter(CtWhile.class));
		for(CtWhile param: lsWhiles){
			System.out.println("[while]: " + param.toString());
		}
		count = lsWhiles.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getDoWhiles(CtMethod method){
		int count=0;
		List<CtDo> lsDoWhiles = method.getElements(new TypeFilter(CtDo.class));
		for(CtDo param: lsDoWhiles){
			System.out.println("[do while]: " + param.toString());
		}
		count = lsDoWhiles.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getDoWhiles(CtConstructor method){
		int count=0;
		List<CtDo> lsDoWhiles = method.getElements(new TypeFilter(CtDo.class));
		for(CtDo param: lsDoWhiles){
			System.out.println("[do while]: " + param.toString());
		}
		count = lsDoWhiles.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getTryBlocks(CtMethod method){
		int count=0;
		List<CtTry> lsTryBlocks = method.getElements(new TypeFilter(CtTry.class));
		for(CtTry param: lsTryBlocks){
			System.out.println("[try block]: " + param.toString());
		}
		count = lsTryBlocks.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getTryBlocks(CtConstructor method){
		int count=0;
		List<CtTry> lsTryBlocks = method.getElements(new TypeFilter(CtTry.class));
		for(CtTry param: lsTryBlocks){
			System.out.println("[try block]: " + param.toString());
		}
		count = lsTryBlocks.size();
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCatchs(CtMethod method){
		int count=0;
		List<CtCatch> lsCatchs = method.getElements(new TypeFilter(CtCatch.class));
		for(CtCatch param: lsCatchs){
			System.out.println("[catch]: " + param.toString());
		}
		count = lsCatchs.size();
		return count;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCatchs(CtConstructor method){
		int count=0;
		List<CtCatch> lsCatchs = method.getElements(new TypeFilter(CtCatch.class));
		for(CtCatch param: lsCatchs){
			System.out.println("[catch]: " + param.toString());
		}
		count = lsCatchs.size();
		return count;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int getFinallys(CtMethod method){
		int count=0;
		for(CtStatement state: method.getBody().getStatements()){
			if(state.toString().contains("finally")){
				System.out.println("[fianlly block]: " + state.toString());
				count++;
			}
		}
		return count;
	}
	@SuppressWarnings({ "rawtypes" })
	public int getFinallys(CtConstructor method){
		int count=0;
		for(CtStatement state: method.getBody().getStatements()){
			if(state.toString().contains("finally")){
				System.out.println("[fianlly block]: " + state.toString());
				count++;
			}
		}
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getAssignments(CtMethod method){
		int count=0;
		List<CtAssignment> lsAssignments = method.getElements(new TypeFilter(CtAssignment.class));
		for(CtAssignment param: lsAssignments){
			System.out.println("[assignment]: " + param.toString());
		}
		count = lsAssignments.size();
		return count;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getAssignments(CtConstructor method){
		int count=0;
		List<CtAssignment> lsAssignments = method.getElements(new TypeFilter(CtAssignment.class));
		for(CtAssignment param: lsAssignments){
			System.out.println("[assignment]: " + param.toString());
		}
		count = lsAssignments.size();
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getInvocations(CtMethod method){
		int count=0;
		List<CtInvocation> lsInvocations = method.getElements(new TypeFilter(CtInvocation.class));
		for(CtInvocation param: lsInvocations){
			System.out.println("[invocation]: " + param.toString());
		}
		count = lsInvocations.size();
		return count;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getInvocations(CtConstructor method){
		int count=0;
		List<CtInvocation> lsInvocations = method.getElements(new TypeFilter(CtInvocation.class));
		for(CtInvocation param: lsInvocations){
			System.out.println("[invocation]: " + param.toString());
		}
		count = lsInvocations.size();
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getReturns(CtMethod method){
		int count=0;
		List<CtReturn> lsReturns = method.getElements(new TypeFilter(CtReturn.class));
		for(CtReturn param: lsReturns){
			System.out.println("[return]: " + param.toString());
		}
		count = lsReturns.size();
		return count;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getReturns(CtConstructor method){
		int count=0;
		List<CtReturn> lsReturns = method.getElements(new TypeFilter(CtReturn.class));
		for(CtReturn param: lsReturns){
			System.out.println("[return]: " + param.toString());
		}
		count = lsReturns.size();
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getUnaryOperators(CtMethod method){
		int count=0;
		List<CtUnaryOperator> lsuops = method.getElements(new TypeFilter(CtUnaryOperator.class));
		for(CtUnaryOperator param: lsuops){
			System.out.println("[unary operator]: " + param.toString());
		}
		count = lsuops.size();
		return count;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getUnaryOperators(CtConstructor method){
		int count=0;
		List<CtUnaryOperator> lsuops = method.getElements(new TypeFilter(CtUnaryOperator.class));
		for(CtUnaryOperator param: lsuops){
			System.out.println("[unary operator]: " + param.toString());
		}
		count = lsuops.size();
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getBinaryOperators(CtMethod method){
		int count=0;
		List<CtBinaryOperator> lsbops = method.getElements(new TypeFilter(CtBinaryOperator.class));
		for(CtBinaryOperator param: lsbops){
			System.out.println("[binary operator]: " + param.toString());
		}
		count = lsbops.size();
		return count;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getBinaryOperators(CtConstructor method){
		int count=0;
		List<CtBinaryOperator> lsbops = method.getElements(new TypeFilter(CtBinaryOperator.class));
		for(CtBinaryOperator param: lsbops){
			System.out.println("[binary operator]: " + param.toString());
		}
		count = lsbops.size();
		return count;
	}
	
	@SuppressWarnings("rawtypes")
	MEDAnalyzer(String proj, String clsName, String medName, int medLine){
		/** Building the meta model */
		String fullClass = proj + "src/main/java/" + clsName.replaceAll("\\.", "/") + ".java";		
		Launcher launcher = new Launcher();
		launcher.addInputResource(fullClass);
		launcher.getEnvironment().setCommentEnabled(true);
		CtModel metaModel = launcher.buildModel();
		
		/** Building the CtMethod */	
		String simpleClsName = clsName.split("\\.")[clsName.split("\\.").length-1];
		if(!simpleClsName.equals(medName)){ //CtMethod
			CtMethod method = getCtMethod(metaModel, clsName, medName, medLine);
			/** extract the features from method */
			extractFeatures(method);
		}else{
			CtConstructor constructor = getCtConstructor(metaModel, clsName, "<init>", medLine);
			/** extract the features from constructor */
			extractFeatures(constructor);
		}
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CtMethod getCtMethod(CtModel metaModel, String clsName, String medName, int medLine){
		
		List<CtMethod> lsMethods = metaModel.getElements(new TypeFilter(CtMethod.class));
		CtMethod methodModel = null; // the unique founded method
		
		for(CtMethod method: lsMethods){
			int startLine = method.getBody().getPosition().getLine();
			int endLine = method.getBody().getPosition().getEndLine();
			String simpleName = method.getSimpleName();
			
			if(simpleName.equals(medName) && (medLine >= startLine && medLine <= endLine)){
				methodModel = method;
//				System.out.println("method: " + method.getSignature());				
//				System.out.println("range : " + startLine + "," + endLine);
				break;
			}
		}
		
		if(methodModel == null){
			System.out.println("we cannot find method named " + medName + " at line " + medLine);
		}
		
		return methodModel;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CtConstructor getCtConstructor(CtModel metaModel, String clsName, String medName, int medLine){
		List<CtConstructor> lsConstructors = metaModel.getElements(new TypeFilter(CtConstructor.class));
		CtConstructor constructorModel = null; // the unique founded constructor

		for(CtConstructor method: lsConstructors){
			int startLine = method.getBody().getPosition().getLine();
			int endLine = method.getBody().getPosition().getEndLine();
			String simpleName = method.getSimpleName();
			
			if(simpleName.equals(medName) && (medLine >= startLine && medLine <= endLine)){
				constructorModel = method;
//				System.out.println("constructor: " + method.getSignature());				
//				System.out.println("range      : " + startLine + "," + endLine);
				break;
			}
		}
		
		if(constructorModel == null){
			System.out.println("we cannot find constructor named " + medName + " at line " + medLine);
		}
		
		return constructorModel;
	}
	
	public void showMEDFeatures(){
		System.out.println(loc + "," + params + "," + locals + "," + ifs + "," + loops + "," + fors
				+ "," + for_eachs + "," + whiles + "," + do_whiles + "," + try_blocks + "," + catchs
				+ "," + finally_blocks + "," + assignments + "," + method_calls + "," + returns
				+ "," + unary_operators + "," + binary_operators + ",");
		
	}
	
	/** extract the features from method */
	public void extractFeatures(CtMethod<?> method){
		loc = getLoc(method);
		params = getParams(method);
		locals = getLocals(method);
		ifs = getIfs(method);
		loops = getLoops(method);
		fors = getFors(method);
		for_eachs = getForEachs(method);
		whiles = getWhiles(method);
		do_whiles = getDoWhiles(method);
		try_blocks = getTryBlocks(method);
		catchs = getCatchs(method);
		finally_blocks = getFinallys(method);
		assignments = getAssignments(method);
		method_calls = getInvocations(method);
		returns = getReturns(method);
		unary_operators = getUnaryOperators(method);
		binary_operators = getBinaryOperators(method);
	}
	
	/** extract the features from constructor */
	public void extractFeatures(CtConstructor<?> method){
		loc = getLoc(method);
		params = getParams(method);
		locals = getLocals(method);
		ifs = getIfs(method);
		loops = getLoops(method);
		fors = getFors(method);
		for_eachs = getForEachs(method);
		whiles = getWhiles(method);
		do_whiles = getDoWhiles(method);
		try_blocks = getTryBlocks(method);
		catchs = getCatchs(method);
		finally_blocks = getFinallys(method);
		assignments = getAssignments(method);
		method_calls = getInvocations(method);
		returns = getReturns(method);
		unary_operators = getUnaryOperators(method);
		binary_operators = getBinaryOperators(method);
	}

}
