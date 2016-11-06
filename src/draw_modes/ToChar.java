package draw_modes;

import processing.core.PGraphics;

public class ToChar implements DrawMode {
	private StringBuffer s;
	private char errorChar;
	
	public ToChar(String s, char errorChar) {
		this.s = new StringBuffer(s);
		this.errorChar = errorChar;
	}

	@Override
	public void draw(int num, int state, int maxState, float x, float y, float cellSqrt, PGraphics pg) {
		pg.fill(0);
		pg.textSize(cellSqrt);
		pg.textAlign(pg.CENTER, pg.CENTER);
		char c = state < s.length() ? s.charAt(state) : errorChar;
		pg.text(c, x, y);
	}
	
	public void setChar(int i, char c) {
		s.setCharAt(i, c);
	}

	public void setString(String s) {
		this.s = new StringBuffer(s);
	}
}
