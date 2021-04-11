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
 * and is used for registration of new users.
 * 
 * @author Matija Frandolić
 */
public class RegistrationForm {

	/**
	 * User's first name.
	 */
	private String firstName;
	
	/**
	 * User's last name.
	 */
	private String lastName;
	
	/**
	 * User's email.
	 */
	private String email;
	
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
		firstName = prepare(req.getParameter("firstName"));
		lastName = prepare(req.getParameter("lastName"));
		email = prepare(req.getParameter("email"));
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
	 * Fills the data from this form into the given {@link BlogUser} object.
	 * SHA-1 hash is calculated and stored instead of plain password. This 
	 * method should only be called if this form was validated previously and no
	 * errors were registered.
	 * 
	 * @param user {@code BlogUser} object which to fill with data from this form
	 */
	public void fillIntoUser(BlogUser user) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(password.getBytes());
			String passwordHash = HexUtil.bytetohex(digest.digest());
			user.setPasswordHash(passwordHash);
		} catch (NoSuchAlgorithmException ignorable) {
			// ignore
		}
	}

	/**
	 * Validates this form. It is necessary to fill this form before validating it.
	 */
	public void validate() {
		errors.clear();
		
		if (firstName.isEmpty()) {
			errors.put("firstName", "First name must not be empty.");
		} else if (firstName.length() > 100) {
			errors.put("firstName", "Length of first name must be less than 100.");			
		}
		
		if (lastName.isEmpty()) {
			errors.put("lastName", "Last name must not be empty.");
		} else if (lastName.length() > 100) {
			errors.put("lastName", "Length of last name must be less than 100.");			
		}

		if (email.isEmpty()) {
			errors.put("email", "Email must not be empty.");
		} else if (email.length() > 100) {
			errors.put("email", "Length of email must be less than 100.");			
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "Invalid email format.");
			}
		}
		
		if (nick.isEmpty()) {
			errors.put("nick", "Nick must not be empty.");
		} else if (nick.length() > 100) {
			errors.put("nick", "Length of nick must be less than 100.");			
		} else if (DAOProvider.getDAO().getUser(nick) != null) {
			errors.put("nick", "Nick is already taken.");
		}
		
		if (password.isEmpty()) {
			errors.put("password", "Password must not be empty.");
		}
	}

	/**
	 * Returns user's first name.
	 * 
	 * @return user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns user's last name.
	 * 
	 * @return user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns user's email.
	 * 
	 * @return user's email
	 */
	public String getEmail() {
		return email;
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
