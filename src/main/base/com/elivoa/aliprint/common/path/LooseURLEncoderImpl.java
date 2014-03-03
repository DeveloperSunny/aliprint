package com.elivoa.aliprint.common.path;

import java.util.BitSet;

import org.apache.tapestry5.internal.services.URLEncoderImpl;

/**
 * LooseURLEncoderImpl is an implement of URLEncoderImpl that ignores space and
 * other UTF-8 characters in the url. Make the project as a normal project.
 * 
 * @TODO add selective path resolve.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Mar 5, 2012]
 */
public class LooseURLEncoderImpl extends URLEncoderImpl {
	static final String ENCODED_NULL = "$N";
	static final String ENCODED_BLANK = "$B";

	/**
	 * Bit set indicating which character are safe to pass through (when
	 * encoding or decoding) as-is. All other characters are encoded as a kind
	 * of unicode escape.
	 */
	private final BitSet safe = new BitSet(128);

	{
		markSafe("abcdefghijklmnopqrstuvwxyz");
		markSafe("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		markSafe("01234567890-_.:");
	}

	private void markSafe(String s) {
		for (char ch : s.toCharArray()) {
			safe.set((int) ch);
		}
	}

	public String encode(String input) {
		return super.encode(input);
	}

	public String decode(String input) {
		assert input != null;

		if (input.equals(ENCODED_NULL))
			return null;

		if (input.equals(ENCODED_BLANK))
			return "";

		boolean dirty = false;

		int length = input.length();

		StringBuilder output = new StringBuilder(length * 2);

		for (int i = 0; i < length; i++) {
			char ch = input.charAt(i);

			if (ch == '$') {
				dirty = true;

				if (i + 1 < length && input.charAt(i + 1) == '$') {
					output.append('$');
					i++;

					dirty = true;
					continue;
				}

				if (i + 4 < length) {
					String hex = input.substring(i + 1, i + 5);

					try {
						int unicode = Integer.parseInt(hex, 16);

						output.append((char) unicode);
						i += 4;
						dirty = true;
						continue;
					} catch (NumberFormatException ex) {
						// Ignore.
					}
				}

				throw new IllegalArgumentException(
						String.format(
								"Input string '%s' is not valid; the '$' character at position %d should be followed by another '$' or a four digit hex number (a unicode value).",
								input, i + 1));
			}

			if (!safe.get((int) ch)) {

				// System.out.println(String.format("[IgnoredException]: Input string '%s' is not valid;"
				// + " the character '%s' at position %d is not valid.", input,
				// ch, i + 1));

				// throw new IllegalArgumentException(String.format(
				// "Input string '%s' is not valid; the character '%s' at position %d is not valid.",
				// input, ch,
				// i + 1));
			}

			output.append(ch);
		}

		return dirty ? output.toString() : input;
	}
}
