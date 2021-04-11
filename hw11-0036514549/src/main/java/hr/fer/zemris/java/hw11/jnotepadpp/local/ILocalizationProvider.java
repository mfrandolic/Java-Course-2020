package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Model of an object which is able to provide translation for the given key.
 * When current language is changed, all registered {@link ILocalizationListener}
 * will be notified.
 * 
 * @author Matija Frandolić
 */
public interface ILocalizationProvider {

	/**
	 * Returns translation for the given key. 
	 * 
	 * @param  key key for which to return translation
	 * @return     translation for the given key
	 */
	String getString(String key);
	
	/**
	 * Adds the given localization listener that subscribes to notifications
	 * about language changes of this provider.
	 * 
	 * @param listener localization listener that wants to subscribe
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Removes the given localization listener that was previously subscribed to 
	 * notifications about language changes of this provider.
	 * 
	 * @param listener localization listener that was previously subscribed
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Returns string code of current language of this provider.
	 * 
	 * @return string code of current language of this provider
	 */
	String getCurrentLanguage();
	
}
