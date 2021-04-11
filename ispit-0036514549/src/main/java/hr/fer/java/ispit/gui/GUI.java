package hr.fer.java.ispit.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.java.ispit.models.ImageModel;
import hr.fer.java.ispit.parser.Parser;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public GUI() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		initGUI();
		setSize(700, 700);
		
		setLocationRelativeTo(null);
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(2, 1));
		
		JTextArea textArea = new JTextArea();
		JPanel imagePanel = new JPanel();
		
		panel.add(new JScrollPane(textArea));
		panel.add(imagePanel);
		cp.add(panel, BorderLayout.CENTER);
		
		JButton drawBtn = new JButton("Nacrtaj");
		drawBtn.addActionListener(l -> {
			ImageModel model = Parser.parse(textArea.getText());
			Image image = Parser.createImage(model);
			imagePanel.getGraphics().drawImage(image, 0, 0, this);
		});

		JButton closeBtn = new JButton("Zatvori");
		closeBtn.addActionListener(l -> {
			System.exit(0);
		});
		
		JPanel status = new JPanel();
		status.add(drawBtn);
		status.add(closeBtn);
		
		cp.add(status, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new GUI().setVisible(true);
		});
	}
	
}
