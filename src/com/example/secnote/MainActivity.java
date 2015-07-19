package com.example.secnote;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String DEBUGTAG = "HBT";
	public static final String NOTENAME = "note.save";
	public static final String Key_FILESAVED = "FileSaved";
	public static final String RESET_CHECKPOINTS = "RESET_CHECKPOINTS";
	public static final int CAPTURE_IMAGE = 3;
	public static final int IMAGE_CAPTURED = 4;
	public static final int BROWSGALLARY = 7;
	public static final String IMAGE_NAME = "CHECKPOINT_IMAGE";
	public static final String CUSTOM_IMAGE = "CUMTOM_IMAGE";
	public static final String SHARED_PREFERENCE = "SHARED_PREFERENCE";
	public static final String PROGRAM_PERMISSION = "PROGRAM_PERMISSION";

	private File imageFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);

		if (prefs.getBoolean(Key_FILESAVED, false)) {
			loadSavedFile();
		}

		Toast.makeText(MainActivity.this, "Access Granted.", Toast.LENGTH_LONG)
				.show();
	}

	// Saving file
	private void Save() {
		Log.d(DEBUGTAG, "Save button clicked");

		EditText edit_text = (EditText) findViewById(R.id.editText);
		String text = edit_text.getText().toString();

		try {
			FileOutputStream fos = openFileOutput(NOTENAME,
					Context.MODE_PRIVATE);
			fos.write(text.getBytes());
			fos.close();

			SharedPreferences prefs = getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean(Key_FILESAVED, true);
			editor.commit();

			Toast.makeText(this, "Text Saved", Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(MainActivity.this, R.string.toasts_save_error,
					Toast.LENGTH_LONG).show();
			Log.d(DEBUGTAG, "Unable to save file.");
		}
	}

	// Loading file
	private void loadSavedFile() {
		try {
			String line;
			FileInputStream fis = openFileInput(NOTENAME);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new DataInputStream(fis)));
			EditText edit_text = (EditText) findViewById(R.id.editText);

			while ((line = reader.readLine()) != null) {
				edit_text.append(line);
				edit_text.append("\n");
			}
			fis.close();

		} catch (Exception e) {
			Log.d(DEBUGTAG, "Unable to read file/no save file");
			Toast.makeText(MainActivity.this, R.string.toasts_load_error,
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Lock() {
		Intent i = new Intent(MainActivity.this, ImageActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionbar_lock:
			Lock();
			return true;
		case R.id.actionbar_save:
			Save();
			return true;
		case R.id.checkpoint_reset:
			Intent i = new Intent(this, ImageActivity.class);

			i.putExtra(RESET_CHECKPOINTS, true);

			startActivity(i);
			return true;
		case R.id.checkpoint_setimage:

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View v = getLayoutInflater().inflate(R.layout.replaceimage, null);

			builder.setTitle(R.string.replace_dig);
			builder.setView(v);

			final AlertDialog dig = builder.create();
			dig.show();

			Button takePhoto = (Button) dig.findViewById(R.id.take_photo);
			Button choosePhoto = (Button) dig.findViewById(R.id.choose);

			// Camera Code
			takePhoto.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					File filePath = Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

					Log.d(DEBUGTAG, "File path is: " + filePath.toString());
					imageFile = new File(filePath, IMAGE_NAME);

					Intent j = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					j.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
					GivePermission();
					startActivityForResult(j, CAPTURE_IMAGE);
					Log.d(MainActivity.DEBUGTAG, "To the camera ");
				}
			});

			choosePhoto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					GivePermission();
					BrowseGallery();
					Log.d(DEBUGTAG, "Gallary Button Clicked");
				}
			});

			return true;
		default:
			return super.onMenuItemSelected(featureId, item);

		}
	}

	private void BrowseGallery() {

		Intent i = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		Log.d(DEBUGTAG, "Start Gallary for result");
		startActivityForResult(i, BROWSGALLARY);

	}

	private void GivePermission() {
		SharedPreferences permission = getSharedPreferences(SHARED_PREFERENCE,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = permission.edit();
		editor.putBoolean(PROGRAM_PERMISSION, true);
		editor.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		StringBuilder builder = new StringBuilder();
		builder.append("Request code is ");
		builder.append(requestCode);
		builder.append("\t");
		builder.append("Result code is ");
		builder.append(resultCode);

		Log.d(DEBUGTAG, builder.toString());

		if (requestCode == 0) {
			Toast.makeText(this, "Unable to Load an Image", Toast.LENGTH_LONG)
					.show();
		} else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {

			Log.d(MainActivity.DEBUGTAG, "Returned with image captured ");
			Bitmap photo = BitmapFactory
					.decodeFile(imageFile.getAbsolutePath());
			Log.d(MainActivity.DEBUGTAG, "Decoding Bitmap ... ");
			if (photo != null) {
				Log.d(MainActivity.DEBUGTAG, "Setting Photo ...  ");
				resetImage(imageFile.getAbsolutePath().toString());
			}
		} else if (requestCode == BROWSGALLARY && resultCode == RESULT_OK) {
			Log.d(DEBUGTAG, "Returned with gallary requst code");

			if (data.getData() == null) {
				Log.d(DEBUGTAG, "Empty result from gallary");
			}

			String[] columns = { MediaStore.Images.Media.DATA };
			Uri imageUri = data.getData();
			Cursor cursor = getContentResolver().query(imageUri, columns, null,
					null, null);

			cursor.moveToFirst();
			int culumnIndex = cursor.getColumnIndex(columns[0]);
			String pathToImage = cursor.getString(culumnIndex);
			Log.d(DEBUGTAG, pathToImage);

			Uri uri = Uri.parse(pathToImage);

			resetImage(uri.getPath().toString());

			// Important to close cursor to stop memory leak.
			cursor.close();

		} else {
			RevokPermission();
			Toast.makeText(this, "Unable to load image", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Save();
		SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCE,
				MODE_PRIVATE);
		if (!pref.getBoolean(PROGRAM_PERMISSION, false)) {
			Intent i = new Intent(this, ImageActivity.class);
			startActivity(i);
		}
	}

	private void resetImage(String path) {
		SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCE,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(CUSTOM_IMAGE, path);

		editor.commit();

		Intent k = new Intent(this, ImageActivity.class);
		k.putExtra(CUSTOM_IMAGE, true);
		startActivity(k);
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

	private void RevokPermission() {
		SharedPreferences permission = getSharedPreferences(
				MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);
		SharedPreferences.Editor editor = permission.edit();
		editor.putBoolean(MainActivity.PROGRAM_PERMISSION, false);
		editor.commit();
	}
}
