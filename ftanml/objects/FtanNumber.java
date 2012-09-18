package ftanml.objects;

import java.io.IOException;
import java.io.Writer;

public class FtanNumber extends FtanValue {
	private Double value;
	
	public FtanNumber(Double value) {
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
		if(!(rhs instanceof FtanNumber))
			return false;
		else {
			FtanNumber rhs_ = (FtanNumber)rhs;
			return rhs_.value.equals(value);
		}
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
