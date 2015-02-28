/*
 *
 */
package com.blueteam.phonebook.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.blueteam.phonebook.R;
import com.blueteam.phonebook.adapters.PhoneBookAdapter;
import com.blueteam.phonebook.dialogs.CustomAlertDialog;
import com.blueteam.phonebook.dialogs.CustomProgressDialog;
import com.blueteam.phonebook.entities.ContactObject;
import com.blueteam.phonebook.utils.ContactUtils;
import com.blueteam.phonebook.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

// TODO: Auto-generated Javadoc
/**
 * The Class MainChangePhoneFragment.
 */
@SuppressLint("NewApi")
public class MainChangePhoneFragment extends Fragment implements OnClickListener{

	/** The m text status. */
	private TextView mTextStatus;

	/** The m list view contact. */
	private ListView mListViewContact;

	/** The m restore btn. */
	private Button mRestoreBtn;

	/** The m backup btn. */
	private Button mBackupBtn;

	/** The m check backup contact. */
	private CheckBox mCheckBackupContact;

	/** The m adapter. */
	private PhoneBookAdapter mAdapter;

	/** The m list contact object. */
	private List<ContactObject> mListContactObject;

	/** The m is backup. */
	private boolean mIsBackup = true;

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_change_phone_book , container, false);;
		initView(view);
		AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
		return view;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mListContactObject = new ArrayList<ContactObject>();
	}

	/**
	 * Inits the view.
	 *
	 * @param v the v
	 */
	public void initView(View v){
		mTextStatus = (TextView)v.findViewById(R.id.prefix_list_contact_status_id);
		mListViewContact = (ListView)v.findViewById(R.id.prefix_list_contact_id);
		mRestoreBtn = (Button)v.findViewById(R.id.restore_list_phone_id);
		mBackupBtn = (Button)v.findViewById(R.id.prefix_start_update_id);
		mCheckBackupContact = (CheckBox)v.findViewById(R.id.prefix_backup_id);

		mAdapter = new PhoneBookAdapter(getActivity(), mListContactObject);
		mListViewContact.setAdapter(mAdapter);

		Utils.setTypefaceRoboto(getActivity(), mTextStatus);

		mRestoreBtn.setOnClickListener(this);
		mBackupBtn.setOnClickListener(this);
		mCheckBackupContact.setChecked(true);
		mCheckBackupContact.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					// checked checkbox
					mIsBackup = true;
				}else{
					// unchecked checkbox
					mIsBackup = false;
				}
			}
		});
		new getListContact().execute();
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.restore_list_phone_id:
			new reStoreListContact().execute();
			break;
		case R.id.prefix_start_update_id:
			new updateListContact().execute();
			break;
		default:
			break;
		}
	}

	/**
	 * The Class getListContact.
	 */
	public class getListContact extends AsyncTask<Void, Void, List<ContactObject>>{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			CustomProgressDialog.getInstance().show(getActivity());
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected List<ContactObject> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<ContactObject> listContact = ContactUtils.getListContactHomePhoneFromDevice(getActivity());
			return listContact;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<ContactObject> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mListContactObject = result;
			mAdapter.setListContactObject(mListContactObject);
			mAdapter.notifyDataSetChanged();
			CustomProgressDialog.getInstance().dismiss(getActivity());
		}
	}

	/**
	 * The Class updateListContact.
	 */
	public class updateListContact extends AsyncTask<Void, Void, Boolean>
	{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			CustomProgressDialog.getInstance().show(getActivity());
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean isBackup = true;
			boolean result = false;
			if(mIsBackup){
				isBackup = ContactUtils.backupContactList(getActivity(), mListContactObject);
			}
			if(isBackup){
				result = ContactUtils.updateContactWithPrefixNew(getActivity(), mListContactObject);
			}
			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				new getListContact().execute();
				CustomAlertDialog alertDialog = CustomAlertDialog.getInstance(getActivity().getString(R.string.custom_alert_dialog_title_str), getActivity().getString(R.string.update_contact_list_surcess_str));
				alertDialog.show(getActivity());
				mRestoreBtn.setVisibility(View.VISIBLE);
			}else{
				CustomProgressDialog.getInstance().dismiss(getActivity());
				CustomAlertDialog alertDialog = CustomAlertDialog.getInstance(getActivity().getString(R.string.custom_alert_dialog_title_str), getActivity().getString(R.string.update_contact_list_fail_str));
				alertDialog.show(getActivity());
				mRestoreBtn.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * The Class reStoreListContact.
	 */
	public class reStoreListContact extends AsyncTask<Void, Void, Boolean>
	{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			CustomProgressDialog.getInstance().show(getActivity());
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean result = ContactUtils.restoreContactList(getActivity());
			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				new getListContact().execute();
				CustomAlertDialog alertDialog = CustomAlertDialog.getInstance(getActivity().getString(R.string.custom_alert_dialog_title_str), getActivity().getString(R.string.restore_contact_list_surcess_str));
				alertDialog.show(getActivity());
				mRestoreBtn.setVisibility(View.GONE);
			}else{
				CustomProgressDialog.getInstance().dismiss(getActivity());
				CustomAlertDialog alertDialog = CustomAlertDialog.getInstance(getActivity().getString(R.string.custom_alert_dialog_title_str), getActivity().getString(R.string.restore_contact_list_fail_str));
				alertDialog.show(getActivity());
				mRestoreBtn.setVisibility(View.GONE);
			}
		}
	}

}
