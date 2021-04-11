package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Model of an object which is capable of subscribing to notifications about language
 * changes of {@link ILocalizationProvider}.
 * 
 * @author Matija Frandolić
 */
public interface ILocalizationListener {

	/**
	 * Action that is performed when this listener is notified about language
	 * change of {@code ILocalizationProvider} object to which this listener
	 * is registered to.
	 */
	void localizationChanged();
	
}
