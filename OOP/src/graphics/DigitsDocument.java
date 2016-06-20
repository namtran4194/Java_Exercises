package graphics;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Chỉ nhập số thập phân trong JTextField
 * Sử dụng JTextField.setDocument(new DigitsDocument());
 * */
public class DigitsDocument extends PlainDocument {
	private static final long serialVersionUID = 1L;
	boolean dot = true;

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (str == null) {
			return;
		}
		char[] addedFigures = str.toCharArray();
		char c;
		for (int i = addedFigures.length; i > 0; i--) {
			c = addedFigures[i - 1];
			if (c == '.') {
				if (!dot) {
					dot = true;
					super.insertString(offs, new String(new Character(c).toString()), a);
				}
			} else if (Character.isDigit(c)) {
				super.insertString(offs, new String(new Character(c).toString()), a);
			}
		}
	}
}
