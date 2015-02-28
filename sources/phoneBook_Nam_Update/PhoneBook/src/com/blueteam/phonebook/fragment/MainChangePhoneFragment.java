package com.blueteam.phonebook.fragment;

import java.util.ArrayList;
import java.util.List;

import com.blueteam.phonebook.R;
import com.blueteam.phonebook.adapter.PhoneBookAdapter;
import com.blueteam.phonebook.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainChangePhoneFragment extends Fragment implements OnClickListener, OnItemClickListener{
	
	private TextView mTextStatus;
	private ListView mListViewContact;
	private Button mRestoreBtn;
	private Button mBackupBtn;
	private CheckBox mCheckBackupContact;
	private PhoneBookAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_change_phone_book , container, false);;
		initView(view);
		return view;
	}
	
	public void initView(View v){
		mTextStatus = (TextView)v.findViewById(R.id.prefix_list_contact_status_id);
		mListViewContact = (ListView)v.findViewById(R.id.prefix_list_contact_id);
		mRestoreBtn = (Button)v.findViewById(R.id.restore_list_phone_id);
		mBackupBtn = (Button)v.findViewById(R.id.prefix_start_update_id);
		mCheckBackupContact = (CheckBox)v.findViewById(R.id.prefix_backup_id);
		
		Utils.setTypefaceRoboto(getActivity(), mTextStatus);
		
		mRestoreBtn.setOnClickListener(this);
		mBackupBtn.setOnClickListener(this);
		mCheckBackupContact.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					// checked checkbox
				}else{
					// unchecked checkbox
				}
			}
		});
		List<String> list = new ArrayList<String>();
		mAdapter = new PhoneBookAdapter(getActivity(), list);
		mListViewContact.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.restore_list_phone_id:
			
			break;
		case R.id.prefix_start_update_id:
			
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
}
