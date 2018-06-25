package cn.edu.whu.cstar.timer;

import org.junit.*;

public class CLSAnalyzerTest {
	
	CLSAnalyzer alser;

	@Test
	public void testgetInherited_0(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.net.BCodec");
		int isherited = alser.getInherited();
		Assert.assertEquals(0, isherited);
	}
	
	@Test
	public void testgetInherited_1(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.B64");
		int isherited = alser.getInherited();
		Assert.assertEquals(1, isherited);
	}
	
	@Test
	public void testgetInherited_2(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.DigestUtils");
		int isherited = alser.getInherited();
		Assert.assertEquals(1, isherited);
	}
	
	@Test
	public void testgetFields_0(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.net.BCodec");
		int fields = alser.getFields();
		Assert.assertEquals(1, fields);
	} 
	
	@Test
	public void testgetFields_1(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.B64");
		int fields = alser.getFields();
		Assert.assertEquals(1, fields);
	} 
	
	@Test
	public void testgetFields_2(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.DigestUtils");
		int fields = alser.getFields();
		Assert.assertEquals(1, fields);
	}
	
	@Test
	public void testgetLocalVariables_0(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.net.BCodec");
		int fields = alser.getLocalVariables();
		Assert.assertEquals(0, fields);
	} 
	
	@Test
	public void testgetLocalVariables_1(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.B64");
		int fields = alser.getLocalVariables();
		Assert.assertEquals(4, fields);
	} 
	
	@Test
	public void testgetLocalVariables_2(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.DigestUtils");
		int fields = alser.getLocalVariables();
		Assert.assertEquals(2, fields);
	}
	
	@Test
	public void testgetMethods_0(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.net.BCodec");
		int fields = alser.getMethods();
		Assert.assertEquals(11, fields);
	} 
	
	@Test
	public void testgetMethods_1(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.DigestUtils");
		int fields = alser.getMethods();
		Assert.assertEquals(54, fields);
	} 
	
	@Test
	public void testgetMethods_2(){
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.B64");
		int fields = alser.getMethods();
		Assert.assertEquals(2, fields);
	}
	
	@Test
	public void testgetComments_0() throws Exception{
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.net.BCodec");
		int fields = alser.getComments();
		Assert.assertEquals(128, fields);
	} 
	
	@Test
	public void testgetComments_1() throws Exception{
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.B64");
		int fields = alser.getComments();
		Assert.assertEquals(53, fields);
	} 
	
	@Test
	public void testgetComments_2() throws Exception{
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.DigestUtils");
		int fields = alser.getComments();
		Assert.assertEquals(567, fields);
	}
	
	@Test
	public void testgetImports_0() throws Exception{
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.net.BCodec");
		String fullpath = "src/main/resources/projs/Codec_parent/src/main/java/org/apache/commons/codec/net/BCodec.java";
		int fields = alser.getImports(fullpath);
		Assert.assertEquals(8, fields);
	} 
	
	@Test
	public void testgetImports_1() throws Exception{
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.B64");
		String fullpath = "src/main/resources/projs/Codec_parent/src/main/java/org/apache/commons/codec/digest/B64.java";
		int fields = alser.getImports(fullpath);
		Assert.assertEquals(1, fields);
	} 
	
	@Test
	public void testgetImports_2() throws Exception{
		alser = new CLSAnalyzer("src/main/resources/projs/Codec_parent/", "org.apache.commons.codec.digest.DigestUtils");
		String fullpath = "src/main/resources/projs/Codec_parent/src/main/java/org/apache/commons/codec/digest/DigestUtils.java";
		int fields = alser.getImports(fullpath);
		Assert.assertEquals(6, fields);
	}
	
	@Test
	public void testClassName_0(){
		alser = new CLSAnalyzer("src/main/resources/projs/Collection_4.1_parent/", "org.apache.commons.collections4.trie.AbstractPatriciaTrie$EntrySet$EntryIterator");		
		String QClassName = alser.getCtClass().getQualifiedName();
		String SClassName = alser.getCtClass().getSimpleName();
		Assert.assertEquals("org.apache.commons.collections4.trie.AbstractPatriciaTrie$EntrySet$EntryIterator", QClassName);
		Assert.assertEquals("EntryIterator", SClassName);
	}
	
	@Test
	public void testClassName_1(){
		alser = new CLSAnalyzer("src/main/resources/projs/Collection_4.1_parent/", "org.apache.commons.collections4.trie.AbstractPatriciaTrie");		
		String QClassName = alser.getCtClass().getQualifiedName();
		String SClassName = alser.getCtClass().getSimpleName();
		Assert.assertEquals("org.apache.commons.collections4.trie.AbstractPatriciaTrie", QClassName);
		Assert.assertEquals("AbstractPatriciaTrie", SClassName);
	}
}
