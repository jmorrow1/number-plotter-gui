package gui;

import controllers.ControllerDisplay;
import controllers.Toggle;
import draw_modes.ToShape;
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
    
    public static abstract class CurveDisplay extends Display {
        protected int n;
        
        public CurveDisplay(int n) {
            this.n = n;
        }
        
        public void setN(int n) {
            this.n = n;
        }
        
        public int getN() {
            return n;
        }
    }
    
    /**********************
     ***** Draw Modes *****
     **********************/
    
    public static class DrawShapeToggleDisplay implements ControllerDisplay<Toggle> {
        private int mode;
        
        public DrawShapeToggleDisplay(int mode) {
            this.mode = mode;
        }
        
        public void display(PGraphics g, Toggle t) {
            //background
            if (t.isOff()) {
                g.strokeWeight(2);
                g.stroke(20);
                g.fill(255);
            }
            else {
                g.noStroke();
                g.fill(20);
            }
            g.rectMode(CORNER);
            g.rect(t.getX1(), t.getY1(), t.getWidth(), t.getHeight());
            
            //shape
            g.noStroke();
            g.fill(t.isOn() ? 255 : 20);
            if (mode == ToShape.CIRCLE) {
                g.ellipseMode(CENTER);
                g.ellipse(t.getCenx(), t.getCeny(), t.getWidth()/3, t.getHeight()/3);
            }
            else if (mode == ToShape.SQUARE) {
                g.rectMode(CENTER);
                g.rect(t.getCenx(), t.getCeny(), t.getWidth()/3, t.getHeight()/3);
            }
        }
        
        public void setMode(int mode) {
            this.mode = mode;
        }
    }
    
    /****************
     ***** Text *****
     ****************/
    
    public static class StringToggleDisplay implements ControllerDisplay<Toggle> {
        private String s;
        
        public StringToggleDisplay(String s) {
            this.s = s;
        }
        
        @Override
        public void display(PGraphics g, Toggle t) {
            //background
            if (t.isOff()) {
                g.strokeWeight(2);
                g.stroke(20);
                g.fill(255);
            }
            else {
                g.noStroke();
                g.fill(20);
            }
            g.rectMode(CORNER);
            g.rect(t.getX1(), t.getY1(), t.getWidth(), t.getHeight());
            
            //text
            g.fill(t.isOn() ? g.color(255) : g.color(20));
            g.rectMode(CORNER);
            g.textAlign(CENTER, CENTER);
            g.textSize(16);
            g.text(s, t.getX1(), t.getY1(), t.getWidth(), t.getHeight());
        }
    }
    
    /******************
     ***** Curves *****
     ******************/
    
    public static class CurveToggleDisplay<T extends CurveDisplay> implements ControllerDisplay<Toggle> {
        protected T curveDisplay;
        protected Rect rect;
        
        public CurveToggleDisplay(T curveDisplay) {
            this.curveDisplay = curveDisplay;
            rect = new Rect(0, 0, 0, 0, CORNER);
        }
        
        @Override
        public void display(PGraphics g, Toggle t) {
            //background
            if (t.isOff()) {
                g.strokeWeight(2);
                g.stroke(20);
                g.fill(255);
            }
            else {
                g.noStroke();
                g.fill(20);
            }
            g.rectMode(CORNER);
            g.rect(t.getX1(), t.getY1(), t.getWidth(), t.getHeight());
            
            //curve
            g.stroke(t.isOn() ? g.color(255) : g.color(20));
            rect.set(t.getCenx(), t.getCeny(), 0.8f * t.getWidth(), 0.8f * t.getHeight(), CENTER);
            curveDisplay.display(g, rect);
        }
        
        public int getN() {
            return curveDisplay.getN();
        }
        
        public void setN(int n) {
            curveDisplay.setN(n);
        }
    }
    
    public static class Sacks extends CurveDisplay {

        public Sacks(int n) {
            super(n);
        }

        @Override
        public void display(PGraphics g, Rect r) {
            g.strokeWeight(2);
            g.noFill();
            
            float radius = 0.5f * PApplet.min(r.getWidth(), r.getHeight());
            
            spiral(g, r.getCenx(), r.getCeny(), radius, 3);
            
        }
        
        private void spiral(PGraphics g, float x, float y, float radius, float loopCount) {
            int n = 100;

            float t = 0;
            float dt = 1f / n;
            
            g.beginShape();
            for (int i=0; i<n+1; i++) {
                float r = radius * quadEaseOut(t);
                float phi = loopCount * PApplet.TWO_PI * quadEaseOut(t);
                g.vertex(x + r * PApplet.cos(phi), y + r * PApplet.sin(phi));
                t += dt;
            }
            g.endShape();
        }
        
        private static float quadEaseOut(float t) {
            return -t*(t-2);
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
            g.strokeWeight(2f);

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
    
    public static class SacksToggleDisplay extends CurveToggleDisplay<Sacks> {
        public SacksToggleDisplay(int n) {
            super(new Sacks(n));
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
