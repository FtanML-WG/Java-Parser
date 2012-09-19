import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ftanml.objects.FtanNumber;


public class NumberTest {

	@Test
	public void testEqualsObject() {
		assertTrue(new FtanNumber(0.0).equals(new FtanNumber(0.0)));
		assertTrue(new FtanNumber(123.0).equals(new FtanNumber(123.0)));
		assertTrue(new FtanNumber(123.456).equals(new FtanNumber(123.456)));
		assertTrue(new FtanNumber(-123.0).equals(new FtanNumber(-123.0)));
		assertTrue(new FtanNumber(-123.456).equals(new FtanNumber(-123.456)));
		assertFalse(new FtanNumber(0.0).equals(new FtanNumber(0.1)));
		assertFalse(new FtanNumber(123.0).equals(new FtanNumber(123.456)));
		assertFalse(new FtanNumber(123.456).equals(new FtanNumber(123.654)));
		assertFalse(new FtanNumber(-123.0).equals(new FtanNumber(123.0)));
		assertFalse(new FtanNumber(-123.456).equals(new FtanNumber(123.456)));
		assertTrue(new FtanNumber(0.0).hashCode()==new FtanNumber(0.0).hashCode());
		assertTrue(new FtanNumber(123.0).hashCode()==new FtanNumber(123.0).hashCode());
		assertTrue(new FtanNumber(123.456).hashCode()==new FtanNumber(123.456).hashCode());
		assertTrue(new FtanNumber(-123.0).hashCode()==new FtanNumber(-123.0).hashCode());
		assertTrue(new FtanNumber(-123.456).hashCode()==new FtanNumber(-123.456).hashCode());
	}
	
	private void testWithGenerator(double value, String ftanML) {
		Util.testWithGenerator(new FtanNumber(value),ftanML);
	}
	
	private void test(double value, String ftanML) {
		Util.test(new FtanNumber(value),ftanML);
	}

	@Test
	public void testGeneratorAndParser() {
		testWithGenerator(0.0,"0.0");
		testWithGenerator(123.0,"123.0");
		testWithGenerator(123.456,"123.456");
		testWithGenerator(-123.0,"-123.0");
		testWithGenerator(-123.456,"-123.456");
		test(0.0,"0");
		test(123.0,"123");
		test(-123.0,"-123");
		test(0.0,"0e0");
		test(0.0,"0e-0");
		test(0.0,"0e+0");
		test(0.0,"0e100");
		test(0.0,"0e+100");
		test(0.0,"0e-100");
		test(123.456,"1.23456e2");
		test(123.456,"1.23456e+2");
		test(123.456,"12345.6e-2");
		test(1234560.0,"12345.6e2");
		test(1234560.0,"12345.6e+2");
		test(0.0,"0E0");
		test(0.0,"0E-0");
		test(0.0,"0E+0");
		test(0.0,"0E100");
		test(0.0,"0E+100");
		test(0.0,"0E-100");
		test(123.456,"1.23456E2");
		test(123.456,"1.23456E+2");
		test(123.456,"12345.6E-2");
		test(1234560.0,"12345.6E2");
		test(1234560.0,"12345.6E+2");
	}
	
}
