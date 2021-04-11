package hr.fer.java.ispit.models;

import java.awt.Color;

public class Oval {

	private int x;
	private int y;
	private int rx;
	private int ry;
	private Color stroke;
	private Color fill;
	
	public Oval() {
		
	}
	
	public Oval(int x, int y, int rx, int ry, Color stroke, Color fill) {
		this.x = x;
		this.y = y;
		this.rx = rx;
		this.ry = ry;
		this.stroke = stroke;
		this.fill = fill;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRx() {
		return rx;
	}

	public void setRx(int rx) {
		this.rx = rx;
	}

	public int getRy() {
		return ry;
	}

	public void setRy(int ry) {
		this.ry = ry;
	}

	public Color getStroke() {
		return stroke;
	}

	public void setStroke(Color stroke) {
		this.stroke = stroke;
	}

	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}
	
}
