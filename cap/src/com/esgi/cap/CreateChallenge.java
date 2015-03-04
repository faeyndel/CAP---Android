package com.esgi.cap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import apiCap.RequestTask;
import camera.PictureManager;

import common.JsonReadStream;
import common.MyApp;
import common.informationPopUp;
import common.onClickButton;

import dataStorage.disconnect;
import dataStorage.disconnectManager;

public class CreateChallenge extends ActionBarActivity {

	private static final int MENU_DISCONNECT = Menu.FIRST;
	static final String FILEFOLDER = "/CAP/"; 
	private static int RESULT_LOAD_IMAGE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1;
	private Boolean loadPict = false;
	ImageView mImageView;
	String mCurrentPhotoPath;
	String cathegory;
	String filePathName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_challenge);
		MyApp.setContext(this);
		disconnect.checkUserConnexion(this);
		
		ImageView pict = (ImageView) findViewById(R.id.pictureController);
		pict.setVisibility(View.GONE);
		
		try {
			populateCathegoryList();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void populateCathegoryList() throws JSONException
	{
		try {
			new RequestTask().execute("cathegory").get();
			String jsonData = JsonReadStream.stringToJSONString(MyApp.getCathegoryList(), "data");
			ArrayList<HashMap<String, String>> dataList = JsonReadStream.jsonCathegoryParsing(jsonData, "data");
			Spinner list = (Spinner) findViewById(android.R.id.list);
			SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.create_challenge_spinner, new String[]{"id", "name"}, new int[]{R.id.SPINNER_ID, R.id.SPINNER_NAME});
			list.setAdapter( adapter );
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void loadChallengePicture(View view){
		loadPict = true;
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, RESULT_LOAD_IMAGE);
	}

	public void createChallengePicture(View view){
		PictureManager.dispatchTakePictureIntent(this);
	}

	public void sendChallenge(View view){
		ImageView pictureController = (ImageView) findViewById(R.id.pictureController);
		TextView challengeName = (TextView) findViewById(R.id.challengeName);
		Spinner cathegorySpinner = (Spinner) findViewById(android.R.id.list);
		cathegory = cathegorySpinner.getSelectedItem().toString();
		String[] id = cathegory.split("=");
		String[] idcathegory = id[1].split(",");
		cathegory = idcathegory[0];

		String[] fileExplode = filePathName.split("/");

		String filename = fileExplode[fileExplode.length - 1];
		String filepath = "";
		for (int i = 0; i < fileExplode.length - 1; i++) {
			if(i == 0)
				continue;
			filepath += "/"+fileExplode[i];
		}
		filepath += "/";

		new RequestTask().execute("addChallenge",
				filename,
				filepath,
				cathegory,
				challengeName.getText().toString());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(loadPict == true){
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
				filePathName = PictureManager.loadPict(this, data);
				ImageView pict = (ImageView) findViewById(R.id.pictureController);
				pict.setVisibility(View.VISIBLE);
			}
		}else{
			if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
				mImageView = PictureManager.createPicture(this, data);
			}
		}
	}

	public void rulesPopup(View view) {
		informationPopUp.popInfo(this.getString(R.string.popup_title_rules), this.getString(R.string.popup_desc_rules_create), this);
	}
	public void launchCat(View view) {
		onClickButton.cathegoryLaunch(this);
	}
	public void launchDoDefi(View view) {
		onClickButton.challengeDoLaunch(this);
	}
	public void launchAddDefi(View view) {
		onClickButton.challengeAddLaunch(this);
	}
	public void profilAccess(View view) {
		onClickButton.profilLaunch(this);
	}
	
	/* BACK BUTTON MANAGER*/
	

	@Override
	public void onBackPressed() {
	    moveTaskToBack(false);
	    onClickButton.backHome(this);
	}
	
/* MENU MANAGER */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, MENU_DISCONNECT, 0, "Deconnexion");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case MENU_DISCONNECT:
	    	disconnectManager.logout(this);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
