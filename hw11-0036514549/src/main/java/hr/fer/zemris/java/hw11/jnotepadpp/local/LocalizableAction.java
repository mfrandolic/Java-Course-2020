package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 * Subclass of {@link AbstractAction} with localized {@code NAME}, 
 * {@code SHORT_DESCRIPTION}, {@code ACCELERATOR_KEY} and {@code MNEMONIC_KEY}
 * properties, as defined by the given keys and {@link ILocalizationProvider}.
 * 
 * @author Matija Frandolić
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new {@code LocalizableAction} with localized {@code NAME}, 
	 * {@code SHORT_DESCRIPTION}, {@code ACCELERATOR_KEY} and {@code MNEMONIC_KEY}
	 * properties, as defined by the given keys and {@link ILocalizationProvider}.
	 * 
	 * @param name        key of the string that will be used as {@code NAME}
	 * @param description key of the string that will be used as {@code SHORT_DESCRIPTION}
	 * @param accelerator key of the string that will be used as {@code ACCELERATOR_KEY}
	 * @param mnemonic    key of the string that will be used as {@code MNEMONIC_KEY}
	 * @param lp          localization provider that provides localized properties
	 *                    according to the given keys
	 */
	public LocalizableAction(String name, String description, String accelerator, 
	                         String mnemonic, ILocalizationProvider lp) {
		if (name != null) {
			putValue(NAME, lp.getString(name));
			lp.addLocalizationListener(() -> putValue(NAME, lp.getString(name)));
		}
		if (description != null) {
			putValue(SHORT_DESCRIPTION, lp.getString(description));
			lp.addLocalizationListener(() -> putValue(SHORT_DESCRIPTION, lp.getString(description)));
		}
		if (accelerator != null) {
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(lp.getString(accelerator)));
			lp.addLocalizationListener(() -> putValue(ACCELERATOR_KEY, 
					KeyStroke.getKeyStroke(lp.getString(accelerator)))
			);
		}
		if (mnemonic != null) {
			putValue(MNEMONIC_KEY, KeyStroke.getKeyStroke(lp.getString(mnemonic)).getKeyCode());
			lp.addLocalizationListener(() -> putValue(MNEMONIC_KEY, 
					KeyStroke.getKeyStroke(lp.getString(mnemonic)).getKeyCode())
			);
		}
	}

}
