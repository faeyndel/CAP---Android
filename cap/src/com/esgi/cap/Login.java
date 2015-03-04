package com.esgi.cap;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import apiCap.RequestTask;
import common.MyApp;
import common.informationPopUp;
import common.onClickButton;
import dataStorage.ConnectionDetector;

public class Login extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		MyApp.setContext(this);
		MyApp.setTokenFlag(false);
		MyApp.setGlobalMessage("false");
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		MyApp.setScreenWidth(displaymetrics.widthPixels);
		
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext()); 
		Boolean isInternetPresent = cd.isConnectingToInternet();
		
		if(isInternetPresent == false){
			informationPopUp.popInfo("Connexion", "Aucune connexion internet détecter", this);
		}
	}
	
	public void onLogin(View view){
		TextView pseudo = (TextView) findViewById(R.id.pseudo);
		TextView password = (TextView) findViewById(R.id.password);
		boolean errorFlag = false;
		String errorMessage = "";
		
		if (pseudo.getText().toString().equals("")){
			errorMessage += "- Veuillez saisir un pseudonyme\t\n";
			errorFlag = true;
		}
		if (password.getText().toString().equals("")){
			errorMessage += "- Veuillez saisir un mot de passe\r\n";
			errorFlag = true;
		}

		if(errorFlag == false){
			
			try {
				isFinishing();
				new RequestTask().execute("login",
						pseudo.getText().toString(),
						password.getText().toString()).get();

				if(MyApp.getGlobalMessage().equals("false")){
					Toast toast = Toast.makeText(this, "Identifiant Incorrecte", Toast.LENGTH_LONG);
		            toast.show();
				}else{
					MyApp.setUserName(pseudo.getText().toString());
					Toast toast = Toast.makeText(this, "Connecter", Toast.LENGTH_LONG);
		            toast.show();
					MyApp.setTokenFlag(true);
					onClickButton.lastChallengeLaunch(this);
					finish();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}else{
			informationPopUp.popInfo("Informations incorrecte", errorMessage, this);
		}
	}
	
	public void launchRegister(View view) {
		onClickButton.registerLaunch(this);
		//finish();
	}
	public void launchCat(View view) {
		//onClickButton.cathegoryLaunch(this);
	}
	
/* BACK BUTTON MANAGER*/
	
	
	@Override
	public void onBackPressed() {
	    moveTaskToBack(false);
	    finish();
	  /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    intent.putExtra("EXIT", true);
	    startActivity(intent); */
	}
}