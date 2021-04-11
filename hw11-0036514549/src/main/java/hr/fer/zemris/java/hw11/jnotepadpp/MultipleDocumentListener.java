package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Model of an object which performs some action when a document is added or removed 
 * from the {@link MultipleDocumentModel} this listener is registered to or when 
 * the current document of that model is changed.
 * 
 * @author Matija Frandolić
 */
public interface MultipleDocumentListener {

	/**
	 * Action that is performed when the current document of {@code MultipleDocumentModel} 
	 * object to which this listener is registered to changes.
	 * 
	 * @param previousModel document that was previously the current document
	 * @param currentModel  the current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, 
			                    SingleDocumentModel currentModel);
	
	/**
	 * Action that is performed when a document is added to the 
	 * {@code MultipleDocumentModel} object to which this listener is registered to.
	 * 
	 * @param model {@code MultipleDocumentModel} to which a document was added
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Action that is performed when a document is removed from the 
	 * {@code MultipleDocumentModel} object to which this listener is registered to.
	 * 
	 * @param model {@code MultipleDocumentModel} from which a document was removed
	 */
	void documentRemoved(SingleDocumentModel model);

}
