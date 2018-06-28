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
	
	public List<String> initilizeSingleCrash_2(){
		List<String> crash =  new ArrayList<String>();
		crash.add("--- com.j256.ormlite.dao.CreateOrUpdateTest::shouldStoreEntityWithStringIdAndLoadFromDbOnCreateOrUpdate");
		crash.add("java.lang.ClassCastException: java.lang.String cannot be cast to com.j256.ormlite.dao.CreateOrUpdateTest$EntityId");
		crash.add("	at com.j256.ormlite.dao.CreateOrUpdateTest$EntityIdType.javaToSqlArg(CreateOrUpdateTest.java:153)");
		crash.add("	at com.j256.ormlite.field.FieldType.convertJavaFieldToSqlArgValue(FieldType.java:652)");
		crash.add("	at com.j256.ormlite.stmt.StatementExecutor.ifExists(StatementExecutor.java:691)");
		crash.add("	at com.j256.ormlite.dao.BaseDaoImpl.idExists(BaseDaoImpl.java:946)");
		crash.add("	at com.j256.ormlite.dao.BaseDaoImpl.createOrUpdate(BaseDaoImpl.java:386)");
		crash.add("	at com.j256.ormlite.dao.CreateOrUpdateTest.shouldStoreEntityWithStringIdAndLoadFromDbOnCreateOrUpdate(CreateOrUpdateTest.java:49)");
		crash.add("MUTATIONID:<<com.j256.ormlite.field.DataPersisterManager,lookupForField ,85>>");
		
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
//		System.out.println(className);
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
	public void testgetTopLine_2(){
		List<String> lines = initilizeSingleCrash_2();
		CrashNode cn = new CrashNode(lines);
		String topLine = cn.getTopLine(lines);
		Assert.assertEquals("	at com.j256.ormlite.field.FieldType.convertJavaFieldToSqlArgValue(FieldType.java:652)", topLine);
	}
	
	@Test
	public void testgetBottomLine_0(){
		List<String> lines = initilizeSingleCrash();
		CrashNode cn = new CrashNode(lines);
		String bottomLine = cn.getBottomLine(lines);
		Assert.assertEquals("	at org.apache.commons.codec.digest.Crypt.crypt(Crypt.java:149)", bottomLine);
	}

	@Test
	public void testgetBottomLine_2(){
		List<String> lines = initilizeSingleCrash_2();
		CrashNode cn = new CrashNode(lines);
		String bottomLine = cn.getBottomLine(lines);
//		System.out.println(">>>> " + bottomLine);
		Assert.assertEquals("	at com.j256.ormlite.dao.BaseDaoImpl.createOrUpdate(BaseDaoImpl.java:386)", bottomLine);
	}
	
	@Test
	public void testgetExceptionName_0(){
		String line = "java.lang.IllegalArgumentException: Negative initial size: 1024";
		String eName = CrashNode.getExceptionName(line);
//		System.out.println(">> " + eName);
		Assert.assertEquals("IllegalArgumentException", eName);
	}
	
	@Test
	public void testgetExceptionType_0(){
		int type = CrashNode.getExceptionType("IllegalArgumentException");
		Assert.assertEquals(11, type);		
	}
	
	@Test
	public void testgetExceptionName_1(){
		String line = "java.lang.ArrayIndexOutOfBoundsException: 3";
		String eName = CrashNode.getExceptionName(line);
//		System.out.println(">> " + eName);
		Assert.assertEquals("ArrayIndexOutOfBoundsException", eName);
	}
	
	@Test
	public void testgetExceptionType_1(){
		int type = CrashNode.getExceptionType("ArrayIndexOutOfBoundsException");
		Assert.assertEquals(14, type);		
	}
	
	@Test
	public void testgetExceptionName_2(){
		String line = "java.io.IOException: File 'E:\\Datasets\\Commons-io-2.5_mutant\\Commons-io-2.5_mutant_FileUtils_18\\test\\io\\lines.txt' cannot be read";
		String eName = CrashNode.getExceptionName(line);
//		System.out.println(">> " + eName);
		Assert.assertEquals("IOException", eName);
	}
	
	@Test
	public void testgetExceptionType_2(){
		int type = CrashNode.getExceptionType("IOException");
		Assert.assertEquals(21, type);		
	}
	
	@Test
	public void testgetExceptionName_3(){
		String line = "java.lang.NullPointerException";
		String eName = CrashNode.getExceptionName(line);
//		System.out.println(">> " + eName);
		Assert.assertEquals("NullPointerException", eName);
	}
	
	@Test
	public void testgetExceptionType_3(){
		int type = CrashNode.getExceptionType("NullPointerException");
		Assert.assertEquals(16, type);		
	}
	
	@Test
	public void testgetClassNum_0(){
		List<String> lines = initilizeSingleCrash();
		CrashNode cn = new CrashNode(lines);
		int numCls = cn.getClassNum(lines);
		Assert.assertEquals(2, numCls);
	}
	
	@Test
	public void testgetClassNum_2(){
		List<String> lines = initilizeSingleCrash_2();
		CrashNode cn = new CrashNode(lines);
		int numCls = cn.getClassNum(lines);
		Assert.assertEquals(3, numCls);
	}
	
	@Test
	public void testgetMethodNum_0(){
		List<String> lines = initilizeSingleCrash();
		CrashNode cn = new CrashNode(lines);
		int numMed = cn.getMethodNum(lines);
		Assert.assertEquals(3, numMed);
	}
	
	@Test
	public void testgetMethodNum_2(){
		List<String> lines = initilizeSingleCrash_2();
		CrashNode cn = new CrashNode(lines);
		int numMed = cn.getMethodNum(lines);
		Assert.assertEquals(4, numMed);
	}
	
	@Test
	public void testisOverLoaded_0(){
		List<String> lines = initilizeSingleCrash();
		CrashNode cn = new CrashNode(lines);
		Assert.assertTrue(cn.isOverLoaded(lines));
	}
	
	@Test
	public void testisOverLoaded_2(){
		List<String> lines = initilizeSingleCrash_2();
		CrashNode cn = new CrashNode(lines);
		Assert.assertFalse(cn.isOverLoaded(lines));
	}
}
