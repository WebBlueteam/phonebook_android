/*
 *
 */
package com.blueteam.phonebook.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.blueteam.phonebook.R;
import com.blueteam.phonebook.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomAlertDialog.
 */
@SuppressLint("NewApi")
public class CustomAlertDialog extends BaseDialogFragment {

    /** The m title text view. */
    private TextView mTitleTextView;

    /** The m content text view. */
    private TextView mContentTextView;

    /** The m yes button. */
    private Button mYesButton;

    /** The m on click yes listener. */
    private OnClickYesListener mOnClickYesListener;

    /** The m is touch out side dismiss. */
    private boolean mIsTouchOutSideDismiss = true;

    /** The m tile. */
    private String mTile;

    /** The m content. */
    private String mContent;

    /** The instance. */
    private static CustomAlertDialog instance;

    /**
     * Instantiates a new custom alert dialog.
     */
    private CustomAlertDialog(){

    }

    /**
     * Gets the single instance of CustomAlertDialog.
     *
     * @param title the title
     * @param content the content
     * @return single instance of CustomAlertDialog
     */
    public static CustomAlertDialog getInstance(String title, String content){
        TAG = "customalertdialog";
        if (instance == null) {
            instance = new CustomAlertDialog();
        }
        instance.mOnClickYesListener = null;
        instance.mTile = title;
        instance.mContent = content;
        return instance;
    }

    /**
     * Gets the single instance of CustomAlertDialog.
     *
     * @param title the title
     * @param content the content
     * @param isTouchOutSideDismiss the is touch out side dismiss
     * @return single instance of CustomAlertDialog
     */
    public static CustomAlertDialog getInstance(String title, String content,boolean isTouchOutSideDismiss){
        TAG = "customalertdialog";
        if (instance == null) {
            instance = new CustomAlertDialog();
        }
        instance.mOnClickYesListener = null;
        instance.mTile = title;
        instance.mContent = content;
        instance.mIsTouchOutSideDismiss = isTouchOutSideDismiss;
        return instance;
    }

    /* (non-Javadoc)
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.customdialog_alert_lay, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(mIsTouchOutSideDismiss);
        mTitleTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_title_id);
        mContentTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_content_id);
        mTitleTextView.setText(mTile);
        mContentTextView.setText(mContent);
        mYesButton = (Button)v.findViewById(R.id.custom_dialog_alert_button_yes_id);
        Utils.setTypefaceRoboto(getActivity(), mTitleTextView, mContentTextView, mYesButton);
        mYesButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mOnClickYesListener != null){
                    mOnClickYesListener.onClickYes();
                }
                dismiss();
            }
        });
        return v;
    }

    /**
     * Show dialog with a default message for activity.
     *
     * @param activity            the activity
     */
	public void show(Activity activity) {
        if (!findToShow(activity)) {
            instance.show(activity.getFragmentManager(), TAG);
        }
    }

    /**
     * The listener interface for receiving onClickYes events.
     * The class that is interested in processing a onClickYes
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnClickYesListener<code> method. When
     * the onClickYes event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OnClickYesEvent
     */
    public interface OnClickYesListener{

        /**
         * On click yes.
         */
        public void onClickYes();
    }

    /**
     * Gets the on click yes listener.
     *
     * @return the on click yes listener
     */
    public OnClickYesListener getOnClickYesListener() {
        return mOnClickYesListener;
    }

    /**
     * Sets the on click yes listener.
     *
     * @param mOnClickYesListener the new on click yes listener
     */
    public void setOnClickYesListener(OnClickYesListener mOnClickYesListener) {
        this.mOnClickYesListener = mOnClickYesListener;
    }
}
