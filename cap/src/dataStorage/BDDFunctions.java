package dataStorage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

public class BDDFunctions {

 static final String NL = System.getProperty("line.separator");

	SQLiteDatabase db;
	
	public void displayData(View v) {
	//	TextView dataText = (TextView) findViewById(R.id.dataText);
		
		/*if (db != null) {
			StringBuilder data = new StringBuilder();
			
			Cursor cursor = db.query(capBDStorage.TABLE_ABUSE, null, null, null, null, null, null);
			while (cursor.moveToNext()) {
				long studentId = cursor.getLong(0);
				String studentName = cursor.getString(cursor.getColumnIndex(capBDStorage.FIELD1));
//				Log.v(TAG, "student name: " + studentName);
				data.append(studentId).append(";").append(studentName).append(NL);
			}
			cursor.close();
			
		//	dataText.setText(data.toString());
		}*/
	}

	

	
}
