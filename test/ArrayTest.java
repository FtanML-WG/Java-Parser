import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import ftanml.objects.FtanArray;
import ftanml.objects.FtanBoolean;
import ftanml.objects.FtanNull;
import ftanml.objects.FtanNumber;
import ftanml.objects.FtanString;
import ftanml.objects.FtanValue;


public class ArrayTest {
	
	private FtanArray createArray(FtanValue...elements) {
		ArrayList<FtanValue> values=new ArrayList<FtanValue>();
        for (int i = 0; i < elements.length; i++) {
            values.add(elements[i]);
        }
        return new FtanArray(values);
    }

	@Test
	public void testEqualsObject() {
		assertTrue(createArray().equals(createArray()));
		assertTrue(createArray().hashCode()==createArray().hashCode());
		assertTrue(createArray(new FtanString("bla")).equals(createArray(new FtanString("bla"))));
		assertTrue(createArray(new FtanString("bla")).hashCode()==createArray(new FtanString("bla")).hashCode());
		assertTrue(createArray(new FtanNumber(2.34)).equals(createArray(new FtanNumber(2.34))));
		assertTrue(createArray(new FtanNumber(2.34)).hashCode()==createArray(new FtanNumber(2.34)).hashCode());
		assertTrue(createArray(new FtanBoolean(true),new FtanNull()).equals(createArray(new FtanBoolean(true),new FtanNull())));
		assertTrue(createArray(new FtanBoolean(true),new FtanNull()).hashCode()==createArray(new FtanBoolean(true),new FtanNull()).hashCode());
		assertFalse(createArray(new FtanBoolean(true),new FtanNull()).equals(createArray(new FtanBoolean(true))));
		assertFalse(createArray(new FtanBoolean(true),new FtanNull()).equals(createArray(new FtanBoolean(false),new FtanNull())));
		assertFalse(createArray(new FtanString("1")).equals(createArray(new FtanNumber(1.0))));
		assertFalse(createArray(new FtanString("1.0")).equals(createArray(new FtanNumber(1.0))));
	}

	@Test
	public void testGeneratorAndParser() {
		Util.testWithGenerator(createArray(),"[]");
		Util.testWithGenerator(createArray(new FtanString("bla")), "[\"bla\"]");
		Util.testWithGenerator(createArray(new FtanNumber(2.34)), "[2.34]");
		Util.test(createArray(new FtanNumber(2.34),new FtanNumber(23.4)),"[2.34,2.34e1]");
		Util.testWithGenerator(createArray(new FtanNumber(1.23),new FtanString("bl\"a"),new FtanBoolean(false),new FtanNull(),createArray(new FtanNumber(1.0),new FtanNull())),"[1.23,'bl\"a',false,null,[1.0,null]]");
		Util.test(createArray(new FtanNumber(1.23),new FtanString("bl\"a"),new FtanBoolean(false),new FtanNull(),createArray(new FtanNumber(1.0),new FtanNull())),"[12.3e-1,\"bl\\\"a\",false,null,[1e+0,null]]");
		Util.testWithGenerator(createArray(createArray(createArray(new FtanNumber(1.2)))),"[[[1.2]]]");
		Util.testWithGenerator(createArray(createArray(createArray()),createArray()),"[[[]],[]]");
		Util.test(createArray(createArray(createArray()),createArray())," [ [ [ ] ] , [ ] ] ");
		Util.testWithGenerator(createArray(new FtanNull(),createArray(new FtanNumber(0.1),createArray(new FtanString("0.2")),new FtanBoolean(true)),new FtanBoolean(false),createArray(new FtanNumber(0.5)),new FtanNumber(0.6)),"[null,[0.1,[\"0.2\"],true],false,[0.5],0.6]");
		Util.test(createArray(new FtanNull(),createArray(new FtanNumber(0.1),createArray(new FtanString("0.2")),new FtanBoolean(true)),new FtanBoolean(false),createArray(new FtanNumber(0.5)),new FtanNumber(0.6)),"[null,[1e-1,['0.2'],true],false,[0.05e1],0.06E+1]");
	}
	
}
