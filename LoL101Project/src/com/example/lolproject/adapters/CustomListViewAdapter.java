package com.example.lolproject.adapters;

import java.util.List;

import com.example.lolproject.R;
import com.example.lolproject.bean.HistoryRowItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<HistoryRowItem> {

	Context context;

	public CustomListViewAdapter(Context context, int resourceId,
			List<HistoryRowItem> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	private class ViewHolder {
		ImageView imageview;
		TextView txtTitle;
		TextView txtDesc;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		HistoryRowItem rowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.summoner_history, null);
			holder = new ViewHolder();
			holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
			holder.imageview = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtDesc.setText(rowItem.getDesc());
		holder.txtTitle.setText(rowItem.getTitle());
		holder.imageview.setImageResource(rowItem.getImageId());
		
		return convertView;
		
	}
}
