package hr.fer.zemris.java.hw15.web.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Model of a form which is web-representation of a domain model {@link BlogEntry} 
 * and is used for adding new or editing existing blog entries.
 * 
 * @author Matija Frandolić
 */
public class EntryForm {

	/**
	 * Entry title.
	 */
	private String title;
	
	/**
	 * Entry content.
	 */
	private String text;

	/**
	 * Map of errors for each property of this form.
	 */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * Returns {@code true} if this form contains at least one error and 
	 * {@code false} if there are no errors.
	 * 
	 * @return {@code true} if this form contains at least one error and 
	 *         {@code false} if there are no errors
	 */
	public boolean errorsExist() {
		return !errors.isEmpty();
	}
	
	/**
	 * Returns error message for the given property or {@code null} if error
	 * message does not exist.
	 * 
	 * @param  name name of property for which to return error message
	 * @return      error message for the given property or {@code null} if error
	 *              message does not exist
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Fills this form with values of parameters of the given
	 * {@link HttpServletRequest}.
	 * 
	 * @param req {@code HttpServletRequest} with parameters which are used to
	 *            fill this form
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		title = prepare(req.getParameter("title"));
		text = prepare(req.getParameter("text"));
	}
	
	/**
	 * Returns an empty string if {@code null} was given, otherwise returns
	 * trimmed string.
	 * 
	 * @param  s string which to prepare
	 * @return   an empty string if {@code null} was given, otherwise trimmed string
	 */
	private static String prepare(String s) {
		return s == null ? "" : s.trim();
	}
	
	/**
	 * Fills this form with values of the given {@link BlogEntry} object.
	 * 
	 * @param entry {@code BlogEntry} object from which to fill this form
	 */
	public void fillFromEntry(BlogEntry entry) {
		title = entry.getTitle();
		text = entry.getText();
	}
	
	/**
	 * Fills the given {@link BlogEntry} object with data from this form. This 
	 * method should only be called if this form was validated previously and no
	 * errors were registered.
	 * 
	 * @param entry {@code BlogEntry} object to be filled with data from this form
	 */
	public void fillIntoEntry(BlogEntry entry) {
		entry.setTitle(title);
		entry.setText(text);
	}

	/**
	 * Validates this form. It is necessary to fill this form before validating it.
	 */
	public void validate() {
		errors.clear();
		
		if (title.isEmpty()) {
			errors.put("title", "Title must not be empty.");
		} else if (title.length() > 200) {
			errors.put("title", "Length of title must be less than 200.");			
		}
		
		if (text.isEmpty()) {
			errors.put("text", "Text must not be empty.");
		} else if (text.length() > 4096) {
			errors.put("text", "Length of text must be less than 4096.");			
		}
	}

	/**
	 * Returns entry title.
	 * 
	 * @return entry title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns entry content.
	 * 
	 * @return entry content.
	 */
	public String getText() {
		return text;
	}

}
