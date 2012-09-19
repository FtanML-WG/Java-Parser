package ftanml.objects;

import java.io.IOException;
import java.io.Writer;

public class FtanNull extends FtanValue {
	
	public FtanNull() {
	}
	
	public void toFtanML(Writer writer) {
		try {
			writer.append("null");
		} catch(IOException e)
		{
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public boolean equals(Object rhs) {
		if(!(rhs instanceof FtanNull))
			return false;
		else
			return true;
	}
	
	@Override
	public int hashCode() {
		return new Integer(0).hashCode();
	}

}
