package com.example.lol101project;

import java.util.List;
import com.example.lolproject.R;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendListAdapter extends ArrayAdapter<FriendList> {
	private Context mContext;
	private List<FriendList> mShoppingList;

	public FriendListAdapter(Context context, List<FriendList> objects) {
		super(context, R.layout.shopping_list_row, objects);
		this.mContext = context;
		this.mShoppingList = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
			convertView = mLayoutInflater.inflate(R.layout.shopping_list_row, null);
		}

		FriendList friendList = mShoppingList.get(position);

		TextView descriptionView = (TextView) convertView
				.findViewById(R.id.shopping_list_name);

		descriptionView.setText(friendList.getName());

		if (friendList.isCompleted()) {
			descriptionView.setPaintFlags(descriptionView.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			descriptionView.setPaintFlags(descriptionView.getPaintFlags()
					& (~Paint.STRIKE_THRU_TEXT_FLAG));
		}

		return convertView;
	}

}
