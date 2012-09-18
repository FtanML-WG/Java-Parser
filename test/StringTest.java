import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Test;

import ftanml.objects.FtanArray;
import ftanml.objects.FtanElement;
import ftanml.objects.FtanString;
import ftanml.objects.FtanValue;


public class StringTest {

	@Test
	public void testEqualsObject() {
		assertTrue((new FtanString("")).equals(new FtanString("")));
		assertTrue(new FtanString("").hashCode()==new FtanString("").hashCode());
		assertTrue((new FtanString("blabla")).equals(new FtanString("blabla")));
		assertTrue(new FtanString("blabla").hashCode()==new FtanString("blabla").hashCode());
		assertFalse((new FtanString("")).equals(new FtanString("bla")));
		assertFalse((new FtanString("bla")).equals(new FtanString("bl")));
	}
	
	private void testWithGenerator(String value, String ftanML) {
		Util.testWithGenerator(new FtanString(value),ftanML);
	}
	
	private void test(String value, String ftanML) {
		Util.test(new FtanString(value),ftanML);
	}

	@Test
	public void testGeneratorAndParser() {
		testWithGenerator("bla","\"bla\"");
		testWithGenerator("bla\"","'bla\"'");
		testWithGenerator("bla'","\"bla'\"");
		testWithGenerator("<>|\"'\\\b\f\n\r\t€","\"<>|\\\"'\\\\\\b\\f\\n\\r\\t€\"");
		test("bla","'bla'");
		test("bla\"","\"bla\\\"\"");
		test("bla'","\"bla\\'\"");
		test("bla\"","'bla\\\"'");
		test("bla'","'bla\\''");
		test("<>|\"'\\\b\f\n\r\t€/'><€€€€","\"<>|\\\"'\\\\\\b\\f\\n\\r\\t€\\/\\'\\>\\<\\u20ac\\u20aC\\u20Ac\\u20AC\"");
		test("<>|'\"\\\b\f\n\r\t€/\"><€€€€","'<>|\\\'\"\\\\\\b\\f\\n\\r\\t€\\/\\\"\\>\\<\\u20ac\\u20aC\\u20Ac\\u20AC'");
	}
	
	private FtanElement generateNameElement(String name) {
		LinkedHashMap<FtanString,FtanValue> attributes=new LinkedHashMap<FtanString,FtanValue>();
		attributes.put(FtanElement.NAME_KEY, new FtanString(name));
		return new FtanElement(attributes);
	}
	
	private FtanElement generateContentElement(String content) {
		ArrayList<FtanValue> contentattr = new ArrayList<FtanValue>();
		contentattr.add(new FtanString(content));
		LinkedHashMap<FtanString,FtanValue> attributes=new LinkedHashMap<FtanString,FtanValue>();
		attributes.put(FtanElement.CONTENT_KEY, new FtanArray(contentattr));
		return new FtanElement(attributes);
	}
	
	@Test
	public void testContentEscaping() {
		//Test recognizing content area
		Util.testWithGenerator(generateContentElement("bla"),"<|bla>");
		//Test characters that have to be escaped
		Util.testWithGenerator(generateContentElement("bla<\\>"),"<|bla\\<\\\\\\>>");
		//Test characters that may be escaped optionally
		Util.test(generateContentElement("<>|'\"\\\b\f\n\r\t€/\"><€€€€'"),"<|\\<\\>|\\\'\"\\\\\\b\\f\\n\\r\\t€\\/\\\"\\>\\<\\u20ac\\u20aC\\u20Ac\\u20AC'>");
	}
	
	@Test
	public void testNameEscaping() {
		//Test recognizing content area
		Util.testWithGenerator(generateNameElement("bla"),"<bla>");
		//Test automatically adding of quotes when there are unallowed characters
		Util.testWithGenerator(generateNameElement("bla\""),"<'bla\"'>");
		Util.testWithGenerator(generateNameElement("bla'"),"<\"bla'\">");
		Util.testWithGenerator(generateNameElement("bla€"),"<\"bla€\">");
		Util.testWithGenerator(generateNameElement("b5lA_:"),"<b5lA_:>");
		//Test characters that have to be escaped
		Util.testWithGenerator(generateNameElement("<>|\"'\\\b\f\n\r\t€"),"<\"<>|\\\"'\\\\\\b\\f\\n\\r\\t€\">");
		//Test characters that may be escaped optionally
		Util.test(generateNameElement("<>|\"'\\\b\f\n\r\t€/'><€€€€"),"<\"<>|\\\"'\\\\\\b\\f\\n\\r\\t€\\/\\'\\>\\<\\u20ac\\u20aC\\u20Ac\\u20AC\">");
		Util.test(generateNameElement("<>|'\"\\\b\f\n\r\t€/\"><€€€€"),"<'<>|\\\'\"\\\\\\b\\f\\n\\r\\t€\\/\\\"\\>\\<\\u20ac\\u20aC\\u20Ac\\u20AC'>");
		Util.test(generateNameElement("<>|'\"\\\b\f\n\r\t€/\"><€€€€'"),"<'\\<\\>|\\\'\"\\\\\\b\\f\\n\\r\\t€\\/\\\"\\>\\<\\u20ac\\u20aC\\u20Ac\\u20AC\\''>");
	}

}
