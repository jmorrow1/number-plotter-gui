package draw_modes;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class ToPixel implements DrawMode {
    private int minStateFillColor, maxStateFillColor;

    public ToPixel(int minStateFillColor, int maxStateFillColor) {
        super();
        this.minStateFillColor = minStateFillColor;
        this.maxStateFillColor = maxStateFillColor;
    }
    
    @Override
    public void preDraw(PGraphics g) {
        g.loadPixels();
    }

    @Override
    public void draw(int num, int state, int maxState, float x, float y, float cellSqrt, PGraphics g) {
        int i = (int)x;
        int j = (int)y;
        int k = i + j*g.width;

        if (0 <= k && k < g.pixels.length) {
            float amt = PApplet.map(state, 0, maxState, 0, 1);
            int color = PApplet.lerpColor(minStateFillColor, maxStateFillColor, amt, PApplet.RGB);
            g.pixels[k] = color;
        }
    }
    
    @Override
    public void postDraw(PGraphics g) {
        g.updatePixels();
    }

}
