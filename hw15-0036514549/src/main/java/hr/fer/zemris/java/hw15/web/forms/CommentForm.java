package hr.fer.zemris.java.hw15.web.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogComment;

/**
 * Model of a form which is web-representation of a domain model {@link BlogComment} 
 * and is used for posting new comments.
 * 
 * @author Matija Frandolić
 */
public class CommentForm {

	/**
	 * Comment content.
	 */
	private String message;

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
		message = prepare(req.getParameter("message"));
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
	 * Fills the given {@link BlogComment} object with data from this form. This 
	 * method should only be called if this form was validated previously and no
	 * errors were registered.
	 * 
	 * @param comment {@code BlogComment} object to be filled with data from this form
	 */
	public void fillIntoComment(BlogComment comment) {
		comment.setMessage(message);
	}

	/**
	 * Validates this form. It is necessary to fill this form before validating it.
	 */
	public void validate() {
		errors.clear();
		
		if (message.isEmpty()) {
			errors.put("message", "Message must not be empty.");
		} else if (message.length() > 4096) {
			errors.put("nick", "Length of message must be less than 4096.");			
		}
	}

	/**
	 * Return comment content.
	 * 
	 * @return comment content
	 */
	public String getMessage() {
		return message;
	}

}
