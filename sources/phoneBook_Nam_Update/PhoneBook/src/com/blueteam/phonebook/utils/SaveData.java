/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2014 LunexTelecom, Inc. All rights reserved.
 * Author: Anh Bui
 * Location: Zippie - com.lunextelecom.zippie.utils - SaveData.java
 * 
 */
package com.blueteam.phonebook.utils;
import android.content.Context;
import android.content.SharedPreferences;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveData.
 */
public class SaveData {

    /** The save data. */
    private static SaveData saveData = null;

    /** The share preference. */
    private SharedPreferences sharePreference;

    /**
     * Instantiates a new save data.
     * 
     * @param context
     *            the context
     */
    public SaveData(Context context) {
        sharePreference = context.getSharedPreferences("zippie",
                Context.MODE_PRIVATE);
    }

    /**
     * Gets the single instance of SaveData.
     * 
     * @param context
     *            the context
     * @return single instance of SaveData
     */
    public static SaveData getInstance(Context context) {
        if (saveData == null) {
            saveData = new SaveData(context);
        }
        return saveData;
    }

    /**
     * Checks if is first time.
     * 
     * @return true, if is first time
     */
    public boolean isFirstTime() {
        return sharePreference.getBoolean("first_time", true);
    }

    /**
     * Sets the first time.
     * 
     * @param isFirstTime
     *            the new first time
     */
    public void setFirstTime(boolean isFirstTime) {
        sharePreference.edit().putBoolean("first_time", isFirstTime).commit();

    }

    /**
     * Sets the internet connecting.
     *
     * @param isConnected the new internet connecting
     */
    public void setInternetConnecting(boolean isConnected) {
        sharePreference.edit().putBoolean("network", isConnected).commit();
    }

    /**
     * Checks if is internet connecting.
     *
     * @return true, if is internet connecting
     */
    public boolean isInternetConnecting() {
        return sharePreference.getBoolean("network", false);
    }

    /**
     * Sets the registered.
     *
     * @param isRegistered the new registered
     */
    public void setRegistered(boolean isRegistered) {
        sharePreference.edit().putBoolean("registered", isRegistered).commit(); // will change when done sign up
    }

    /**
     * Checks if is registered.
     *
     * @return true, if is registered
     */
    public boolean isRegistered() {
        return sharePreference.getBoolean("registered", false);
    }
    
    
    public void setCurrentFragmentSignUp(String current) {
        sharePreference.edit().putString("currentfragment", current).commit();
    }

    public String getCurrentFragmentSignUp() {
        return sharePreference.getString("currentfragment", "");
    }
    
    /**
     * Sets the country code.
     *
     * @param countrycode the new country code
     */
    public void setCountryCode(String countrycode) {
        sharePreference.edit().putString("countrycode", countrycode).commit();
    }

    /**
     * Gets the country code.
     *
     * @return the country code
     */
    public String getCountryCode() {
        return sharePreference.getString("countrycode", "");
    }

    /**
     * Sets the phone number.
     *
     * @param phonenumber the new phone number
     */
    public void setPhoneNumber(String phonenumber) {
        sharePreference.edit().putString("phonenumber", phonenumber).commit();
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return sharePreference.getString("phonenumber", "");
    }

    /**
     * Sets the login.
     *
     * @param login the new login
     */
    public void setLogin(String login) {
        sharePreference.edit().putString("login", login).commit();
    }

    /**
     * Gets the login.
     *
     * @return the login
     */
    public String getLogin() {
        return sharePreference.getString("login", "");
    }

    /*    *//**
     * Sets the activation code.
     *
     * @param activationcode the new activation code
     *//*
    public void setActivationCode(String activationcode) {
        sharePreference.edit().putString("activationcode", activationcode).commit();
    }

      *//**
      * Gets the activation code.
      *
      * @return the activation code
      *//*
    public String getActivationCode() {
        return sharePreference.getString("activationcode", "");
    }*/

    /**
     */
    public void setSystemContactChanged(boolean isSystemContactChanged) {
        sharePreference.edit().putBoolean("isSystemContactChanged", isSystemContactChanged).commit();
    }

    /**
     */
    public boolean isSystemContactChanged() {
        return sharePreference.getBoolean("isSystemContactChanged", true);
    }

    /**
     */
    public void setSynsContactInit(boolean isSyncContactInit) {
        sharePreference.edit().putBoolean("isSyncContactInit", isSyncContactInit).commit();
    }

    /**
     */
    public boolean isSynsContactInit() {
        return sharePreference.getBoolean("isSyncContactInit", false);
    }

    /**
     */
    public void setSynsContactChanged(boolean isSyncContactChanged) {
        sharePreference.edit().putBoolean("isSyncContactChanged", isSyncContactChanged).commit();
    }

    /**
     */
    public boolean isSynsContactChanged() {
        return sharePreference.getBoolean("isSyncContactChanged", true);
    }

    public void setAccountId(String accountId){
        sharePreference.edit().putString("accountId", accountId).commit();
    }

    public String getAccountId(){
        return sharePreference.getString("accountId", "acba9280-7a9d-11e4-b234-97226ab77825");
    }
}
