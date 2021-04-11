package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Model of a single document with information about the path from which the 
 * document was loaded, document modification status and a reference to
 * {@link JTextArea} which is used for editing.
 * 
 * @author Matija Frandolić
 */
public interface SingleDocumentModel {

	/**
	 * Returns the {@code JTextArea} used for editing this document.
	 * 
	 * @return the {@code JTextArea} used for editing this document
	 */
	JTextArea getTextComponent();
	
	/**
	 * Returns the path from which the document was loaded or {@code null}
	 * if this document is newly created.
	 * 
	 * @return the path from which the document was loaded or {@code null}
	 *         if this document is newly created
	 */
	Path getFilePath();
	
	/**
	 * Sets the new path of this document. The given path cannot be {@code null}.
	 * 
	 * @param  path path to be set as a new path of this document
	 * @throws NullPointerException if the given path is {@code null}
	 */
	void setFilePath(Path path);
	
	/**
	 * Returns {@code true} if this document was modified or {@code false}
	 * if it wasn't.
	 * 
	 * @return {@code true} if this document was modified, {@code false} otherwise
	 */
	boolean isModified();
	
	/**
	 * Sets the modification status of this document.
	 * 
	 * @param modified new modification status of this document: {@code true} indicates
	 *                 that document was modified and {@code false} that it wasn't
	 */
	void setModified(boolean modified);

	/**
	 * Adds the given {@link SingleDocumentListener} that will be notified
	 * about the changes to the modification status and path of this document.
	 * 
	 * @param l listener to be added to this document model
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes the given {@link SingleDocumentListener} that was previously 
	 * registered to this document model.
	 * 
	 * @param l listener to be removed from this document model
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
	
}
