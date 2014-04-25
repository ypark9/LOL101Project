package com.example.lolproject;



import java.util.ArrayList;
import java.util.List;

import com.example.lolproject.adapters.CustomListViewAdapter;
import com.example.lolproject.bean.FriendListBean;
import com.example.lolproject.bean.HistoryBean;
import com.example.lolproject.dataCollecters.ChampionDataCollecter;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.lolproject.adapters.CustomListViewAdapter;



public class ChampionList extends ListActivity {
	
	ChampionDataCollecter CIC= new ChampionDataCollecter();
	List<HistoryBean> rowItems = new ArrayList<HistoryBean>();
	public static List<ParseObject> champions = null;
	public static String selectedChamp = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "8WNQbZjHZoEy3aPwgohudCGP4pRNTVljWdvHqdOx", "5tNaLUd1h1VB0nJE8pQr2MD0dO89uruTu8d7VmN4");
		setContentView(R.layout.activity_item);
		if(champions==null)
		{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Character");
		query.orderByAscending("Name");
		query.setLimit(200);
		List<ParseObject> champList = null;
		try {
			champList = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		champions=champList;
		for (int i = 0; i < champList.size(); i++) {
			int champID = CIC.champIconCollector(champList.get(i).getString("Name").replaceAll("\\s", ""));
			String champName = champList.get(i).getString("Name");
			String champClass = champList.get(i).getString("primaryClass")+" / "+champList.get(i).getString("secondaryClass");
			HistoryBean hItem = new HistoryBean(champID,champName , champClass);
			rowItems.add(hItem);
		}
		}else{
			for (int i = 0; i < champions.size(); i++) {
				int champID = CIC.champIconCollector(champions.get(i).getString("Name").replaceAll("\\s", ""));
				String champName = champions.get(i).getString("Name");
				String champClass = champions.get(i).getString("primaryClass")+" / "+champions.get(i).getString("secondaryClass");
				HistoryBean hItem = new HistoryBean(champID,champName , champClass);
				rowItems.add(hItem);
			}

		}
			Log.d("Champions",""+champions.size());
		ListView listView = (ListView) findViewById(R.id.listview);
		CustomListViewAdapter adapter = new CustomListViewAdapter(
				ChampionList.this, R.layout.summoner_history,
				rowItems);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int itemPostition = position;
				selectedChamp = parent.getItemAtPosition(itemPostition).toString().toLowerCase();
				Log.d("Champ","Selected: "+selectedChamp);
				 DialogFragment newFragment = new CounterSelect();
				 newFragment.show(getFragmentManager(), "champSelect");
//				getApplicationContext().stopService(getIntent());
//				Intent intent = new Intent(getApplicationContext(), SummonerHistoryActivity.class);
//				intent.putExtra("friendlist", summonerId);
//				startActivity(intent);
				
			}
		});	
		
	}
	

	
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				HomePageActivity.class);
		startActivity(intent);
		finish();
	}



}
