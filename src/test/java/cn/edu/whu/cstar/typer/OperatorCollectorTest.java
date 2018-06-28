package cn.edu.whu.cstar.typer;

import org.junit.*;

import cn.edu.whu.sctar.typer.OperatorCollector;

public class OperatorCollectorTest {

	@Test
	public void testgetInformsByFileMutLine_0(){
		String mutLine = "Collection_4.1_mutant_ArrayStack_11, org.apache.commons.collections4.ArrayStack, pop : 122 -> negated conditional (2)";
		String[] informs = OperatorCollector.getInformsByFileMutLine(mutLine);
		
		Assert.assertEquals("org.apache.commons.collections4.ArrayStack", informs[0]);
		Assert.assertEquals("pop", informs[1]);
		Assert.assertEquals("122", informs[2]);
		Assert.assertEquals("negated conditional (2)", informs[3]);
	}
	
	@Test
	public void testgetInformsByFileMutLine_1(){
		String mutLine = "Collection_4.1_mutant_ArrayStack_0, org.apache.commons.collections4.ArrayStack, empty : 78 -> replaced return of integer sized value with (x == 0 ? 1 : 0) (0)";
		String[] informs = OperatorCollector.getInformsByFileMutLine(mutLine);
		
		Assert.assertEquals("org.apache.commons.collections4.ArrayStack", informs[0]);
		Assert.assertEquals("empty", informs[1]);
		Assert.assertEquals("78", informs[2]);
		Assert.assertEquals("replaced return of integer sized value with (x == 0 ? 1 : 0) (0)", informs[3]);
	}
	
	@Test
	public void testgetOperatorID_0(){
		String mutDetails = "negated conditional (2)";
		int operator_id = OperatorCollector.getOperatorID(mutDetails);
		
		Assert.assertEquals(4, operator_id);
	}
	
	@Test
	public void testgetOperatorID_1(){
		String mutDetails = "replaced return of integer sized value with (x == 0 ? 1 : 0) (0)";
		int operator_id = OperatorCollector.getOperatorID(mutDetails);
		
		Assert.assertEquals(5, operator_id);
	}
}
