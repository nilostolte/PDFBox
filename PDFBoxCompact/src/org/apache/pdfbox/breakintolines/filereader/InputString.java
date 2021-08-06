package org.apache.pdfbox.breakintolines.filereader;

public class InputString implements Input {
	private int position;
	private String text;
	private String word;
	private boolean end;
	private int nblanks;
	
	public void setText(String text) {
		position = 0;
		this.text = text;
	}
	
	@Override
	public int nextword() {
		if ( !readword()) return -1;
		return word.length();
	}
	@Override
	public String getWord() {
		return word;
	}
	
	public boolean readword() {
		int i;
		nblanks = 0;
		for ( i = position; i < text.length() && text.charAt(i) == ' '; i++ );
		nblanks += i - position;
		position = i;
		for ( ; i < text.length() && text.charAt(i)!= ' '; i++);
		if ( end = (i >= text.length()) ) {
			return false;
		}
		word = text.substring(position, i);
		position = i;
		return true;
	}

	@Override
	public boolean end() {
		return end;
	}

	@Override
	public int getnblanks() {
		return nblanks;
	}
}
