package org.apache.pdfbox.breakintolines;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.breakintolines.filereader.Input;
import org.apache.pdfbox.breakintolines.filereader.InputFile;
import org.apache.pdfbox.breakintolines.filereader.InputString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.util.Matrix;

public class DocumentManager implements DocumentSettings {

	private PDDocument doc;
	private PDRectangle pagesize;
	//private ArrayList<PDPage> pages;
	PDPageContentStream currentpagestream;
	String outfile = "../output/org.apache.pdfbox.breakintolines.DocumentManager.Output.pdf";
	
	DocumentManager (PDRectangle pagedimesions) {
		doc = new PDDocument();
		pagesize = pagedimesions;
	}
	
	private PDPage getnewpage() {
		PDPage newpage = new PDPage(pagesize);
		//pages.add(newpage);
		doc.addPage(newpage);
		return newpage;
	}

	public void newpagestream() throws IOException {
		PDPage newpage = getnewpage();
		// currentpagestream = new PDPageContentStream(doc, newpage, AppendMode.OVERWRITE, false);
		currentpagestream = new PDPageContentStream(doc, newpage, AppendMode.OVERWRITE, true);
		currentpagestream.beginText();
	}
	
	public void closepagestream() throws IOException {
		currentpagestream.endText();
		currentpagestream.close();
		currentpagestream = null;
	}
	
	public void closedocument() throws IOException {
        doc.save(outfile);
        doc.close();
        //pages.clear();
	}
	
	public void setOutputFileName( String name) {
		outfile = name;
	}
	
	public float getPageWidth() {
		return pagesize.getWidth();
	}
	
	public float getPageHeight() {
		return pagesize.getHeight();
	}

	public PDFont loadfont(String filename) throws IOException {
		return PDType0Font.load(doc, new File(filename));
	}

	// read text document from file (UTF-8)
	InputFile infile = new InputFile();
	InputString instring = new InputString();
	Input in;
	
	public void setInputFile( String file ) throws IOException {
		infile.setFile(file);
		in = infile;
	}
	
	public void setInputString (String text) {
		instring.setText(text);
		in = instring;
	}
	
	//=============================================================================
	
	private float maxfontsize;
	
	public float getMaxfontsize() {
		return maxfontsize;
	}

	public void initializeCurrentfont(String filename, float font_size) throws IOException {
		PDFont f = loadfont(filename);
		initializeCurrentfont(f, font_size);
		maxfontsize = font_size;
	}
	
	private void initializeCurrentfont(PDFont currentfont, float font_size) {
		// function to initialize base font for single page breakintolines
		fonts[0] = currentfont;
		font_sizes[0] = font_size;
		nfonts = 1;
		this.currentfont = 0;
		this.convertionfont = 0;
	}
	
	public void addFont(String filename, float font_size) throws IOException {
		PDFont f = loadfont(filename);
		addFont(f, font_size);	
		if ( maxfontsize < font_size ) maxfontsize = font_size;
	}
	
	private void addFont(PDFont font, float font_size) {
		// function to add font for single page breakintolines
		if (nfonts < MAX_NFONTS) {
			fonts[nfonts] = font;
			font_sizes[nfonts++] = font_size;
		}
	}	
	
	public void changeCurrentFont(int i) {
		if (i >= nfonts) return;
		currentfont = i;
		return;
	}
	
	private void changeConvertionFont(int i) {
		if (i >= nfonts) return;
		convertionfont = i;
		return;
	}
	
	private int interpretCommand(String s, int i) {
		if ( s.charAt(i) <= '\007') {
			if (s.charAt(i) == '\000') {
				if ( i >= (s.length() - 1) ) return 1;
				changeConvertionFont(s.charAt(i+1));
				return 2;
			}
		}
		return 1;
	}
	
	// word conversion to Word
	
	private Word convert(String s) throws IOException {
		int i, b;
		int font;
		String w = "";
		if (s.length() == 1) {
			if (s.charAt(0) == '\n') { // paragraph break
				return null;
			}
		}
		// skip all characters that are not printable and interpret commands
		for ( i = 0; (i < s.length()) && (s.charAt(i) < ' ');  ) {
			i += interpretCommand(s,i);
		}
		font = convertionfont;
		if (i >= s.length()) return null;
		Word word = null;
		for ( w = "", b = i; i < s.length(); ) {
			// skip all characters that are not printable and interpret commands
			if (s.charAt(i) < ' ') {
				// are there any printable chars waiting?
				if ( b != i ) {
					w += s.substring(b, i);
					i += interpretCommand(s, i);
					// font changed, we must store subword
					if (font != convertionfont ) {
						if (word == null) word = new Word();
						word.add(font, w);
						font = convertionfont;
						w = "";
					}
					b = i;
					continue;
				}
				i += interpretCommand(s, i);
				b = i;
				continue;
			}
			i++;
		}
		if ( b != i ) {
			if (word == null) word = new Word();
			w += s.substring(b, i);
			word.add(font, w);
		}
		else if (w.length() != 0) {
			if (word == null) word = new Word();
			word.add(font, w);
		}
		return word;
	}
	
	
	ArrayList<Word> line = new ArrayList<Word>();
	
	Matrix m = new Matrix();
	
	public void writeline( float x, float y, float wordsep ) throws IOException {
		Word word;
		int i;
		float linewidth = 0f;
		if (line.size() == 0)  return;
        m.setValue(2, 0, x);
        m.setValue(2, 1, y);
		currentpagestream.setTextMatrix(m);
//		currentpagestream.newLineAtOffset(x, y);
		for ( i = 0; i < line.size(); ) {
			word = line.get(i);
			word.write();
			//linewidth = linewidth + word.getWidth() + wordsep;
			linewidth = word.getWidth() + wordsep;
//			m.setValue(2, 0, x + linewidth);
//			currentpagestream.setTextMatrix(m);
			i++;
			if (i >= line.size()) break;
			currentpagestream.newLineAtOffset(linewidth, 0);
		}
		line.clear();
	}
	
	String errorNoFontn = "** addFont() was not called: no font ";
	String errorNoFont = "** initializeCurrentfont() was not called: no fonts **";
	
	int nfonts;
	final int MAX_NFONTS = 255;
	PDFont[] fonts = new PDFont[MAX_NFONTS];
	float[] font_sizes = new float[MAX_NFONTS];
	int currentfont;
	int convertionfont;
	
	private class Word {
		float width;
		public float getWidth() {
			return width;
		}
		ArrayList<SubWord> text;
		Word() { 
			width = 0f;
			text = new ArrayList<SubWord>(1);
		}
		private void add(SubWord t) {
			text.add(t);
			width += t.width();
		}
		void add(int font, String w) throws IOException {
			add(new SubWord(font, w));
		}
		void write() throws IOException {
			for (SubWord w : text) {
				w.write();
			}
		}
		
		private class SubWord {
			float width;
			int font;
			String text;
			SubWord( int f, String t) throws IOException {
				font = f;
				text = t;
				if (fonts[currentfont] ==  null) throw new IOException(errorNoFontn + font + " **");
				width = (fonts[font].getStringWidth(t) / 1000f) * font_sizes[font];
			}
			public float width() {
				return width;
			}
			public void write() throws IOException {
				if (font != currentfont) {
					currentfont = font;
					if (fonts[currentfont] ==  null) throw new IOException(errorNoFontn + font + " **");
					currentpagestream.setFont(fonts[font], font_sizes[font]);
				}
				//currentpagestream.showText(text);
				currentpagestream.writeTextInbinary(text);
			}
		}
	}

	public void clearline() {
		line.clear();
	}

	public void setcurrentfont() throws IOException {
		if (fonts[currentfont] ==  null) throw new IOException(errorNoFont);
		currentpagestream.setFont(fonts[currentfont], font_sizes[currentfont]);
	}

	private Word nextword;
	
	public float nextword() throws IOException {
		if (in.nextword() == -1) return -1f;
		nextword = convert(in.getWord()); // convert to Word
		if ( nextword == null )  return 0f;
		return nextword.getWidth();
	}

	public void addnextwordtoline() {
		line.add(nextword);	
	}
	
	public boolean emptyline( ) {
		return line.isEmpty();
	}

	public boolean end() {
		return in.end();
	}
	
	public void writepagenumber(int page, String title, float borderwidth, float highy, int titlefont) throws IOException {
		String s = title+" - page "+page;
		float fontsize = font_sizes[titlefont];
		PDFont f = fonts[titlefont];
		currentpagestream.setFont(f, fontsize);
		float width = (f.getStringWidth(s) / 1000f) * fontsize;
        m.setValue(2, 0, pagesize.getWidth() - width - borderwidth);
        m.setValue(2, 1, pagesize.getHeight() - ((pagesize.getHeight() - highy)*0.5f));
		currentpagestream.setTextMatrix(m);
		currentpagestream.writeTextInbinary(s);
	}

}
