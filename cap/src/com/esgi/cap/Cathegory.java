package com.esgi.cap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import apiCap.RequestTask;
import common.JsonReadStream;
import common.MyApp;
import common.onClickButton;
import dataStorage.disconnect;
import dataStorage.disconnectManager;

public class Cathegory<T> extends ListActivity implements View.OnClickListener{

	private static final int MENU_DISCONNECT = Menu.FIRST;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cathegory);
		disconnect.checkUserConnexion(this);
		
		MyApp.setContext(this);
		MyApp.setCathegoryList("");

		try {
			initListView();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	private void initListView() throws JSONException
	{
		try {
			new RequestTask().execute("cathegory").get();
			if(MyApp.getCathegoryList().equals("")){}else{
				String jsonData = JsonReadStream.stringToJSONString(MyApp.getCathegoryList(), "data");
				ArrayList<HashMap<String, String>> dataList = JsonReadStream.jsonCathegoryParsing(jsonData, "data");
				ListView list = (ListView) findViewById(android.R.id.list);
				SimpleAdapter arrayAdapter = new SimpleAdapter(this, dataList, R.layout.cathegory_row, new String[]{"id", "name"}, new int[]{R.id.IMG_CELL, R.id.TEXT_CELL});
		        list.setAdapter(arrayAdapter);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id){
		catDisplayAction(v);
	}
	
	public void catDisplayAction(View view){
		ListView lv = getListView();
		int position = lv.getPositionForView(view);
		ListAdapter data = lv.getAdapter();
		
		String idString = ((HashMap<String, String>) data.getItem(position)).get("id");
		String nameString = ((HashMap<String, String>) data.getItem(position)).get("name");

		Intent intent = new Intent(getApplicationContext(), CathegoryChallenge.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("id", idString);
		intent.putExtra("name", nameString);
		startActivity(intent);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
