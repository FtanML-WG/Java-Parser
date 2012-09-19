import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ftanml.objects.FtanBoolean;


public class BooleanTest {

	@Test
	public void testEqualsObject() {
		assertTrue(new FtanBoolean(true).equals(new FtanBoolean(true)));
		assertTrue(new FtanBoolean(false).equals(new FtanBoolean(false)));
		assertFalse(new FtanBoolean(true).equals(new FtanBoolean(false)));
		assertFalse(new FtanBoolean(false).equals(new FtanBoolean(true)));
		assertTrue(new FtanBoolean(true).hashCode()==new FtanBoolean(true).hashCode());
		assertTrue(new FtanBoolean(true).hashCode()!=new FtanBoolean(false).hashCode());
		assertTrue(new FtanBoolean(false).hashCode()!=new FtanBoolean(true).hashCode());
		assertTrue(new FtanBoolean(false).hashCode()==new FtanBoolean(false).hashCode());
	}

	@Test
	public void testGeneratorAndParser() {
		Util.testWithGenerator(new FtanBoolean(true),"true");
		Util.testWithGenerator(new FtanBoolean(false),"false");
	}
	
}
