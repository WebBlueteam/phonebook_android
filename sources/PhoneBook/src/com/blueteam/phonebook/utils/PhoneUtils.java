/*
 *
 */
package com.blueteam.phonebook.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.blueteam.phonebook.entities.ProvineObject;

// TODO: Auto-generated Javadoc
/**
 * The Class PhoneUtils.
 */
public class PhoneUtils {

	/** The m list mobile code. */
	private final List<String> mListMobileCode;

	/** The instance. */
	private static PhoneUtils instance;

	/** The m context. */
	private Context mContext;

	/**
	 * Gets the single instance of PhoneUtils.
	 *
	 * @param context the context
	 * @return single instance of PhoneUtils
	 */
	public static PhoneUtils getInstance(Context context){
		if(instance == null){
			instance = new PhoneUtils(context);
		}
		instance.mContext = context;
		return instance;
	}

	/**
	 * Instantiates a new phone utils.
	 *
	 * @param context the context
	 */
	private PhoneUtils(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mListMobileCode = FileAssetsUtils.getListMobileCode(mContext.getAssets());
	}

	/**
	 * Filter phone home list.
	 *
	 * @param phone the phone
	 * @return the list
	 */
	public List<String> filterPhoneHomeList(List<String> phone) {
		List<String> temp = new ArrayList<String>();
		if(phone != null && mListMobileCode != null){
			for (int i = 0; i < phone.size(); i++) {
				int k = 0;
				for (int j = 0; j < mListMobileCode.size(); j++) {
					String newPhoneNumber = removeCountryCode(phone.get(i));
					if (newPhoneNumber.startsWith(mListMobileCode.get(j))) {
						k++;
						break;
					} 
				}
				if (k == 0) {
					temp.add(phone.get(i));
				}
			}
		}
		return temp;
	}

	/**
	 * Removes the country code.
	 *
	 * @param phoneNumber the phone number
	 * @return the string
	 */
	public String removeCountryCode(String phoneNumber) {
		for (int i = 0; i < Constants.DEFAULT_COUNTRY_CODE_VN.length; i++) {
			if (phoneNumber.startsWith(Constants.DEFAULT_COUNTRY_CODE_VN[i])) {
				return phoneNumber.substring(Constants.DEFAULT_COUNTRY_CODE_VN[i]
						.length());
			}
		}
		return phoneNumber;
	}

	/**
	 * Edits the phone list.
	 *
	 * @param pre the pre
	 * @param Number the number
	 * @return the list
	 */
	public List<String> editPhoneList(List<ProvineObject> pre, List<String> Number) {
		List<String> ContactPrefix = new ArrayList<String>();
		if(pre != null && Number != null){
			for (int i = 0; i < Number.size(); i++) {
				String tmp = normalizePhoneNumber(Number.get(i));
				ContactPrefix.add(editPhone(pre, removeCountryCode(tmp)));
			}
		}
		return ContactPrefix;
	}

	/**
	 * Edits the phone.
	 *
	 * @param prefix the prefix
	 * @param phoneNumber the phone number
	 * @return the string
	 */
	public String editPhone(List<ProvineObject> prefix, String phoneNumber) {
		for (int j = 0; j < prefix.size(); j++) {
			if( checkEditedPhone(prefix, phoneNumber) == true ){
				return phoneNumber;
			}
			if ( phoneNumber.startsWith(prefix.get(j).getOldCode()) ) {
				String new_phone = "0" + prefix.get(j).getNewCode()
						+ phoneNumber.substring(prefix.get(j).getOldCode().length());
				return new_phone;
			}
			
			else if ( phoneNumber.startsWith("0"+prefix.get(j).getOldCode())) {
				String new_phone = "0" + prefix.get(j).getNewCode()
						+ phoneNumber.substring(prefix.get(j).getOldCode().length()+1);
				return new_phone;
			}
		}
		return phoneNumber;
	}
	public boolean checkEditedPhone( List<ProvineObject> prefix, String phoneNumber ) {
		for (int j = 0; j < prefix.size(); j++) {
			if ( phoneNumber.startsWith("0"+prefix.get(j).getNewCode()) ) {
				return true;
			} else if (phoneNumber.startsWith("+84"+prefix.get(j).getNewCode())){
				return true;
			} else if (phoneNumber.startsWith("84"+prefix.get(j).getNewCode())){
				return true;
			}
		}
		return false;
	}
	public String normalizePhoneNumber(String iPhoneNumber){
    	String iReplace = iPhoneNumber.replace("(", " ");
		String iReplace1 = iReplace.replace(")", " ");
		String iReplace2 = iReplace1.replace("-", " ");
		String iReplace3 = iReplace2.replace(" ", "");
		String iPhoneOutput = iReplace3.trim();
    	return iPhoneOutput;
    }
}
