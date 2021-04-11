package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model of a blog comment.
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Unique id of this comment.
	 */
	private Long id;
	
	/**
	 * Entry on which this comment was posted.
	 */
	private BlogEntry blogEntry;
	
	/**
	 * Email of user who posted this comment.
	 */
	private String usersEmail;
	
	/**
	 * Content of this comment.
	 */
	private String message;
	
	/**
	 * Date when this comment was posted.
	 */
	private Date postedOn;
	
	/**
	 * Returns the unique id of this comment.
	 * 
	 * @return the unique id of this comment
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the unique id of this comment.
	 * 
	 * @param id the unique id of this comment
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the entry on which this comment was posted.
	 * 
	 * @return the entry on which this comment was posted
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets the entry on which this comment was posted.
	 * 
	 * @param blogEntry the entry on which this comment was posted
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns the email of user who posted this comment.
	 * 
	 * @return the email of user who posted this comment
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEmail() {
		return usersEmail;
	}

	/**
	 * Sets the email of user who posted this comment.
	 * 
	 * @param usersEmail the email of user who posted this comment
	 */
	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
	}

	/**
	 * Returns the content of this comment.
	 * 
	 * @return the content of this comment
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the content of this comment.
	 * 
	 * @param message the content of this comment
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the date when this comment was posted.
	 * 
	 * @return the date when this comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the date when this comment was posted.
	 * 
	 * @param postedOn the date when this comment was posted
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
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
