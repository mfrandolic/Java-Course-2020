package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Model of a blog user.
 * 
 * @author Matija Frandolić
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {

	/**
	 * Unique id of this user.
	 */
	private Long id;
	
	/**
	 * First name of this user.
	 */
	private String firstName;
	
	/**
	 * Last name of this user.
	 */
	private String lastName;
	
	/**
	 * Nick of this user.
	 */
	private String nick;
	
	/**
	 * Email of this user.
	 */
	private String email;
	
	/**
	 * SHA-1 hash of this user's password.
	 */
	private String passwordHash;
	
	/**
	 * Entries written by this user.
	 */
	private List<BlogEntry> entries = new ArrayList<>();
	
	/**
	 * Returns the unique id of this user.
	 * 
	 * @return the unique id of this user
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the unique id of this user. 
	 * 
	 * @param id the unique id of this user
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns the first name of this user.
	 * 
	 * @return the first name of this user
	 */
	@Column(length = 100, nullable = false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name of this user.
	 * 
	 * @param firstName the first name of this user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns the last name of this user.
	 * 
	 * @return the last name of this user
	 */
	@Column(length = 100, nullable = false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name of this user.
	 * 
	 * @param lastName the last name of this user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Returns the nick of this user.
	 * 
	 * @return the nick of this user
	 */
	@Column(length = 100, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Sets the nick of this user.
	 * 
	 * @param nick the nick of this user
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Returns the email of this user.
	 * 
	 * @return the email of this user
	 */
	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email of this user.
	 * 
	 * @param email the email of this user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns the SHA-1 hash of this user's password.
	 * 
	 * @return the SHA-1 hash of this user's password
	 */
	@Column(length = 100, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Sets the SHA-1 hash of this user's password.
	 * 
	 * @param passwordHash the SHA-1 hash of this user's password
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Returns the list of entries written by this user.
	 * 
	 * @return the list of entries written by this user
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, 
	           cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("lastModifiedAt")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets the list of entries written by this user.
	 * 
	 * @param entries the list of entries written by this user
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;			
		}
		if (obj == null) {
			return false;			
		}
		if (getClass() != obj.getClass()) {
			return false;			
		}
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null) {
				return false;				
			}
		} else if (!id.equals(other.id)) {
			return false;			
		}
		return true;
	}
	
}
