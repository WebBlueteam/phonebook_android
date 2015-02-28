/*
 * 
 */
package com.blueteam.phonebook.entities;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactObject.
 */
public class ContactObject {
	
	/** The id. */
	private Long id;
	
	/** The user id. */
	private Long userId;
	
	/** The lookup key. */
	private String lookupKey;
	
	/** The display name. */
	private String displayName;
	
	/** The first name. */
	private String firstName;
	
	/** The middle name. */
	private String middleName;
	
	/** The last name. */
	private String lastName;
	
	/** The list phone number. */
	private List<String> listPhoneNumber;
	
	/** The photo uri. */
	private String photoUri;
	
	/** The home email. */
	private String homeEmail;
	
	/** The work email. */
	private String workEmail;
	
	/**
	 * Instantiates a new contact object.
	 */
	public ContactObject() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new contact object.
	 *
	 * @param user the user
	 */
	public ContactObject(ContactObject user){
		this.id = user.getId();
		this.userId = user.getUserId();
		this.lookupKey = user.getLookupKey();
		this.displayName = user.getDisplayName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.middleName = user.getMiddleName();
		this.homeEmail = user.getHomeEmail();
		this.workEmail = user.getWorkEmail();
		this.photoUri = user.getPhotoUri();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the lookup key.
	 *
	 * @return the lookup key
	 */
	public String getLookupKey() {
		return lookupKey;
	}
	
	/**
	 * Sets the lookup key.
	 *
	 * @param lookupKey the new lookup key
	 */
	public void setLookupKey(String lookupKey) {
		this.lookupKey = lookupKey;
	}
	
	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Sets the display name.
	 *
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}
	
	/**
	 * Sets the middle name.
	 *
	 * @param middleName the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Gets the photo uri.
	 *
	 * @return the photo uri
	 */
	public String getPhotoUri() {
		return photoUri;
	}
	
	/**
	 * Sets the photo uri.
	 *
	 * @param photoUri the new photo uri
	 */
	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}
	
	/**
	 * Gets the home email.
	 *
	 * @return the home email
	 */
	public String getHomeEmail() {
		return homeEmail;
	}
	
	/**
	 * Sets the home email.
	 *
	 * @param homeEmail the new home email
	 */
	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}
	
	/**
	 * Gets the work email.
	 *
	 * @return the work email
	 */
	public String getWorkEmail() {
		return workEmail;
	}
	
	/**
	 * Sets the work email.
	 *
	 * @param workEmail the new work email
	 */
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	
	/**
	 * Gets the list phone number.
	 *
	 * @return the list phone number
	 */
	public List<String> getListPhoneNumber() {
		return listPhoneNumber;
	}
	
	/**
	 * Sets the list phone number.
	 *
	 * @param listPhoneNumber the new list phone number
	 */
	public void setListPhoneNumber(List<String> listPhoneNumber) {
		this.listPhoneNumber = listPhoneNumber;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ContactObject clone(){
		ContactObject user = new ContactObject();
		user.setId(id);
		user.setUserId(userId);
		user.setLookupKey(lookupKey);
		user.setDisplayName(displayName);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setHomeEmail(homeEmail);
		user.setWorkEmail(workEmail);
		user.setPhotoUri(photoUri);
		return user;
	}
}
