package main;

import java.util.ArrayList;
import java.util.List;

class Prefix {
	String codeCityOld;
	String codeCityNew;

	public Prefix(String old, String news) {
		codeCityOld = old;
		codeCityNew = news;
	}
}

public class main {
	private static final String[] DEFAULT_COUNTRY_CODE_VN = { "+84", "084",
			"84" };
	private static final String[] DEFAULT_MOBILE_CODE_VN = { "091", "91",
			"094", "94", "0123", "123" };

	/**
	 * check mobile phone
	 * 
	 * @param nums
	 * @param phone
	 * @return list phone is not mobile phone
	 */
	public List<String> filterPhoneHomeList(List<String> phone) {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < phone.size(); i++) {
			int k = 0;
			for (int j = 0; j < DEFAULT_MOBILE_CODE_VN.length; j++) {
				String newPhoneNumber = removeCountryCode(phone.get(i));
				if (newPhoneNumber.startsWith(DEFAULT_MOBILE_CODE_VN[j])) {
					k++;
					break;
				}
			}
			if (k == 0) {
				temp.add(phone.get(i));
			}
		}
		return temp;
	}

	/**
	 * get edit contact
	 * 
	 * @param phoneNumber
	 * @return String
	 */
	public String removeCountryCode(String phoneNumber) {
		for (int i = 0; i < DEFAULT_COUNTRY_CODE_VN.length; i++) {
			if (phoneNumber.startsWith(DEFAULT_COUNTRY_CODE_VN[i])) {
				return phoneNumber.substring(DEFAULT_COUNTRY_CODE_VN[i]
						.length());
			}
		}
		return phoneNumber;
	}

	/**
	 * get edit contact list
	 * 
	 * @param pre
	 * @param Number
	 * @return List<String>
	 */
	public List<String> editContactList(List<Prefix> pre, List<String> Number) {
		List<String> ContactPrefix = new ArrayList<String>();
		for (int i = 0; i < Number.size(); i++) {
			ContactPrefix
					.add(editContact(pre, removeCountryCode(Number.get(i))));
		}
		return ContactPrefix;
	}

	/**
	 * get edit a contact
	 * 
	 * @param prefix
	 * @param phoneNumber
	 * @return String
	 */
	public String editContact(List<Prefix> prefix, String phoneNumber) {
		for (int j = 0; j < prefix.size(); j++) {
			if (phoneNumber.startsWith(prefix.get(j).codeCityOld)) {
				String new_phone = prefix.get(j).codeCityNew
						+ phoneNumber.substring(prefix.get(j).codeCityOld.length());
				return new_phone;
			}
		}
		return phoneNumber;
	}

	public static void main(String[] agrs) {
		main a = new main();

		// list prefix mobile phone get from file json
		List<String> p = new ArrayList<String>();

		// phone phone get from contact
		List<String> phoneNumber = new ArrayList<String>();
		phoneNumber.add("01212199614"); // di dong 1
		phoneNumber.add("01265040909");// di dong 2
		phoneNumber.add("0723892052"); // ban 1
		phoneNumber.add("081251414");// ban 2
		phoneNumber.add("0841251414");// ban 3
		phoneNumber.add("0916521979");// di dong 3
		phoneNumber.add("0946521973");// di dong 4
		phoneNumber.add("0936521978");// di dong 5
		List<String> Number = a.filterPhoneHomeList(phoneNumber);

		// list prefix get from json
		List<Prefix> pre = new ArrayList<Prefix>();
		pre.add(new Prefix("125", "296"));
		pre.add(new Prefix("064", "254"));
		pre.add(new Prefix("0240", "204"));
		pre.add(new Prefix("0781", "291"));
		pre.add(new Prefix("0241", "222"));
		pre.add(new Prefix("075", "275"));
		pre.add(new Prefix("056", "256"));
		pre.add(new Prefix("072", "272"));
		pre.add(new Prefix("08", "28"));
		//
		List<String> result = a.editContactList(pre, Number);
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
	}
}
