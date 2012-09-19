import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ftanml.objects.FtanNull;


public class NullTest {

	@Test
	public void testEqualsObject() {
		assertTrue(new FtanNull().equals(new FtanNull()));
		assertTrue(new FtanNull().hashCode()==new FtanNull().hashCode());
	}

	@Test
	public void testGeneratorAndParser() {
		Util.testWithGenerator(new FtanNull(),"null");
	}
	
}
