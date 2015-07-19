package com.example.secnote;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ImageActivity extends Activity implements Listener {

	private PointCollector pointCollector = new PointCollector();
	private Database db = new Database(this);

	public static final String KEY_PASSPOINTS_SET = "KEY_PASSPOINTS_SET";
	private AlertDialog diglog;

	// How accurate the passpoints
	private static final int TOUCH_TOLERANCE = 80;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		ActionBar bar = getActionBar();
		bar.hide();

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Bundle extras = getIntent().getExtras();
		RevokPermission();

		if (extras != null) {

			if (extras.getBoolean(MainActivity.CUSTOM_IMAGE)) {
				Log.d(MainActivity.DEBUGTAG, "Recieved from Custom Image.");

				SharedPreferences pref = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(MainActivity.CUSTOM_IMAGE, true);
				editor.putBoolean(ImageActivity.KEY_PASSPOINTS_SET, false);
				editor.commit();
			}

			else if (extras.getBoolean(MainActivity.RESET_CHECKPOINTS)) {
				SharedPreferences prefss = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = prefss.edit();
				editor.putBoolean(KEY_PASSPOINTS_SET, false);
				editor.commit();
			}
		}

		SharedPreferences sharedPrefs = getSharedPreferences(
				MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);

		ImageView checkImage = (ImageView) findViewById(R.id.touch_image);

		if (sharedPrefs.getString(MainActivity.CUSTOM_IMAGE, null) != null) {
			Log.d(MainActivity.DEBUGTAG, "Using Custom image ...");
			String path = sharedPrefs
					.getString(MainActivity.CUSTOM_IMAGE, null);

			Log.d(MainActivity.DEBUGTAG, "Custom image has path" + path);

			Bitmap photo = BitmapFactory.decodeFile(path);
			checkImage.setImageBitmap(photo);
		} else {
			Log.d(MainActivity.DEBUGTAG, "Using Default ...");
			checkImage.setImageResource(R.drawable.image_default2);
		}

		if (!prefs.getBoolean(KEY_PASSPOINTS_SET, false)) {
			initialPrompt();
		} else {
			logoinPrompt();
		}
		addTouchListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	private void logoinPrompt() {
		Toast.makeText(this, "Enter your pass points now", Toast.LENGTH_LONG)
				.show();
	}

	private void initialPrompt() {

		AlertDialog.Builder builder = new Builder(this);
		builder.setPositiveButton("I Understand",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(ImageActivity.this,
								"Touch your desired passpaints now. ",
								Toast.LENGTH_SHORT).show();
					}
				});

		builder.setTitle("How does it work? ");
		builder.setMessage("Touch 4 points on the image in the desired order to set them as your password.");

		this.diglog = builder.create();
		diglog.show();

	}

	private void RevokPermission() {
		SharedPreferences permission = getSharedPreferences(
				MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);
		SharedPreferences.Editor editor = permission.edit();
		editor.putBoolean(MainActivity.PROGRAM_PERMISSION, false);
		editor.commit();
	}

	private android.content.DialogInterface.OnClickListener confirmation() {
		// Confirmation button, does nothing.
		return null;
	}

	private void addTouchListener() {
		ImageView image = (ImageView) findViewById(R.id.touch_image);
		pointCollector.setListener(this);
		image.setOnTouchListener(pointCollector);
	}

	private void SaveCheckPoints(final List<Point> points) {

		AlertDialog.Builder alb = new AlertDialog.Builder(this);
		alb.setMessage("Saving . . . ");
		final AlertDialog dig = alb.create();
		dig.show();

		// Thread
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				Log.d(MainActivity.DEBUGTAG,
						"Collected points: " + points.size());
				db.storePoints(points);
				List<Point> pointsLsit = db.getPoints();
				for (Point point : pointsLsit) {
					Log.d(MainActivity.DEBUGTAG, String.format(
							"Got point: (%d, %d)", point.x, point.y));
				}
				Log.d(MainActivity.DEBUGTAG, "Points saved : " + points.size());
				pointCollector.ClearPoints();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {

				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(KEY_PASSPOINTS_SET, true);
				editor.commit();
				dig.dismiss();

				logoinPrompt();

			}
		};
		task.execute();

	}

	private void VerifyCheckpoints(final List<Point> touchedPoints) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Checking points ...");

		final AlertDialog dig = builder.create();
		dig.show();

		AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {

				List<Point> savedPoints = db.getPoints();

				Log.d(MainActivity.DEBUGTAG,
						"Loaded points" + savedPoints.size());

				if (savedPoints.size() != pointCollector.NUM_POINTS
						|| touchedPoints.size() != pointCollector.NUM_POINTS) {
					return false;
				}

				for (int i = 0; i < pointCollector.NUM_POINTS; i++) {
					Point savedPoint = savedPoints.get(i);
					Point touchedPoint = touchedPoints.get(i);

					int xDiff = savedPoint.x - touchedPoint.x;
					int yDiff = savedPoint.y - touchedPoint.y;

					int diff = xDiff * xDiff + yDiff * yDiff;

					Log.d(MainActivity.DEBUGTAG, "Difference: " + diff);

					if (diff > TOUCH_TOLERANCE * TOUCH_TOLERANCE) {
						return false;
					}
				}
				return true;
			}

			@Override
			// Takes value return by doinbackground.
			protected void onPostExecute(Boolean pass) {
				dig.dismiss();
				pointCollector.ClearPoints();
				if (pass) {
					// Change to a different activity
					Intent i = new Intent(ImageActivity.this,
							MainActivity.class);
					startActivity(i);
				} else {
					Toast.makeText(ImageActivity.this,
							"Authentication failed.", Toast.LENGTH_LONG).show();
				}
			}
		};

		task.execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.checkpoint_reset) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void pointsCollected(final List<Point> points) {

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		boolean checkPointsSaved = prefs.getBoolean(KEY_PASSPOINTS_SET, false);

		if (!checkPointsSaved) {
			Log.d(MainActivity.DEBUGTAG, "Saving Check Points ...");
			this.SaveCheckPoints(points);
		} else {
			Log.d(MainActivity.DEBUGTAG, "Verifying Check Points ...");
			this.VerifyCheckpoints(points);
		}

	}

}
