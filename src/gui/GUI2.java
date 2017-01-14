package gui;

import java.util.ArrayList;

import controllers.Controller;
import controllers.ControllerDisplay;
import controllers.ControllerListener;
import controllers.ControllerUpdater;
import controllers.Toggle;
import geom.Rect;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public class GUI2 extends PApplet implements ControllerListener {
    public static void main(String[] args) {
        PApplet.main("gui.GUI2");
    }
    
    private ControllerUpdater updater;
    private ArrayList<Text> texts = new ArrayList<Text>();
    private Toggle[] sequenceToggles, curveToggles, propertyToggles, drawModeToggles;
    
    public void settings() {
        size(600, 200);
    }
    
    public void setup() {
        updater = new ControllerUpdater(this);
        updater.setDefaultListener(this);
        
        int dx = width / 4;
        int x = 10;
        int y = 25;
        String[] ss = new String[] {"Sequence", "Curve", "Property", "Draw mode"};
        for (int i=0; i<4; i++) {
            PFont font = g.getStyle().textFont;
            Text text = new Text(color(20), x, y, ss[i], LEFT, BOTTOM, font, 14, true);
            texts.add(text);
            x += dx;
        }
    }
    
    public void draw() {
        background(255);
        for (Text t : texts) {
            t.draw(g);
        }
    }

    @Override
    public void controllerEvent(Controller c) {
        
    }
    
    public static class Display {
        public ControllerDisplay cdisplay;
        public Rect rect;
        
        public Display(ControllerDisplay cdisplay, Rect rect) {
            this.cdisplay = cdisplay;
            this.rect = rect;
        }
        
        void draw(PGraphics g) {
//            cdisplay.display(g, controller);
        }
    }
    
    public static class Text {
        public int fillColor;
        public float x, y;
        public String text;
        public int textAlignX, textAlignY;
        public PFont font;
        public float fontSize;
        boolean underline;

        public Text(int fillColor, float x, float y, String text, int textAlignX, int textAlignY, PFont font, float fontSize, boolean underline) {
            this.fillColor = fillColor;
            this.x = x;
            this.y = y;
            this.text = text;
            this.textAlignX = textAlignX;
            this.textAlignY = textAlignY;
            this.font = font;
            this.fontSize = fontSize;
            this.underline = underline;
        }
        
        public void draw(PGraphics g) {
//            g.textFont(font, fontSize);
            g.textSize(fontSize);
            g.fill(fillColor);
            g.textAlign(textAlignX, textAlignY);
            g.text(text, x, y);
            if (underline) {
                g.stroke(fillColor);
                g.line(getX1(g), getY2(g), getX2(g), getY2(g));
//                g.line(getX1(g), y, getX2(g), y);
            }
        }
        
        public float getX1(PGraphics g) {
//          g.textFont(font, fontSize);
            g.textAlign(textAlignX, textAlignY);
            if (textAlignX == LEFT) {
                return x;
            }
            else if (textAlignX == CENTER) {
                return x - getWidth(g)/2;
            }
            else {
                return x - getWidth(g);
            }            
        }
        
        public float getX2(PGraphics g) {
//          g.textFont(font, fontSize);
            g.textAlign(textAlignX, textAlignY);
            if (textAlignX == LEFT) {
                return x + getWidth(g);
            }
            else if (textAlignX == CENTER) {
                return x + getWidth(g)/2;
            }
            else {
                return x;
            }    
        }
        
        public float getY1(PGraphics g) {
//          g.textFont(font, fontSize);
            g.textAlign(textAlignX, textAlignY);
            if (textAlignY == TOP) {
                return y;
            }
            else if (textAlignY == CENTER) {
                return y - g.textAscent();
            }
            else {
                return y - g.textAscent() - g.textDescent();
            }
        }
        
        public float getY2(PGraphics g) {
//          g.textFont(font, fontSize);
            g.textAlign(textAlignX, textAlignY);
            if (textAlignY == TOP) {
                return y + g.textAscent() + g.textDescent();
            }
            else if (textAlignY == CENTER) {
                return y + g.textDescent();
            }
            else {
                return y;
            }
        }
        
        public float getWidth(PGraphics g) {
//            g.textFont(font, fontSize);
            return g.textWidth(text);
        }
    }
}
