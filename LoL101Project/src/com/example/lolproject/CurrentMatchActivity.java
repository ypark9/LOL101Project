package com.example.lolproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.lolproject.adapters.CustomListViewAdapter;
import com.example.lolproject.bean.HistoryBean;
import com.example.lolproject.dataCollecters.ChampionDataCollecter;
import com.example.lolproject.dataCollecters.ServiceHandler;

public class CurrentMatchActivity extends ListActivity {

	private String TAG_CHAMPID = "championId";
	private String TAG_TEAMID = "teamId";
	private String TAG_SUMMONERID = "summonerId";
	private String mFriendList;
	private ProgressDialog pDialog;
	private JSONArray players;
	ArrayList<HashMap<String, String>> Game_List;
	private HashMap<String, String> champion_Name_ID;
	ChampionDataCollecter CIC = new ChampionDataCollecter();
	String URL_CURRENT_GAME = "https://community-league-of-legends.p.mashape.com/api/v1.0/NA/summoner/retrieveInProgressSpectatorGameInfo/";

	// to user customized adapter.
	ListView listView;
	List<HistoryBean> rowItems;
	// fellowPlayers JSONArray
	JSONArray fellowPlayers;
	String error = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		final ListView listview = (ListView) findViewById(R.id.listview);

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
			/*
			 * Creating service handler class instance
			 */
			ServiceHandler sh = new ServiceHandler();
			String JSON_Champion_Name = sh.makeServiceCall(
					AllStaticValues.url_Champion_code, ServiceHandler.GET);
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
						.getJSONArray(AllStaticValues.CHAMPIONS);

				for (int i = 0; i < SummonerID.length(); i++) {
					JSONObject champ = SummonerID.getJSONObject(i);
					champ_Name = champ.getString(AllStaticValues.NAME);
					champ_ID = champ.getString(AllStaticValues.ID);

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
			String JSON_CurrentGame = sh.makeServiceCall(URL_CURRENT_GAME,
					ServiceHandler.MASHAPE);
			// Log.d("account ID: ", "> " + accountID);
			Log.d("output from Mashape ", ">" + JSON_CurrentGame);
			try {

				JSONObject jsonObj = new JSONObject(JSON_CurrentGame);
				if (jsonObj.has("success")) {
					Log.d("success? : ", ">" + jsonObj.getString("success"));

					error = jsonObj.getString("error");
					Log.d("error", error);

				} else {
					JSONObject gameObj = jsonObj.getJSONObject("game");
					JSONObject playersObj = gameObj
							.getJSONObject("playerChampionSelections");
					players = playersObj.getJSONArray("array");

					for (int i = 0; i < players.length(); i++) {
						String championId = "";
						String teamId = "";
						String summonerId = "";
						if (i <= 4) {
							championId = players.getJSONObject(i).getString(
									"championId");
							teamId = "Home";
							summonerId = players.getJSONObject(i).getString(
									"summonerInternalName");
						} else {
							championId = players.getJSONObject(i).getString(
									"championId");
							teamId = "Away";
							summonerId = players.getJSONObject(i).getString(
									"summonerInternalName");
						}
						String champion_Name = champion_Name_ID.get(championId);
						Log.d("id is -> ", championId + " " + teamId + " "
								+ summonerId);

						// tmp Hash Map for single contact
						HashMap<String, String> _map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						_map.put(TAG_CHAMPID, champion_Name);
						_map.put(TAG_TEAMID, teamId);
						_map.put(TAG_SUMMONERID, summonerId);

						// adding contact to contact list
						Game_List.add(_map);
					}

					// Log.d("observer is -> ", id);
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
			if (error == null) {
				rowItems = new ArrayList<HistoryBean>();
				for (int i = 0; i < Game_List.size(); i++) {
					HistoryBean hItem = new HistoryBean(CIC.champIconCollector(Game_List.get(i).get(TAG_CHAMPID)), Game_List.get(i).get(
									TAG_SUMMONERID), Game_List.get(i).get(
									TAG_TEAMID));
					rowItems.add(hItem);
				}
				
			}else {
				rowItems = new ArrayList<HistoryBean>();
				HistoryBean hItem = new HistoryBean(1, "Nobody",error);
				rowItems.add(hItem);

			}

				listView = (ListView) findViewById(R.id.listview);
				CustomListViewAdapter adapter = new CustomListViewAdapter(
						CurrentMatchActivity.this, R.layout.summoner_history,
						rowItems);
				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						int itemPostition = position;
						String summonerId = parent
								.getItemAtPosition(itemPostition).toString()
								.toLowerCase();
						// Toast.makeText(getApplicationContext(),
						// summonerId+"" + itemPostition + " " ,
						// Toast.LENGTH_SHORT).show();
						getApplicationContext().stopService(getIntent());
						Intent intent = new Intent(getApplicationContext(),
								SummonerHistoryActivity.class);
						intent.putExtra("friendlist", summonerId);
						startActivity(intent);
					}
				});

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
