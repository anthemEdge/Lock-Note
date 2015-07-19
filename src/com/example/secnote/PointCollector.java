package com.example.secnote;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PointCollector implements OnTouchListener {

	public static final int NUM_POINTS = 4;

	private List<Point> points = new ArrayList<Point>();
	private Listener listener;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();

		String pos = String.format("Coordinates: %d, %d", x, y);
		Log.d(MainActivity.DEBUGTAG, pos);
		points.add(new Point(x, y));

		if (points.size() == NUM_POINTS) {
			if (listener != null) {
				listener.pointsCollected(this.points);
			}
		}
		return false;
	}

	public void ClearPoints() {
		points.clear();
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}
}
