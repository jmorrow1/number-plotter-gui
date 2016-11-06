package draw_modes;

import curves.Point;
import processing.core.PGraphics;

public interface DrawMode {
    public default void draw(int num, int state, int maxState, Point pt, float cellSqrt, PGraphics pg) {
    	draw(num, state, maxState, pt.x, pt.y, cellSqrt, pg);
    }
    
    public void draw(int num, int state, int maxState, float x, float y, float cellSqrt, PGraphics pg);
}
