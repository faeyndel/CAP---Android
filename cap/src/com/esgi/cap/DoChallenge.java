package com.esgi.cap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.w3c.dom.ls.LSInput;

import camera.PictureManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import apiCap.RequestTask;
import common.JsonReadStream;
import common.MyApp;
import common.informationPopUp;
import common.onClickButton;
import dataStorage.disconnect;
import dataStorage.disconnectManager;

public class DoChallenge extends Activity implements OnItemSelectedListener {

	private static final int MENU_DISCONNECT = Menu.FIRST;
	static final String FILEFOLDER = "/CAP/"; 
	private static int RESULT_LOAD_IMAGE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1;
	private Boolean loadPict = false;
	ImageView mImageView;
	String mCurrentPhotoPath;
	String cathegory;
	String challenge;
	String filePathName;
	Spinner cathSpin;
	Spinner challSpin;
	private boolean firstSpin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_challenge);
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
			cathSpin = (Spinner) findViewById(android.R.id.list);
			SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.create_challenge_spinner, new String[]{"id", "name"}, new int[]{R.id.SPINNER_ID, R.id.SPINNER_NAME});
			cathSpin.setAdapter( adapter );

			cathSpin.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
					Spinner cathegorySpinner = (Spinner) findViewById(android.R.id.list);
					cathegory = cathegorySpinner.getSelectedItem().toString();
					String[] idCat= cathegory.split("=");
					String[] idcathegory = idCat[1].split(",");
					cathegory = idcathegory[0];
					MyApp.setCathegoryList("");
					try {
						populateChallengeList(idcathegory[0].toString());
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// Dummy
				}
			});




		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void populateChallengeList(String idTag) throws JSONException
	{
		try {
			new RequestTask().execute("challengeCathegory", idTag).get();

			if(MyApp.getCathegoryList().equals("false")){
				ArrayList<HashMap<String, String>> listChallenge = new ArrayList<HashMap<String, String>>();
				Spinner list = (Spinner) findViewById(android.R.id.custom);
				SimpleAdapter adapter = new SimpleAdapter(this, listChallenge, R.layout.create_challenge_spinner, new String[]{"id", "description"}, new int[]{R.id.SPINNER_ID, R.id.SPINNER_NAME});
				list.setAdapter( adapter );
			}else{
				String jsonData = JsonReadStream.stringToJSONString(MyApp.getCathegoryList(), "data");
				ArrayList<HashMap<String, String>> dataList = JsonReadStream.jsonCathegoryChallengeParsing(jsonData, "data");
				ArrayList<HashMap<String, String>> listChallenge = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < dataList.size(); i++) {
					String[] id = dataList.get(i).toString().split("id=");
					String[] challId = id[1].split(",");
					String[] desc = dataList.get(i).toString().split("description=");
					/*String[] descId = desc[1].split(" ");
					descId[0] = descId[0].replaceAll("\\}", "");*/
					String descId = desc[1].replaceAll("\\}", "");
					//descId[0] = descId[0].replaceAll("\\}", "");
					
					HashMap<String, String> data = new HashMap<String, String>();
					data.put("id", challId[0]);
					data.put("description", descId);
					listChallenge.add(data);

					challSpin = (Spinner) findViewById(android.R.id.custom);
					SimpleAdapter adapter = new SimpleAdapter(this, listChallenge, R.layout.create_challenge_spinner, new String[]{"id", "description"}, new int[]{R.id.SPINNER_ID, R.id.SPINNER_NAME});
					challSpin.setAdapter( adapter );

					challSpin.setOnItemSelectedListener(new OnItemSelectedListener(){
						@Override
						public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
							Spinner cathegorySpinner = (Spinner) findViewById(android.R.id.custom);
							challenge = cathegorySpinner.getSelectedItem().toString();
							String[] idCat= challenge.split("id=");
							String[] idcathegory = idCat[1].split(",");
							challenge = idcathegory[0];
						}
						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// Dummy
						}
					});
				}
			}

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


	public void sendChallenge(View view){
		String[] fileExplode = filePathName.split("/");

		String filename = fileExplode[fileExplode.length - 1];
		String filepath = "";
		for (int i = 0; i < fileExplode.length - 1; i++) {
			if(i == 0)
				continue;
			filepath += "/"+fileExplode[i];
		}
		filepath += "/";

		new RequestTask().execute("doChallenge",
				filename,
				filepath,
				challenge);
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		//Log.v("__SPINNER__", "select Change");
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
