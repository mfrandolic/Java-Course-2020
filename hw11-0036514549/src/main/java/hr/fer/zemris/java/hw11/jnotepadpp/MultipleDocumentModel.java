package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Model of an object capable of holding zero, one or more {@link SingleDocumentModel}s,
 * with methods for creating new documents, opening existing documents, saving
 * documents and closing them, all by keeping track of the current document.
 * This model can hold a single document for each different path but can have
 * multiple documents with no path associated.
 * 
 * @author Matija Frandolić
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document and returns it.
	 * 
	 * @return newly created document
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Returns the current document.
	 * 
	 * @return the current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads document from the given path, which cannot be {@code null}, and 
	 * returns it.
	 * 
	 * @param  path path from which to load the document
	 * @return      document that was loaded from the given path
	 * @throws NullPointerException if the given path is {@code null}
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the given document to the given path and updates the path of the 
	 * document. The given path can be {@code null}, in which case the document 
	 * is saved using the path associated from the document. If this method
	 * is called with path of some document that is already contained in this
	 * model, the method will fail.
	 * 
	 * @param  model   document that is saved
	 * @param  newPath path to which the given document is saved
	 * @throws IllegalArgumentException if the given path is a path of some document
	 *                                  that is already contained in this model
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes the given document by removing it from documents kept by this model.
	 * 
	 * @param model document that is closed
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds the given {@link MultipleDocumentListener} that will be notified
	 * when a document is added or removed from this model or when the current 
	 * document is changed.
	 * 
	 * @param l listener to be added to this document model
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes the given {@link MultipleDocumentListener} that was previously 
	 * registered to this model.
	 * 
	 * @param l listener to be removed from this document model
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Returns the number of documents kept by this model.
	 * 
	 * @return the number of documents kept by this model
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns the document at the given index. Valid indexes are from 0 to
	 * {@code MultipleDocumentModel#getNumberOfDocuments() - 1}.
	 * 
	 * @param  index index at which is the wanted document
	 * @return       document at the given index
	 * @throws IllegalArgumentException if the index is invalid
	 */
	SingleDocumentModel getDocument(int index);

}
