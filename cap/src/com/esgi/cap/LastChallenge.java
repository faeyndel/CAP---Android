package com.esgi.cap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import apiCap.RequestTask;
import camera.LazyAdapterLastChallenges;
import common.JsonReadStream;
import common.MyApp;
import common.onClickButton;
import dataStorage.disconnect;
import dataStorage.disconnectManager;

public class LastChallenge extends Activity implements OnClickListener {

	private static final int MENU_DISCONNECT = Menu.FIRST;
	public String[] imageUrls;
	LazyAdapterLastChallenges adapter;
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.last_challenge);
		
		disconnect.checkUserConnexion(this);

		MyApp.setContext(this);
		MyApp.setCathegoryList("");

		
		
		if(MyApp.getCurUserId() == null){
			try {
				new RequestTask().execute("getUserID", MyApp.getToken()).get();
			} catch (InterruptedException e1) { e1.printStackTrace(); } catch (ExecutionException e1) { e1.printStackTrace(); }
		}

		/*setTextDisplay.setFonts(this, R.id.profilChallengeNumber, "GROBOLD.ttf");
		setTextDisplay.setFonts(this, R.id.profilUserName, "GROBOLD.ttf");*/

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
			new RequestTask().execute("lastUserChallenge").get();
			if(MyApp.getCathegoryList().equals("false")){}else{
				ArrayList<HashMap<String, String>> dataChallengeList  = JsonReadStream.jsonLastChallengeParsing(MyApp.getCathegoryList(), "challenge");

				list = (ListView) findViewById(android.R.id.list);

				ArrayList<String> stringArrayList = new ArrayList<String>();

				for (int i = 0; i < dataChallengeList.size(); i++) {
					stringArrayList.add(dataChallengeList.get(i).get("image").toString());
					stringArrayList.add(dataChallengeList.get(i).get("username").toString());
					/*stringArrayList.add(dataChallengeList.get(i).get("countCap").toString());
					stringArrayList.add(dataChallengeList.get(i).get("countPasCap").toString());
					stringArrayList.add(dataChallengeList.get(i).get("createdAt").toString());*/
					//stringArrayList.add(dataChallengeList.get(i).get("author").toString());
					stringArrayList.add(dataChallengeList.get(i).get("description").toString());
				}
				imageUrls = stringArrayList.toArray(new String[stringArrayList.size()]);

				list=(ListView)findViewById(android.R.id.list);
				adapter=new LazyAdapterLastChallenges(this, imageUrls);
				list.setAdapter(adapter);

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
		View child = lv.getChildAt(position);
		ImageButton cap = (ImageButton) child.findViewById(R.id.capButton);
		ImageButton uncap = (ImageButton) child.findViewById(R.id.uncapButton);
		//Log.v("__capv__", cap.getVisibility()+"");
		cap.setVisibility(View.GONE);
		uncap.setVisibility(View.VISIBLE);
	}

	public void uncapLaunch(View view) {
		Log.v("__uncap__", "start uncap");
		ListView lv = (ListView) findViewById(android.R.id.list);
		int position = lv.getPositionForView(view);
		View newView = lv.getAdapter().getView(position, null, lv);
		onClickButton.uncapAction(this, newView);
	}

	/* BACK BUTTON MANAGER*/

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:

				break;
			}
		}
	};


	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Voulez vous quitter CAP ?").setPositiveButton("CAP", dialogClickListener)
		.setNegativeButton("Pas CAP", dialogClickListener).show();
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
