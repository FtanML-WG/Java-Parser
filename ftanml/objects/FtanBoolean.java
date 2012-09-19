package ftanml.objects;

import java.io.IOException;
import java.io.Writer;

public class FtanBoolean extends FtanValue {
	private Boolean value;
	
	public FtanBoolean(Boolean value) {
		this.value=value;
	}
	
	public void toFtanML(Writer writer) {
		try {
			writer.append(value.toString());
		} catch(IOException e)
		{
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public boolean equals(Object rhs) {
		if(!(rhs instanceof FtanBoolean))
			return false;
		else {
			FtanBoolean rhs_ = (FtanBoolean)rhs;
			return rhs_.value.equals(value);
		}
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
