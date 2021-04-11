package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretListener;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Implementation of a status bar that shows length of the currently opened document
 * from the given {@link MultipleDocumentModel}, caret position and selection length
 * in that document and current date and time.
 * 
 * @author Matija Frandolić
 */
public class StatusBar extends JPanel {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parent frame of this status bar.
	 */
	private JFrame parent;
	
	/**
	 * Multiple document model used by this status bar.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Localization provider for {@code StatusBar#lengthLabel}.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Label that shows length of currently opened document.
	 */
	private JLabel lengthLabel;
	
	/**
	 * Label that shows caret position and selection length in currently opened document.
	 */
	private JLabel caretLabel;
	
	/**
	 * Label that shows current date and time.
	 */
	private JLabel timeLabel;
	
	/**
	 * Separators between labels.
	 */
	private JSeparator[] separators;

	/**
	 * Constructs a new {@code StatusBar} from the given parent frame that will use
	 * this status bar, the given multiple document model and the given localization
	 * provider.
	 * 
	 * @param parent parent frame that will use this status bar
	 * @param model  multiple document model to be used by this status bar
	 * @param lp     localization provider for this status bar
	 */
	public StatusBar(JFrame parent, MultipleDocumentModel model, ILocalizationProvider lp) {
		this.parent = parent;
		this.model = model;
		this.lp = lp;
		
		initGUI();
		initTimeLabel();
		initLengthAndCaretLabels();
	}
	
	/**
	 * Initializes the GUI components and layout.
	 */
	private void initGUI() {
		lengthLabel = new JLabel();
		caretLabel = new JLabel();
		timeLabel = new JLabel();
		
		lengthLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		caretLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		final int SEPARATORS = 2; 
		separators = new JSeparator[SEPARATORS];
		for (int i = 0; i < separators.length; i++) {
			separators[i] = new JSeparator(SwingConstants.VERTICAL);
			separators[i].setMaximumSize(new Dimension(1, Integer.MAX_VALUE));
			separators[i].setVisible(false);
		}
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
		
		final int GAP = 100;
		leftPanel.add(lengthLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(GAP, 0)));
		leftPanel.add(separators[0]);
		leftPanel.add(caretLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(GAP, 0)));
		leftPanel.add(separators[1]);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(leftPanel);
		add(Box.createHorizontalGlue());
		add(timeLabel);
	}
	
	/**
	 * Implements the functionality of the time label, that is displaying the 
	 * current date and time.
	 */
	private void initTimeLabel() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		timeLabel.setText(dateFormat.format(Calendar.getInstance().getTime()));
		
		final int INTERVAL = 1000;
		Timer timer = new Timer(INTERVAL, (e) -> {
			timeLabel.setText(dateFormat.format(Calendar.getInstance().getTime()));
		});
		timer.start();
		
		parent.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				timer.stop();
			}
		});
	}
	
	/**
	 * Implements the functionality of the length label and the caret label, that 
	 * is displaying the length of the currently opened document, the caret position 
	 * and selection length in the currently opened document.
	 */
	private void initLengthAndCaretLabels() {
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			CaretListener listener;
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, 
			                                   SingleDocumentModel currentModel) {
				if (previousModel != null) {
					JTextComponent previousTextComponent = previousModel.getTextComponent();
					previousTextComponent.removeCaretListener(listener);					
				}
				if (currentModel != null) {
					JTextComponent currentTextComponent = currentModel.getTextComponent();
					updateLengthAndCaretLabels(currentTextComponent);
					listener = e -> updateLengthAndCaretLabels(currentTextComponent);
					currentTextComponent.addCaretListener(listener);

					for (JSeparator separator : separators) {
						separator.setVisible(true);
					}
				} else {
					lengthLabel.setText("");
					caretLabel.setText("");
					for (JSeparator separator : separators) {
						separator.setVisible(false);
					}
				}
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				// do nothing
			}
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// do nothing
			}
		});
	}
	
	/**
	 * Action to be performed in order to set the correct values of the length
	 * label and caret label based on the given {@link JTextComponent}.
	 * 
	 * @param textComponent {@code JTextComponent} that provides information about 
	 *                      document length, caret position and selection length
	 */
	private void updateLengthAndCaretLabels(JTextComponent textComponent) {
		int characterCount = textComponent.getText().length();
		lengthLabel.setText(String.format("%s : %d", lp.getString("length"), characterCount));
		
		int pos = textComponent.getCaretPosition();
		Document doc = textComponent.getDocument();
		Element root = doc.getDefaultRootElement();
		Caret caret = textComponent.getCaret();
		
		int ln = root.getElementIndex(pos);
		int col = pos - root.getElement(ln).getStartOffset();
		int sel = Math.abs(caret.getDot() - caret.getMark());
		caretLabel.setText(String.format("Ln:%d  Col:%d  Sel:%d", ln + 1, col + 1, sel));
	}

}
