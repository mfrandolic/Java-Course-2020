package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JToolBar;

/**
 * Subclass of {@link JToolBar} with localized name property, as defined
 * by the given key and {@link ILocalizationProvider}.
 * 
 * @author Matija Frandolić
 */
public class LocalizableJToolBar extends JToolBar {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code LocalizableJToolBar} with localized name defined
	 * by the given key and localization provider.
	 * 
	 * @param key key of the string that will be used as name
	 * @param lp  localization provider that provides localized name according
	 *            to the given key
	 */
	public LocalizableJToolBar(String key, ILocalizationProvider lp) {
		setName(lp.getString(key));
		lp.addLocalizationListener(() -> setName(lp.getString(key)));
	}
	
}
