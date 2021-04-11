package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Subclass of {@link LocalizationProviderBridge} that registers itself
 * as a {@code WindowListener} on the given {@link JFrame} object. Connection to
 * the parent localization listener is established when the frame opens and 
 * connection is closed when the frame closes.
 * 
 * @author Matija Frandolić
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructs a new {@code FormLocalizationProvider} from the given 
	 * parent localization provider and the given frame.
	 * 
	 * @param parent parent localization provider to which the connection is opened
	 *               as long as the given frame is opened
	 * @param frame  frame to which this provider will be registered as a window listener
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
	
}
