/*
 *
 */
package com.blueteam.phonebook.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseDialogFragment.
 */
@SuppressLint("NewApi")
public abstract class BaseDialogFragment extends DialogFragment {
    /** The waiting dismiss. */
    protected static boolean waitingDismiss = false;

    /** The is showing. */
    protected boolean isShowing;

    /** The Constant TAG. */
    protected static String TAG;

    /*
     * (non-Javadoc)
     *
     * @see android.app.DialogFragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        isShowing = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.DialogFragment#show(android.app.FragmentManager,
     * java.lang.String)
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

    /*
     * (non-Javadoc)
     *
     * @see android.app.DialogFragment#dismiss()
     */
    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        try {
            isShowing = false;
            super.dismiss();
        } catch (IllegalStateException e) {
            waitingDismiss = true;
        } catch (NullPointerException e) {
            waitingDismiss = true;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.DialogFragment#onStart()
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (waitingDismiss) {
            dismiss();
            waitingDismiss = false;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.DialogFragment#onDestroyView()
     */
    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        isShowing = false;
        super.onDestroyView();
    }

    /**
     * Dismiss.
     *
     * @param activity
     *            the activity
     */
    public  void dismiss(Activity activity) {
        if (activity != null) {
            DialogFragment creditDialog = ((DialogFragment) (activity
                    .getFragmentManager().findFragmentByTag(TAG)));
            if (creditDialog != null) {
                creditDialog.dismiss();
            } else {
                waitingDismiss = true;
            }
        }
    }

    /**
     * Find to show.
     *
     * @param activity the activity
     * @return true, if successful
     */
    protected  boolean findToShow(Activity activity){
        DialogFragment dialogFragment = ((DialogFragment) (activity
                .getFragmentManager()
                .findFragmentByTag(TAG)));
        if (dialogFragment != null) {
            if (dialogFragment.isHidden() || !dialogFragment.isAdded()) {
            	dialogFragment.show(activity.getFragmentManager(),
                        TAG);
            }
            return true;
        }
        return false;
    }
}
