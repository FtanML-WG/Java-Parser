package ftanml.objects;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class FtanArray extends FtanValue {
	private List<FtanValue> values;
	
	public FtanArray(List<FtanValue> values) {
		this.values=values;
	}
	
	public void toFtanML(Writer writer) {
		try {
			writer.append("[");
			Iterator<FtanValue> iterator=values.iterator();
			if(iterator.hasNext()) {
				iterator.next().toFtanML(writer);
			
				while(iterator.hasNext()) {
					writer.append(",");
					iterator.next().toFtanML(writer);
				}
			}
			writer.append("]");
		} catch(IOException e)
		{
			throw new IllegalStateException(e);
		}
	}
	
	public void toFtanMLContent(Writer writer) {
		Iterator<FtanValue> iterator=values.iterator();
		while(iterator.hasNext()) {
			FtanValue next=iterator.next();
			if(next instanceof FtanString)
				((FtanString)next).toFtanMLContent(writer);
			else if(next instanceof FtanElement)
				((FtanElement)next).toFtanMLContent(writer);
			else
				//TODO Correct exception class
				throw new IllegalStateException("Given FtanArray isn't valid content for a FtanElement");
		}
	}
	
	/**
	 * Check, if this FtanArray can be output in the content area of a tag
	 * 
	 * @return True, if this FtanArray could be used in the content area of a tag
	 */
	public boolean isValidElementContent() {
		boolean isValid=true;
		for(FtanValue value: values) {
			if(!(value instanceof FtanString) && !(value instanceof FtanElement)) {
				isValid=false;
				break;
			}
		}
		return isValid;
	}
	
	@Override
	public boolean equals(Object rhs) {
		if(!(rhs instanceof FtanArray))
			return false;
		else {
			FtanArray rhs_ = (FtanArray)rhs;
			return rhs_.values.equals(values);
		}
	}
	
	@Override
	public int hashCode() {
		return values.hashCode();
	}

}
