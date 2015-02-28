/*
 * 
 */
package com.blueteam.phonebook.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blueteam.phonebook.entities.ProvineObject;

import android.content.res.AssetManager;

// TODO: Auto-generated Javadoc
/**
 * The Class FileAssetsUtils.
 */
public class FileAssetsUtils {
	
	/**
	 * Load json from asset.
	 *
	 * @param fileName the file name
	 * @param asset the asset
	 * @return the string
	 */
	private static String LoadJsonFromAsset(String fileName, AssetManager asset){
		try {
			InputStream is = asset.open(fileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			return new String(buffer,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the provice from json file.
	 *
	 * @param asset the asset
	 * @return the provice from json file
	 */
	public static List<ProvineObject> getProviceFromJsonFile(AssetManager asset){
		List<ProvineObject> lst = new ArrayList<ProvineObject>();
		try {
			JSONArray ja = new JSONArray(LoadJsonFromAsset("provine_code_json.json",asset));
			for(int i = 0, size = ja.length(); i< size; i++){
				JSONObject ob = ja.getJSONObject(i);
				String code = ob.getString("tinhthanh");
				String name = ob.getString("mavungcu");
				String dial_code = ob.getString("mavungmoi");
				ProvineObject c = new ProvineObject(code,name, dial_code);
				lst.add(c);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lst;
	}
}
