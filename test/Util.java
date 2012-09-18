import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringReader;

import ftanml.FtanML;
import ftanml.ParseException;
import ftanml.objects.FtanValue;


public class Util {
	static void testWithGenerator(FtanValue value, String ftanML) {
		//Test generator
		assertTrue(value.toFtanML().equals(ftanML));
		//Test parser
		assertTrue(Util.parse(ftanML).equals(value));
		//Test parsing the generated result
		Util.genAndParse(value);
		//Test generating the parsed result
		Util.parseAndGen(ftanML);
	}
	
	static void test(FtanValue value, String ftanML) {
		//Test parser
		assertTrue(Util.parse(ftanML).equals(value));
		//Test parsing the generated result
		Util.genAndParse(value);
	}
	
	static void genAndParse(FtanValue value) {
		try {
			FtanML parser=new FtanML(new StringReader(value.toFtanML()));
			assertTrue(value.equals(parser.Value()));
		} catch(ParseException e) {
			fail(e.getMessage());
		}
	}
	
	static void parseAndGen(String input) {
		try {
			FtanML parser = new FtanML(new StringReader(input));
			FtanValue parsed = parser.Value();
			assertTrue(parsed.toFtanML().equals(input));
		} catch(ParseException e) {
			fail(e.getMessage());
		}
	}
	
	static FtanValue parse(String input) {
		try {
			FtanML parser = new FtanML(new StringReader(input));
			return parser.Value();
		} catch(ParseException e) {
			fail(e.getMessage());
			return null;
		}
	}
}
