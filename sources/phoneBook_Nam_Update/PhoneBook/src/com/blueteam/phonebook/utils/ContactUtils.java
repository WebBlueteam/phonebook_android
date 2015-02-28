/*
 * 
 */
package com.blueteam.phonebook.utils;

import java.util.ArrayList;
import java.util.List;

import com.blueteam.phonebook.entities.ContactObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactUtils.
 */
public class ContactUtils {
	
	/**
	 * Gets the list contact from device.
	 *
	 * @param context the context
	 * @return the list contact from device
	 */
	public static List<ContactObject> getListContactFromDevice(Context context){
		Cursor cursor = context.getContentResolver().query(ContactsQuery.CONTENT_URI, ContactsQuery.PROJECTION, 
				ContactsQuery.SELECTION, null, ContactsQuery.SORT_ORDER);
		if(cursor != null && cursor.getCount() >0){
			List<ContactObject> list = new ArrayList<ContactObject>();
			Long id;
			String displayName;
			String firstName = null;
			String middleName = null;
			String lastName = null;
			List<String> listPhoneNumber;
			String homeMail = null;
			String workMail = null;
			String photoUri = null;
			while(cursor.moveToNext()){
				id = cursor.getLong(ContactsQuery.ID);
				displayName = cursor.getString(ContactsQuery.DISPLAY_NAME);
				photoUri = cursor.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA);
				
				//Get name
				String[] projection = new String[] {ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME};
				String where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
				String[] whereParameters = new String[]{id.toString(), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
				Cursor nameCursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, where, whereParameters, null);
				if (nameCursor.moveToFirst()) { 
					firstName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
					middleName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
	                lastName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
				} 
				nameCursor.close();
				
				//Get phone
				listPhoneNumber = new ArrayList<String>();
				Cursor phonesCursor = context.getContentResolver().query(Phone.CONTENT_URI, null,
				        Phone.CONTACT_ID + " = " + id, null, null);
			    while (phonesCursor.moveToNext()) {
			        String number = phonesCursor.getString(phonesCursor.getColumnIndex(Phone.NUMBER));
			        listPhoneNumber.add(number);
			    }
			    phonesCursor.close();
			    
			    //Get mail
			    Cursor emailCur = context.getContentResolver().query( 
			    	    Email.CONTENT_URI, 
			    	    null,
			    	    Email.CONTACT_ID + " = ?", 
			    	    new String[]{id.toString()}, null); 
		    	while (emailCur.moveToNext()) { 
		    	    String email = emailCur.getString(
		    	                  emailCur.getColumnIndex(Email.DATA));
		    	    int emailType = emailCur.getInt(
		    	                  emailCur.getColumnIndex(Email.TYPE));
		    	    switch (emailType) {
		            case Email.TYPE_HOME:
		                homeMail = email;
		                break;
		            case Phone.TYPE_WORK:
		                workMail = email;
		                break;
		    	    }
		    	} 
		    	emailCur.close();
				
		    	ContactObject c = new ContactObject();
				c.setId(id);
				c.setDisplayName(displayName);
				c.setFirstName(firstName);
				c.setMiddleName(middleName);
				c.setLastName(lastName);
				c.setListPhoneNumber(listPhoneNumber);
				c.setHomeEmail(homeMail);
				c.setWorkEmail(workMail);
				c.setPhotoUri(photoUri);
				list.add(c);
			}
			cursor.close();
			return list;
		}
		return null;
		
	}
	
	/**
	 * The Interface ContactsQuery.
	 */
	public interface ContactsQuery {

        // An identifier for the loader
        /** The Constant QUERY_ID. */
        final static int QUERY_ID = 1;

        // A content URI for the Contacts table
        /** The Constant CONTENT_URI. */
        final static Uri CONTENT_URI = Contacts.CONTENT_URI;

        // The search/filter query Uri
        /** The Constant FILTER_URI. */
        final static Uri FILTER_URI = Contacts.CONTENT_FILTER_URI;

        // The selection clause for the CursorLoader query. The search criteria defined here
        // restrict results to contacts that have a display name and are linked to visible groups.
        // Notice that the search on the string provided by the user is implemented by appending
        // the search string to CONTENT_FILTER_URI.
        /** The Constant SELECTION. */
        @SuppressLint("InlinedApi")
        final static String SELECTION =
                (Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME) +
                "<>''" + " AND " + Contacts.IN_VISIBLE_GROUP + "=1";

        // The desired sort order for the returned Cursor. In Android 3.0 and later, the primary
        // sort key allows for localization. In earlier versions. use the display name as the sort
        // key.
        /** The Constant SORT_ORDER. */
        @SuppressLint("InlinedApi")
        final static String SORT_ORDER =
        		Utils.hasHoneycomb() ? Contacts.SORT_KEY_PRIMARY : Contacts.DISPLAY_NAME;

        // The projection for the CursorLoader query. This is a list of columns that the Contacts
        // Provider should return in the Cursor.
        /** The Constant PROJECTION. */
        @SuppressLint("InlinedApi")
        final static String[] PROJECTION = {

                // The contact's row id
                Contacts._ID,

                // A pointer to the contact that is guaranteed to be more permanent than _ID. Given
                // a contact's current _ID value and LOOKUP_KEY, the Contacts Provider can generate
                // a "permanent" contact URI.
                Contacts.LOOKUP_KEY,

                // In platform version 3.0 and later, the Contacts table contains
                // DISPLAY_NAME_PRIMARY, which either contains the contact's displayable name or
                // some other useful identifier such as an email address. This column isn't
                // available in earlier versions of Android, so you must use Contacts.DISPLAY_NAME
                // instead.
                Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME,

                // In Android 3.0 and later, the thumbnail image is pointed to by
                // PHOTO_THUMBNAIL_URI. In earlier versions, there is no direct pointer; instead,
                // you generate the pointer from the contact's ID value and constants defined in
                // android.provider.ContactsContract.Contacts.
                Utils.hasHoneycomb() ? Contacts.PHOTO_THUMBNAIL_URI : Contacts._ID,

                // The sort order column for the returned Cursor, used by the AlphabetIndexer
                SORT_ORDER,
        };

        // The query column numbers which map to each value in the projection
        /** The Constant ID. */
        final static int ID = 0;
        
        /** The Constant LOOKUP_KEY. */
        final static int LOOKUP_KEY = 1;
        
        /** The Constant DISPLAY_NAME. */
        final static int DISPLAY_NAME = 2;
        
        /** The Constant PHOTO_THUMBNAIL_DATA. */
        final static int PHOTO_THUMBNAIL_DATA = 3;
        
        /** The Constant SORT_KEY. */
        final static int SORT_KEY = 4;
    }
}
