package com.example.lolproject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.example.lolproject.adapters.CustomListViewAdapter;
import com.example.lolproject.bean.HistoryBean;
import com.example.lolproject.dataCollecters.ChampionDataCollecter;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class GoodAgainst extends Activity {
	ChampionDataCollecter CIC= new ChampionDataCollecter();
	List<HistoryBean> rowItems = new ArrayList<HistoryBean>();
    List<ParseObject> champions = ChampionList.champions;
    List<String> champs = null;
    String selectedChamp = ChampionList.selectedChamp;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for(int i = 0; i<champions.size();i++){
			if(champions.get(i).getString("Name").equalsIgnoreCase(selectedChamp)){
			
			champs=champions.get(i).getList("goodAgainst");
			}
		}
		Log.d("Test", "Array Size "+ selectedChamp);
		
	
		setContentView(R.layout.activity_item);
		for (int i = 0; i < champions.size(); i++) {
			for(int j=0; j<champs.size();j++){
				
			
				if(champions.get(i).getString("Name").equalsIgnoreCase(champs.get(j))){
					
				
				int champID = CIC.champIconCollector(champions.get(i).getString("Name").replaceAll("\\s", ""));
				String champName = champions.get(i).getString("Name");
				String champClass = champions.get(i).getString("primaryClass")+" / "+champions.get(i).getString("secondaryClass")+"\n";
				List<ParseObject> counterItem = champions.get(i).getList("counterItems");
				String counterItemString = "\nCounter Items: ";
				for(int y=0;y<counterItem.size();y++)
				{
					counterItemString+=counterItem.get(y);
					if(y!=counterItem.size()-1){
						counterItemString+=", ";
					}
				}
				champClass+=counterItemString+"\n\n";
				List<ParseObject> counterTips = champions.get(i).getList("counterTips");
				String counterTipsString = "Counter Tips: ";
				for(int y=0;y<counterTips.size();y++)
				{
					counterTipsString+=counterTips.get(y)+"\n";
					
				}
				champClass+=counterTipsString;
				HistoryBean hItem = new HistoryBean(champID,champName , champClass);
				rowItems.add(hItem);
				}
			}
		}
		ListView listView = (ListView) findViewById(R.id.listview);
		CustomListViewAdapter adapter = new CustomListViewAdapter(
				GoodAgainst.this, R.layout.summoner_history,
				rowItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int itemPostition = position;
				ChampionList.selectedChamp = parent.getItemAtPosition(itemPostition).toString().toLowerCase();
				Log.d("Champ","Selected: "+selectedChamp);
				 DialogFragment newFragment = new CounterSelect();
				 newFragment.show(getFragmentManager(), "champSelect");
//								
			}
		});	

	}
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				ChampionList.class);
		startActivity(intent);
		finish();
	}

}
