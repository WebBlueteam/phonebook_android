/*
 *
 */
package com.blueteam.phonebook.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blueteam.phonebook.R;
import com.blueteam.phonebook.entities.ContactObject;
import com.blueteam.phonebook.utils.PhoneUtils;
import com.blueteam.phonebook.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class PhoneBookAdapter.
 */
public class PhoneBookAdapter extends BaseAdapter{

	/** The m context. */
	private final Context mContext;

	/** The m list contact object. */
	private List<ContactObject> mListContactObject;

	/**
	 * Instantiates a new phone book adapter.
	 *
	 * @param context the context
	 * @param list the list
	 */
	public PhoneBookAdapter(Context context, List<ContactObject> list){
		this.mContext = context;
		this.mListContactObject = list;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;
		if(mListContactObject != null && mListContactObject.size() > 0){
			count = mListContactObject.size();
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListContactObject.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if(convertView == null){
			holder = new Holder();
			// change lay out item for list
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_contact_lay, parent, false);
			holder.mNameContact = (TextView)convertView.findViewById(R.id.item_for_contact_name_id);
			holder.mNumberPhone = (TextView)convertView.findViewById(R.id.item_for_contact_number_phone_id);
			Utils.setTypefaceRoboto(mContext, holder.mNameContact, holder.mNumberPhone);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		ContactObject contact = mListContactObject.get(position);
		holder.mNameContact.setText(contact.getDisplayName());
		String phone = PhoneUtils.getInstance(mContext).filterPhoneHomeList(contact.getListPhoneNumber()).get(0);
		holder.mNumberPhone.setText(phone);
		return convertView;
	}

	/**
	 * The Class Holder.
	 */
	public class Holder{

		/** The m name contact. */
		TextView mNameContact;

		/** The m number phone. */
		TextView mNumberPhone;
	}

	/**
	 * Gets the list contact object.
	 *
	 * @return the list contact object
	 */
	public List<ContactObject> getListContactObject() {
		return mListContactObject;
	}

	/**
	 * Sets the list contact object.
	 *
	 * @param listContact the new list contact object
	 */
	public void setListContactObject(List<ContactObject> listContact){
		mListContactObject = listContact;
	}
}
