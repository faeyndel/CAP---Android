package com.esgi.cap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import apiCap.RequestTask;
import camera.LazyAdapter;

import common.JsonReadStream;
import common.MyApp;
import common.onClickButton;
import common.setTextDisplay;

import dataStorage.disconnect;
import dataStorage.disconnectManager;

public class Profil extends Activity implements OnClickListener {

	private static final int MENU_DISCONNECT = Menu.FIRST;
	public String[] imageUrls;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);

		disconnect.checkUserConnexion(this);
		
		MyApp.setContext(this);
		MyApp.setCathegoryList("");

		if(MyApp.getCurUserId() == null){
			try {
				new RequestTask().execute("getUserID", MyApp.getToken()).get();
			} catch (InterruptedException e1) { e1.printStackTrace(); } catch (ExecutionException e1) { e1.printStackTrace(); }
		}

		setTextDisplay.setFonts(this, R.id.profilChallengeNumber, "GROBOLD.ttf");
		setTextDisplay.setFonts(this, R.id.profilUserName, "GROBOLD.ttf");
		
		try {
			initListView();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
	}
	
	private void initListView() throws JSONException
	{
		try {
			MyApp.setCathegoryList("");
			new RequestTask().execute("userChallenge", MyApp.getCurUserId()).get();
			if(MyApp.getCathegoryList().equals("false")){}else{
				String jsonData = JsonReadStream.stringToJSONString(MyApp.getCathegoryList(), "data");
				ArrayList<HashMap<String, String>> dataChallengeList  = JsonReadStream.jsonUserDataChallengeParsing(jsonData, "challenge");
				ArrayList<HashMap<String, String>> dataList = JsonReadStream.jsonUserDataParsing(jsonData, "challenge");

				for (HashMap<String, String> map : dataList)
				     for (Entry<String, String> mapEntry : map.entrySet())
				        {
					        String key = mapEntry.getKey();
					        String value = mapEntry.getValue();
					        if(key.equalsIgnoreCase("totalCount")){
					        	TextView user = (TextView) findViewById(R.id.profilChallengeNumber);
					        	user.setText(value+" Défis");
					        }
					        if(key.equalsIgnoreCase("username")){
					        	TextView user = (TextView) findViewById(R.id.profilUserName);
					        	user.setText(value);
					        }
				        }

				ListView list = (ListView) findViewById(android.R.id.list);

				ArrayList<String> stringArrayList = new ArrayList<String>();
				
				for (int i = 0; i < dataChallengeList.size(); i++) {
					stringArrayList.add(dataChallengeList.get(i).get("image").toString());
					stringArrayList.add(dataChallengeList.get(i).get("id").toString());
					stringArrayList.add(dataChallengeList.get(i).get("author").toString());
					stringArrayList.add(dataChallengeList.get(i).get("description").toString());
				}
				imageUrls = stringArrayList.toArray(new String[stringArrayList.size()]);
						
				list=(ListView)findViewById(android.R.id.list);
				LazyAdapter adapter=new LazyAdapter(this, imageUrls);
		        list.setAdapter(adapter);
		        
		        /*SimpleAdapter arrayAdapter = new SimpleAdapter(this, dataChallengeList, R.layout.challenge_row, new String[]{"id", "author", "description"}, new int[]{R.id.idChallenge, R.id.challengerName, R.id.challengeName});
				list.setAdapter(arrayAdapter);*/
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
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
	
	/* Challenge Actions Button */
	public void shareLaunch(View view) {
		ListView lv = (ListView) findViewById(android.R.id.list);
		int position = lv.getPositionForView(view);
		View newView = lv.getAdapter().getView(position, null, lv);
		onClickButton.shareAction(this, newView);
	}
	
	public void commentLaunch(View view) {
		ListView lv = (ListView) findViewById(android.R.id.list);
		int position = lv.getPositionForView(view);
		View newView = lv.getAdapter().getView(position, null, lv);
		onClickButton.commentAction(this, newView);
	}
	
	@Override
	public void onClick(View v) {}

	public void capLaunch(View view) {
		ListView lv = (ListView) findViewById(android.R.id.list);
		int position = lv.getPositionForView(view);
		View newView = lv.getAdapter().getView(position, null, lv);
		onClickButton.capAction(this, newView);
	}

	public void uncapLaunch(View view) {
		ListView lv = (ListView) findViewById(android.R.id.list);
		int position = lv.getPositionForView(view);
		View newView = lv.getAdapter().getView(position, null, lv);
		onClickButton.uncapAction(this, newView);
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
