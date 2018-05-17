/**
 * Copyright (C) futuretek AG 2016
 * All Rights Reserved
 *
 * @author Artan Veliju
 */
package survey.android.futuretek.ch.ft_survey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

	private static final int database_VERSION = 1;
	private static final String database_NAME = "FTSurveyDB";
	private static final String tableAdminSettings = "admin_settings";
	private static final String tableSkills = "skills";
	private static final String fieldID = "id";
	private static final String fieldVal = "val";

	private static final String[] COLUMNS = {fieldVal};
	private static final String[] SKILL_COLUMNS = {fieldID};

	public Database(Context context) {
		super(context, database_NAME, null, database_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+tableAdminSettings+" (id TEXT PRIMARY KEY NOT NULL, val TEXT NULL)");
		db.execSQL("CREATE TABLE "+tableSkills+" (id TEXT PRIMARY KEY NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+tableAdminSettings);
		this.onCreate(db);
	}

	public void put(String key, String value) {
		if(exists(key)){
			update(key, value);
		}else{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(fieldID, key);
			values.put(fieldVal, value);
			db.insert(tableAdminSettings, null, values);
			db.close();
		}
	}

	public String get(String key) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(tableAdminSettings, COLUMNS, " id = ?", new String[] { String.valueOf(key) }, null, null, null, null);
		if (cursor != null) {
			if(cursor.moveToFirst()){
				String val = cursor.getString(0);
				db.close();
				return val;
			}
		}
		db.close();
		return null;
	}
	
	public boolean exists(String key) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(tableAdminSettings, COLUMNS, " id = ?", new String[] { String.valueOf(key) }, null, null, null, null);
		if(cursor!=null && cursor.getCount()>0) {
			db.close();
			return true;
		}
		db.close();
		return false;
	}

	public void put(String key, boolean value){
		put(key, ""+value);
	}

	public boolean getBoolean(String key){
		return toBoolean(get(key), false);
	}

	public static boolean toBoolean(String val, boolean defaultVal){
		try{
			return Boolean.parseBoolean(val);
		}catch (Exception e){
		}
		return defaultVal;
	}

	private int update(String key, String value) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(fieldVal, value); // get author
		int i = db.update(tableAdminSettings, values, fieldID + " = ?", new String[] { String.valueOf(key) });
		db.close();
		return i;
	}

	public List<String> getAllSkills() {
		List<String> skills = new ArrayList<String>();
		String query = "SELECT  * FROM " + tableSkills;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				skills.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		return skills;
	}

	public void putSkill(String key) {
		if(getSkill(key)==null){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(fieldID, key);
			db.insert(tableSkills, null, values);
			db.close();
		}
	}

	public String getSkill(String key) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(tableSkills, SKILL_COLUMNS, " id = ?", new String[] { String.valueOf(key) }, null, null, null, null);
		if (cursor != null) {
			if(cursor.moveToFirst()){
				String val = cursor.getString(0);
				db.close();
				return val;
			}
		}
		db.close();
		return null;
	}

	public void updateSkill(String oldSkill, String newSkill) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(fieldID, newSkill);
		db.update(tableSkills, values," id = ?", new String[] { String.valueOf(oldSkill) });
		db.close();
	}

	public void deleteSkill(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tableSkills, fieldID + " = ?", new String[] { String.valueOf(id) });
		db.close();
	}
}
