package com.example.lolproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.lolproject.SimpleGestureFilter.SimpleGestureListener;

public class HomePageActivity extends Activity implements SimpleGestureListener {

	private SimpleGestureFilter detector;

	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page_list);

		// Detect touched area
		detector = new SimpleGestureFilter(HomePageActivity.this, this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		String str = "";
		// TODO Auto-generated method stubString str = "";
		switch (direction) {
		case SimpleGestureFilter.SWIPE_RIGHT:
			str = "Swipe Right";
			intent = new Intent(this, FriendListActivity.class);
			startActivity(intent);
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			str = "Swipe Left";
			break;
		case SimpleGestureFilter.SWIPE_DOWN:
			str = "Swipe Down";
			break;
		case SimpleGestureFilter.SWIPE_UP:
			str = "Swipe Up";
			break;
		}
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDoubleTap() {
		Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}

}
