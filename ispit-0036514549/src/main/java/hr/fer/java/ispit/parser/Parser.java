package hr.fer.java.ispit.parser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import hr.fer.java.ispit.models.ImageModel;
import hr.fer.java.ispit.models.Line;
import hr.fer.java.ispit.models.Oval;

public class Parser {

	public static ImageModel parse(String text) {
		String[] lines = text.split("\\r?\\n");
		ImageModel model = new ImageModel();
		
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith("#") || line.isBlank()) { 
				continue;
			}
			
			if (line.startsWith("SIZE")) {
				parseSize(line, model);
			} else if (line.startsWith("LINE")) {
				parseLine(line, model);
			} else if (line.startsWith("OVAL")) {
				parseOval(line, model);
			} else {
				throw new ParserException();				
			}
		}
		
		return model;
	}
	
	private static int indexOfFirstBlankOrLetter(String line, int index) {
		char[] chars = line.toCharArray();
		for (int i = index; i < line.length(); i++) {
			if (chars[i] == ' '  || Character.isLetter(chars[i])) {
				return i;
			}
		}
		return line.length();
	}
	
	private static Color parseColor(String line, int index) {
		int r;
		try {
			r = Integer.parseInt(line.substring(0, line.indexOf(',')));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		int g;
		try {
			g = Integer.parseInt(line.substring(line.indexOf(',') + 1, line.lastIndexOf(',')));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		int b;
		try {
			b = Integer.parseInt(line.substring(line.lastIndexOf(',') + 1));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		return new Color(r, g, b);
	}
	
	private static void parseSize(String line, ImageModel model) {
		if (!line.contains("dim=") || !line.contains("background:rgb=")) {
			throw new ParserException();
		}
		
		int index = line.indexOf("dim") + "dim".length() + 1;
		String dimString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		
		int x;
		try {
			x = Integer.parseInt(dimString.substring(0, dimString.indexOf(',')));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		int y;
		try {
			y = Integer.parseInt(dimString.substring(dimString.indexOf(',') + 1));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		index = line.indexOf("background:rgb") + "background:rgb".length() + 1;
		String colorString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		Color color = parseColor(colorString, index);
		
		Dimension dim = new Dimension(x, y);
		model.setSize(dim);
		model.setBackground(color);
	}
	
	private static void parseLine(String line, ImageModel model) {
		if (!line.contains("start=") || !line.contains("end=") || !line.contains("stroke:rgb=")) {
			throw new ParserException();
		}
		
		int index = line.indexOf("start") + "start".length() + 1;
		String startString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		
		int x1;
		try {
			x1 = Integer.parseInt(startString.substring(0, startString.indexOf(',')));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		int y1;
		try {
			y1 = Integer.parseInt(startString.substring(startString.indexOf(',') + 1));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		index = line.indexOf("end") + "end".length() + 1;
		String endString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		
		int x2;
		try {
			x2 = Integer.parseInt(endString.substring(0, endString.indexOf(',')));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		int y2;
		try {
			y2 = Integer.parseInt(endString.substring(endString.indexOf(',') + 1));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		index = line.indexOf("stroke:rgb") + "stroke:rgb".length() + 1;
		String colorString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		Color color = parseColor(colorString, index);
		
		Line lineObject = new Line(x1, y1, x2, y2, color);
		model.getLines().add(lineObject);
	}
	
	private static void parseOval(String line, ImageModel model) {
		if (!line.contains("center=") || !line.contains("rx=") || !line.contains("ry=") || 
				!line.contains("stroke:rgb=") || !line.contains("fill:rgb=")) {
			throw new ParserException();
		}
		
		int index = line.indexOf("center") + "center".length() + 1;
		String centerString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		
		int x;
		try {
			x = Integer.parseInt(centerString.substring(0, centerString.indexOf(',')));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		int y;
		try {
			y = Integer.parseInt(centerString.substring(centerString.indexOf(',') + 1));
		} catch (Exception e) {
			throw new ParserException();
		}
		
		index = line.indexOf("rx") + "rx".length() + 1;
		String rxString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		
		int rx;
		try {
			rx = Integer.parseInt(rxString);
		} catch (Exception e) {
			throw new ParserException();
		}
		
		index = line.indexOf("ry") + "ry".length() + 1;
		String ryString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		
		int ry;
		try {
			ry = Integer.parseInt(ryString);
		} catch (Exception e) {
			throw new ParserException();
		}
		
		index = line.indexOf("stroke:rgb") + "stroke:rgb".length() + 1;
		String colorString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		Color strokeColor = parseColor(colorString, index);
		
		index = line.indexOf("fill:rgb") + "fill:rgb".length() + 1;
		colorString = line.substring(index, indexOfFirstBlankOrLetter(line, index));
		Color fillColor = parseColor(colorString, index);
		
		Oval oval = new Oval(x, y, rx, ry, strokeColor, fillColor);
		model.getOvals().add(oval);
	}
	
	public static Image createImage(ImageModel model) {
		BufferedImage image = new BufferedImage(model.getSize().width, model.getSize().height, 
				BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		g.setColor(model.getBackground());
		g.fillRect(0, 0, model.getSize().width - 1, model.getSize().height - 1);
		
		for (Line line : model.getLines()) {
			g.setColor(line.getStroke());
			g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
		}
		
		for (Oval oval : model.getOvals()) {
			g.setColor(oval.getFill());
			g.fillOval(oval.getX(), oval.getY(), oval.getRx() * 2, oval.getRy() * 2);
			g.setColor(oval.getStroke());
			g.drawOval(oval.getX(), oval.getY(), oval.getRx() * 2, oval.getRy() * 2);
		}
		
		return image;
	}
	
}
