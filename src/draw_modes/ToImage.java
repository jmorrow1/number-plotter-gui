package draw_modes;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class ToImage implements DrawMode {
    private PImage img;
    private int bkgdCol, errorCol;

    public ToImage(PImage img, int bkgdCol, int errorCol) {
        this.img = img;
        this.bkgdCol = bkgdCol;
        this.errorCol = errorCol;
    }

    @Override
    public void draw(int num, int state, int maxState, float x, float y, float cellSqrt, PGraphics g) {
        g.background(255);
        g.loadPixels();

        if (state != 0) {
            int i = (int) (x - cellSqrt / 2f);
            int iEnd = i + (int) cellSqrt;

            while (i < iEnd) {
                int j = (int) (y - cellSqrt / 2f);
                int jEnd = j + (int) cellSqrt;

                while (j < jEnd) {
                    int index = i * img.height + j;

                    if (index < img.pixels.length) {
                        if (index < g.pixels.length) {
                            g.pixels[index] = img.pixels[index];
                        }
                    } else if (index < g.pixels.length) {
                        g.pixels[index] = errorCol;
                    }
                    j++;
                }
                i++;
            }
        }
        g.updatePixels();

    }

}
