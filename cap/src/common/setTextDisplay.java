package common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.esgi.cap.R;

public class setTextDisplay extends Activity {
	
	public static void setFonts(Context ctx, int ressource, String font){
		Activity ctxActivity = (Activity) ctx; 
		TextView hindiTextView=(TextView)ctxActivity.findViewById(ressource);
		Typeface hindiFont=Typeface.createFromAsset(ctxActivity.getAssets(), font);
		hindiTextView.setTypeface(hindiFont);
	}
	
	public static void addTextToTextView(TextView ressource, String text){
		ressource.setText(text);
	}
	
}
