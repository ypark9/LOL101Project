package com.example.lolproject.adapters;

import java.util.List;

import com.example.lolproject.R;
import com.example.lolproject.R.id;
import com.example.lolproject.R.layout;
import com.example.lolproject.bean.FriendListBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendListAdapter extends ArrayAdapter<FriendListBean> {
	private Context mContext;
	private List<FriendListBean> mShoppingList;

	public FriendListAdapter(Context context, List<FriendListBean> objects) {
		super(context, R.layout.shopping_list_row, objects);
		this.mContext = context;
		this.mShoppingList = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
			convertView = mLayoutInflater.inflate(R.layout.shopping_list_row, null);
		}

		FriendListBean friendList = mShoppingList.get(position);

		TextView descriptionView = (TextView) convertView
				.findViewById(R.id.shopping_list_name);

		descriptionView.setText(friendList.getName());

//		if (friendList.isCompleted()) {
//			descriptionView.setPaintFlags(descriptionView.getPaintFlags()
//					| Paint.STRIKE_THRU_TEXT_FLAG);
//		} else {
//			descriptionView.setPaintFlags(descriptionView.getPaintFlags()
//					& (~Paint.STRIKE_THRU_TEXT_FLAG));
//		}

		return convertView;
	}

}
