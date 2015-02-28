/*
 *
 */
package com.blueteam.phonebook.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;

import com.blueteam.phonebook.entities.ContactObject;
import com.blueteam.phonebook.entities.ProvineObject;

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
	 * Gets the list contact home phone from device.
	 *
	 * @param context the context
	 * @return the list contact home phone from device
	 */
	public static List<ContactObject> getListContactHomePhoneFromDevice(Context context){
		List<ContactObject> listContactHome = new ArrayList<ContactObject>();
		List<ContactObject> listContact = getListContactFromDevice(context);
		PhoneUtils phoneUtils = PhoneUtils.getInstance(context);
		if(listContact != null){
			for(ContactObject contact: listContact){
				List<String> phone = contact.getListPhoneNumber();
				List<String> homePhone = phoneUtils.filterPhoneHomeList(phone);
				if(homePhone != null && homePhone.size() > 0){
					listContactHome.add(contact);
				}
			}
		}
		return listContactHome;
	}

	/**
	 * Update contact with prefix new.
	 *
	 * @param context the context
	 * @param listContact the list contact
	 * @return true, if successful
	 */
	public static boolean updateContactWithPrefixNew(Context context, List<ContactObject> listContact){
		List<ProvineObject> listPrefix = FileAssetsUtils.getProviceFromJsonFile(context.getAssets());
		PhoneUtils phoneUtils = PhoneUtils.getInstance(context);
		if(listContact != null && listPrefix != null){
			for(ContactObject contact: listContact){
				List<String> phoneNew = phoneUtils.editPhoneList(listPrefix, contact.getListPhoneNumber());
				if(phoneNew != null & phoneNew.size() > 0){
					contact.setListPhoneNumber(phoneNew);
					updateContactToContactSytem(context, contact);
				}
			}
		}
		return true;
	}

	/**
	 * Backup contact list.
	 *
	 * @param context the context
	 * @param listContact the list contact
	 * @return true, if successful
	 */
	public static boolean backupContactList(Context context, List<ContactObject> listContact){
		File file = new File(context.getFilesDir(), Constants.BACKUP_CONTACT_FILE_NAME);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			if(listContact != null){
				for(ContactObject contact: listContact){
					writer.append(contact.getId().toString());
					if(contact.getListPhoneNumber() != null){
						for(String phone: contact.getListPhoneNumber()){
							writer.append(Constants.COMMA_DELIMITER);
						    writer.append(phone);
						}
					}
					writer.append(Constants.NEW_LINE_SEPARATOR);
				}
			}
		    return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Load contact list from file.
	 *
	 * @param context the context
	 * @return the list
	 */
	public static List<ContactObject> loadContactListFromFile(Context context){
		List<ContactObject> listContact = new ArrayList<ContactObject>();
		File file = new File(context.getFilesDir(), Constants.BACKUP_CONTACT_FILE_NAME);
		if(file.exists()){
			BufferedReader fileReader = null;
			String line = "";
			try {
	            fileReader = new BufferedReader(new FileReader(file));
	            while ((line = fileReader.readLine()) != null) {
	                String[] tokens = line.split(Constants.COMMA_DELIMITER);
	                if (tokens.length > 0) {
	                	ContactObject contact = new ContactObject();
	                	contact.setId(Long.valueOf(tokens[0]));
	                	List<String> phoneNumber = new ArrayList<String>();
	                	for(int i = 1; i < tokens.length; i++){
	                		phoneNumber.add(tokens[i]);
	                	}
	                	contact.setListPhoneNumber(phoneNumber);
	                	listContact.add(contact);
					}
	            }
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				try {
					fileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return listContact;
	}

	/**
	 * Restore contact list.
	 *
	 * @param pContext the context
	 * @param listContact the list contact
	 * @return true, if successful
	 */
	public static boolean restoreContactList(Context pContext){
		List<ContactObject> listContact = loadContactListFromFile(pContext);
		if(listContact != null){
			for(ContactObject contact: listContact){
				if(contact.getListPhoneNumber() != null && contact.getListPhoneNumber().size() > 0){
					updateContactToContactSytem(pContext, contact);
				}
			}
		}
		return true;
	}

	/**
	 * Update contact to contact sytem.
	 *
	 * @param pContext the context
	 * @param contact the contact
	 * @return true, if successful
	 */
	public static boolean updateContactToContactSytem(Context pContext, ContactObject contact){
		if(pContext != null && contact != null && contact.getListPhoneNumber() != null){
			String iContactId = contact.getId().toString();
	        String iRawContactId = ContactUtils.getRawContactIdFromSystem(pContext,iContactId);
	        deletePhoneNumberToContactSystem(pContext,iContactId);
	        for(String iPhoneNew : contact.getListPhoneNumber()){
	            insertPhoneNumberToContactSytem(pContext, iRawContactId,iPhoneNew);
	        }
	        return true;
		}
        return false;
	}

	/**
	 * Insert phone number to contact sytem.
	 *
	 * @param iContext the i context
	 * @param iRawContactId the i raw contact id
	 * @param iPhoneNumber the i phone number
	 */
	public static void insertPhoneNumberToContactSytem(Context iContext,String iRawContactId, String iPhoneNumber) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, iRawContactId);
        contentValues.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER,
                iPhoneNumber);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValues(contentValues)
                .withYieldAllowed(true)
                .build());
        try {
            iContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Delete phone number to contact system.
	 *
	 * @param iContext the i context
	 * @param iContactId the i contact id
	 */
	public static void deletePhoneNumberToContactSystem(Context iContext, String iContactId) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String where = ContactsContract.Data.CONTACT_ID + "=? AND "
                + ContactsContract.Data.MIMETYPE + "=?";
        String[] params = new String[] {
                iContactId,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE };
        ops.add(ContentProviderOperation.newDelete(Data.CONTENT_URI)
                .withSelection(where, params).build());
        try {
            iContext.getContentResolver().applyBatch(
                    ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Gets the raw contact id from system.
	 *
	 * @param pContext the context
	 * @param pContactId the contact id
	 * @return the raw contact id from system
	 */
	public static String getRawContactIdFromSystem(Context pContext,String pContactId) {
        String mRawContactId = null;
        try {
            String[] projection=new String[]{ContactsContract.RawContacts._ID};
            String whereRawId=ContactsContract.RawContacts.CONTACT_ID+ " =?";
            String[] whereRawIdArgs=new String[]{pContactId};
            Cursor iCursorContactId= pContext.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,projection,whereRawId,whereRawIdArgs , null);
            while (iCursorContactId.moveToNext()) {
                mRawContactId = String.valueOf(iCursorContactId.getInt(iCursorContactId.getColumnIndex(ContactsContract.RawContacts._ID)));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mRawContactId;
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
