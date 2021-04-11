package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Default implementation of {@link SingleDocumentModel}.
 * 
 * @author Matija Frandolić
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Modification status.
	 */
	private boolean modified;
	
	/**
	 * Path of this document.
	 */
	private Path filePath;
	
	/**
	 * Text component that is used to edit this document.
	 */
	private JTextArea textComponent;
	
	/**
	 * List of listeners.
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Constructs a new {@code DefaultSingleDocumentModel} with the given path
	 * and text content.
	 * 
	 * @param path path to be set as the path of this document
	 * @param text text to be set as content of this document
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		modified = false;
		filePath = path;
		textComponent = new JTextArea(text);
		listeners = new ArrayList<SingleDocumentListener>();
		
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		filePath = Objects.requireNonNull(path, "Path to document cannot be null.");
		fireDocumentFilePathUpdated();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		fireDocumentModifyStatusUpdated();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Notifies all registered listeners that modification status changed.
	 */
	private void fireDocumentModifyStatusUpdated() {
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}
	
	/**
	 * Notifies all registered listeners that path changed.
	 */
	private void fireDocumentFilePathUpdated() {
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

}
