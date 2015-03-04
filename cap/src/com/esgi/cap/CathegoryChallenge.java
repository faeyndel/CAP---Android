package com.esgi.cap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import apiCap.RequestTask;
import camera.LazyAdapterLastChallenges;

import common.JsonReadStream;
import common.MyApp;
import common.onClickButton;
import common.setTextDisplay;

import dataStorage.disconnectManager;

public class CathegoryChallenge extends Activity implements OnClickListener{

	private static final int MENU_DISCONNECT = Menu.FIRST;
	public String[] imageUrls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cathegory_challenge);

		Bundle extra = getIntent().getExtras();
		String tagId = extra.getString("id");
		String tagName = extra.getString("name");

		MyApp.setContext(this);
		String mss = "Les défis de la\r\ncatégorie \""+tagName+"\"";
		setTextDisplay.addTextToTextView((TextView)findViewById(R.id.title), mss);
		setTextDisplay.setFonts(this, R.id.title, "GROBOLD.ttf");

		try {
			initListView(tagId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initListView(String tagId) throws JSONException
	{
		try {
			Log.v("__tagid__", tagId);
			MyApp.setCathegoryList("");
			new RequestTask().execute("cathegoryChallenge", tagId).get();
			
			if(MyApp.getCathegoryList().equals("false")){}else{
				if (MyApp.getCathegoryList().equals("{}")) {
				}else{
					ArrayList<HashMap<String, String>> dataChallengeList  = JsonReadStream.jsonCathegoriesChallengeParsing(MyApp.getCathegoryList(), "challenge");
					if(dataChallengeList != null){
						ListView list = (ListView) findViewById(android.R.id.list);

						ArrayList<String> stringArrayList = new ArrayList<String>();
						
						for (int i = 0; i < dataChallengeList.size(); i++) {
							stringArrayList.add(dataChallengeList.get(i).get("image").toString());
							stringArrayList.add(dataChallengeList.get(i).get("username").toString());
							//stringArrayList.add(dataChallengeList.get(i).get("author").toString());
							stringArrayList.add(dataChallengeList.get(i).get("description").toString());
						}
						imageUrls = stringArrayList.toArray(new String[stringArrayList.size()]);
								
						list=(ListView)findViewById(android.R.id.list);
						LazyAdapterLastChallenges adapter=new LazyAdapterLastChallenges(this, imageUrls);
				        list.setAdapter(adapter);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/* Footer + Header Actions Button */

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
