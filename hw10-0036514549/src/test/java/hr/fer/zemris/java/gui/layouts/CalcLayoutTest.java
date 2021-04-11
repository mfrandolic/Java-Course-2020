package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	public void testIllegalConstraints() {
		JPanel p = new JPanel(new CalcLayout());
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(-2, 2));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(4, -2));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(-2, -3));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(6, 6));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(7, 10));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(4, 8));			
		});
	}
	
	@Test
	public void testIllegalConstraintsForFirstRow() {
		JPanel p = new JPanel(new CalcLayout());
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(1, 2));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(1, 3));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(1, 4));			
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(), new RCPosition(1, 5));			
		});
	}
	
	@Test
	public void testConstraintWithMultipleComponents() {
		JPanel p = new JPanel(new CalcLayout());
		JLabel l1 = new JLabel();
		JLabel l2 = new JLabel();
		
		p.add(l1, new RCPosition(2, 2));			
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(2, 2));			
		});
	}
	
	@Test
	public void testPreferredSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void testPreferredSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

}
