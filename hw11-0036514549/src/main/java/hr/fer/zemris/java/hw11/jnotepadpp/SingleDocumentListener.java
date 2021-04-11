package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Model of an object which performs some action when it is notified about
 * the changes to the modification status or path of {@link SingleDocumentModel} 
 * object to which this listener is registered to.
 * 
 * @author Matija Frandolić
 */
public interface SingleDocumentListener {

	/**
	 * Action that is performed when this listener is notified about modification 
	 * status change of {@code SingleDocumentModel} object to which this listener
	 * is registered to.
	 * 
	 * @param model {@code SingleDocumentModel} whose modification status changed
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Action that is performed when this listener is notified about path change 
	 * of {@code SingleDocumentModel} object to which this listener is registered to.
	 * 
	 * @param model {@code SingleDocumentModel} whose path changed
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
	
}
