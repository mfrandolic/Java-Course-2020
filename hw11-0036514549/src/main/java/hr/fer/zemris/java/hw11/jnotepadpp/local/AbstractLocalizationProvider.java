package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ILocalizationProvider} interface which provides
 * implementation of methods for listener registration and deregistration and
 * adds method {@link AbstractLocalizationProvider#fire()} that is used to notify
 * all registered listeners about language change of the localization provider.
 * 
 * @author Matija Frandolić
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List in which listeners are stored.
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners = new ArrayList<>(listeners);
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(listener);
	}
	
	/**
	 * Notifies all registered listeners of this localization provider about
	 * the language change.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
	
}
