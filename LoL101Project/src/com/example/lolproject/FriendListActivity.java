package com.example.lolproject;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lolproject.adapters.FriendListAdapter;
import com.example.lolproject.bean.FriendListBean;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class FriendListActivity extends Activity {

	protected static final int DIALOG_ALERT = 1101;
	private EditText mFriendListInput;
	private ListView mListView;
	private FriendListAdapter mAdapter;
	private String Selected_Summoner_Name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);

		Parse.initialize(this, "xOS5VQLKI6CApi4PRL8yiEodz7ewzmMdkLwmzLZN",
				"F5Pe0zPXgN5TWbnFT0zhO1dfK0fF90sKb9aist0d");
		ParseAnalytics.trackAppOpened(getIntent());
		ParseObject.registerSubclass(FriendListBean.class);


		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}

		mAdapter = new FriendListAdapter(this, new ArrayList<FriendListBean>());

		mFriendListInput = (EditText) findViewById(R.id.friend_list_input);
		mListView = (ListView) findViewById(R.id.friend_list);
		mListView.setAdapter(mAdapter);

		final SwipeDetector swipeDetector = new SwipeDetector();

		// ListView Item Swipe Listener
		mListView.setOnTouchListener(swipeDetector);

		// ListView Item Click Listener
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FriendListBean list = mAdapter.getItem(position);
				
				if (swipeDetector.swipeDetected()) {
					// Do the onSwipe action
					confirmDelete(list);
				} else {
					Selected_Summoner_Name = list.getName();
					showDialog(DIALOG_ALERT);
//					// Do the onItemClick action
//					// Go to Item page
//					Intent intent = new Intent(getApplicationContext(), SummonerDataActivity.class);
//					intent.putExtra("friendlist", list.getName());
//					startActivity(intent);
//					finish();
				}
			}
		});

		// ListView Item Long Click Listener
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					View view, int position, long id) {
				// Show Alert
				Toast.makeText(getApplicationContext(),
						"Item Long Clicked", Toast.LENGTH_LONG).show();
				return true;
			}
		});
		
		updateData();
	}

	public void updateData() {
		ParseQuery<FriendListBean> query = ParseQuery
				.getQuery(FriendListBean.class);
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<FriendListBean>() {
			@Override
			public void done(List<FriendListBean> lists, ParseException error) {

				if (lists != null) {
					mAdapter.clear();
					for (int i = 0; i < lists.size(); i++) {
						if (lists.get(i).isCompleted() == false)
							mAdapter.add(lists.get(i));
					}
				}
			}
		});
	}

	public void createShoppingList(View v) {
		if (mFriendListInput.getText().length() > 0) {
			FriendListBean friendlist = new FriendListBean();
			friendlist.setACL(new ParseACL(ParseUser.getCurrentUser()));
			friendlist.setUser(ParseUser.getCurrentUser());
			friendlist.setName(mFriendListInput.getText().toString());
			friendlist.setCompleted(false);
			friendlist.saveEventually();
			mAdapter.insert(friendlist, 0);
			mFriendListInput.setText("");
		}
	}
	
	public void confirmDelete(final FriendListBean list) {
		AlertDialog.Builder alertDialogBuilder = 
				new AlertDialog.Builder(FriendListActivity.this);
		
		// set title
		alertDialogBuilder.setTitle("Confirm Deletion");
		
		// set dialog message
		alertDialogBuilder
				.setMessage("Do you really want to delete " + list.getName() + "?")
				.setCancelable(true)
				.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, delete
						list.setCompleted(true);
						list.saveInBackground();
						mAdapter.remove(list);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close the dialog
						dialog.cancel();
					}
				});
		
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		// show it
		alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout:
			ParseUser.logOut();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return false;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
	  switch (id) {
	    case DIALOG_ALERT:
	      Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage("What do you want to know about this summoner?");
	      builder.setCancelable(true);
	      builder.setPositiveButton("Current Game", new OkOnClickListener());
	      builder.setNegativeButton("Match History", new CancelOnClickListener());
	      AlertDialog dialog = builder.create();
	      dialog.show();
	  }
	  return super.onCreateDialog(id);
	}

	private final class CancelOnClickListener implements
	    DialogInterface.OnClickListener {
	  public void onClick(DialogInterface dialog, int which) {
			// Do the onItemClick action
			Intent intent = new Intent(getApplicationContext(), SummonerHistoryActivity.class);
			intent.putExtra("friendlist", Selected_Summoner_Name);
			startActivity(intent);
	  }
	}

	private final class OkOnClickListener implements
	    DialogInterface.OnClickListener {
	  public void onClick(DialogInterface dialog, int which) {
			// Do the onItemClick action
			Intent intent = new Intent(getApplicationContext(), CurrentMatchActivity.class);
			intent.putExtra("friendlist", Selected_Summoner_Name);
			startActivity(intent);
	  }
	}
}
