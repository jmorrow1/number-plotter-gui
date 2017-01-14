package gui;

import controllers.ControllerDisplay;
import controllers.Toggle;
import geom.Rect;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Displays {
    public static abstract class Display {
        public abstract void display(PGraphics g, Rect r);
    }
    
    public static abstract class CurveDisplay {
        protected int n;
        
        public CurveDisplay(int n) {
            this.n = n;
        }
        
        public abstract void display(PGraphics g, Rect r);
        
        public void setN(int n) {
            this.n = n;
        }
        
        public int getN() {
            return n;
        }
    }
    
    public static class CurveToggleDisplay<T extends CurveDisplay> implements ControllerDisplay<Toggle> {
        protected T curveDisplay;
        protected Rect rect;
        
        public CurveToggleDisplay(T curveDisplay) {
            this.curveDisplay = curveDisplay;
            rect = new Rect(0, 0, 0, 0, CORNER);
        }
        
        @Override
        public void display(PGraphics g, Toggle t) {
            g.stroke(t.getColorInCurrentContext());
            rect.set(t.getCenx(), t.getCeny(), t.getWidth(), t.getHeight(), CENTER);
            curveDisplay.display(g, rect);
        }
        
        public int getN() {
            return curveDisplay.getN();
        }
        
        public void setN(int n) {
            curveDisplay.setN(n);
        }
    }
    
    public static class Diamond extends CurveDisplay {
        public Diamond(int n) {
            super(n);
        }

        public void display(PGraphics g, Rect r) {
            g.strokeWeight(2);
            g.noFill();

            // g.rectMode(g.CENTER);
            // g.rect(r.getCenx(), r.getCeny(), r.getWidth(), r.getHeight());

            float dy = r.getHeight() / n;
            float y = r.getY1() + dy / 2f;
            float dLen = r.getWidth() / (n + 1);
            float len = dLen / 2f;

            int end = PApplet.ceil(n / 2f) - 1;
            for (int i = 0; i < end; i++) {
                g.line(r.getCenx() - len, y, r.getCenx() + len, y);
                len += dLen;
                y += dy;
            }

            g.line(r.getCenx() - len, y, r.getCenx() + len, y);
            y += dy;

            if (n % 2 == 0) {
                g.line(r.getCenx() - len, y, r.getCenx() + len, y);
                y += dy;
                len -= dLen;
            } else {
                len -= dLen;
            }

            for (int i = 0; i < end; i++) {
                g.line(r.getCenx() - len, y, r.getCenx() + len, y);
                len -= dLen;
                y += dy;
            }
        }
    }
    
    public static class Ulam extends CurveDisplay {
        private PVector[] ulamSeq;
        
        public Ulam(int n) {
            super(n);
            setN(n);
        }

        @Override
        public void display(PGraphics g, Rect r) {
            g.noFill();
            g.strokeWeight(2);

            // g.rectMode(g.CENTER);
            // g.rect(r.getCenx(), r.getCeny(), r.getWidth(), r.getHeight());

            float cellWidth = r.getWidth() / n;
            float cellHeight = r.getHeight() / n;

            float x = r.getCenx();
            float y = r.getCeny();
            if (n % 2 == 0) {
                y -= cellHeight / 2f;
            }

            g.beginShape();
            for (int i = 0; i < ulamSeq.length; i++) {
                g.vertex(x + cellWidth * ulamSeq[i].x, y + cellHeight * ulamSeq[i].y);
            }
            g.endShape();
        }
        
        private static PVector[] ulamSeq(int n) {
            PVector[] seq = new PVector[n];
            for (int i = 0; i < n; i++) {
                int a = i / 4;
                switch (i % 4) {
                case 0:
                    seq[i] = new PVector(-a, -a);
                    break;
                case 1:
                    seq[i] = new PVector(a + 1, -a);
                    break;
                case 2:
                    seq[i] = new PVector(a + 1, a + 1);
                    break;
                case 3:
                    seq[i] = new PVector(-(a + 1), a + 1);
                    break;
                }
            }

            return seq;
        }
        
        @Override
        public void setN(int n) {
            this.n = n;
            ulamSeq = ulamSeq(2 * n);
            if (n % 2 == 1) {
                ulamSeq[ulamSeq.length - 1].x = -ulamSeq[ulamSeq.length - 2].x;
            }
        }
        
    }
    
    public static class Typewriter extends CurveDisplay {

        public Typewriter(int n) {
            super(n);
        }

        @Override
        public void display(PGraphics g, Rect r) {
            g.noFill();

            // g.rectMode(g.CENTER);
            // g.rect(r.getCenx(), r.getCeny(), r.getWidth(), r.getHeight());

            float dy = r.getHeight() / n;
            float y = r.getY1() + dy / 2f;
            float len = n * r.getWidth() / (2 * (n + 1));

            for (int i = 0; i < n; i++) {
                g.line(r.getCenx() - len, y, r.getCenx() + len, y);
                y += dy;
            }
        }
        
    }
    
    public static class Triangle extends CurveDisplay {

        public Triangle(int n) {
            super(n);
        }

        @Override
        public void display(PGraphics g, Rect r) {
            g.noFill();            
            g.strokeWeight(2);

            // g.rectMode(g.CENTER);
            // g.rect(r.getCenx(), r.getCeny(), r.getWidth(), r.getHeight());
            
            float dy = r.getHeight() / n;
            float y = r.getY1() + dy / 2f;
            float dLen = r.getWidth() / (2 * (n + 1));
            float len = dLen / 2f;

            for (int i = 0; i < n; i++) {
                g.line(r.getCenx() - len, y, r.getCenx() + len, y);
                len += dLen;
                y += dy;
            }
        }
    }
    
    public static class DiamondToggleDisplay extends CurveToggleDisplay<Diamond> {
        public DiamondToggleDisplay(int n) {
            super(new Diamond(n));
        }
    }
    
    public static class UlamToggleDisplay extends CurveToggleDisplay<Ulam> {
        public UlamToggleDisplay(int n) {
            super(new Ulam(n));
        }
    }
    
    public static class TypewriterToggleDisplay extends CurveToggleDisplay<Typewriter> {
        public TypewriterToggleDisplay(int n) {
            super(new Typewriter(n));
        }
    }    

    public static class TriangleToggleDisplay extends CurveToggleDisplay<Triangle> {
        public TriangleToggleDisplay(int n) {
            super(new Triangle(n));
        }
    }
}