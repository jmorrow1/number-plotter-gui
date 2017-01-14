package draw_modes;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class ToShape implements DrawMode {
    // primitive shape
    public final static int CIRCLE = 0, SQUARE = 1, TEXT = 2;
    private int primitiveShape;

    // size range
    private int minStateSizeMult, maxStateSizeMult;

    // fill color range
    private int minStateFillColor, maxStateFillColor;

    // opacity range
    private int minStateOpacity, maxStateOpacity;

    // other settings
    private boolean showStroke;

    public ToShape(int primitiveShape, int minStateSizeMult, int maxStateSizeMult, int minStateFillColor,
            int maxStateFillColor, int minStateOpacity, int maxStateOpacity, boolean showStroke) {
        this.primitiveShape = PApplet.constrain(primitiveShape, 0, 2);
        this.minStateSizeMult = minStateSizeMult;
        this.maxStateSizeMult = maxStateSizeMult;
        this.minStateFillColor = minStateFillColor;
        this.maxStateFillColor = maxStateFillColor;
        this.minStateOpacity = minStateOpacity;
        this.maxStateOpacity = maxStateOpacity;
        this.showStroke = showStroke;
    }

    @Override
    public void draw(int num, int state, int maxState, float x, float y, float cellSqrt, PGraphics pg) {
        float size = cellSqrt * PApplet.map(state, 0, maxState, minStateSizeMult, maxStateSizeMult);
        float normalizedState = (float) state / (float) maxState;
        float opacity = PApplet.lerp(minStateOpacity, maxStateOpacity, normalizedState);
        int fillColor = pg.lerpColor(minStateFillColor, maxStateFillColor, normalizedState);
        if (showStroke) {
            pg.stroke(0);
        } else {
            pg.noStroke();
        }

        if (state != 0) {
            pg.fill(fillColor, opacity);
            drawPrimitiveShape(num, x, y, size, pg);
        }

    }

    private void drawPrimitiveShape(int num, float x, float y, float diam, PGraphics pg) {
        switch (primitiveShape) {
        case CIRCLE:
            pg.ellipseMode(PConstants.CENTER);
            pg.ellipse(x, y, diam, diam);
            break;
        case SQUARE:
            pg.rectMode(PConstants.CENTER);
            pg.rect(x, y, diam, diam);
            break;
        case TEXT:
            pg.textAlign(PConstants.CENTER, PConstants.CENTER);
            pg.text(num, x, y);
            break;
        }
    }
}