package common;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class informationPopUp extends Application{
	public static void popInfo(String title, String message, Context context){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", null);
		AlertDialog dialog = builder.show();

		// Must call show() prior to fetching text view
		//TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
	}
}
