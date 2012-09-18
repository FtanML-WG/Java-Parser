package ftanml.objects;

import java.io.StringWriter;
import java.io.Writer;

public abstract class FtanValue {
	public abstract void toFtanML(Writer writer);
	
	public String toFtanML() {
		StringWriter writer=new StringWriter();
		toFtanML(writer);
		return writer.toString();
	}
	
	protected void toFtanMLName(Writer writer) {
		toFtanML(writer);
	}
}
