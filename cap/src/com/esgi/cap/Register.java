package com.esgi.cap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import apiCap.RequestTask;

import common.MyApp;
import common.informationPopUp;

import dataStorage.checkForms;

public class Register extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		MyApp.setContext(this);
	}
	
	
	public void onRegister(View view){
		TextView mail = (TextView) findViewById(R.id.register_mail);
		TextView pseudo = (TextView) findViewById(R.id.register_pseudo);
		TextView password = (TextView) findViewById(R.id.register_password);
		TextView confPassword = (TextView) findViewById(R.id.register_confPassword);
		boolean errorFlag = false;
		String errorMessage = "";
		
		if (checkForms.checkMail(mail.getText().toString())){
			errorMessage += "- Veuillez saisir une adresse email valide\r\n";
			errorFlag = true;
		}
		if (pseudo.getText().toString().equals("")){
			errorMessage += "- Veuillez saisir un pseudonyme\t\n";
			errorFlag = true;
		}
		if (checkForms.checkPasswords(password.getText().toString(), confPassword.getText().toString())){
			errorMessage += "- Mot de passe invalide\r\n";
			errorFlag = true;
		}

		if(errorFlag == false){
			new RequestTask().execute("adduser",
					pseudo.getText().toString(),
					mail.getText().toString(),
					password.getText().toString());
		}else{
			informationPopUp.popInfo("Informations incorrecte", errorMessage, this);
		}
	}
	
	
	@Override
	public void onBackPressed() {
	    moveTaskToBack(false);
	    Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		finish();
	}
	
}
