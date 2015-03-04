package dataStorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.esgi.cap.Login;
import common.MyApp;

public class disconnectManager extends Activity {

	private static void resetAppConnexion(){
		MyApp.setAction(null);
		MyApp.setCathegoryList(null);
		MyApp.setContext(null);
		MyApp.setGlobalMessage(null);
		MyApp.setToken(null);
		MyApp.setTokenFlag(false);
	}
	
	public static void logout(Context ctx){
		resetAppConnexion();
		Intent intent = new Intent(ctx, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
        ctx.startActivity(intent);
	}

}
