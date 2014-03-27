package com.example.lol101project;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lolproject.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class CurrentMatchActivity extends ListActivity {

	private String mFriendList;
	private ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> Game_List;
	Champion_Info_Collector CIC = new Champion_Info_Collector();
	String URL_CURRENT_GAME = "https://community-league-of-legends.p.mashape.com/api/v1.0/NA/summoner/retrieveInProgressSpectatorGameInfo/";


	// fellowPlayers JSONArray
	JSONArray fellowPlayers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		String accountID = "";

		mFriendList = getIntent().getStringExtra("friendlist");
		Log.d(accountID, mFriendList);
		URL_CURRENT_GAME += mFriendList;
		Log.d(accountID, URL_CURRENT_GAME);
		Log.d(accountID, accountID);
		setTitle(mFriendList);

		Game_List = new ArrayList<HashMap<String, String>>();

		// Calling async task to get json
		new GetCurrentGame().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetCurrentGame extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog to inform people that Application is not
			// frozen but working.
			pDialog = new ProgressDialog(CurrentMatchActivity.this);
			pDialog.setMessage("Summoning Current Game...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ServiceHandler sh = new ServiceHandler();
			String JSON_CurrentGame = sh.makeServiceCall(URL_CURRENT_GAME,
					ServiceHandler.MASHAPE);
		   // Log.d("account ID: ", "> " + accountID);
			String id = null;
			Log.d("output from Mashape ", ">" + JSON_CurrentGame);
			try {

				JSONObject jsonObj = new JSONObject(JSON_CurrentGame);
				if (jsonObj.has("success")) {
					Log.d("success? : ", ">" + jsonObj.getString("success"));
				} else {
					JSONObject SummonerID = jsonObj
							.getJSONObject("playerCredentials");
					id = SummonerID.getString("observer");
					Log.d("observer is -> ", id);
					// String name = SummonerID.getString("name");
					// String profileIconId =
					// SummonerID.getString("profileIconId");
					// String summonerLevel =
					// SummonerID.getString("summonerLevel");
					// String revisionDate =
					// SummonerID.getString("revisionDate");
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				FriendListActivity.class);
		startActivity(intent);
		finish();
	}

}
