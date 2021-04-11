package hr.fer.zemris.java.hw15.web.forms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Model of a form which is web-representation of a domain model {@link BlogUser} 
 * and is used for login of existing users.
 * 
 * @author Matija Frandolić
 */
public class LoginForm {

	/**
	 * User's nick.
	 */
	private String nick;
	
	/**
	 * User's password.
	 */
	private String password;

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
		nick = prepare(req.getParameter("nick"));
		password = prepare(req.getParameter("password"));
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
	 * Validates this form. It is necessary to fill this form before validating it.
	 */
	public void validate() {
		errors.clear();
		
		if (nick.isEmpty()) {
			errors.put("nick", "Nick must not be empty.");
		} else if (nick.length() > 100) {
			errors.put("nick", "Length of nick must be less than 100.");			
		}
		
		if (password.isEmpty()) {
			errors.put("password", "Password must not be empty.");
		} else if (!nick.isEmpty()) {
			String passwordHash = null;
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-1");
				digest.update(password.getBytes());
				passwordHash = HexUtil.bytetohex(digest.digest());
			} catch (NoSuchAlgorithmException ignorable) {
				// ignore
			}
			
			BlogUser user = DAOProvider.getDAO().getUser(nick);
			if (user == null || !user.getPasswordHash().equals(passwordHash)) {
				errors.put("password", "Wrong password.");
			}
		}
	}

	/**
	 * Returns user's nick.
	 * 
	 * @return user's nick
	 */
	public String getNick() {
		return nick;
	}

}
