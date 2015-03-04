package common;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import apiCap.RequestTask;

import com.esgi.cap.Cathegory;
import com.esgi.cap.CreateChallenge;
import com.esgi.cap.DoChallenge;
import com.esgi.cap.LastChallenge;
import com.esgi.cap.Login;
import com.esgi.cap.Profil;
import com.esgi.cap.R;
import com.esgi.cap.Register;

public class onClickButton extends Activity{
	private static LayoutInflater inflater=null;
	static LinearLayout.LayoutParams params;

	public static void loginLaunch(Context context) {
		Intent intent = new Intent(context, Login.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public static void lastChallengeLaunch(Context context) {
		Intent intent = new Intent(context, LastChallenge.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public static void cathegoryLaunch(Context context) {
		Intent intent = new Intent(context, Cathegory.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public static void registerLaunch(Context context) {
		Intent intent = new Intent(context, Register.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public static void challengeDoLaunch(Context context) {
		Intent intent = new Intent(context, DoChallenge.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public static void challengeAddLaunch(Context context) {
		Intent intent = new Intent(context, CreateChallenge.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public static void profilLaunch(Context context) {
		Intent intent = new Intent(context, Profil.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	/* Challenge Actions Button */

	public static void shareAction(Context context, View view) {
		TextView challengeName=(TextView)view.findViewById(R.id.challengeName);
		TextView challengerName=(TextView)view.findViewById(R.id.challengerName);

		Activity ctxActivity = (Activity) context;
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		sharingIntent.setType("text/plain");
		String shareBody = challengerName.getText().toString()+" a participé au challenge "+challengeName.getText().toString();
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "CAP");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		ctxActivity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}
	public static void commentAction(Context context, View view) {
		Log.v("__COMMENT__", "clicked");
	}
	public static void capAction(Context context, View view) {
		Log.v("__CAP__", "clicked");
		TextView challengeName=(TextView)view.findViewById(R.id.challengeName);
		TextView challengerName=(TextView)view.findViewById(R.id.challengerName);


		// Log.v("__cap__", idChallenge.toString()+"");
		Log.v("__cap__", challengeName.getText().toString()+"");
		Log.v("__cap__", challengerName.getText().toString()+"");

		
		/*cap.setBackgroundResource(R.drawable.btn_pascap);
		cap.setId(R.id.uncapButton);
		cap.invalidate();
		view.invalidate();*/
		//uncap.setVisibility(View.VISIBLE);



		/*cap.invalidate();
        uncap.invalidate();*/
		//Log.v("__capv__", cap.getVisibility()+"");


		//uncap.refreshDrawableState();
	}
	public static void uncapAction(Context context, View view) {
		Log.v("__UNCAP__", "clicked");
		ImageButton cap = (ImageButton) view.findViewById(R.id.capButton);
		ImageButton uncap = (ImageButton) view.findViewById(R.id.uncapButton);
		//Log.v("__capv__", cap.getVisibility()+"");
		cap.setVisibility(View.VISIBLE);
		uncap.setVisibility(View.GONE);
	}

	/* BACK BUTTON Redirection */

	public static void backHome(Context context) {
		Intent intent = new Intent(context, LastChallenge.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		context.startActivity(intent);
	}

}
