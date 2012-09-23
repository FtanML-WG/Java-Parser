package ftanml.objects;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents the object model for a FtanML element
 */

public class FtanElement extends FtanValue {
	private LinkedHashMap<FtanString, FtanValue> attributes;

	public FtanElement(LinkedHashMap<FtanString, FtanValue> attributes) {
		this.attributes = attributes;
	}
	
	static final public FtanString NAME_KEY=new FtanString("name");
	static final public FtanString CONTENT_KEY=new FtanString("content");

	public void toFtanML(Writer writer) {
		try {
			boolean space_needed = false;

			// Opening bracket
			writer.append("<");
			// Write name, if existing
			FtanValue name = attributes.get(NAME_KEY);
			if (name!=null && name instanceof FtanString) {
				name.toFtanMLName(writer);
				space_needed = true;
			}

			Iterator<Map.Entry<FtanString, FtanValue>> iterator = attributes
					.entrySet().iterator();
			// Write remaining attributes
			while (iterator.hasNext()) {
				Map.Entry<FtanString,FtanValue> current = iterator.next();
				//If there is a name, it already was output. Ignore it here.
				if (current.getKey().equals(NAME_KEY) && (current.getValue() instanceof FtanString))
					continue;
				//If there is valid content, it will be output later. Ignore it here
				if (current.getKey().equals(CONTENT_KEY) && (current.getValue() instanceof FtanArray) && ((FtanArray)current.getValue()).isValidElementContent())
					continue;
				
				if(space_needed)
					writer.append(" ");
				current.getKey().toFtanMLName(writer);
				writer.append('=');
				current.getValue().toFtanML(writer);
				space_needed=true;
			}
			
			//If there is valid content, write it
			FtanValue content=attributes.get(CONTENT_KEY);
			if(content!=null) {
				if(content instanceof FtanArray) {
					FtanArray content_=(FtanArray)content;
					if(content_.isValidElementContent()) {
						writer.append("|");
						content_.toFtanMLContent(writer);
					}
				}
			}

			// Closing bracket
			writer.append(">");

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public void toFtanMLContent(Writer writer) {
		toFtanML(writer);
	}
	
	@Override
	public boolean equals(Object rhs) {
		if(!(rhs instanceof FtanElement))
			return false;
		else {
			FtanElement rhs_ = (FtanElement)rhs;
			return rhs_.attributes.equals(attributes);
		}
	}
	
	@Override
	public int hashCode() {
		return attributes.hashCode();
	}
}
