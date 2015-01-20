/*
 * 
 */
package com.blueteam.phonebook.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class StringUtils.
 */
public class StringUtils {
	
	/**
	 * Checks if is empty.
	 *
	 * @param string the string
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String string){
		if(string != null && string.trim().length() > 0){
			return false;
		}
		return true;
	}
}
