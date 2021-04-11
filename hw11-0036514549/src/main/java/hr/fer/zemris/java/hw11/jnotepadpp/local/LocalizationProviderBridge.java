package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Subclass of {@link AbstractLocalizationProvider} which is a decorator for some
 * other {@link ILocalizationProvider} and provides methods for connecting and
 * disconnecting from that localization provider. When connection is established, this
 * provider listens to the language changes of its parent and notifies its own
 * listeners about the changes.
 * 
 * @author Matija Frandolić
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Current language.
	 */
	private String language;
	
	/**
	 * Connection status.
	 */
	private boolean connected;
	
	/**
	 * Listener that is registered to parent.
	 */
	private ILocalizationListener listener;
	
	/**
	 * Decorated localization provider to which this provider manages connection.
	 */
	private ILocalizationProvider parent;
	
	/**
	 * Constructs a new {@code LocalizationProviderBridge} from the given parent
	 * that will be decorated by this provider in order to manage connection to it.
	 * 
	 * @param parent localization provider that will be decorated by this provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		language = parent.getCurrentLanguage();
		connected = false;
		listener = null;
		this.parent = parent;
	}
	
	/**
	 * Connects this provider to its parent. When connection is established, this
	 * provider listens to the language changes of its parent and notifies its own
	 * listeners about the changes.
	 */
	public void connect() {
		if (connected) {
			return;
		}
		if (!language.equals(parent.getCurrentLanguage())) {
			fire();
			language = parent.getCurrentLanguage();
		}
		connected = true;
		listener = this::fire;
		parent.addLocalizationListener(listener);
	}
	
	/**
	 * Disconnects this provider from its parent.
	 */
	public void disconnect() {
		connected = false;
		parent.removeLocalizationListener(listener);
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
}
