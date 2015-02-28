/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blueteam.phonebook.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.blueteam.phonebook.utils.SaveData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class contains static utility methods.
 */
public class Utils {
	 /** The Constant FONT_ROBOTO_NAME. */
    public static final String FONT_ROBOTO_NAME = "Roboto.ttf";

    /** The Constant FONT_FACEBOLF_NAME. */
    public static final String FONT_FACEBOLF_NAME = "FACEBOLF.OTF";

    /**
     * Sets the typeface.
     * 
     * @param context the context
     * @param fontName the font name
     * @param views the views
     */
    public static void setTypeface(Context context, String fontName, View... views) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        if(views != null && views.length >0){
            for(View view : views){
                if(view instanceof TextView){
                    ((TextView)view).setTypeface(font);
                }else if(view instanceof EditText){
                    ((EditText)view).setTypeface(font);
                }else if(view instanceof Button){
                    ((Button)view).setTypeface(font);
                }
            }
        }
    }

    /**
     * Sets the typeface roboto.
     * 
     * @param context the context
     * @param views the views
     */
    public static void setTypefaceRoboto(Context context, View... views) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto.ttf");
        if(views != null && views.length >0){
            for(View view : views){
                if(view instanceof TextView){
                    ((TextView)view).setTypeface(font);
                }else if(view instanceof EditText){
                    ((EditText)view).setTypeface(font);
                }else if(view instanceof Button){
                    ((Button)view).setTypeface(font);
                }
            }
        }
    }

    /**
     * Uses static final constants to detect if the device's platform version is Gingerbread or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb MR1 or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect if the device's platform version is ICS or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Check internet connection.
     *
     * @param context the context
     */
    public static void checkInternetConnection(Context context){
        boolean isConnected = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null){
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null){
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED){
                        isConnected = true;
                        break;
                    }
                }
            }
        }
        SaveData.getInstance(context).setInternetConnecting(isConnected);
    }

    /**
     * Show input keyboard.
     *
     * @param activity the activity
     * @param v the v
     */
    public static void showInputKeyboard(Activity activity, View v)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hide soft keyboard.
     *
     * @param activity the activity
     * @param v the v
     */
    public static void hideSoftKeyboard(Activity activity,View v) {
        InputMethodManager inputManager = (InputMethodManager)activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSoftKeyboardShow() throws Exception
    {
        /*    	try {
    	    Process process = Runtime.getRuntime().exec("adb shell dumpsys window InputMethod");
    	    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
    	    String output = "";
    	    try {
    	        String line;
    	        while ((line = in.readLine()) != null) {
    	            output += line + "\n";
    	        };
    	    }
    	    catch (IOException e) {}
    	    return true;
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}*/
        return true;
    }

    /**
     * Send sms invite.
     *
     * @param context the context
     * @param phoneNumber the phone number
     * @param body the body
     */
    public static void sendSMSInvite(Context context, String phoneNumber,
            String body){
    	Intent intent = new Intent(Intent.ACTION_SENDTO);
    	intent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
    	intent.putExtra("sms_body", body);
        intent.putExtra("compose_mode", true);
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }
    /**
     * Gets the display width.
     *
     * @param pActivity the activity
     * @return the display width
     */
    public static int getDisplayWidth(Activity pActivity)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        pActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Gets the display height.
     *
     * @param pActivity the activity
     * @return the display height
     */
    public static int getDisplayHeight(Activity pActivity)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        pActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static String getHashKey(PackageManager packageManager){
        try {
            PackageInfo info = packageManager.getPackageInfo(
                    "com.lunextelecom.zippie", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64
                        .encodeToString(md.digest(), Base64.DEFAULT);

                Log.e("MY KEY HASH:", sign);
                return sign;

            }
        } catch (NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }
}
