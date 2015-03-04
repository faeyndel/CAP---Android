package dataStorage;

import android.app.Activity;
import android.content.Context;

import common.MyApp;
import common.onClickButton;

public class disconnect extends Activity{
	
	public static void checkUserConnexion(Context ctx){
		if(MyApp.getTokenFlag() == false){
			Activity activity = (Activity) ctx;
			onClickButton.loginLaunch(ctx);
			activity.finish();
		}
	}
}
