package com.example.lolproject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;

/**
 * 
 * @author Yoonsoo Park
 * 
 */
public class SummonerHistorySeeker {

	public final static String OBJECT_NOT_FOUND = "OBJECT_NOT_FOUND";
	public final static String GET_SUMMONER_BY_NAME = "https://community-league-of-legends.p.mashape.com/api/v1.0/NA/summoner/getSummonerByName/";
	public final static String MASHAPE_API_KEY_YOON = "svDylU2EvmQ9TxWxzVmFIZOO9aJq1CQP";
	/* Hashmap for ListView */
	ArrayList<HashMap<String, String>> contactList;
	String summoner_Account = "";

	public String find_Summoner(String Summoner_Name) {

		contactList = new ArrayList<HashMap<String, String>>();
//		String object = null;
		try {
			summoner_Account = new GetSummonerIDbyName().execute(Summoner_Name)
					.get();
			// object = new GetRecentGamesForPlayer().execute(summoner_Account)
			// .get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (summoner_Account != null) {
			return summoner_Account;
		} else {
			return OBJECT_NOT_FOUND;
		}
	}
	
	
	
	/**
	 * 
	 * @author Yoonsoo Park
	 * 
	 */
	private class GetSummonerIDbyName extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... arg) {
			String result = null;
			try {
				// Instantiate the custom HttpClient
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(GET_SUMMONER_BY_NAME
						+ URLEncoder.encode(arg[0], "UTF-8"));
				get.setHeader("X-Mashape-Authorization", MASHAPE_API_KEY_YOON);
				// Execute the GET call and obtain the response
				HttpResponse res = client.execute(get);
				HttpEntity responseEntity = res.getEntity();
				if (responseEntity != null) {
					result = new JSONObject(
							EntityUtils.toString(responseEntity))
							.getString("summonerId");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	}
}
