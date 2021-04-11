package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that demonstrates usage of {@link PrimListModel}. Two lists are
 * created (both of which are registered to the same {@code PrimListModel}) along
 * with next button that triggers next prime to be generated and shown in both lists.
 * 
 * @author Matija Frandolić
 */
public class PrimDemo extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs the GUI.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		initGUI();
		setSize(500, 500);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Initializes the GUI layout and components.
	 */
	private void initGUI() {
		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(e -> {
			model.next();
		});
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(1, 1));
		
		panel.add(new JScrollPane(list1));
		panel.add(new JScrollPane(list2));
		cp.add(panel, BorderLayout.CENTER);
		cp.add(nextButton, BorderLayout.SOUTH);
	}

	/**
	 * Main method of the program that is responsible for starting the GUI.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
	
}
