package com.blueteam.phonebook.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.blueteam.phonebook.R;
import com.blueteam.phonebook.dialog.CustomAlertDialog;
import com.blueteam.phonebook.utils.Utils;

@SuppressLint("NewApi") public class CustomAlertDialog extends DialogFragment {

    /** The Constant TAG. */
    private static final String TAG = "customalertdialog";

    /** The m title text view. */
    private TextView mTitleTextView;

    /** The m content text view. */
    private TextView mContentTextView;

    /** The m yes button. */
    private Button mYesButton;

    /** The waiting dismiss. */
    private static boolean waitingDismiss = false;

    private OnClickYesListener mOnClickYesListener;

    /** The is showing. */
    private boolean isShowing;

    /** The instance. */
    private static CustomAlertDialog instance;

    /**
     * Instantiates a new custom alert dialog.
     */
    public CustomAlertDialog(){

    }

    /**
     * Gets the single instance of CustomAlertDialog.
     *
     * @param title the title
     * @param content the content
     * @return single instance of CustomAlertDialog
     */
    public static CustomAlertDialog getInstance(String title, String content){
        if (instance == null) {
            instance = new CustomAlertDialog();
        }
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    /* (non-Javadoc)
     * @see android.app.DialogFragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        isShowing = false;
    }

    /* (non-Javadoc)
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.customdialog_alert_lay, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        String title = getArguments().getString("title");
        String content = getArguments().getString("content");
        mTitleTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_title_id);
        mContentTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_content_id);
        mTitleTextView.setText(title);
        mContentTextView.setText(content);
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

    /* (non-Javadoc)
     * @see android.app.DialogFragment#show(android.app.FragmentManager, java.lang.String)
     */
    @Override
    public void show(FragmentManager manager, String tag) {
        // TODO Auto-generated method stub
        waitingDismiss = false;
        if (isShowing) {
            return;
        }
        try {
            isShowing = true;
            super.show(manager, tag);
        } catch (IllegalStateException e) {
        }
    }

    /* (non-Javadoc)
     * @see android.app.DialogFragment#dismiss()
     */
    @Override
    public void dismiss() {
        try {
            isShowing = false;
            super.dismiss();
        } catch (IllegalStateException e) {
            waitingDismiss = true;
        } catch (NullPointerException e) {
            waitingDismiss = true;
        }
    }

    /* (non-Javadoc)
     * @see android.app.DialogFragment#onStart()
     */
    @Override
    public void onStart() {
        super.onStart();
        if (waitingDismiss) {
            dismiss();
            waitingDismiss = false;
        }
    }

    /**
     * Dismiss dialog for activity.
     * 
     * @param activity
     *            the activity
     */
    public static void dismiss(Activity activity) {
        if (activity != null) {
            DialogFragment progressDiag = ((DialogFragment) (activity
                    .getFragmentManager()
                    .findFragmentByTag(CustomAlertDialog.TAG)));
            if (progressDiag != null) {
                progressDiag.dismiss();
            } else {
                waitingDismiss = true;
            }
        }

    }

    /**
     * Show dialog with a default message for activity.
     *
     * @param activity            the activity
     * @param title the title
     * @param content the content
     */
    public static CustomAlertDialog show(Activity activity,String title, String content) {
        CustomAlertDialog progressDiag = ((CustomAlertDialog) (activity
                .getFragmentManager()
                .findFragmentByTag(CustomAlertDialog.TAG)));
        if (progressDiag != null) {
            if (progressDiag.isHidden()) {
                progressDiag.show(activity.getFragmentManager(),
                        CustomAlertDialog.TAG);
            }
        } else {
            progressDiag = getInstance(title,content);
            progressDiag.show(activity.getFragmentManager(),
                    CustomAlertDialog.TAG);
        }
        return progressDiag;
    }

    public interface OnClickYesListener{
        public void onClickYes();
    }

    public OnClickYesListener getOnClickYesListener() {
        return mOnClickYesListener;
    }

    public void setOnClickYesListener(OnClickYesListener mOnClickYesListener) {
        this.mOnClickYesListener = mOnClickYesListener;
    }
}
