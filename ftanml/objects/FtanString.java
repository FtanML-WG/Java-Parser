package ftanml.objects;

import java.io.IOException;
import java.io.Writer;

public class FtanString extends FtanValue {
	private String value;

	public FtanString(String value) {
		this.value = value;
	}

	public static char deescapeChar(String input) {
		if (input.length() == 0)
			// TODO Correct exception class
			throw new IllegalStateException("Empty char can't be deescaped");

		if (input.charAt(0) == '\\') {
			// Parse default escape sequences
			if (input.length() == 2)
				switch (input.charAt(1)) {
				case '"':
				case '\'':
				case '\\':
				case '/':
					return input.charAt(1);
				case 'b':
					return '\b';
				case 'f':
					return '\f';
				case 'n':
					return '\n';
				case 'r':
					return '\r';
				case 't':
					return '\t';
				case '<':
					return '<';
				case '>':
					return '>';
				default:
					// TODO Correct exception class
					throw new IllegalStateException("Unknown escape sequence "
							+ input);
				}
			else if (input.length() == 6 && input.charAt(1) == 'u') {
				// Parse unicode escape sequence
				return (char) Integer.parseInt(input.substring(2, 6), 16);
			} else
				// TODO Correct exception class
				throw new IllegalStateException("Unknown escape sequence "
						+ input);
		} else if (input.length() == 1)
			// This isn't an escape sequence, pass the character through
			return input.charAt(0);
		else
			// TODO Correct exception class
			throw new IllegalStateException(
					"Multi-Character token found, but isn't an escape sequence");
	}

	private static String escapeChar(char input, char usedQuote) {
		if (input == usedQuote)
			return "\\" + usedQuote;

		switch (input) {
		case '\\':
			return "\\\\";
		case '\b':
			return "\\b";
		case '\f':
			return "\\f";
		case '\n':
			return "\\n";
		case '\r':
			return "\\r";
		case '\t':
			return "\\t";
		default:
			// TODO Allow the user to set a flag which generates a \\uXXXX
			// sequence for non standard characters
			return "" + input;
		}
	}

	private static String escape(String value, char usedQuote) {
		String result = new String();
		for (int i = 0; i < value.length(); ++i) {
			result = result + escapeChar(value.charAt(i), usedQuote);
		}
		return result;
	}

	@Override
	public void toFtanML(Writer writer) {
		try {
			//Calculate count of double quotation marks (") and single quotation marks (') in the string
			int numberDQuotes = 0;
			int numberSQuotes = 0;
			for (int i=0;i<value.length();++i) {
				switch (value.charAt(i)) {
				case '"':
					++numberDQuotes;
					break;
				case '\'':
					++numberSQuotes;
					break;
				}
			}
			//Use the quotation mark that causes less escaping
			char usedQuote = (numberDQuotes<=numberSQuotes)?'"':'\'';
			//Output the escaped string
			writer.append(usedQuote + escape(value, usedQuote) + usedQuote);
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private boolean isValidName() {
		return value.matches("[\\p{Alpha}][\\p{Alpha}\\p{Digit}_:]*");
	}

	@Override
	public void toFtanMLName(Writer writer) {
		try {
			if (isValidName())
				writer.append(value);
			else
				toFtanML(writer);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private String escapeContent(String input) {
		return input.replaceAll("\\\\", "\\\\\\\\").replaceAll(">", "\\\\>")
				.replaceAll("\\<", "\\\\<");
	}

	public void toFtanMLContent(Writer writer) {
		try {
			writer.append(escapeContent(value));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean equals(Object rhs) {
		if (!(rhs instanceof FtanString))
			return false;
		else {
			FtanString rhs_ = (FtanString) rhs;
			return rhs_.value.equals(value);
		}
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
