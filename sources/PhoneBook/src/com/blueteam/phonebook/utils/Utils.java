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

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class contains static utility methods.
 */
public class Utils {

    // Prevents instantiation.
    private Utils() {}

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
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb or
     * later.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb MR1 or
     * later.
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect if the device's platform version is ICS or
     * later.
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }
}
