package cn.edu.whu.cstar.typer;

import org.junit.*;
import cn.edu.whu.sctar.typer.Typer;

public class TyperTest {

	@Test
	public void testArrayListAdding_0(){
		int[][] a = {{1,2,3}, {4,5,6}, {7,8,9}};
		int[][] sum = Typer.ArrayListAdding(a, a);
		int[][] orl = {{2,4,6}, {8,10,12}, {14,16,18}};
		Assert.assertArrayEquals(orl, sum);		
	}
	
	@Test
	public void testArrayListAdding_1(){
		int[][] a = {{1,2,3}, {4,5,6}, {7,8,9}};
		int[][] b = {{1,2,2}, {4,5,2}, {7,8,2}};
		int[][] sum = Typer.ArrayListAdding(a, b);
		int[][] orl = {{2,4,5}, {8,10,8}, {14,16,11}};
		Assert.assertArrayEquals(orl, sum);		
	}
	
	@Test
	public void testArrayListAdding_2(){
		int[][] a = {{1,2,3}, {4,5,6}, {7,8,9}};
		int[][] b = {{1,2,2}, {4,5,2}};
		int[][] sum = Typer.ArrayListAdding(a, b);
		int[][] orl = null;
		Assert.assertArrayEquals(orl, sum);		
	}
	
	@Test
	public void testArrayListAdding_3(){
		int[][] a = {{1,2,3}, {4,5,6}, {7,8,9}};
		int[][] b = null;
		int[][] sum = Typer.ArrayListAdding(a, b);
		int[][] orl = null;
		Assert.assertArrayEquals(orl, sum);	
	}
	
}
