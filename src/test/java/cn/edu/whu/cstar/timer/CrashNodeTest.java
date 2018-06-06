package cn.edu.whu.cstar.timer;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

public class CrashNodeTest {
	
	String[] lines = {
			"	at org.apache.commons.codec.binary.BaseNCodec.encode(BaseNCodec.java:431)",
			"	at org.apache.commons.codec.binary.Base32InputStream.<init>(Base32InputStream.java:82)",
			"	at org.apache.commons.codec.binary.Base32InputStreamTest.testBase32InputStreamByteByByte(Base32InputStreamTest.java:245)",
			"	at java.security.MessageDigest$Delegate.engineUpdate(MessageDigest.java:584)"
	};
	
	public List<String> initilizeSingleCrash(){
		List<String> crash =  new ArrayList<String>();
		crash.add("--- org.apache.commons.codec.digest.UnixCryptTest::testUnixCryptStrings");
		crash.add("java.lang.StringIndexOutOfBoundsException: String index out of range: -1");
		crash.add("	at java.lang.AbstractStringBuilder.setCharAt(AbstractStringBuilder.java:407)");
		crash.add("	at java.lang.StringBuilder.setCharAt(StringBuilder.java:76)");
		crash.add("	at org.apache.commons.codec.digest.UnixCrypt.crypt(UnixCrypt.java:247)");
		crash.add("	at org.apache.commons.codec.digest.Crypt.crypt(Crypt.java:75)");
		crash.add("	at org.apache.commons.codec.digest.Crypt.crypt(Crypt.java:149)");
		crash.add("	at org.apache.commons.codec.digest.UnixCryptTest.testUnixCryptStrings(UnixCryptTest.java:37)");
		crash.add("MUTATIONID:<<org.apache.commons.codec.digest.UnixCrypt,crypt ,234>>");
		
		return crash;
	}	
	
	@Test
	public void testgetClassName_0(){
		String line = lines[0];
		String className = CrashNode.getClassName(line);
		Assert.assertEquals("org.apache.commons.codec.binary.BaseNCodec", className);
	}
	
	@Test
	public void testgetMethodName_0(){
		String line = lines[0];
		String methodName = CrashNode.getMethodName(line);
		Assert.assertEquals("encode", methodName);
	}
	
	@Test
	public void testgetMethodLine_0(){
		String line = lines[0];
		int methodLine = CrashNode.getMethodLine(line);
		Assert.assertEquals(431, methodLine);
	}
	
	@Test
	public void testgetClassName_1(){
		String line = lines[1];
		String className = CrashNode.getClassName(line);
		Assert.assertEquals("org.apache.commons.codec.binary.Base32InputStream", className);
	}
	
	@Test
	public void testgetMethodName_1(){
		String line = lines[1];
		String methodName = CrashNode.getMethodName(line);
		Assert.assertEquals("Base32InputStream", methodName);
	}
	
	@Test
	public void testgetMethodLine_1(){
		String line = lines[1];
		int methodLine = CrashNode.getMethodLine(line);
		Assert.assertEquals(82, methodLine);
	}
	
	@Test
	public void testgetClassName_2(){
		String line = lines[2];
		String className = CrashNode.getClassName(line);
		Assert.assertEquals("org.apache.commons.codec.binary.Base32InputStreamTest", className);
	}
	
	@Test
	public void testgetMethodName_2(){
		String line = lines[2];
		String methodName = CrashNode.getMethodName(line);
		Assert.assertEquals("testBase32InputStreamByteByByte", methodName);
	}
	
	@Test
	public void testgetMethodLine_2(){
		String line = lines[2];
		int methodLine = CrashNode.getMethodLine(line);
		Assert.assertEquals(245, methodLine);
	}
	
	@Test
	public void testgetClassName_3(){
		String line = lines[3];
		String className = CrashNode.getClassName(line);
		Assert.assertEquals("java.security.MessageDigest$Delegate", className);
	}
	
	@Test
	public void testgetMethodName_3(){
		String line = lines[3];
		String methodName = CrashNode.getMethodName(line);
		Assert.assertEquals("engineUpdate", methodName);
	}
	
	@Test
	public void testgetMethodLine_3(){
		String line = lines[3];
		int methodLine = CrashNode.getMethodLine(line);
		Assert.assertEquals(584, methodLine);
	}
	
	@Test
	public void testgetTopLine_0(){
		List<String> lines = initilizeSingleCrash();
		CrashNode cn = new CrashNode(lines);
		String topLine = cn.getTopLine(lines);
		Assert.assertEquals("	at org.apache.commons.codec.digest.UnixCrypt.crypt(UnixCrypt.java:247)", topLine);
	}
	
	@Test
	public void testgetBottomLine_0(){
		List<String> lines = initilizeSingleCrash();
		CrashNode cn = new CrashNode(lines);
		String bottomLine = cn.getBottomLine(lines);
		Assert.assertEquals("	at org.apache.commons.codec.digest.Crypt.crypt(Crypt.java:149)", bottomLine);
	}

}
