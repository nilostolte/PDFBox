package org.apache.pdfbox.breakintolines;

import java.io.IOException;

public interface DocumentSettings {
	public void initializeCurrentfont(String filename, float font_size) throws IOException;
	public void addFont(String filename, float font_size) throws IOException;
	public void setInputString (String text);
	public void setInputFile( String file ) throws IOException;
}
