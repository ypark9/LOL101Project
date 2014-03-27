package com.example.lol101project;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Champion_Info_Collector {

	public static HashMap<String, String> champion_Name_ID;
	private final static String url_Champion_code = "https://prod.api.pvp.net/api/lol/na/v1.1/champion?api_key=82968033-b737-453c-aff5-b2b8c81fd7d3";

	public HashMap<String, String> champ_info() {
		/*
		 * Creating service handler class instance
		 */
		ServiceHandler sh = new ServiceHandler();
		String JSON_Champion_Name = sh.makeServiceCall(url_Champion_code,
				ServiceHandler.GET);
		/*
		 * YP: initialize Hash map for champion's name using RIOT API to find
		 * current available champions from JSON result and save those into Hash
		 * map for using it later to output its name instead of just champion id
		 * since people cannot understand just champion ID. (ex: 1 for Annie,
		 * but 1 means nothing to people)
		 */
		champion_Name_ID = new HashMap<String, String>();
		String champ_Name = "";
		String champ_ID = "";
		try {
			JSONObject json_Champion_code = new JSONObject(JSON_Champion_Name);
			JSONArray SummonerID = json_Champion_code.getJSONArray("champions");

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

		return champion_Name_ID;
	}

}
