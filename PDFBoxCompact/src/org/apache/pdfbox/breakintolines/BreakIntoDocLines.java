package org.apache.pdfbox.breakintolines;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class BreakIntoDocLines {
	
	private PDRectangle pagesize;
	
	public void setPagesize(PDRectangle pagesize) {
		this.pagesize = pagesize;
		setAutoParagraphContext();
	}
	
	public DocumentManager getDocumentSettings() {
		return doc;
	}

	private DocumentManager doc;
	private String title = "";
	private int titlefont = 0;
	
	public BreakIntoDocLines(PDRectangle pagesize) throws IOException {
		setPagesize(pagesize);
		doc = new DocumentManager(pagesize);
		doc.newpagestream();
	}

	public void setlineheight(float h) {
		yinc = -h;
	}
	
	private void setAutoParagraphContext() {
		float pageWidth = pagesize.getWidth();
        float gap = pageWidth * 0.073f;
        float mid = pageWidth * 0.048f;
        parwidth = (pageWidth - ((gap * 2f) + mid))/2f;
        pagewidth = pageWidth;
        x = borderwidth = gap;
        // estimate fontsize equal to 10
        y = highy = pagesize.getHeight() - gap - 10f;
        yinc = -12f;
        minsp = 2f;
        ymin = midwidth = mid;
	}


	String errorFontFunc = "** wrong function call for adding/initializing font **";
	
	float parwidth = 380f;
	float yinc = 30f;
	float ypad = 0f;
	float minsp = 5f;
	float x = 0f;
	float y = 0f;
	float ymin = 20f;
	float borderwidth;
	float highy;
	float midwidth = 15f;
	private float pagewidth;
	
	int nlines;
	int page = 0;
	
	public void convert2book() throws IOException {
		int truncate = (int) (pagesize.getHeight() - borderwidth - doc.getMaxfontsize());
		y = highy = (float) truncate;
		breakintolines();
	}
	
	private boolean nextpage() throws IOException {
		if ( (x + parwidth) > pagewidth ) {
			// the paragraph overflows the page, try to change the page
			if ( !doc.end() ) {
				// close this page and start another
				page++;
				doc.closepagestream();
				doc.newpagestream();
				doc.writepagenumber(page, title, borderwidth, highy, titlefont);
				// we need to set font to continue to use it in new page 
				doc.setcurrentfont();
				// nothing changes except the position in the new page
				x = borderwidth;
				y = highy;
				return true;
			}
			// no more paragraphs, close document and leave
			doc.closepagestream();
			doc.closedocument();
			return false;
		}
		return true;
	}

	private void breakintolines() throws IOException {
		float wwidth = 0f;
		nlines = 0;
		float curwidth = 0f;
		int linesize = 0;
		doc.changeCurrentFont(0);
		doc.setcurrentfont();
		doc.writepagenumber(page, title, borderwidth, highy, titlefont);
		while ( (wwidth = doc.nextword()) != -1f ) {
			if (wwidth == 0f) { // is this a paragraph break 
				showline(minsp);
				if( !nextpage() ) return;
				curwidth = 0f;
				linesize = 0;
				continue;
			}
			if ( (curwidth + wwidth + minsp * linesize) > parwidth ) {
				showline((parwidth - curwidth) / (linesize - 1));
				if ( !nextpage() ) return;
				curwidth = 0f;
				linesize = 0;
			}
			curwidth += wwidth;
			linesize++;
			doc.addnextwordtoline();
		}
		// last line is always smaller than linewidth
		showline(minsp);
		doc.closepagestream();
		doc.closedocument();
	}

	private void showline( float wordsep ) throws IOException {
		if (y == highy && doc.emptyline()) return;
		doc.writeline(x, y, wordsep);
		y += yinc;
		nlines++;
		if (y <= ymin) {
			x += parwidth + midwidth;
			y = highy;
			return ;
		}
		return;
	}

	public void settitle(String string, int i) {
		title = string;
		titlefont = i;
	}

	public void firstpage(int i) {
		page = 1;
	}

	public void setminimumspace(float f) {
		minsp = f;
	}
	

}



