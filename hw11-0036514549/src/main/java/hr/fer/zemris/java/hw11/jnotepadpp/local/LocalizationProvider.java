package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Subclass of {@link AbstractLocalizationProvider} which provides localization
 * of strings defined in {@code hr.fer.zemris.java.hw11.jnotepadpp.local.translations}, 
 * with English as default language. This class is a singleton and provides method 
 * {@link LocalizationProvider#getInstance()} to get an instance of this class.
 * 
 * @author Matija Frandolić
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Current language.
	 */
	private String language;
	
	/**
	 * Bundle used for localization.
	 */
	private ResourceBundle bundle;
	
	/**
	 * Instance of this class.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Creates instance of this class with English as default language.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	/**
	 * Returns a single existing instance of {@code LocalizationProvider} object.
	 * 
	 * @return single existing instance of {@code LocalizationProvider} object
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Sets the language that will be used by this localization provider from 
	 * the given string code of the language.
	 * 
	 * @param language string code of the language that will be used by this provider
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
			"hr.fer.zemris.java.hw11.jnotepadpp.local.translations", locale
		);
		fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
