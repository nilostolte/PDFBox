package org.apache.pdfbox.breakintolines.filereader;

public interface Input {
	public int nextword();
	public String getWord();
	public int getnblanks();
	public boolean end();
}
