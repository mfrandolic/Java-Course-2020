package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model of a blog entry.
 */
@Entity
@Table(name = "blog_entries")
public class BlogEntry {

	/**
	 * Unique id of this entry.
	 */
	private Long id;
	
	/**
	 * List of comments associated with this entry.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	
	/**
	 * Date when this entry was created.
	 */
	private Date createdAt;
	
	/**
	 * Date when this entry was last modified.
	 */
	private Date lastModifiedAt;
	
	/**
	 * Title of this entry.
	 */
	private String title;
	
	/**
	 * Text of this entry.
	 */
	private String text;
	
	/**
	 * Author of this entry.
	 */
	private BlogUser creator;
	
	/**
	 * Returns the unique id of this entry.
	 * 
	 * @return the unique id of this entry
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the unique id of this entry.
	 * 
	 * @param id the unique id of this entry
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the list of comments associated with this entry.
	 * 
	 * @return the list of comments associated with this entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, 
	           cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Sets the list of comments associated with this entry.
	 * 
	 * @param comments the list of comments associated with this entry
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns the date when this entry was created.
	 * 
	 * @return the date when this entry was created
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the date when this entry was created.
	 * 
	 * @param createdAt the date when this entry was created
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns the date when this entry was last modified.
	 * 
	 * @return the date when this entry was last modified
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the date when this entry was last modified.
	 * 
	 * @param lastModifiedAt the date when this entry was last modified
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns the title of this entry.
	 * 
	 * @return the title of this entry
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this entry.
	 * 
	 * @param title the title of this entry
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the text of this entry.
	 * 
	 * @return the text of this entry
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this entry.
	 * 
	 * @param text the text of this entry
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the author of this entry.
	 * 
	 * @return the author of this entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets the author of this entry.
	 * 
	 * @param creator the author of this entry
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
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
