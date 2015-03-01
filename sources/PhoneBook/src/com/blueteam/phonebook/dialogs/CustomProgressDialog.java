/*
 *
 */
package com.blueteam.phonebook.dialogs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blueteam.phonebook.R;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomProgressDialog.
 */
public class CustomProgressDialog extends BaseDialogFragment {

    /** The layout. */
    private View layout;

    /** The instance. */
    private static CustomProgressDialog instance;

    /**
     * Gets the single instance of SynDialogFragment.
     *
     * @return single instance of SynDialogFragment
     */
    public static CustomProgressDialog getInstance() {
        TAG = "customprogressdialog";
        if (instance == null) {
            instance = new CustomProgressDialog();
        }
        return instance;
    }

    /**
     * Instantiates a new custom progress dialog.
     */
    private CustomProgressDialog() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.app.ProgressDialog#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Panel);
        setCancelable(false);
    }

    /* (non-Javadoc)
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        layout = inflater.inflate(R.layout.custom_progressbar_lay,container);

        ImageView image = (ImageView)layout.findViewById(R.id.custom_progress_bar_image_id);
        image.setBackgroundResource(R.drawable.common_loading);
        com.blueteam.phonebook.utils.AnimationRotateImage.actionAnimationRotateImage(image, 1000);
        return layout;
    }

    /**
     * Show dialog with a default message for activity.
     *
     * @param activity
     *            the activity
     */
    public  void show(FragmentActivity activity) {
        if (!findToShow(activity)) {
            getInstance().show(activity.getSupportFragmentManager(),
                    TAG);
        }
    }
}
