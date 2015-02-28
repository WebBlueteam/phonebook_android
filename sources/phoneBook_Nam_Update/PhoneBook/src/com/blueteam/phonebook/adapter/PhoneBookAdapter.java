package com.blueteam.phonebook.adapter;

import java.util.List;

import com.blueteam.phonebook.R;
import com.blueteam.phonebook.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PhoneBookAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<String> mListPhone;
	
	public PhoneBookAdapter(Context context, List<String> list){
		this.mContext = context;
		this.mListPhone = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;
		if(mListPhone != null && mListPhone.size() > 0){
			count = mListPhone.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListPhone.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if(convertView == null){	
			holder = new Holder();
			// change lay out item for list
			convertView = LayoutInflater.from(mContext).inflate(R.layout.header_lay, parent, false);
			Utils.setTypefaceRoboto(mContext, holder.mSTT, holder.mNameContact, holder.mNumberPhone);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}		
		return convertView;
	}
	
	public class Holder{
		TextView mSTT;
		TextView mNameContact;
		TextView mNumberPhone;
	}

}
