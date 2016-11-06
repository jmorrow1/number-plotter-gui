package gui;

import controllers.ControllerDisplay;
import controllers.Toggle;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class ShapeIcons {
	public static class Diamond implements ControllerDisplay<Toggle> {
		private int n;
		
		public Diamond(int n) {
			this.n = n;
		}
		
		@Override
		public void display(PGraphics g, Toggle t) {
			g.stroke(t.getColorInCurrentContext());
		    g.strokeWeight(2);
		    g.noFill();
		    
//		    g.rectMode(g.CENTER);
//		    g.rect(t.getCenx(), t.getCeny(), t.getWidth(), t.getHeight());
			
			float dy = t.getHeight() / n;
		    float y = t.getY1() + dy/2f;
		    float dLen = t.getWidth() / (n+1);
		    float len = dLen/2f;
		     
		    int end = PApplet.ceil(n/2f) - 1;
		    for (int i=0; i<end; i++) {
		        g.line(t.getCenx() - len, y, t.getCenx() + len, y);
		        len += dLen;
		        y += dy;
		    }
		    
		    g.line(t.getCenx() - len, y, t.getCenx() + len, y);
		    y += dy;
		    
		    
		    if (n % 2 == 0) {
		        g.line(t.getCenx() - len, y, t.getCenx() + len, y);
		        y += dy;
		        len -= dLen;
		    }
		    else {
		        len -= dLen;
		    }
		    
		    for (int i=0; i<end; i++) {
		        g.line(t.getCenx() - len, y, t.getCenx() + len, y);
		        len -= dLen;
		        y += dy;
		    }
		}
		
		public int getN() {
			return n;
		}
		
		public void setN(int n) {
			this.n = n;
		}
	}
	
	public static class UlamSpiral implements ControllerDisplay<Toggle> {
		private int n;
		PVector[] ulamSeq;
		
		public UlamSpiral(int n) {
			setN(n);
		}

		@Override
		public void display(PGraphics g, Toggle t) {
			g.strokeWeight(2);
			g.stroke(t.getColorInCurrentContext());
		    g.noFill();
		    
//		    g.rectMode(g.CENTER);
//		    g.rect(t.getCenx(), t.getCeny(), t.getWidth(), t.getHeight());
			
			float cellWidth = t.getWidth() / n;
		    float cellHeight = t.getHeight() / n;
		    
		    float x = t.getCenx();
		    float y = t.getCeny();
		    if (n % 2 == 0) {
		        y -= cellHeight/2f;
		    }
		    
		    g.beginShape();
		    for (int i=0; i<ulamSeq.length; i++) {
		    	g.vertex(x + cellWidth*ulamSeq[i].x, y + cellHeight*ulamSeq[i].y);
		    }
		    g.endShape();
		}
		
		private static PVector[] ulamSeq(int n) {
		    PVector[] seq = new PVector[n];
		    for (int i=0; i<n; i++) {
		        int a = i / 4;
		        switch (i % 4) {
		            case 0 : seq[i] = new PVector(-a, -a); break;
		            case 1 : seq[i] = new PVector(a+1, -a); break;
		            case 2 : seq[i] = new PVector(a+1, a+1); break;
		            case 3 : seq[i] = new PVector(-(a+1), a+1); break;
		        }
		    }

		    return seq;
		}
		
		public int getN() {
			return n;
		}
		
		public void setN(int n) {
			this.n = n;
			ulamSeq = ulamSeq(2*n);
			if (n % 2 == 1) {
		        ulamSeq[ulamSeq.length-1].x = -ulamSeq[ulamSeq.length-2].x;      
		    }
		}
		
	}
	
	public static class Typewriter implements ControllerDisplay<Toggle> {
		private int n;
		
		public Typewriter(int n) {
			this.n = n;
		}
		
		@Override
		public void display(PGraphics g, Toggle t) {
			g.stroke(t.getColorInCurrentContext());
		    g.strokeWeight(2);
		    g.noFill();
			
//			g.rectMode(g.CENTER);
//		    g.rect(t.getCenx(), t.getCeny(), t.getWidth(), t.getHeight());
			
			float dy = t.getHeight() / n;
		    float y = t.getY1() + dy/2f;
		    float len = n * t.getWidth() / (2*(n+1));
		  
		    for (int i=0; i<n; i++) {
		        g.line(t.getCenx() - len, y, t.getCenx() + len, y);
		        y += dy;
		    }
		}
		
		public int getN() {
			return n;
		}
		
		public void setN(int n) {
			this.n = n;
		}
	}
	
	public static class Triangle implements ControllerDisplay<Toggle> {
		private int n;
		
		public Triangle(int n) {
			this.n = n;
		}
		
		@Override
		public void display(PGraphics g, Toggle t) {
			g.noFill();
		    g.stroke(t.getColorInCurrentContext());
		    g.strokeWeight(2);
		    
//		    g.rectMode(g.CENTER);
//		    g.rect(t.getCenx(), t.getCeny(), t.getWidth(), t.getHeight());
			
			float dy = t.getHeight() / n;
		    float y = t.getY1() + dy/2f;
		    float dLen = t.getWidth() / (2*(n+1));
		    float len = dLen/2f;
		    
		    for (int i=0; i<n; i++) {
		        g.line(t.getCenx() - len, y, t.getCenx() + len, y);
		        len += dLen;
		        y += dy;
		    }
		}
			
		public int getN() {
			return n;
		}
		
		public void setN(int n) {
			this.n = n;
		}
	}
}
