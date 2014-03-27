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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class SummonerDataActivity extends ListActivity {

	SummonerDataSeeker SDS;
	private String mFriendList;
	private ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> Game_List;
	private HashMap<String, String> champion_Name_ID;

	/*
	 * bottom URL are for tracking recent games of "specific" user.
	 */
	private final static String url_GAME_part1 = "https://prod.api.pvp.net/api/lol/na/v1.3/game/by-summoner/";
	private final static String url_GAME_part2 = "/recent?api_key=82968033-b737-453c-aff5-b2b8c81fd7d3";

	private final static String develop_key_inu = "82968033-b737-453c-aff5-b2b8c81fd7d3";
	private final static String develop_key_rocket = "feef8114-1b45-4b48-8e58-56847d1b5fc6";
	private final static String develop_key_rantol = "2a7807f1-3d3d-42bf-acab-970da47031a7";
	private final static String develop_key_rybink = "";

	public ArrayList<String> key_array;

	private int currently_covered_game_number = 1;
	/*
	 * URL for tracking champion name by champion ID
	 */
	private String url_Champion_code = "https://prod.api.pvp.net/api/lol/na/v1.1/champion?api_key="
			+ develop_key_inu;
	private String Game_URL = "";
	private String url_forFinding_AccoundID = "";
	private String TAG_GAMES = "games";
	private String TAG_GAME_ID = "gameId";
	private String TAG_GAME_MODE = "gameMode";
	private String TAG_GAME_SUBTYPE = "subType";
	// private String TAG_GAME_TYPE = "gameType";
	private String TAG_FELLOWPLAYERS = "fellowPlayers";
	private String TAG_CHAMPID = "championId";
	private String TAG_TEAMID = "teamId";
	private String TAG_SUMMONERID = "summonerId";
	private JSONArray games;
	// fellowPlayers JSONArray
	JSONArray fellowPlayers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		String accountID = "";

		mFriendList = getIntent().getStringExtra("friendlist");
		Log.d(accountID, mFriendList);
		url_forFinding_AccoundID = "https://prod.api.pvp.net/api/lol/na/v1.3/summoner/by-name/"
				+ mFriendList + "?api_key=" + develop_key_inu;
		Log.d(accountID, accountID);
		setTitle(mFriendList);

		Game_List = new ArrayList<HashMap<String, String>>();

	try {
		GetDevelopKeyArray(key_array, develop_key_rantol, develop_key_rocket, develop_key_inu);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		// Calling async task to get json
		new GetHistory().execute();
	}

	public ArrayList<String> GetDevelopKeyArray(ArrayList<String> arraylist,
			String key1, String key2, String key3) throws Exception {

		arraylist = new ArrayList<String>();
		arraylist.add(key1);
		arraylist.add(key2);
		arraylist.add(key3);


		if (arraylist.isEmpty()) {
			throw new Exception();
		} else {
			Log.d("key Array list >", arraylist.toString());
			return key_array;
		}

	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetHistory extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog to inform people that Application is not
			// frozen but working.
			pDialog = new ProgressDialog(SummonerDataActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			/*
			 * Creating service handler class instance
			 */
			ServiceHandler sh = new ServiceHandler();
			String JSON_Champion_Name = sh.makeServiceCall(url_Champion_code,
					ServiceHandler.GET);
			/*
			 * YP: initialize Hash map for champion's name using RIOT API to
			 * find current available champions from JSON result and save those
			 * into Hash map for using it later to output its name instead of
			 * just champion id since people cannot understand just champion ID.
			 * (ex: 1 for Annie, but 1 means nothing to people)
			 */
			champion_Name_ID = new HashMap<String, String>();
			String champ_Name = "";
			String champ_ID = "";
			try {
				JSONObject json_Champion_code = new JSONObject(
						JSON_Champion_Name);
				JSONArray SummonerID = json_Champion_code
						.getJSONArray("champions");

				for (int i = 0; i < SummonerID.length(); i++) {
					JSONObject champ = SummonerID.getJSONObject(i);
					champ_Name = champ.getString("name");
					champ_ID = champ.getString("id");

					champion_Name_ID.put(champ_ID, champ_Name);
					Log.d("champ name ", champ_Name);
				}
				// String name = SummonerID.getString("name");
				// String profileIconId = SummonerID.getString("profileIconId");
				// String summonerLevel = SummonerID.getString("summonerLevel");
				// String revisionDate = SummonerID.getString("revisionDate");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String JSON_AccountID = sh.makeServiceCall(
					url_forFinding_AccoundID, ServiceHandler.GET);
			// Log.d("account ID: ", "> " + accountID);
			String id = null;
			try {
				JSONObject jsonObj = new JSONObject(JSON_AccountID);
				JSONObject SummonerID = jsonObj.getJSONObject(mFriendList);
				id = SummonerID.getString("id");
				// String name = SummonerID.getString("name");
				// String profileIconId = SummonerID.getString("profileIconId");
				// String summonerLevel = SummonerID.getString("summonerLevel");
				// String revisionDate = SummonerID.getString("revisionDate");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/*
			 * Y_P : Making a request to URL and getting response about RECENT
			 * GAMES after that process is just same with above section for
			 * Champion names
			 */
			Game_URL = url_GAME_part1 + id + url_GAME_part2;

			String jsonStr = sh.makeServiceCall(Game_URL, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj_gameInfo = new JSONObject(jsonStr);

					/*
					 * Getting JSON Array node
					 */
					games = jsonObj_gameInfo.getJSONArray(TAG_GAMES);

					Log.d("fist json array", games.length() + "");
					/*
					 * looping through All Contacts
					 */
					// TODO need to figure out how to get more game from API
					// Since current API allows only 10 queries in 10 seconds.
					for (int i = 0; i < currently_covered_game_number; i++) {

						String gameID = games.getJSONObject(i)
								.getString(TAG_GAME_ID).toString();
						String gameMode = games.getJSONObject(i)
								.getString(TAG_GAME_MODE).toString();
						String subType = games.getJSONObject(i)
								.getString(TAG_GAME_SUBTYPE).toString();
						Log.d("gameID, Mode, subype", gameID + ", " + gameMode
								+ "");

						HashMap<String, String> Game_Information = new HashMap<String, String>();
						JSONArray fellowPlayers = games.getJSONObject(i)
								.getJSONArray(TAG_FELLOWPLAYERS);

						Log.d("fellowPlayers json array",
								fellowPlayers.length() + "");

						for (int j = 0; j < fellowPlayers.length(); j++) {

							JSONObject d = fellowPlayers.getJSONObject(j);
							String championId = d.getString(TAG_CHAMPID);
							String champion_Name = champion_Name_ID
									.get(championId);
							// Log.d("champid", champion_Name);
							String teamId = d.getString(TAG_TEAMID);

							/*
							 * to Show which member was in players team, and who
							 * was in the enemy team
							 */
							if (teamId.equalsIgnoreCase("100")) {
								teamId = "Home";
							} else {
								teamId = "Enemy";
							}
							String summonerId = d.getString(TAG_SUMMONERID);

							Log.d("SummonerID, TeamID, ChampID", summonerId
									+ ", " + teamId + ", " + champion_Name);

							String URL_Find_Summoner_Name = "https://prod.api.pvp.net/api/lol/na/v1.3/summoner/"
									+ summonerId
									+ "?api_key="
									+ develop_key_rocket;

							String JSON_Summoner_Name = sh.makeServiceCall(
									URL_Find_Summoner_Name, ServiceHandler.GET);

							JSONObject json_obj_accoundID = new JSONObject(
									JSON_Summoner_Name);

							JSONObject json_id = json_obj_accoundID
									.getJSONObject(summonerId);
							String summoner_Name = json_id.getString("name");

							// tmp Hash Map for single contact
							HashMap<String, String> players = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							players.put(TAG_CHAMPID, champion_Name);
							players.put(TAG_TEAMID, teamId);
							players.put(TAG_SUMMONERID, summoner_Name);

							// adding contact to contact list
							Game_List.add(players);
						}
					}

				} catch (JSONException e) {

					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(SummonerDataActivity.this,
					Game_List, R.layout.player_info_list, new String[] {
							TAG_SUMMONERID, TAG_TEAMID, TAG_CHAMPID },
					new int[] { R.id.SUMMONERID, R.id.TEAMID, R.id.CHAMPID });

			setListAdapter(adapter);
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
