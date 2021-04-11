package hr.fer.java.ispit.models;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class ImageModel {

	private Dimension size;
	private Color background;
	
	private List<Line> lines = new ArrayList<>();
	private List<Oval> ovals = new ArrayList<>();
	
	public ImageModel() {
		
	}
	
	public ImageModel(Dimension size, Color background) {
		this.size = size;
		this.background = background;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public List<Line> getLines() {
		return lines;
	}

	public List<Oval> getOvals() {
		return ovals;
	}
	
}
