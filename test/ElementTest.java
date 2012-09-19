import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Test;

import ftanml.FtanML;
import ftanml.ParseException;
import ftanml.objects.FtanArray;
import ftanml.objects.FtanBoolean;
import ftanml.objects.FtanElement;
import ftanml.objects.FtanNull;
import ftanml.objects.FtanNumber;
import ftanml.objects.FtanString;
import ftanml.objects.FtanValue;


public class ElementTest {
	
	private FtanElement createElement(FtanValue...attributes) {
		LinkedHashMap<FtanString,FtanValue> values=new LinkedHashMap<FtanString,FtanValue>();
        for (int i = 0; i < attributes.length/2; i++) {
            values.put((FtanString)attributes[i*2],attributes[i*2+1]);
        }
        return new FtanElement(values);
    }
	
	private FtanArray createArray(FtanValue...elements) {
		ArrayList<FtanValue> values=new ArrayList<FtanValue>();
        for (int i = 0; i < elements.length; i++) {
            values.add(elements[i]);
        }
        return new FtanArray(values);
    }

	@Test
	public void testEqualsObject() {
		assertTrue(createElement().equals(createElement()));
		assertTrue(createElement().hashCode()==createElement().hashCode());
		assertTrue(createElement(new FtanString("bla"),new FtanString("value")).equals(createElement(new FtanString("bla"),new FtanString("value"))));
		assertTrue(createElement(new FtanString("bla"),new FtanString("value")).hashCode()==createElement(new FtanString("bla"),new FtanString("value")).hashCode());
		assertTrue(createElement(new FtanString("key"),new FtanNumber(2.34)).equals(createElement(new FtanString("key"),new FtanNumber(2.34))));
		assertTrue(createElement(new FtanString("key"),new FtanNumber(2.34)).hashCode()==createElement(new FtanString("key"),new FtanNumber(2.34)).hashCode());
		assertTrue(createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull()).equals(createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull())));
		assertTrue(createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull()).hashCode()==createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull()).hashCode());
		assertTrue(createElement(new FtanString("key"),new FtanNull()).equals(createElement(new FtanString("key"),new FtanNull())));
		assertTrue(createElement(new FtanString("key"),new FtanNull()).hashCode()==createElement(new FtanString("key"),new FtanNull()).hashCode());
		assertTrue(createElement(new FtanString("key2"),new FtanNull(),new FtanString("key3"),new FtanString("MeinName"),new FtanString("key4"),new FtanString("bla")).equals(createElement(new FtanString("key2"),new FtanNull(),new FtanString("key3"),new FtanString("MeinName"),new FtanString("key4"),new FtanString("bla"))));
		assertTrue(createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull(),FtanElement.NAME_KEY,new FtanString("MeinName"),FtanElement.CONTENT_KEY,createArray(new FtanString("before"),createElement(new FtanString("bla"),createArray()),new FtanString("after"))).equals(createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull(),FtanElement.NAME_KEY,new FtanString("MeinName"),FtanElement.CONTENT_KEY,createArray(new FtanString("before"),createElement(new FtanString("bla"),createArray()),new FtanString("after")))));
		assertTrue(createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull(),FtanElement.NAME_KEY,new FtanString("MeinName"),FtanElement.CONTENT_KEY,createArray(new FtanString("before"),createElement(new FtanString("bla"),createArray()),new FtanString("after"))).hashCode()==createElement(new FtanString("key"),new FtanBoolean(true),new FtanString("key2"),new FtanNull(),FtanElement.NAME_KEY,new FtanString("MeinName"),FtanElement.CONTENT_KEY,createArray(new FtanString("before"),createElement(new FtanString("bla"),createArray()),new FtanString("after"))).hashCode());
		assertFalse(createElement(new FtanString("bla"),new FtanBoolean(true)).equals(createElement(new FtanString("bla"),new FtanBoolean(false))));
		assertFalse(createElement(new FtanString("bla"),new FtanBoolean(true)).equals(createElement(new FtanString("blb"),new FtanBoolean(true))));
		assertFalse(createElement(new FtanString("bla"),new FtanBoolean(true),new FtanString("bla2"),new FtanNull()).equals(createElement(new FtanString("bla"),new FtanBoolean(true))));
		assertFalse(createElement(new FtanString("bla"),new FtanString("1")).equals(createElement(new FtanString("bla"),new FtanNumber(1.0))));
		assertFalse(createElement(new FtanString("bla"),new FtanString("1.0")).equals(createElement(new FtanString("bla"),new FtanNumber(1.0))));
	}

	@Test
	public void testGeneratorAndParser() throws ParseException{
		Util.testWithGenerator(createElement(),"<>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanString("")),"<\"\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("")),"<''>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanString("myTagName")),"<myTagName>");
		Util.testWithGenerator(createElement(FtanElement.CONTENT_KEY,createArray(new FtanString("myContent"))),"<|myContent>");
		Util.testWithGenerator(createElement(FtanElement.CONTENT_KEY,new FtanString("myContent")),"<content=\"myContent\">");
		Util.testWithGenerator(createElement(new FtanString("attr"),new FtanString("myValue")),"<attr=\"myValue\">");
		Util.test(createElement(new FtanString("attr"),new FtanString("myValue")),"<\"attr\"=\"myValue\">");
		Util.test(createElement(new FtanString("attr"),new FtanString("myValue")),"<'attr'=\"myValue\">");
		Util.test(createElement(new FtanString("attr"),new FtanString("myValue")),"<attr='myValue'>");
		Util.test(createElement(new FtanString("attr"),new FtanString("myValue")),"<\"attr\"='myValue'>");
		Util.test(createElement(new FtanString("attr"),new FtanString("myValue")),"<'attr'='myValue'>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<tagname attr=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<tagname \"attr\"=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<tagname 'attr'=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<tagname attr='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<tagname \"attr\"='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<tagname 'attr'='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<'tagname' attr=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<'tagname' \"attr\"=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<'tagname' 'attr'=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<'tagname' attr='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<'tagname' \"attr\"='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<'tagname' 'attr'='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<\"tagname\" attr=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<\"tagname\" \"attr\"=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<\"tagname\" 'attr'=\"myValue\">");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<\"tagname\" attr='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<\"tagname\" \"attr\"='myValue'>");
		Util.test(createElement(FtanElement.NAME_KEY,new FtanString("tagname"),new FtanString("attr"),new FtanString("myValue")),"<\"tagname\" 'attr'='myValue'>");
		Util.testWithGenerator(createElement(new FtanString("att'r"),new FtanString("myValue")),"<\"att'r\"=\"myValue\">");
		Util.testWithGenerator(createElement(new FtanString("att\"r"),new FtanString("myValue")),"<'att\"r'=\"myValue\">");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanString("a123:4_5"),FtanElement.CONTENT_KEY,createArray(new FtanString("myContent"),createElement(FtanElement.NAME_KEY,new FtanString("innerName")),new FtanString("afterContent"))),"<a123:4_5|myContent<innerName>afterContent>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanString("12"),FtanElement.CONTENT_KEY,createArray(new FtanString("myContent"),createElement(FtanElement.NAME_KEY,new FtanString("innerName")),new FtanString("afterContent"))),"<\"12\"|myContent<innerName>afterContent>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanString("1.2"),FtanElement.CONTENT_KEY,createArray(new FtanString("myContent"),createElement(FtanElement.NAME_KEY,new FtanString("innerName")),new FtanString("afterContent"))),"<\"1.2\"|myContent<innerName>afterContent>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanNumber(1.2),FtanElement.CONTENT_KEY,createArray(new FtanString("myContent"),createElement(FtanElement.NAME_KEY,new FtanString("innerName")),new FtanString("afterContent"))),"<name=1.2|myContent<innerName>afterContent>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY,new FtanString("a1:2"),FtanElement.CONTENT_KEY,createArray(new FtanString("myContent"),createElement(FtanElement.NAME_KEY,new FtanString("innerName")),new FtanNumber(1.0),new FtanString("afterContent"))),"<a1:2 content=[\"myContent\",<innerName>,1.0,\"afterContent\"]>");
		Util.testWithGenerator(createElement(new FtanString("attr1"),new FtanNumber(1.3),new FtanString("attr2"),new FtanNull(),FtanElement.NAME_KEY,new FtanString("1.2"),FtanElement.CONTENT_KEY,createArray(new FtanString("myContent"),createElement(FtanElement.NAME_KEY,new FtanString("innerName")),new FtanString("afterContent"))),"<\"1.2\" attr1=1.3 attr2=null|myContent<innerName>afterContent>");
		Util.testWithGenerator(createElement(new FtanString("attr1"),new FtanBoolean(false),new FtanString("attr2"),new FtanNumber(2.0)),"<attr1=false attr2=2.0>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY, new FtanString("outertag"),new FtanString("style"),new FtanString("outer"),FtanElement.CONTENT_KEY, createArray(new FtanString("start"),createElement(FtanElement.NAME_KEY, new FtanString("inner"),FtanElement.CONTENT_KEY, createArray(createElement(FtanElement.NAME_KEY, new FtanString("second"),	FtanElement.CONTENT_KEY, createArray(createElement(FtanElement.CONTENT_KEY, createArray (new FtanString("third"), createElement(FtanElement.CONTENT_KEY, createArray(createElement())))))),new FtanString("after"))))),"<outertag style=\"outer\"|start<inner|<second|<|third<|<>>>>after>>");
		Util.testWithGenerator(createElement(FtanElement.NAME_KEY, new FtanString("outer'tag"),new FtanString("style"),new FtanString("outer"),FtanElement.CONTENT_KEY, createArray(new FtanString("start"),createElement(FtanElement.NAME_KEY, new FtanString("inner"),FtanElement.CONTENT_KEY, createArray(createElement(FtanElement.NAME_KEY, new FtanString("second"), new FtanString("attr"),new FtanNull(), FtanElement.CONTENT_KEY, createArray(createElement(FtanElement.CONTENT_KEY, createArray (new FtanString("third"), createElement(FtanElement.CONTENT_KEY, createArray(createElement())))))),new FtanString("after"))))),"<\"outer'tag\" style=\"outer\"|start<inner|<second attr=null|<|third<|<>>>>after>>");
		Util.test(createElement(FtanElement.NAME_KEY, new FtanString("outer'tag"),new FtanString("style"),new FtanString("outer"),FtanElement.CONTENT_KEY, createArray(new FtanString("start"),createElement(FtanElement.NAME_KEY, new FtanString("inner"),FtanElement.CONTENT_KEY, createArray(createElement(FtanElement.NAME_KEY, new FtanString("second"), new FtanString("attr"),new FtanNull(), FtanElement.CONTENT_KEY, createArray(createElement(FtanElement.CONTENT_KEY, createArray (new FtanString("third"), createElement(FtanElement.CONTENT_KEY, createArray(createElement())))))),new FtanString("after"))))),"<'outer\\'tag' style='outer'|start<inner|<second attr=null|<content=['third',<content=[<>]>]>>after>>");
		Util.testWithGenerator(createElement(new FtanString("attr"),createArray(new FtanNull(),createElement(FtanElement.CONTENT_KEY,createArray(new FtanString("bla"),createElement(new FtanString("a"),createArray(createArray())))))),"<attr=[null,<|bla<a=[[]]>>]>");
		Util.testWithGenerator(createArray(new FtanString("bla"),createElement(new FtanString("attr"),createArray(new FtanNull(),createElement(FtanElement.CONTENT_KEY,createArray(new FtanString("bla"),createElement(new FtanString("a"),createArray(createArray())))))),new FtanString("bla2")),"[\"bla\",<attr=[null,<|bla<a=[[]]>>]>,\"bla2\"]");
	}
	
	@Test
	public void testAttributeOrderPreserving() {
		Util.testWithGenerator(createElement(new FtanString("a"),new FtanNumber(1.0),new FtanString("b"),new FtanNumber(2.0)),"<a=1.0 b=2.0>");
		Util.testWithGenerator(createElement(new FtanString("a"),new FtanNumber(2.0),new FtanString("b"),new FtanNumber(1.0)),"<a=2.0 b=1.0>");
		Util.testWithGenerator(createElement(new FtanString("b"),new FtanNumber(1.0),new FtanString("a"),new FtanNumber(2.0)),"<b=1.0 a=2.0>");
		Util.testWithGenerator(createElement(new FtanString("b"),new FtanNumber(2.0),new FtanString("a"),new FtanNumber(1.0)),"<b=2.0 a=1.0>");
	}
	
}
