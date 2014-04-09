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
import android.widget.ListView;

import com.example.lolproject.adapters.CustomListViewAdapter;
import com.example.lolproject.bean.HistoryBean;
import com.example.lolproject.dataCollecters.ChampionDataCollecter;
import com.example.lolproject.dataCollecters.ServiceHandler;
import com.example.lolproject.dataCollecters.SummonerHistoryCollecter;

public class SummonerHistoryActivity extends ListActivity {

	SummonerHistoryCollecter SDS;
	ChampionDataCollecter CIC;
	private String mFriendList;
	private ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> Game_List;
	private HashMap<String, String> champion_Name_ID;

	// to user customized adapter.
	ListView listView;
	List<HistoryBean> rowItems;

	// ArrayList that will be containing Develop Keys
	public ArrayList<String> key_array;
	private String game_URL = "";
	private String url_forFinding_AccoundID = "";
	private String TAG_GAMES = "games";
	private String TAG_GAME_ID = "gameId";
	private String TAG_GAME_MODE = "gameMode";
	// private String TAG_GAME_SUBTYPE = "subType";
	// private String TAG_GAME_TYPE = "gameType";
	private String TAG_FELLOWPLAYERS = "fellowPlayers";
	private String TAG_CHAMPID = "championId";
	private String TAG_TEAMID = "teamId";
	private String TAG_SUMMONERID = "summonerId";
	// private String TAG_CHAMPICON = "championIcon";
	private JSONArray games;
	// private JSONObject summonerOtherInfo;
	// fellowPlayers JSONArray
	JSONArray fellowPlayers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		String accountID = "";
		// getting summoner ID that we searched in previous activity comes from
		// this.
		mFriendList = getIntent().getStringExtra("friendlist");
		Log.d(accountID, mFriendList);

		url_forFinding_AccoundID = AllStaticValues.USING_LEAGUE_API_VERSION_1_3
				+ "summoner/by-name/" + mFriendList
				+ AllStaticValues.API_KEY_IS + AllStaticValues.DEVELOP_KEY_INU;
		Log.d(accountID, accountID);
		setTitle(mFriendList);

		Game_List = new ArrayList<HashMap<String, String>>();
		// to get champion Icon
		CIC = new ChampionDataCollecter();

		try {
			GetDevelopKeyArray(key_array, AllStaticValues.DEVELOP_KEY_RANTOL,
					AllStaticValues.DEVELOP_KEY_ROCKET,
					AllStaticValues.DEVELOP_KEY_INU);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Calling ASYNC task to get JSON
		new GetHistory().execute();
	}

	/**
	 * This method is for adding key to array list.
	 * 
	 * @param arraylist
	 *            array List that you want to input keys
	 * @param key1
	 *            target 1
	 * @param key2
	 *            target 2
	 * @param key3
	 *            target 3
	 * @return ArrayList that contains keys
	 * @throws Exception
	 *             if ArrayList is not properly made, exception will be thrown
	 */
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

	// // Array of integers points to images stored in /res/drawable-ldpi/
	// int[] flags = new int[] { R.drawable.aatrox, R.drawable.ahri,
	// R.drawable.akali, R.drawable.alistar, R.drawable.amumu,
	// R.drawable.anivia, R.drawable.annie, R.drawable.ashe,
	// R.drawable.blitzcrank, R.drawable.brand, R.drawable.caitlyn,
	// R.drawable.cassiopeia, R.drawable.chogath, R.drawable.corki,
	// R.drawable.darius, R.drawable.diana, R.drawable.draven,
	// R.drawable.drmundo, R.drawable.elise, R.drawable.evelynn,
	// R.drawable.ezreal, R.drawable.fiddlesticks, R.drawable.fiora,
	// R.drawable.fizz, R.drawable.galio, R.drawable.gangplank,
	// R.drawable.garen, R.drawable.gragas, R.drawable.graves,
	// R.drawable.hecarim, R.drawable.heimerdinger,
	// R.drawable.ic_launcher, R.drawable.irelia, R.drawable.janna,
	// R.drawable.jarvaniv, R.drawable.jax, R.drawable.jayce,
	// R.drawable.jinx, R.drawable.karma, R.drawable.karthus,
	// R.drawable.kassadin, R.drawable.katarina, R.drawable.kayle,
	// R.drawable.kennen, R.drawable.khazix, R.drawable.kogmaw,
	// R.drawable.leblanc, R.drawable.leesin, R.drawable.leona,
	// R.drawable.lissandra, R.drawable.lol101logo, R.drawable.lucian,
	// R.drawable.lulu, R.drawable.lux, R.drawable.malphite,
	// R.drawable.malzahar, R.drawable.maokai, R.drawable.masteryi,
	// R.drawable.missfortune, R.drawable.monkeyking,
	// R.drawable.mordekaiser, R.drawable.morgana, R.drawable.nami,
	// R.drawable.nasus, R.drawable.nautilus, R.drawable.nidalee,
	// R.drawable.nocturne, R.drawable.nunu, R.drawable.olaf,
	// R.drawable.orianna, R.drawable.pantheon, R.drawable.poppy,
	// R.drawable.quinn, R.drawable.rammus, R.drawable.renekton,
	// R.drawable.rengar, R.drawable.riven, R.drawable.rumble,
	// R.drawable.ryze, R.drawable.sejuani, R.drawable.shaco,
	// R.drawable.shen, R.drawable.shyvana, R.drawable.singed,
	// R.drawable.sion, R.drawable.sivir, R.drawable.skarner,
	// R.drawable.sona, R.drawable.soraka, R.drawable.swain,
	// R.drawable.syndra, R.drawable.talon, R.drawable.taric,
	// R.drawable.teemo, R.drawable.thresh, R.drawable.tristana,
	// R.drawable.trundle, R.drawable.tryndamere, R.drawable.twistedfate,
	// R.drawable.twitch, R.drawable.udyr, R.drawable.urgot,
	// R.drawable.varus, R.drawable.vayne, R.drawable.veigar,
	// R.drawable.vi, R.drawable.viktor, R.drawable.vladimir,
	// R.drawable.volibear, R.drawable.warwick, R.drawable.xerath,
	// R.drawable.xinzhao, R.drawable.yasuo, R.drawable.yorick,
	// R.drawable.zac, R.drawable.zed, R.drawable.ziggs,
	// R.drawable.zilean, R.drawable.zyra };

	/**
	 * ASYNC task class to get JSON by making HTTP call
	 * */
	private class GetHistory extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog to inform people that Application is not
			// frozen but working.
			pDialog = new ProgressDialog(SummonerHistoryActivity.this);
			pDialog.setMessage("History...");
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

			String JSON_AccountID = sh.makeServiceCall(
					url_forFinding_AccoundID, ServiceHandler.GET);
			// Log.d("account ID: ", "> " + accountID);
			String id = null;
			try {
				JSONObject jsonObj = new JSONObject(JSON_AccountID);
				JSONObject SummonerID = jsonObj.getJSONObject(mFriendList);
				id = SummonerID.getString(AllStaticValues.ID);
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
			game_URL = AllStaticValues.URL_GAME_PART1 + id
					+ AllStaticValues.URL_GAME_PART2;

			String jsonStr = sh.makeServiceCall(game_URL, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj_gameInfo = new JSONObject(jsonStr);

					/*
					 * Getting JSON Array node
					 */
					games = jsonObj_gameInfo.getJSONArray(TAG_GAMES);
					Log.d("fist json array,", games.length() + ", ");
					/*
					 * looping through All Contacts
					 */
					// TODO need to figure out how to get more game from API
					// Since current API allows only 10 queries in 10 seconds.
					for (int i = 0; i < AllStaticValues.GAME_CAN_BE_HANDLED; i++) {

						String gameID = games.getJSONObject(i)
								.getString(TAG_GAME_ID).toString();
						String gameMode = games.getJSONObject(i)
								.getString(TAG_GAME_MODE).toString();
						String thisSummonerChamp = games.getJSONObject(i)
								.getString(TAG_CHAMPID).toString();
						String thisSummonerTeam = games.getJSONObject(i)
								.getString(TAG_TEAMID).toString();
						// String subType = games.getJSONObject(i)
						// .getString(TAG_GAME_SUBTYPE).toString();
						Log.d("gameID, Mode, champion, team", gameID + ", "
								+ gameMode + ", " + thisSummonerChamp + ", "
								+ thisSummonerTeam);

						// HashMap<String, String> Game_Information = new
						// HashMap<String, String>();
						JSONArray fellowPlayers = games.getJSONObject(i)
								.getJSONArray(TAG_FELLOWPLAYERS);

						Log.d("fellowPlayers json array",
								fellowPlayers.length() + "");

						int totalGameMember = fellowPlayers.length() + 1;
						for (int j = 0; j < totalGameMember; j++) {

							String championId = "";
							String teamId = "";
							String summonerId = "";
							if (j == totalGameMember - 1) {
								summonerId = mFriendList;
								championId = thisSummonerChamp;
								teamId = thisSummonerTeam;
							} else {
								JSONObject d = fellowPlayers.getJSONObject(j);
								// Log.d("champid", champion_Name);
								teamId = d.getString(TAG_TEAMID);
								championId = d.getString(TAG_CHAMPID);
								summonerId = d.getString(TAG_SUMMONERID);
							}
							String champion_Name = champion_Name_ID
									.get(championId);

							/*
							 * to Show which member was in players team, and who
							 * was in the enemy team
							 */
							if (teamId.equalsIgnoreCase("100")) {
								teamId = "Home";
							} else {
								teamId = "Enemy";
							}

							String summoner_Name = "";
							if (j != totalGameMember - 1) {

								String URL_Find_Summoner_Name = AllStaticValues.USING_LEAGUE_API_VERSION_1_3
										+ "summoner/"
										+ summonerId
										+ AllStaticValues.API_KEY_IS
										+ AllStaticValues.DEVELOP_KEY_ROCKET;

								String JSON_Summoner_Name = sh.makeServiceCall(
										URL_Find_Summoner_Name,
										ServiceHandler.GET);

								JSONObject json_obj_accoundID = new JSONObject(
										JSON_Summoner_Name);

								JSONObject json_id = json_obj_accoundID
										.getJSONObject(summonerId);
								summoner_Name = json_id.getString("name");
							} else if (j == totalGameMember - 1) {
								summoner_Name = summonerId;
							} else {
								System.exit(2);
							}
							// log checking..
							Log.d("SummonerID, TeamID, ChampID", summonerId
									+ ", " + teamId + ", " + champion_Name);

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

			rowItems = new ArrayList<HistoryBean>();
			for (int i = 0; i < Game_List.size(); i++) {
				HistoryBean hItem = new HistoryBean(
						CIC.champIconCollector(Game_List.get(i)
								.get(TAG_CHAMPID)), Game_List.get(i).get(
								TAG_SUMMONERID), Game_List.get(i).get(
								TAG_TEAMID));
				rowItems.add(hItem);
			}

			listView = (ListView) findViewById(R.id.listview);
			CustomListViewAdapter adapter = new CustomListViewAdapter(
					SummonerHistoryActivity.this, R.layout.summoner_history,
					rowItems);
			listView.setAdapter(adapter);

			// // Keys used in HashMap
			// String[] from = { "TAG_CHAMPICON", "TAG_SUMMONERID", "TAG_TEAMID"
			// };
			//
			// // Ids of views in listview_layout
			// int[] to = { R.id.flag, R.id.txt, R.id.cur};
			//
			// // Instantiating an adapter to store each items
			// // R.layout.listview_layout defines the layout of each item
			// SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
			// Game_List, R.layout.summoner_history, from, to);
			//
			// // Getting a reference to listview of main.xml layout file
			/**
			 * ListView listView = ( ListView ) findViewById(R.id.listview);
			 * 
			 * //Updating parsed JSON data into ListView
			 * 
			 * ListAdapter adapter = new SimpleAdapter(
			 * SummonerHistoryActivity.this, Game_List,
			 * R.layout.player_info_list, new String[] { TAG_SUMMONERID,
			 * TAG_TEAMID, TAG_CHAMPID }, new int[] { R.id.SUMMONERID,
			 * R.id.TEAMID, R.id.CHAMPID });
			 * 
			 * setListAdapter(adapter);
			 */

			// listView.setAdapter(adapter);
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
