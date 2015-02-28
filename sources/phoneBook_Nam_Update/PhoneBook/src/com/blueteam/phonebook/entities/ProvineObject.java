/*
 * 
 */
package com.blueteam.phonebook.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class ProvineObject.
 */
public class ProvineObject {
	
	/** The name. */
	private String name;
	
	/** The old code. */
	private String oldCode;
	
	/** The new code. */
	private String newCode;
	
	/**
	 * Instantiates a new provine object.
	 *
	 * @param name the name
	 * @param oldCode the old code
	 * @param newCode the new code
	 */
	public ProvineObject(String name, String oldCode, String newCode) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.oldCode = oldCode;
		this.newCode = newCode;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the old code.
	 *
	 * @return the old code
	 */
	public String getOldCode() {
		return oldCode;
	}
	
	/**
	 * Sets the old code.
	 *
	 * @param oldCode the new old code
	 */
	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
	
	/**
	 * Gets the new code.
	 *
	 * @return the new code
	 */
	public String getNewCode() {
		return newCode;
	}
	
	/**
	 * Sets the new code.
	 *
	 * @param newCode the new new code
	 */
	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}
}
